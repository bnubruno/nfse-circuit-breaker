package com.contaazul.interactions;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import com.contaazul.entities.Invoice;
import com.contaazul.services.InvoiceIssuerService;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

@Service
@AllArgsConstructor
public class InvoiceIssuerInteraction implements InvoiceIssuer {

	private CircuitBreakerRegistry circuitBreakerRegistry;
	private InvoiceIssuerService invoiceService;

	public Long send(String cityName, Invoice invoice) {
		try {
			return circuitBreakerRegistry
					.circuitBreaker( cityName )
					.decorateCallable( () -> invoiceService.send( cityName, invoice ) )
					.call();
		} catch (Exception e) {
			System.out.println( "An error ocurred on sending nfs-e: " + e );
		}
		return null;
	}

}
