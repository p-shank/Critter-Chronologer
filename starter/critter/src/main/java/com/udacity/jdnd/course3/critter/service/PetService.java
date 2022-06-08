package com.udacity.jdnd.course3.critter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jdnd.course3.critter.exception.CustomerNotFound;
import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;

@Transactional
@Service
public class PetService {

	@Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public PetDTO save(PetDTO petDTO){
        Pet pet = convertDTOToEntity(petDTO);
        pet = petRepository.save(pet);
        if(pet.getOwner() != null){
            pet.getOwner().addPetToCustomer(pet);
            customerRepository.save(pet.getOwner());
        }
        return convertEntityToDTO(pet);
    }

    public PetDTO getPetByOwnerId(Long id){
        Optional<Pet> optionalPet = petRepository.findById(id);
        if(optionalPet.isPresent()){
            return convertEntityToDTO(optionalPet.get());
        }
        else{
            throw new NullPointerException();
        }
    }

    public List<PetDTO> getAllPets(){
        List<Pet> pets = petRepository.findAll();
        List<PetDTO> petDTO = new ArrayList<PetDTO>();
        for(Pet pet : pets){
            petDTO.add(convertEntityToDTO(pet));
        }
        return petDTO;
    }

    public List<PetDTO> getPetsByOwnerId(Long ownerId){
        Optional<Customer> optionalCustomer = customerRepository.findById(ownerId);
        if(!optionalCustomer.isPresent()){
            throw new CustomerNotFound();
        }
        List<Pet> pets = petRepository.findAllByOwner_Id(ownerId);
        List<PetDTO> petDTO = new ArrayList<PetDTO>();
        for(Pet pet : pets){
            petDTO.add(convertEntityToDTO(pet));
        }
        return petDTO;
    }


    private PetDTO convertEntityToDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        if(pet.getOwner() != null){
            petDTO.setOwnerId(pet.getOwner().getId());
        }
        return petDTO;
    }

    private Pet convertDTOToEntity(PetDTO petDTO){
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        if(petDTO.getOwnerId() != 0){
            pet.setOwner(customerRepository.findById(petDTO.getOwnerId()).get());
        }
        return pet;
    }
}
