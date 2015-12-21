package com.mainapp.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mainapp.model.Account;
import com.mainapp.model.PurchaseLC;
import com.mainapp.model.Schedule;
import com.mainapp.model.SingleMessage;
import com.mainapp.modelws.Purchase;
import com.mainapp.modelws.User;

@Controller
public class PurchaseController {

	@Autowired
	private SessionFactory sessionFactory;
	
	@RequestMapping(value = "/purchase/confirmation", method = RequestMethod.GET)
	public String confirmation(@RequestParam(value = "schedule_id", required = true) int scheduleId,
								Model model, HttpSession session) {
		
		Client c = ClientBuilder.newClient();
		String url = "http://localhost:3000/servico_empresa_aerea/webresources/schedule/" + scheduleId;
		Schedule schedule = c.target(url).request(MediaType.APPLICATION_JSON).get(Schedule.class);
				
		model.addAttribute("schedule", schedule);
		
		if(session.getAttribute("user") != null) { 
			return "purchase.confirmation";
		} else {
			return "authentication.login";
		}
	}
	
	@RequestMapping(value = "/purchase/make", method = RequestMethod.POST)
	public String makePurchase(@RequestParam(value = "schedule_id") int scheduleId,
								@RequestParam(value = "agency") int agency,
								@RequestParam(value = "account") int account,
								HttpSession session,
								Model model) {
		
		String errorMessage = "";
		
		User user = (User) session.getAttribute("user");
		
		Client clientSchedule = ClientBuilder.newClient();
		String urlSchedule = "http://localhost:3000/servico_empresa_aerea/webresources/schedule/" + scheduleId;
		Schedule schedule = clientSchedule.target(urlSchedule).request(MediaType.APPLICATION_JSON).get(Schedule.class);
		
		Client clientAccountVerification = ClientBuilder.newClient();
		String urlBalanceVerification = "http://localhost:3000/servico_banco/webresources/account/balanceverification/account/" + account + "/agency/" + agency + "/price/" + schedule.getPrice();
		SingleMessage messageVerification = clientAccountVerification.target(urlBalanceVerification).request(MediaType.APPLICATION_JSON).get(SingleMessage.class);

		if(messageVerification.getCode() == Account.OK) {
			String urlBalanceUpdate = "http://localhost:3000/servico_banco/webresources/account/balanceupdate";
			Client clientAccountUpdate = ClientBuilder.newClient();
			WebTarget targetAccountUpdate = clientAccountUpdate.target(urlBalanceUpdate);
			
			Form form = new Form();
			form.param("account", String.valueOf(account));
			form.param("agency", String.valueOf(agency));
			form.param("price", String.valueOf(schedule.getPrice()));
			form.param("operation", String.valueOf(Account.DEBT));
			
			SingleMessage messageUpdate = targetAccountUpdate.request(MediaType.APPLICATION_JSON).put(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), SingleMessage.class);
			
			if(messageUpdate.getCode() == Account.OK) {
				//Criando a compra no WS
				String urlMakePurchase = "http://localhost:3000/servico_empresa_aerea/webresources/purchase/save";		
				Client clientPurchase = ClientBuilder.newClient();
				WebTarget targetPurchase = clientPurchase.target(urlMakePurchase);
				
				Form purchaseParams = new Form();
				purchaseParams.param("schedule_id", String.valueOf(scheduleId));
				purchaseParams.param("client_id", String.valueOf(user.getId()));
				purchaseParams.param("account", String.valueOf(account));
				purchaseParams.param("agency", String.valueOf(agency));
				
				Purchase purchaseWS = targetPurchase.request(MediaType.APPLICATION_JSON).post(Entity.entity(purchaseParams, MediaType.APPLICATION_FORM_URLENCODED_TYPE), Purchase.class);
				
				//Criando a compra no banco local
				PurchaseLC purchaseLC = new PurchaseLC();
				purchaseLC.setClientId(user.getId());
				purchaseLC.setScheduleId(scheduleId);
				purchaseLC.setPrice(schedule.getPrice());
				purchaseLC.setCreateAt(purchaseWS.getCreatedAt());
				
				Session sessionDb = sessionFactory.openSession();
				Transaction t = sessionDb.beginTransaction();
	
				try {
					sessionDb.save(purchaseLC);				
					t.commit();
	
					sessionDb.flush();
					sessionDb.close();
				} catch (Exception ex) {
					ex.printStackTrace();
					t.rollback();
				}
				
				return "redirect:/mypurchases";
			}
		} else if(messageVerification.getCode() == Account.INVALID_DATA) {
			errorMessage = "Dados bancários inválidos.";
		} else {
			errorMessage = "Saldo insuficiente.";
		}
		
		model.addAttribute("errorMessage", errorMessage);
		model.addAttribute("schedule", schedule);
		model.addAttribute("schedule_id", schedule.getId());
		
		return "purchase.confirmation";
	}
	
	@RequestMapping(value = "/mypurchases", method = RequestMethod.GET)
	public String myPurchases(Model model, HttpSession session) {
		
		User user = (User) session.getAttribute("user");
		
		Client c = ClientBuilder.newClient();
		String url = "http://localhost:3000/servico_empresa_aerea/webresources/purchase/client/" + user.getId();
		List<Purchase> purchases = c.target(url).request(MediaType.APPLICATION_JSON).get(new GenericType<List<Purchase>>() {});

		model.addAttribute("purchases", purchases);
		
		return "purchase.my";
	}
	
	@RequestMapping(value = "/purchase/cancel", method = RequestMethod.GET)
	public String cancel(@RequestParam(value = "id", required = true) int id,
								Model model) {
		
		String urlCancelPurchase = "http://localhost:3000/servico_empresa_aerea/webresources/purchase/cancel";
		Client clientCancelPurchase = ClientBuilder.newClient();
		WebTarget targetCancelPurchase = clientCancelPurchase.target(urlCancelPurchase);
		
		Form formCancelPurchase = new Form();
		formCancelPurchase.param("id", String.valueOf(id));
		
		Purchase purchase = targetCancelPurchase.request(MediaType.APPLICATION_JSON).put(Entity.entity(formCancelPurchase, MediaType.APPLICATION_FORM_URLENCODED_TYPE), Purchase.class);
		
		String urlCreditAccount = "http://localhost:3000/servico_banco/webresources/account/balanceupdate";
		Client clientCreditAccount = ClientBuilder.newClient();
		WebTarget targetCreditAccount = clientCancelPurchase.target(urlCreditAccount);
		
		Form formBalanceUpdate = new Form();
		formBalanceUpdate.param("account", String.valueOf(purchase.getAccount()));
		formBalanceUpdate.param("agency", String.valueOf(purchase.getAgency()));
		formBalanceUpdate.param("price", String.valueOf(purchase.getSchedule().getPrice()));
		formBalanceUpdate.param("operation", String.valueOf(2));
		
		SingleMessage messageBalanceUpdate = targetCreditAccount.request(MediaType.APPLICATION_JSON).put(Entity.entity(formBalanceUpdate, MediaType.APPLICATION_FORM_URLENCODED_TYPE), SingleMessage.class);
		
		return "redirect:/mypurchases";
	}	
}
