package com.sds.act.coe.customer.service;

import com.sds.act.coe.customer.domain.Customer;
import com.sds.act.coe.customer.message.Sender;
import com.sds.act.coe.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CustomerService {

    CustomerRepository customerRepository;
    Sender sender;

    public CustomerService(CustomerRepository customerRepository, Sender sender) {
        this.customerRepository = customerRepository;
        this.sender = sender;
    }

    public Customer register(Customer customer) {
        Optional<Customer> existingCustomer = customerRepository.findByName(customer.getName());
        if (existingCustomer.isPresent()) {
            throw new RuntimeException("is already exists");
        } else {
            customerRepository.save(customer);
            sender.send(customer.getEmail());
        }
        return customer;
    }

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Optional<Customer> get(Integer id) {
        return customerRepository.findById(id);
    }

    public void deleteAll() {
        customerRepository.deleteAll();
    }
}