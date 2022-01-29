package com.contaazul.integration;

import com.contaazul.entities.Invoice;

public interface InvoiceIssuer {

	Long send(String cityName, Invoice invoice) throws Exception;

}
