package com.udacity.jdnd.course3.critter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jdnd.course3.critter.exception.CustomerNotFound;
import com.udacity.jdnd.course3.critter.exception.PetNotFound;
import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;

@Transactional
@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetRepository petRepository;

    public CustomerDTO save(CustomerDTO customerDTO){
        Customer customer = convertDTOToEntity(customerDTO);
        customer = customerRepository.save(customer);
        return convertEntityToDTO(customer);
    }

    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> customerDTOs = new ArrayList<CustomerDTO>();
        for(Customer customer : customers){
            customerDTOs.add(convertEntityToDTO(customer));
        }
        return customerDTOs;
    }

    public CustomerDTO getCustomerByPet(Long petId){
        Optional<Pet> optionalPet = petRepository.findById(petId);
        if(optionalPet.isPresent()){
            Optional<Customer> optionalCustomer = customerRepository.findById(optionalPet.get().getOwner().getId());
            if(optionalCustomer.isPresent()){
                return convertEntityToDTO(optionalCustomer.get());
            } else {
            	throw new CustomerNotFound();
            }
        } else {
        	throw new PetNotFound();
        }
    }

    private CustomerDTO convertEntityToDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        if(customer.getPets() != null){
            List<Long> petIds = new ArrayList<Long>();
            for (Pet pet: customer.getPets()) {
            	petIds.add(pet.getId());
            }
            customerDTO.setPetIds(petIds);
        }
        return customerDTO;
    }

    private Customer convertDTOToEntity(CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        if(customerDTO.getPetIds() != null){
            List<Pet> pets = new ArrayList<Pet>();
            for (Long petId : customerDTO.getPetIds()) {
            	pets.add(petRepository.findById(petId).get());
            }
            customer.setPets(pets);
        }
        return customer;
    }
}