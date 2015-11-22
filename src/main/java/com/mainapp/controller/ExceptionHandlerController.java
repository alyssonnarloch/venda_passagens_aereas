package com.mainapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		ModelAndView model = new ModelAndView("error.page");
		
		model.addObject("errorMessage", "Requisição: " + req.getRequestURL() + "<br />Causa: " + e.getMessage());
		e.printStackTrace();
		
		return model;
	}
	
}
