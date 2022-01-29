package com.contaazul.interactions;

import static io.github.resilience4j.circuitbreaker.CircuitBreaker.State.CLOSED;
import static io.github.resilience4j.circuitbreaker.CircuitBreaker.State.OPEN;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.contaazul.Application;
import com.contaazul.entities.Invoice;
import com.contaazul.services.UnavailableServerMockService;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.collection.Stream;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = Application.class)
public class InvoiceInteractionTest {

	private static final String CITY_SP = "São Paulo";
	private static final String CITY_BLUMENAU = "Blumenau";

	@Autowired
	private InvoiceIssuerInteraction invoiceInteraction;

	@Autowired
	private UnavailableServerMockService cityService;

	@Autowired
	private CircuitBreakerRegistry registry;

	@Autowired
	private InvoiceIssuerWithoutCircuitBrakerInteraction withoutCircuitBraker;

	@Before
	public void setUp() {
		registry.circuitBreaker( CITY_BLUMENAU ).reset();
		registry.circuitBreaker( CITY_SP ).reset();
	}

	@Test
	public void whenIssuedWithCircuitBreaker() {
		CircuitBreaker blumenau = registry.circuitBreaker( CITY_BLUMENAU );
		CircuitBreaker sp = registry.circuitBreaker( CITY_SP );
		cityService.down( CITY_SP );

		runFor( this.invoiceInteraction, 50, CITY_SP );
		runFor( this.invoiceInteraction, 50, CITY_BLUMENAU );

		assertThat( sp.getState(), equalTo( OPEN ) );
		assertThat( blumenau.getState(), equalTo( CLOSED ) );
		assertThat( blumenau.getMetrics().getNumberOfSuccessfulCalls(), equalTo( 10 ) );
	}

	@Test
	public void withoutCircuitBreaker() {
		whenIssue( this.withoutCircuitBraker );
	}

	private void whenIssue(InvoiceIssuer invoiceIssuer) {
		cityService.down( CITY_SP );

		runFor( invoiceIssuer, 50, CITY_SP );
		runFor( invoiceIssuer, 50, CITY_BLUMENAU );
	}

	private void runFor(InvoiceIssuer invoiceIssuer, int times, String cityName) {
		Stream.rangeClosed( 1, times ).forEach( count -> {
			try {
				invoiceIssuer.send( cityName, buildInvoice( count.longValue() ) );
			} catch (Exception ignored) {
			}
		} );
	}

	private Invoice buildInvoice(Long invoiceId) {
		return Invoice.builder()
				.invoiceId( invoiceId )
				.build();
	}

}
