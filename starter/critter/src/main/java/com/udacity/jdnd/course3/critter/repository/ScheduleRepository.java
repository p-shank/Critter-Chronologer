package com.udacity.jdnd.course3.critter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.model.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
	List<Schedule> findAllByEmployeeContaining(Employee employee);
    List<Schedule> findAllByPetsContaining(Pet pet);
    List<Schedule> findAllByPetsIn(List<Pet> pets);
}
