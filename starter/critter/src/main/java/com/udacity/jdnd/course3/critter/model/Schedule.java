package com.udacity.jdnd.course3.critter.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.*;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

@Entity
public class Schedule {

	@Id
    @GeneratedValue
    @Column(name = "schedule_id")
    private long id;

    @ManyToMany(
            fetch = FetchType.LAZY)
    @JoinTable(
            name = "Employee_Schedule",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private Set<Employee> employee;

    @ManyToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinTable(
            name = "Pet_Schedule",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "pet_id"))
    private Set<Pet> pets;

    @Column(name = "schedule_date")
    private LocalDate scheduleDate;

    @ElementCollection
    private Set<EmployeeSkill> activity;

    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    public Set<Employee> getEmployee() {
        return employee;
    }
    
    public void setEmployee(Set<Employee> employee) {
        this.employee = employee;
    }

    public Set<Pet> getPets() {
        return pets;
    }
    
    public void setPets(Set<Pet> pets) {
        this.pets = pets;
    }
    
    public LocalDate getScheduleDate() {
        return scheduleDate;
    }
    
    public void setScheduleDate(LocalDate scheduleDate) {
        this.scheduleDate = scheduleDate;
    }
    
    public Set<EmployeeSkill> getActivity() {
        return activity;
    }
    
    public void setActivity(Set<EmployeeSkill> activity) {
        this.activity = activity;
    }
    
}
