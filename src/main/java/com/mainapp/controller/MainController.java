package com.mainapp.controller;

import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.catalina.connector.Request;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mainapp.model.Autocomplete;
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
	
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String main(
			@RequestParam(value = "start_destination_id", required = true) String startDestinationId,
			@RequestParam(value = "end_destination_id", required = true) String endDestinationId,
			Model model) {	
        Locale BRAZIL = new Locale("pt","BR"); 
		Client c = ClientBuilder.newClient();
		
		String urlSchedule = "http://localhost:3000/servico_empresa_aerea/webresources/schedule/";
		String paramsUrl = "start/" + startDestinationId + "/end/" + endDestinationId; 
		List<Schedule> schedules = c.target(urlSchedule + paramsUrl).request(MediaType.APPLICATION_JSON).get(new GenericType<List<Schedule>>() {});
		
		String urlCity = "http://localhost:3000/servico_empresa_aerea/webresources/city/";
		City startDestination = c.target(urlCity + startDestinationId).request(MediaType.APPLICATION_JSON).get(City.class);
		City endDestination = c.target(urlCity + endDestinationId).request(MediaType.APPLICATION_JSON).get(City.class);
		
        //Response r = c.target("http://localhost:3000/flightservice/webresources/destination/1").request(MediaType.APPLICATION_JSON).get();
        //URI selfUri = r.getLink("self").getUri();
        //System.out.println("Link: " + selfUri.toString());
		
		Map<String, List<Schedule>> agrupedSchedules = new HashMap<String, List<Schedule>>();
		
		List<String> scheduleDates = new ArrayList<String>();
		
		//DateFormat df = new SimpleDateFormat("dd/MM/yyyy");		
		DateFormat df = new SimpleDateFormat();		
		df = DateFormat.getDateInstance(DateFormat.FULL, BRAZIL);  
		
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
		model.addAttribute("startDestination", startDestination);
		model.addAttribute("endDestination", endDestination);
		
		return "teste.index";
	}

	@RequestMapping(value = "/loadcities", method = RequestMethod.GET)
	@ResponseBody
	public List<Autocomplete> loadCities(@RequestParam("term") String term) {				
		
		String urlCity = "http://localhost:3000/servico_empresa_aerea/webresources/city/search/" + term;
		
		Client c = ClientBuilder.newClient();
		
		List<City> cities = c.target(urlCity).request(MediaType.APPLICATION_JSON).get(new GenericType<List<City>>() {});
		List<Autocomplete> acCities = new ArrayList<Autocomplete>();
		
		
		for(City city: cities) {
			Autocomplete ac = new Autocomplete();
			
			ac.setId(city.getId());
			ac.setValue(city.getCityName() + " (" + city.getAirportName() + ")");
			ac.setLabel(city.getCityName() + " (" + city.getAirportName() + ")");
			
			acCities.add(ac);
		}
		
		return acCities;
	}
}
