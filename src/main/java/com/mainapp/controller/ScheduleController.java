package com.mainapp.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mainapp.configuration.Definition;
import com.mainapp.model.Autocomplete;
import com.mainapp.model.City;
import com.mainapp.model.Schedule;
import com.mainapp.util.Extras;

@Controller
public class ScheduleController {

	@Autowired
	private SessionFactory sessionFactory;
	
	@RequestMapping(value = "/schedules", method = RequestMethod.GET)
	public String index(@RequestParam(value = "start_destination_id", required = true) int startDestinationId,
					@RequestParam(value = "end_destination_id", required = true) int endDestinationId,
					@RequestParam(value = "start_date", required = false) String startDateParam, 
					Model model) {	
		
        Locale BRAZIL = new Locale("pt","BR"); 
		Client c = ClientBuilder.newClient();
		
		String urlSchedule = Definition.FLIGHT_COMPANY_URI + "schedule/";
		String paramsUrl = "start/" + startDestinationId + "/end/" + endDestinationId + "/" + Extras.brDateToUs(startDateParam); 
		List<Schedule> schedules = c.target(urlSchedule + paramsUrl).request(MediaType.APPLICATION_JSON).get(new GenericType<List<Schedule>>() {});
		
		String urlCity = Definition.FLIGHT_COMPANY_URI + "city/";
		City startDestination = c.target(urlCity + startDestinationId).request(MediaType.APPLICATION_JSON).get(City.class);
		City endDestination = c.target(urlCity + endDestinationId).request(MediaType.APPLICATION_JSON).get(City.class);
		
		Map<String, List<Schedule>> agrupedSchedules = new HashMap<String, List<Schedule>>();
		
		List<String> scheduleDates = new ArrayList<String>();
		
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
		model.addAttribute("startDate", startDateParam);
		
		return "schedule.index";
	}

	@RequestMapping(value = "/loadcities", method = RequestMethod.GET)
	@ResponseBody
	public List<Autocomplete> loadCities(@RequestParam("term") String term) {				
		
		String urlCity = Definition.FLIGHT_COMPANY_URI + "city/search/" + term.trim();
		
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
	
	@RequestMapping(value = "/destinations", method = RequestMethod.GET)
	public String showDestinations(Model model) {
		
		String url = Definition.FLIGHT_COMPANY_URI + "city/alldestinations";
		Client c = ClientBuilder.newClient();
		List<City> cities = c.target(url).request(MediaType.APPLICATION_JSON).get(new GenericType<List<City>>() {});
		
		model.addAttribute("cities", cities);
		
		return "schedule.destinations";
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public String handleBadRequests(HttpServletResponse response) throws IOException {
	    return "home.index";
	}
}
