package com.maxtrain.bootcamp.invoiceline;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface InvoicelineRepository extends CrudRepository<Invoiceline, Integer> {
	Optional<Invoiceline> findByInvoiceId(int invoiceId);
	Optional<Invoiceline> findByProductId(int productId);
}
