package com.mainapp.controller;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mainapp.model.Account;
import com.mainapp.model.Schedule;
import com.mainapp.model.SingleMessage;

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
		String urlBalanceUpdate = "localhost:3000/servico_banco/webresources/account/balanceupdate/";
		
		Schedule schedule = c.target(urlSchedule).request(MediaType.APPLICATION_JSON).get(Schedule.class);

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(urlBalanceUpdate).path("resource");
		
		Form form = new Form();
		form.param("account", String.valueOf(account));
		form.param("agency", String.valueOf(agency));
		form.param("price", String.valueOf(schedule.getPrice()));
		
		SingleMessage message = target.request(MediaType.APPLICATION_JSON).put(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), SingleMessage.class);
		System.out.println("IRAAAAAAAAAAAAAAAAAAAAAAAAA: " + message.getMessage());
		if(message.getCode() == Account.OK) {
			return "";
		} else if(message.getCode() == Account.INVALID_DATA) {
			errorMessage = "Dados bancários inválidos.";
		} else {
			errorMessage = "Saldo insuficiente.";
		}
		
		model.addAttribute(errorMessage);
		
		return "purchase.confirmation";
	}
}
