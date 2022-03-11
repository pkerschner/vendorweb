package com.maxtrain.bootcamp.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
	
	@Autowired
	private InvoiceRepository invRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<Invoice>> getInvoices() {
		var invoices = invRepo.findAll();
		return new ResponseEntity<Iterable<Invoice>>(invoices, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Invoice> getInvoice(@PathVariable int id) {
		var invoice = invRepo.findById(id);
		if(invoice.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Invoice>(invoice.get(), HttpStatus.OK);
	}
	
	@GetMapping("reviews")
	public ResponseEntity<Iterable<Invoice>> getInvoicesInReview() {
		var invoices = invRepo.findByStatus("REVIEW");
		return new ResponseEntity<Iterable<Invoice>>(invoices, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Invoice> postInvoice(@RequestBody Invoice invoice) {
		if(invoice == null || invoice.getId() != 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		invoice.setStatus("NEW");
		var inv = invRepo.save(invoice);
		return new ResponseEntity<Invoice>(inv, HttpStatus.CREATED);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("review/{id}")
	public ResponseEntity reviewInvoice(@PathVariable int id, @RequestBody Invoice invoice) {
		var statusValue = (invoice.getTotal() <= 50) ? "APPROVED" : "REVIEW";
		invoice.setStatus(statusValue);
		return putInvoice(id, invoice);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("approve/{id}")
	public ResponseEntity approveInvoice(@PathVariable int id, @RequestBody Invoice invoice) {		
		invoice.setStatus("APPROVED");
		return putInvoice(id, invoice);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("reject/{id}")
	public ResponseEntity rejectInvoice(@PathVariable int id, @RequestBody Invoice invoice) {		
		invoice.setStatus("REJECTED");
		return putInvoice(id, invoice);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putInvoice(@PathVariable int id, @RequestBody Invoice invoice) {
		if(invoice == null || invoice.getId() == 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var inv = invRepo.findById(invoice.getId());
		if(inv.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		invRepo.save(invoice);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteInvoice(@PathVariable int id) {
		var invoice = invRepo.findById(id);
		if(invoice.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		invRepo.delete(invoice.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
