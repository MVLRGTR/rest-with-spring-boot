package com.digitalmindkr.apirest.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.digitalmindkr.apirest.model.Greeting;
import java.util.concurrent.atomic.AtomicLong;


@RestController
public class GreetingController {

	private static final String template = "Hello , %s , Have nice day";
	private final AtomicLong counter = new AtomicLong();

	// http://localhost:8080/greeting?name=Leandro
	@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "Word") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
}
