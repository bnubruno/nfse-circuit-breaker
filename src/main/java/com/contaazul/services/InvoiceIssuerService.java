package com.contaazul.services;

import java.time.Duration;
import java.util.Random;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import com.contaazul.entities.Invoice;

@Service
@AllArgsConstructor
public class InvoiceIssuerService {

	private UnavailableServerMockService cityService;

	public Long send(String cityName, Invoice invoice) {
		mockUnavailableServerByIbgeCode( cityName );
		return request( invoice );
	}

	private long request(Invoice invoice) {
		System.out.println( "Sending invoiceId: " + invoice.getInvoiceId() );
		long result = new Random().nextLong();
		System.out.println( "Received NFS-e number: " + result + " for invoiceId: " + invoice.getInvoiceId() );
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
