package com.contaazul.integration;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.contaazul.entities.Invoice;
import com.contaazul.services.InvoiceIssuerService;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

@Slf4j
@Service
@AllArgsConstructor
public class InvoiceIssuerInteraction implements InvoiceIssuer {

	private static final String SENDING_ERROR_MESSAGE = "An error ocurred on sending nfs-e: ";

	private CircuitBreakerRegistry circuitBreakerRegistry;
	private InvoiceIssuerService invoiceService;

	public Long send(String cityName, Invoice invoice) throws Exception {
		try {
			return circuitBreakerRegistry
					.circuitBreaker( cityName )
					.decorateCallable( () -> invoiceService.send( cityName, invoice ) )
					.call();
		} catch (Exception e) {
			log.error( SENDING_ERROR_MESSAGE, e );
			throw e;
		}
	}

}
