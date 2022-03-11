package com.maxtrain.bootcamp.invoice;

import javax.persistence.*;

import com.maxtrain.bootcamp.vendor.Vendor;

@Entity
public class Invoice {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(columnDefinition="int")
	private int id;
	@Column(length=30, nullable=false)
	private String description;
	@Column(length=30, nullable=false)
	private String status;
	@Column(columnDefinition="decimal(9,2) not null")
	private double total;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="vendorId", columnDefinition="int")
	private Vendor vendor; // FK
	
	public Invoice() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

}
