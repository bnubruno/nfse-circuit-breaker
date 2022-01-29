package com.contaazul.controllers;

import static java.lang.String.format;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.contaazul.entities.Invoice;
import com.contaazul.integration.InvoiceIssuerInteraction;
import com.contaazul.services.UnavailableServerMockService;

@RestController
@AllArgsConstructor
public class InvoiceIssuerController {

	private UnavailableServerMockService mockService;
	private InvoiceIssuerInteraction invoiceIssuerInteraction;

	@PostMapping("/issue/{cityName}/{invoiceId}")
	public Long issue(@PathVariable("cityName") String cityName,
			@PathVariable("invoiceId") Long invoiceId) throws Exception {
		return invoiceIssuerInteraction.send( cityName, new Invoice( invoiceId ) );
	}

	@PostMapping("/{cityName}/up")
	public String up(@PathVariable("cityName") String cityName) {
		mockService.up( cityName );
		return format( "City %s is available", cityName );
	}

	@PostMapping("/{cityName}/down")
	public String down(@PathVariable("cityName") String cityName) {
		mockService.down( cityName );
		return format( "City %s is unavailable", cityName );
	}

}
