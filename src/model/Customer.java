package model;

import java.util.HashSet;
import java.util.Set;

public class Customer {
	private long id;
	private String firstname;
	private String surname;
	private int age;
	private Set<Service> services;
	private Set<Address> address;
	private Set<BorrowDetail> borrowdetails;

	public Customer() {

	}

	public Set<Service> getServices() {
		return services;
	}

	public void setServices(Set<Service> services) {
		this.services = services;
	}

	public Customer(String firstname, String surname, int age) {
		this.firstname = firstname;
		this.surname = surname;
		this.age = age;
		this.services = new HashSet<Service>();
		this.address = new HashSet<Address>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Set<Service> getService() {
		return services;
	}

	public void setService(Set<Service> service) {
		this.services = service;
	}

	public Set<Address> getAddress() {
		return address;
	}

	public void setAddress(Set<Address> address) {
		this.address = address;
	}

	public Set<BorrowDetail> getBorrowdetails() {
		return borrowdetails;
	}

	public void setBorrowdetails(Set<BorrowDetail> borrowdetail) {
		this.borrowdetails = borrowdetail;
	}

}
