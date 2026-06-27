package cz.hornakova.barbora.tennisclub.exception;

public class SurfaceTypeNotFoundException extends RuntimeException {

    public SurfaceTypeNotFoundException(Long id) {
        super("Could not find surface type with id: " + id);
    }

}
