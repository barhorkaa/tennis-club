package cz.hornakova.barbora.tennisclub.dao;

import cz.hornakova.barbora.tennisclub.model.entity.Customer;

import java.util.Optional;

public interface CustomerDao extends Dao<Customer> {

    Optional<Customer> getByPhoneNumber(String phoneNumber);

}
