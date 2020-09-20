package csku.atm.controller.service;


import csku.atm.model.Customer;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    private ArrayList<Customer> customerList;


    @PostConstruct
    public void postConstruct(){
        this.customerList = new ArrayList<>();
    }

    public void createCustomer(Customer customer){
        //hash pin for customer
        String hashPin = hash(customer.getPin());
        customer.setPin(hashPin);
        customerList.add(customer);
    }

    public List<Customer> getCustomers(){
        return new ArrayList<>(this.customerList);
    }

    private String hash(String pin) {
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(pin,salt);
    }

    public Customer findCustomer(int id){
        for(Customer customer:customerList){
            if(customer.getId() == id){
                return customer;
            }
        }
        return null;
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
