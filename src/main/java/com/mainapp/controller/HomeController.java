package com.mainapp.controller;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mainapp.configuration.Definition;
import com.mainapp.model.City;

@Controller
public class HomeController {

	@RequestMapping("/home")
	public String index(Model model) {

		String url = Definition.FLIGHT_COMPANY_URI + "city/alldestinations";
		Client c = ClientBuilder.newClient();
		
		List<City> cities = c.target(url).request(MediaType.APPLICATION_JSON).header(Definition.AUTH_HEADER, Definition.AUTH_TOKEN_FLIGHT).get(new GenericType<List<City>>() {});
		
		model.addAttribute("numDestinations", cities.size());
		
		return "home.index";
	}
	
}
