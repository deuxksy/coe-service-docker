package com.sds.act.coe.order.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sds.act.coe.order.domain.Customer;
import com.sds.act.coe.order.api.CustomerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private CustomerClient customerClient;

    @Autowired
    public OrderService(CustomerClient customerClient) {
        this.customerClient = customerClient;
    }

    @HystrixCommand(groupKey = "ORDER", commandKey = "[ORDER] Get Customer information")
    public List<Customer> getAllCustomer() {
        return customerClient.findAll();
    }

}
