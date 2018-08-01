package com.sds.act.coe.customer.web.rest;

import com.sds.act.coe.customer.domain.Customer;
import com.sds.act.coe.customer.service.CustomerService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static com.sds.act.coe.customer.web.rest.ApiConstants.API_V1_BASE_PATH;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = API_V1_BASE_PATH + "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {
    private Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private CustomerService customerService;


    @Autowired
    private RestTemplate restTemplate;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation(value = "register customer")
    public Customer register(@RequestBody Customer customer) {
        return customerService.register(customer);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get customer")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "customer id", required = true, dataType = "int", paramType = "path")
    })
    public Customer get(@PathVariable Integer id) {
        logger.info("galled get(" + id + ")");
        Optional<Customer> customer = customerService.get(id);
        customer.ifPresent(customer1 -> customer1.add(linkTo(methodOn(this.getClass()).get(id)).withSelfRel()));
        return customer.get();
    }

    @DeleteMapping
    @ApiOperation(value = "Delete customer")
    public void delete() {
        customerService.deleteAll();
    }

    @GetMapping
    @ApiOperation(value = "get all customer")
    public List<Customer> getAll() {
        logger.info("galled getAll");
//        restTemplate.getForObject("http://localhost:17001/api/v1/orders/customer" , String.class);
        return customerService.getAll();
    }


    @GetMapping("/mq")
    @ApiOperation(value = "get data from order")
    public String mqTest() {
        logger.info("hi");
        return restTemplate.getForObject("http://localhost:17001/api/v1/orders/customer", String.class);
    }
}