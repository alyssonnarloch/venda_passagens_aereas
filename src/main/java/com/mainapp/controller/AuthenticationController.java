package com.mainapp.controller;

import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mainapp.modelws.User;

@Controller
public class AuthenticationController {
	
	@RequestMapping("/login")
	public String login() {
		return "authentication.login";
	}
	
	@RequestMapping(value = "/authentication", method = RequestMethod.POST)
	public String authentication(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session) {
		
		String errorMessage = "";
		
		String url = "http://localhost:3000/servico_empresa_aerea/webresources/authentication/verification";
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(url);
		
		Form form = new Form();
		form.param("email", email);
		form.param("password", password);

		User user = (User) target.request(MediaType.APPLICATION_JSON).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), User.class);
		
		if(user.getId() != 0) {
			errorMessage = "Email ou senha inválidos.";
		} else {
			session.setAttribute("user", user);
		}
		
		return "purchase.confirmation";
	}
	
}
