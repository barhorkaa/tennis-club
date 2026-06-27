package cz.hornakova.barbora.tennisclub.exception;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException(Long id) {
        super("Could not find reservation with id:" + id);
    }
}
