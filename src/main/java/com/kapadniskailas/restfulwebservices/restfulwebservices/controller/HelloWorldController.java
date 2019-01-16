/**
 * 
 */
package com.kapadniskailas.restfulwebservices.restfulwebservices.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.kapadniskailas.restfulwebservices.restfulwebservices.beans.HelloWorldBean;
import com.kapadniskailas.restfulwebservices.restfulwebservices.beans.SomeBean;

/**
 * @author kailaskapadnis
 *
 */

@RestController
public class HelloWorldController {
	
	@Autowired
	private MessageSource messageSource;

	//@RequestMapping(method=RequestMethod.GET, path="hello-world")
	@GetMapping(path="/hello-world")
	public String helloWorld() {
		return "Hello World";
	}
	
	@GetMapping(path="hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("hello world.");
	}
	
	@GetMapping(path="/hello-world/{name}")
	public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello World. %s", name));
	}
	
	@GetMapping("/hello-world-internationalized")
	public String helloWorldInternationalized(@RequestHeader(name="Accept-Language", required=false) Locale locale) {
		return messageSource.getMessage("good.morning.message", null, locale);
	}
	
	@GetMapping("/hello-world-internationalized1")
	public String helloWorldInternationalized1() {
		return messageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
	}
	
	//static filtering on somebean class.
	@GetMapping("/some-bean-static-filtering")
	public SomeBean someBean() {
		return new SomeBean("field1", "field2", "field3");
	}
	
	//dynamic filtering on somebean class.
	@GetMapping("/some-bean-dynamic-filtering")
	public MappingJacksonValue retrieveSomeBean() {
		SomeBean someBean = new SomeBean("value1", "value2", "value3");
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1","field2");
		FilterProvider filters = new SimpleFilterProvider().addFilter("someBeanFilter", filter);
		MappingJacksonValue mapping = new MappingJacksonValue(someBean);
		mapping.setFilters(filters);
		return mapping;
	}
}
