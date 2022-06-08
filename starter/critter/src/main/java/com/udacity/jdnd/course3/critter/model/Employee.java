package com.udacity.jdnd.course3.critter.model;

import java.time.DayOfWeek;
import java.util.Set;

import javax.persistence.*;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

@Entity
public class Employee {

	@Id
    @GeneratedValue
    @Column(name = "employee_id")
    private long id;

	@Column(name = "employee_name")
    private String employeeName;

    @ElementCollection
    private Set<EmployeeSkill> skills;

    @ManyToMany(mappedBy = "employee")
    private Set<Schedule> schedule;
    
    @ElementCollection
    private Set<DayOfWeek> daysAvailable;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Set<EmployeeSkill> getSkills() {
		return skills;
	}

	public void setSkills(Set<EmployeeSkill> skills) {
		this.skills = skills;
	}

	public Set<Schedule> getSchedule() {
		return schedule;
	}

	public void setSchedules(Set<Schedule> schedule) {
		this.schedule = schedule;
	}

	public Set<DayOfWeek> getDaysAvailable() {
		return daysAvailable;
	}

	public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
		this.daysAvailable = daysAvailable;
	}

	public void setSchedule(Set<Schedule> schedule) {
		this.schedule = schedule;
	}
}
