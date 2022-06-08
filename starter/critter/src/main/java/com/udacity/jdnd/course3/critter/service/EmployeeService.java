package com.udacity.jdnd.course3.critter.service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jdnd.course3.critter.exception.EmployeeNotFound;
import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

@Transactional
@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeDTO save(EmployeeDTO employeeDTO){
        Employee employee = convertDTOToEntity(employeeDTO);
        employee = employeeRepository.save(employee);
        return convertEntityToDTO(employee);
    }

    public EmployeeDTO getEmployeeById(Long id){
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if(optionalEmployee.isPresent()){
            return convertEntityToDTO(optionalEmployee.get());
        } else {
        	throw new EmployeeNotFound();
        }
    }

    public void setEmployeeAvailability(Long id, Set<DayOfWeek> daysAvailable){
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if(optionalEmployee.isPresent()){
            Employee employee = optionalEmployee.get();
            employee.setDaysAvailable(daysAvailable);
            employeeRepository.save(employee);
        } else {
        	throw new EmployeeNotFound();
        }
    }

    public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO employeeRequestDTO){
        Set<EmployeeSkill> employeeSkills = employeeRequestDTO.getSkills();
        DayOfWeek availability = employeeRequestDTO.getDate().getDayOfWeek();
        List<Employee> employees = employeeRepository.findAllByDaysAvailableContaining(availability);
        List<EmployeeDTO> employeeDTOs = new ArrayList<EmployeeDTO>();
        for(Employee employee : employees){
            if(employee.getSkills().containsAll(employeeSkills)){
                employeeDTOs.add(convertEntityToDTO(employee));
            }
        }
        return employeeDTOs;
    }

    private static EmployeeDTO convertEntityToDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

    private static Employee convertDTOToEntity(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }
}