package com.udacity.jdnd.course3.critter.model;

import java.util.Set;
import javax.persistence.*;
import com.udacity.jdnd.course3.critter.pet.PetType;

@Entity
public class Pet {

	@Id
    @GeneratedValue
    @Column(name = "pet_id")
    private long id;

	@Column(name = "pet_name")
	private String petName;
    
	@Column(name = "pet_type")
	private PetType petType;

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Customer owner;

//	@ManyToMany(mappedBy = "pets")
//	private Set<Schedule> schedule;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public PetType getPetType() {
		return petType;
	}

	public void setPetType(PetType petType) {
		this.petType = petType;
	}

	public Customer getOwner() {
		return owner;
	}

	public void setOwner(Customer owner) {
		this.owner = owner;
	}

//	public Set<Schedule> getSchedule() {
//		return schedule;
//	}
//
//	public void setSchedule(Set<Schedule> schedule) {
//		this.schedule = schedule;
//	}
}
