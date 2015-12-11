package com.mainapp.controller;

import java.util.Date;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

import org.hibernate.Query;
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
import com.mainapp.model.Purchase;
import com.mainapp.model.Schedule;
import com.mainapp.model.SingleMessage;
import com.mainapp.model.User;

@Controller
public class PurchaseController {

	@Autowired
	private SessionFactory sessionFactory;
	
	@RequestMapping(value = "/purchase/confirmation", method = RequestMethod.GET)
	public String confirmation(@RequestParam(value = "schedule_id", required = true) int scheduleId,
								Model model) {
		Client c = ClientBuilder.newClient();
		
		String url = "http://localhost:3000/servico_empresa_aerea/webresources/schedule/" + scheduleId;
		
		Schedule schedule = c.target(url).request(MediaType.APPLICATION_JSON).get(Schedule.class);
				
		model.addAttribute("schedule", schedule);
		
		return "purchase.confirmation";
	}
	
	@RequestMapping(value = "/purchase/make", method = RequestMethod.POST)
	public String makePurchase(@RequestParam(value = "schedule_id") int scheduleId,
								@RequestParam(value = "agency") int agency,
								@RequestParam(value = "account") int account,
								Model model) {
		
		String errorMessage = "";
		
		Client c = ClientBuilder.newClient();
		
		String urlSchedule = "http://localhost:3000/servico_empresa_aerea/webresources/schedule/" + scheduleId;
		String urlBalanceUpdate = "http://localhost:3000/servico_banco/webresources/account/balanceupdate";
		
		Schedule schedule = c.target(urlSchedule).request(MediaType.APPLICATION_JSON).get(Schedule.class);

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(urlBalanceUpdate);
		
		Form form = new Form();
		form.param("account", String.valueOf(account));
		form.param("agency", String.valueOf(agency));
		form.param("price", String.valueOf(schedule.getPrice()));
		form.param("operation", String.valueOf(Account.DEBT));
		
		SingleMessage message = target.request(MediaType.APPLICATION_JSON).put(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), SingleMessage.class);
		
		if(message.getCode() == Account.OK) {
			User user = new User();
			user.setId(1);
			user.setName("Alysson Narloch");
			user.setPassword("123456");
			
			Purchase clientPurchase = new Purchase();
			clientPurchase.setClient(user);
			clientPurchase.setAccount(account);
			clientPurchase.setAgency(agency);
			clientPurchase.setStartDestinationId(schedule.getStartDestination().getId());
			clientPurchase.setEndDestinationId(schedule.getEndDestination().getId());
			clientPurchase.setStartAt(schedule.getStartAt());
			clientPurchase.setEndAt(schedule.getEndAt());
			clientPurchase.setPrice(schedule.getPrice());
			clientPurchase.setStatus(Purchase.EFFECTED);
			clientPurchase.setCreateAt(new Date());
			clientPurchase.setStartDestinationName(schedule.getStartDestination().getCityName() + " (" + schedule.getStartDestination().getAirportName() + ")");
			clientPurchase.setEndDestinationName(schedule.getEndDestination().getCityName() + " (" + schedule.getEndDestination().getAirportName() + ")");
			
			Session session = sessionFactory.openSession();
			Transaction t = session.beginTransaction();

			try {
				session.save(clientPurchase);				
				t.commit();

				session.flush();
				session.close();
			} catch (Exception ex) {
				ex.printStackTrace();
				t.rollback();
			}
			
			return "redirect:/mypurchases";
		} else if(message.getCode() == Account.INVALID_DATA) {
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
	public String myPurchases(Model model) {
		
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		
		try {
			String sql = "FROM Purchase WHERE client_id = :clientId";
			
			Query query = session.createQuery(sql);
			query.setInteger("clientId", 1);
			
			List<Purchase> purchases = query.list();
		
			model.addAttribute("purchases", purchases);
			
			session.flush();
			session.close();
		} catch(Exception ex) {
			ex.printStackTrace();
			t.rollback();
		}

		return "purchase.my";
	}
}
