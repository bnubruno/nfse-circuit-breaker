
package com.contaazul.integration;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.contaazul.entities.Invoice;
import com.contaazul.services.InvoiceIssuerService;

@Slf4j
@Service
@AllArgsConstructor
public class InvoiceIssuerWithoutCircuitBrakerInteraction implements InvoiceIssuer {

	private static final String SENDING_ERROR_MESSAGE = "An error ocurred on sending nfs-e: ";

	private InvoiceIssuerService invoiceService;

	public Long send(String cityName, Invoice invoice) {
		try {
			return invoiceService.send( cityName, invoice );
		} catch (Exception e) {
			log.error( SENDING_ERROR_MESSAGE, e );
			throw e;
		}
	}

}
