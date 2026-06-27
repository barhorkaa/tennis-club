package cz.hornakova.barbora.tennisclub.controller;

import cz.hornakova.barbora.tennisclub.model.dto.CustomerResponse;
import cz.hornakova.barbora.tennisclub.service.CustomerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    List<CustomerResponse> getAll() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        return customerService.getAll();
    }

    @GetMapping("/id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    CustomerResponse getById(@PathVariable Long id) {
        return customerService.getById(id);
    }

    @GetMapping("/phone/{phoneNumber}")
    @PreAuthorize("hasRole('ADMIN')")
    CustomerResponse getByPhoneNumber(@PathVariable String phoneNumber) {
        return customerService.getByPhoneNumber(phoneNumber);
    }

}
