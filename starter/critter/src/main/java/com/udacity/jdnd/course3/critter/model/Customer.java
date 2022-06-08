package com.udacity.jdnd.course3.critter.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Customer {

	@Id
    @GeneratedValue
    @Column(name = "customer_id")
    private long id;

	@Column(name = "customer_name")
    private String customerName;

	@Column(name = "contact_number")
    private String contactNumber;

    @OneToMany(mappedBy = "owner",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Pet> pets;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public List<Pet> getPets() {
		return pets;
	}

	public void setPets(List<Pet> pets) {
		this.pets = pets;
	}
	
	public void addPetToCustomer(Pet pet) {
        if(pets == null){
            pets = new ArrayList<Pet>();
        }
        pets.add(pet);
    }
}
