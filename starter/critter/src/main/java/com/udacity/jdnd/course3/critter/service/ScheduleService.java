package com.udacity.jdnd.course3.critter.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.model.Schedule;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;

@Transactional
@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CustomerRepository customerRepository;


    public ScheduleDTO save(ScheduleDTO scheduleDTO){
        Schedule schedule = convertScheduleDTOToEntity(scheduleDTO);
        schedule = scheduleRepository.save(schedule);
        return convertEntityToScheduleDTO(schedule);
    }

    public List<ScheduleDTO> getSchedules(){
        List<Schedule> schedules = scheduleRepository.findAll();
        List<ScheduleDTO> scheduleDTOs = new ArrayList<ScheduleDTO>();
        for(Schedule schedule : schedules){
            scheduleDTOs.add(convertEntityToScheduleDTO(schedule));
        }
        return scheduleDTOs;
    }

    public List<ScheduleDTO> getSchedulesByPet(Long petId){
        Optional<Pet> optionalPet = petRepository.findById(petId);
        if(optionalPet.isPresent()){
            List<Schedule> schedules = scheduleRepository.findAllByPetsContaining(optionalPet.get());
            List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
            for(Schedule schedule : schedules) {
                scheduleDTOs.add(convertEntityToScheduleDTO(schedule));
            }
            return scheduleDTOs;
        } else {
            throw new NullPointerException();
        }
    }

    public List<ScheduleDTO> getSchedulesByEmployee(Long employeeId){
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if(optionalEmployee.isPresent()){
            List<Schedule> schedules = scheduleRepository.findAllByEmployeeContaining(optionalEmployee.get());
            List<ScheduleDTO> scheduleDTOs = new ArrayList<ScheduleDTO>();
            for(Schedule schedule : schedules) {
                scheduleDTOs.add(convertEntityToScheduleDTO(schedule));
            }
            return scheduleDTOs;
        } else {
            throw new NullPointerException();
        }
    }

    public List<ScheduleDTO> getSchedulesByCustomer(Long customerId){
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if(optionalCustomer.isPresent()){
            List<Pet> pets = optionalCustomer.get().getPets();
            List<Schedule> schedules = scheduleRepository.findAllByPetsIn(pets);
            List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
            for(Schedule schedule : schedules) {
                scheduleDTOs.add(convertEntityToScheduleDTO(schedule));
            }
            return scheduleDTOs;
        } else {
        	throw new NullPointerException();
        }
    }

    private ScheduleDTO convertEntityToScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        if(schedule.getPets() != null){
            List<Long> pets = new ArrayList<>();
            schedule.getPets().forEach(pet -> pets.add(pet.getId()));
            scheduleDTO.setPetIds(pets);
        }
        if(schedule.getEmployee() != null){
            List<Long> employees = new ArrayList<>();
            schedule.getEmployee().forEach(employee -> employees.add(employee.getId()));
            scheduleDTO.setEmployeeIds(employees);
        }
        return scheduleDTO;
    }

    private Schedule convertScheduleDTOToEntity(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        if(scheduleDTO.getPetIds() != null){
            Set<Pet> pets = new HashSet<Pet>();
            scheduleDTO.getPetIds().forEach(petId -> pets.add(petRepository.findById(petId).get()));
            schedule.setPets(pets);
        }
        if(scheduleDTO.getEmployeeIds() != null){
            Set<Employee> employees = new HashSet<Employee>();
            scheduleDTO.getEmployeeIds().forEach(employeeId -> employees.add(employeeRepository.findById(employeeId).get()));
            schedule.setEmployee(employees);
        }
        return schedule;
    }
}