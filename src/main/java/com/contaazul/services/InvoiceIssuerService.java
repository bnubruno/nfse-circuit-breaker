package com.contaazul.services;

import java.time.Duration;
import java.util.Random;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.contaazul.entities.Invoice;

@Slf4j
@Service
@AllArgsConstructor
public class InvoiceIssuerService {

	private static final String SENDING_MESSAGE = "Sending invoiceId: {}";
	private static final String RECEIVED_MESSAGE = "Received NFS-e number: {} for invoiceId: {}";

	private UnavailableServerMockService cityService;

	public Long send(String cityName, Invoice invoice) {
		mockUnavailableServerByIbgeCode( cityName );
		return request( invoice );
	}

	private long request(Invoice invoice) {
		log.info( SENDING_MESSAGE, invoice.getInvoiceId() );
		long result = new Random().nextLong();
		log.info( RECEIVED_MESSAGE, result, invoice.getInvoiceId() );
		return result;
	}

	private void mockUnavailableServerByIbgeCode(String cityName) {
		if (cityService.isDown( cityName )) {
			sleep( 2 );
			throw new RuntimeException();
		}
	}

	private void sleep(int seconds) {
		try {
			Thread.sleep( Duration.ofSeconds( seconds ).toMillis() );
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
