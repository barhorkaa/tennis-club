package cz.hornakova.barbora.tennisclub.dao;

import cz.hornakova.barbora.tennisclub.dao.impl.CustomerDaoImpl;
import cz.hornakova.barbora.tennisclub.model.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(CustomerDaoImpl.class)
@ActiveProfiles("test")
class CustomerDaoImplTest {

    @Autowired
    private CustomerDaoImpl customerDao;

    @Autowired
    private TestEntityManager em;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setName("John");
        customer.setPhoneNumber("123");
        customer.setDeleted(false);

        em.persist(customer);
        em.flush();
    }

    @Test
    void shouldFindById() {
        Optional<Customer> result = customerDao.getById(customer.getId());

        assertTrue(result.isPresent());
        assertEquals("John", result.get().getName());
    }

    @Test
    void shouldReturnEmptyWhenDeleted() {
        customer.setDeleted(true);
        em.merge(customer);
        em.flush();

        Optional<Customer> result = customerDao.getById(customer.getId());

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldFindByPhoneNumber() {
        Optional<Customer> result = customerDao.getByPhoneNumber("123");

        assertTrue(result.isPresent());
        assertEquals("123", result.get().getPhoneNumber());
    }

    @Test
    void shouldReturnAllNonDeletedCustomers() {
        List<Customer> result = customerDao.getAll();

        assertEquals(1, result.size());
    }

    @Test
    void shouldSoftDeleteCustomer() {
        customerDao.delete(customer);

        Customer deleted = em.find(Customer.class, customer.getId());

        assertTrue(deleted.isDeleted());
    }

    @Test
    void shouldSaveCustomer() {
        Customer newCustomer = new Customer();
        newCustomer.setName("Anna");
        newCustomer.setPhoneNumber("999");

        Customer saved = customerDao.save(newCustomer);

        assertNotNull(saved.getId());
    }

    @Test
    void shouldUpdateCustomer() {
        customer.setName("Updated");

        customerDao.save(customer);
        em.flush();

        Customer updated = em.find(Customer.class, customer.getId());

        assertEquals("Updated", updated.getName());
    }
}
