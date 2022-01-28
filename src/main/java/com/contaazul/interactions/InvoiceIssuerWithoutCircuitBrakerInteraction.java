
package com.contaazul.interactions;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import com.contaazul.entities.Invoice;
import com.contaazul.services.InvoiceIssuerService;

@Service
@AllArgsConstructor
public class InvoiceIssuerWithoutCircuitBrakerInteraction implements InvoiceIssuer {

	private InvoiceIssuerService invoiceService;

	public Long send(String cityName, Invoice invoice) {
		try {
			invoiceService.send( cityName, invoice );
		} catch (Exception e) {
			System.out.println( "An error ocurred on sending nfs-e: " + e );
		}
		return null;
	}

}
