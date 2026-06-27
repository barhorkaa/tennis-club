package cz.hornakova.barbora.tennisclub.exception;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(Long id) {
        super("Could not find customer with id: " + id);
    }

    public CustomerNotFoundException(String phoneNumber) {
        super("Could not find employee with phone number: " + phoneNumber);
    }
}
