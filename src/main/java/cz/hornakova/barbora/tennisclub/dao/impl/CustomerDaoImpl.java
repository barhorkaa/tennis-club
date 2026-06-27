package cz.hornakova.barbora.tennisclub.dao.impl;

import cz.hornakova.barbora.tennisclub.dao.CustomerDao;
import cz.hornakova.barbora.tennisclub.model.entity.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class CustomerDaoImpl implements CustomerDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Customer> getByPhoneNumber(String phoneNumber) {
        List<Customer> result = em.createQuery(
                """
                   SELECT c
                   FROM Customer c
                   WHERE c.phoneNumber = :phoneNumber
                   AND c.deleted = false
                   """,
                Customer.class)
                .setParameter("phoneNumber", phoneNumber)
                .getResultList();

        return result.stream().findFirst();
    }

    @Override
    public List<Customer> getAll() {
        return em.createQuery(
                        """
                        SELECT c
                        FROM Customer c
                        WHERE c.deleted = false
                        """,
                        Customer.class)
                .getResultList();
    }

    @Override
    public Optional<Customer> getById(Long id) {
        Customer customer = em.find(Customer.class, id);

        if (customer == null || customer.isDeleted()) {
            return Optional.empty();
        }

        return Optional.of(customer);
    }

    @Override
    public Customer save(Customer customer) {

        if (customer.getId() == 0) {
            em.persist(customer);
            return customer;
        }

        return  em.merge(customer);
    }

    @Override
    public void delete(Customer customer) {
        customer.setDeleted(true);

        em.merge(customer);
    }
}
