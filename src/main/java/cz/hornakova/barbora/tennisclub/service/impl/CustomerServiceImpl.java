package cz.hornakova.barbora.tennisclub.service.impl;

import cz.hornakova.barbora.tennisclub.mapper.CustomerMapper;
import cz.hornakova.barbora.tennisclub.model.entity.Customer;
import cz.hornakova.barbora.tennisclub.dao.CustomerDao;
import cz.hornakova.barbora.tennisclub.model.dto.CustomerResponse;
import cz.hornakova.barbora.tennisclub.exception.CustomerNotFoundException;
import cz.hornakova.barbora.tennisclub.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;
    private final CustomerMapper mapper;

    public CustomerServiceImpl(CustomerDao customerDao, CustomerMapper mapper) {
        this.customerDao = customerDao;
        this.mapper = mapper;
    }

    @Override
    public List<CustomerResponse> getAll() {
        return customerDao.getAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public CustomerResponse getById(Long id) {
        Customer customer = customerDao.getById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));

        return mapper.toResponse(customer);
    }

    @Override
    public CustomerResponse getByPhoneNumber(String phoneNumber) {
        Customer customer = customerDao.getByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new CustomerNotFoundException(phoneNumber));

        return mapper.toResponse(customer);
    }
}
