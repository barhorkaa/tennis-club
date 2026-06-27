package cz.hornakova.barbora.tennisclub.service;

import cz.hornakova.barbora.tennisclub.model.dto.CustomerResponse;

import java.util.List;

public interface CustomerService {

    List<CustomerResponse> getAll();
    CustomerResponse getById(Long id);
    CustomerResponse getByPhoneNumber(String phoneNumber);

}
