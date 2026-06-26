package cz.hornakova.barbora.tennisclub.mapper;

import cz.hornakova.barbora.tennisclub.model.dto.CustomerCreateRequest;
import cz.hornakova.barbora.tennisclub.model.dto.CustomerResponse;
import cz.hornakova.barbora.tennisclub.model.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getPhoneNumber()
        );
    }

    public Customer toEntity(CustomerCreateRequest request) {
        return new Customer(
                request.name(),
                request.phoneNumber()
        );
    }
}
