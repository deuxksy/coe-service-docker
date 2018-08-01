package com.sds.act.coe.order.web.rest;

import com.sds.act.coe.order.domain.Customer;
import com.sds.act.coe.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.sds.act.coe.order.web.rest.ApiConstants.API_V1_BASE_PATH;

@RestController
@RequestMapping(value = API_V1_BASE_PATH + "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    private Logger logger = LoggerFactory.getLogger(OrderController.class);

    private OrderService orderService;


    public OrderController(@Autowired OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/customers")
    public List<Customer> getAll() {
        return orderService.getAllCustomer();
    }

    @GetMapping("/customer")
    public Customer getCustomer() {
        logger.info("called getCustomer");
        Customer customer = new Customer(1, "park", "11@gmail.com");
        return customer;
    }
}