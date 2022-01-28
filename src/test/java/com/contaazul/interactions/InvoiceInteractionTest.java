package com.contaazul.interactions;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.contaazul.entities.Invoice;
import com.contaazul.services.UnavailableServerMockService;

@SpringBootTest
public class InvoiceInteractionTest {

	private static final String SP = "São Paulo";
	private static final String BLUMENAU = "Blumenau";

	@Autowired
	private InvoiceIssuerInteraction invoiceInteraction;

	@Autowired
	private UnavailableServerMockService cityService;

	@Autowired
	private InvoiceIssuerWithoutCircuitBrakerInteraction withoutCircuitBraker;

	@Test
	public void withCircuitBreaker() {
		whenIssue( this.invoiceInteraction );
	}

	@Test
	public void withoutCircuitBreaker() {
		whenIssue( this.withoutCircuitBraker );
	}

	private void whenIssue(InvoiceIssuer invoiceIssuer) {
		cityService.down( SP );
		long begin = System.currentTimeMillis();
		System.out.println( "Iniciado em: " + begin );
		runFor( invoiceIssuer, 50, SP );
		runFor( invoiceIssuer, 50, BLUMENAU );
		long end = System.currentTimeMillis();
		System.out.println( "Finalizado em: " + end );
		System.out.println( "Total: " + (end - begin) / 1000 + " segundos" );
	}

	private void runFor(InvoiceIssuer invoiceIssuer, int times, String cityName) {
		Random random = new Random();
		for (int i = 0; i < times; i++) {
			invoiceIssuer.send( cityName, buildInvoice( random.nextLong() ) );
		}
	}

	private Invoice buildInvoice(Long invoiceId) {
		return Invoice.builder()
				.invoiceId( invoiceId )
				.build();
	}

}
