package cz.hornakova.barbora.tennisclub.service;

import cz.hornakova.barbora.tennisclub.dao.CustomerDao;
import cz.hornakova.barbora.tennisclub.exception.CustomerNotFoundException;
import cz.hornakova.barbora.tennisclub.mapper.CustomerMapper;
import cz.hornakova.barbora.tennisclub.model.dto.CustomerResponse;
import cz.hornakova.barbora.tennisclub.model.entity.Customer;
import cz.hornakova.barbora.tennisclub.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class CustomerServiceImplTest {

    @Mock
    private CustomerDao customerDao;

    @Mock
    private CustomerMapper mapper;

    @InjectMocks
    private CustomerServiceImpl service;

    private Customer customer;
    private CustomerResponse response;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setName("John");
        customer.setPhoneNumber("123");
        customer.setDeleted(false);

        response = new CustomerResponse(
                1L,
                "John",
                "123"
        );
    }

    @Test
    void shouldReturnAllCustomers() {
        when(customerDao.getAll()).thenReturn(List.of(customer));
        when(mapper.toResponse(customer)).thenReturn(response);

        List<CustomerResponse> result = service.getAll();

        assertEquals(1, result.size());
        assertEquals("John", result.get(0).name());
    }

    @Test
    void shouldReturnCustomerById() {
        when(customerDao.getById(1L)).thenReturn(Optional.of(customer));
        when(mapper.toResponse(customer)).thenReturn(response);

        CustomerResponse result = service.getById(1L);

        assertEquals(1L, result.id());
        assertEquals("John", result.name());
    }

    @Test
    void shouldThrowExceptionWhenCustomerByIdNotFound() {
        when(customerDao.getById(1L)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class,
                () -> service.getById(1L));
    }

    @Test
    void shouldReturnCustomerByPhone() {
        when(customerDao.getByPhoneNumber("123"))
                .thenReturn(Optional.of(customer));
        when(mapper.toResponse(customer)).thenReturn(response);

        CustomerResponse result = service.getByPhoneNumber("123");

        assertEquals("123", result.phoneNumber());
    }

    @Test
    void shouldThrowExceptionWhenPhoneNotFound() {
        when(customerDao.getByPhoneNumber("999"))
                .thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class,
                () -> service.getByPhoneNumber("999"));
    }
}
