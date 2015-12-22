package com.mainapp.controller;

import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mainapp.model.Schedule;
import com.mainapp.modelws.User;

@Controller
public class AuthenticationController {
	
	@RequestMapping("/login")
	public String login() {
		return "authentication.login";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/home";
	}
	
	@RequestMapping(value = "/authentication", method = RequestMethod.POST)
	public String authentication(@RequestParam(value = "email", required = true) String email, 
								@RequestParam(value = "password", required = true) String password, 
								@RequestParam(value = "schedule_id", required = false) int scheduleId, 
								Model model,
								HttpSession session) {
		
		String urlAuthentication = "http://localhost:3000/servico_empresa_aerea/webresources/authentication/verification";
		Client clientAuthentication = ClientBuilder.newClient();
		WebTarget targetAuthentication = clientAuthentication.target(urlAuthentication);
		
		Form form = new Form();
		form.param("email", email);
		form.param("password", password);
		
		User user = targetAuthentication.request(MediaType.APPLICATION_JSON).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), User.class);

		Schedule schedule = null;
		
		if(user.getId() != 0) {
			session.setAttribute("user", user);
			model.addAttribute("errorMessage", "");
			model.addAttribute("schedule", schedule);
			
			if(scheduleId > 0) {
				Client clientSchedule = ClientBuilder.newClient();
				String urlSchedule = "http://localhost:3000/servico_empresa_aerea/webresources/schedule/" + scheduleId;
				schedule = clientSchedule.target(urlSchedule).request(MediaType.APPLICATION_JSON).get(Schedule.class);

				return "purchase.confirmation";
			} else {
				return "redirect:/mypurchases";
			}
		} else {
			model.addAttribute("schedule", schedule);
			model.addAttribute("errorMessage", "Email ou senha inválidos.");
			return "authentication.login";
		}
		
	}
	
}
