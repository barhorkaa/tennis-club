package cz.hornakova.barbora.tennisclub.mapper;

import cz.hornakova.barbora.tennisclub.model.dto.CustomerCreateRequest;
import cz.hornakova.barbora.tennisclub.model.entity.Customer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerMapperTest {

    private final CustomerMapper mapper = new CustomerMapper();

    @Test
    void toResponse_mapsCorrectly() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John");
        customer.setPhoneNumber("123");

        var response = mapper.toResponse(customer);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("John");
        assertThat(response.phoneNumber()).isEqualTo("123");
    }

    @Test
    void toEntity_mapsCorrectly() {
        CustomerCreateRequest request =
                new CustomerCreateRequest("John", "123");

        Customer entity = mapper.toEntity(request);

        assertThat(entity.getName()).isEqualTo("John");
        assertThat(entity.getPhoneNumber()).isEqualTo("123");
    }
}