package com.mainapp.controller;

import java.net.URI;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mainapp.model.City;
import com.mainapp.model.Schedule;

@Controller
public class MainController {

	@Autowired
	private SessionFactory sessionFactory;
	
	@RequestMapping("/main")
	public String main(Model model) {
		//System.out.println("OIEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
		
		/*Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		
		Destination d = new Destination();
		d.setCityName("Papanduva");
		d.setAirportName("Unknow Airport");
		
		session.save(d);
		
		t.commit();
		
		session.flush();
		session.close();*/
		
		Client c = ClientBuilder.newClient();
		
		List<Schedule> schedules = c.target("http://localhost:3000/flightservice/webresources/schedule/all").request(MediaType.APPLICATION_JSON).get(new GenericType<List<Schedule>>() {});
		
        //Response r = c.target("http://localhost:3000/flightservice/webresources/destination/1").request(MediaType.APPLICATION_JSON).get();
        //URI selfUri = r.getLink("self").getUri();
        //System.out.println("Link: " + selfUri.toString());
        
		model.addAttribute("schedules", schedules);
		
		return "teste.index";
	}

}
