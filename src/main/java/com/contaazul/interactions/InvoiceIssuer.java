package com.contaazul.interactions;

import com.contaazul.entities.Invoice;

public interface InvoiceIssuer {

	Long send(String cityName, Invoice invoice);

}
