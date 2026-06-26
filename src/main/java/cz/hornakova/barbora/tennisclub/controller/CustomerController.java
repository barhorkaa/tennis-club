package cz.hornakova.barbora.tennisclub.controller;

import cz.hornakova.barbora.tennisclub.model.dto.CustomerResponse;
import cz.hornakova.barbora.tennisclub.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerRepository) {
        this.customerService = customerRepository;
    }

    @GetMapping("/api/customers")
    List<CustomerResponse> getAll() {
        return customerService.getAll();
    }

    @GetMapping("/api/customers/id/{id}")
    CustomerResponse getById(@PathVariable Long id) {
        return customerService.getById(id);
    }

    @GetMapping("/api/customers/phone/{phoneNumber}")
    CustomerResponse getByPhoneNumber(@PathVariable String phoneNumber) {
        return customerService.getByPhoneNumber(phoneNumber);
    }

}
