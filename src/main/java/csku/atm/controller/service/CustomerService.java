package csku.atm.controller.service;


import csku.atm.controller.data.CustomerRepository;
import csku.atm.model.Customer;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    private CustomerRepository repository;



    public void createCustomer(Customer customer){
        //hash pin for customer
        String hashPin = hash(customer.getPin());
        customer.setPin(hashPin);
        repository.save(customer);
    }

    public List<Customer> getCustomers(){
        return repository.findAll();
    }

    private String hash(String pin) {
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(pin,salt);
    }

    public Customer findCustomer(int id){
        try {
            return repository.findById(id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    public Customer checkPin(Customer loginCustomer){
        Customer storedCustomer = findCustomer(loginCustomer.getId());
        if(storedCustomer != null){
            String hashPin = storedCustomer.getPin();

            if(BCrypt.checkpw(loginCustomer.getPin(), hashPin)){
                return storedCustomer;
            }
        }
        return null;
    }

}
