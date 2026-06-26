package cz.hornakova.barbora.tennisclub.exception;

public class CourtNotFoundException extends RuntimeException {

    public CourtNotFoundException(Long id) {
        super("Could not find court with id: " + id);
    }

}
