package cz.hornakova.barbora.tennisclub.controller;

import cz.hornakova.barbora.tennisclub.auth.JwtFilter;
import cz.hornakova.barbora.tennisclub.model.dto.CustomerResponse;
import cz.hornakova.barbora.tennisclub.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
class CustomerControllerTest {

    @MockitoBean
    JwtFilter jwtFilter;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;

    @Test
    void shouldReturnAllCustomers() throws Exception {
        List<CustomerResponse> response = List.of(
                new CustomerResponse(1L, "John", "123"),
                new CustomerResponse(2L, "Jane", "456")
        );

        when(customerService.getAll()).thenReturn(response);

        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("John"));

        verify(customerService, times(1)).getAll();
    }

    @Test
    void shouldReturnCustomerById() throws Exception {
        CustomerResponse response = new CustomerResponse(1L, "John", "123");

        when(customerService.getById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/customers/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.phoneNumber").value("123"));

        verify(customerService).getById(1L);
    }

    @Test
    void shouldReturnCustomerByPhone() throws Exception {
        CustomerResponse response = new CustomerResponse(1L, "John", "123");

        when(customerService.getByPhoneNumber("123")).thenReturn(response);

        mockMvc.perform(get("/api/customers/phone/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(customerService).getByPhoneNumber("123");
    }
}