package cz.hornakova.barbora.tennisclub.exception;

public class ReservationCollisionException extends RuntimeException {
    public ReservationCollisionException() {
        super("New reservation overlaps with an existing one.");
    }
}
