package com.mainapp.controller;

import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
	
	@RequestMapping("/dbtest")
	public void dbtest() {
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		
		City d = new City();
		d.setCityName("Papanduva");
		d.setAirportName("Unknow Airport");
		
		session.save(d);
		
		t.commit();
		
		session.flush();
		session.close();
	}
	
	@RequestMapping("/main")
	public String main(Model model) {		
		Client c = ClientBuilder.newClient();
		
		List<Schedule> schedules = c.target("http://localhost:3000/servico_empresa_aerea/webresources/schedule/start/1/end/1").request(MediaType.APPLICATION_JSON).get(new GenericType<List<Schedule>>() {});
		
        //Response r = c.target("http://localhost:3000/flightservice/webresources/destination/1").request(MediaType.APPLICATION_JSON).get();
        //URI selfUri = r.getLink("self").getUri();
        //System.out.println("Link: " + selfUri.toString());
		
		Map<String, List<Schedule>> agrupedSchedules = new HashMap<String, List<Schedule>>();
		
		List<String> scheduleDates = new ArrayList<String>();
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");		
		
		for(Schedule s : schedules) {		
			String startDate = df.format(s.getStartAt());
			if(!scheduleDates.contains(startDate)) {
				scheduleDates.add(startDate);
			}
		}

		for(String dateText : scheduleDates) {
			List<Schedule> schedulesByDate = new ArrayList<Schedule>();
			Iterator<Schedule> iterator = schedules.iterator();
			
			while(iterator.hasNext()) {
				Schedule s = iterator.next();
				String startDate = df.format(s.getStartAt());
				
				if(dateText.equals(startDate)) {
					schedulesByDate.add(s);
					iterator.remove();

				}
				agrupedSchedules.put(dateText, schedulesByDate);
			}			
		}			
		
		model.addAttribute("agrupedSchedules", agrupedSchedules);
		
		return "teste.index";
	}

}
