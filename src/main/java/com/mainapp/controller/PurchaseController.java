package com.mainapp.controller;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mainapp.model.Schedule;

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
}
