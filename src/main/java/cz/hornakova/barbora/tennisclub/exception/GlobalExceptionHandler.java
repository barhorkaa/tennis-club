package cz.hornakova.barbora.tennisclub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleCustomerNotFound(
            CustomerNotFoundException ex
    ) {
        return ex.getMessage();
    }

    @ExceptionHandler(CourtNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleCourtNotFound(
            CourtNotFoundException ex
    ) {
        return ex.getMessage();
    }

    @ExceptionHandler(SurfaceTypeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleSurfaceTypeNotFound(
            SurfaceTypeNotFoundException ex
    ) {
        return ex.getMessage();
    }
}