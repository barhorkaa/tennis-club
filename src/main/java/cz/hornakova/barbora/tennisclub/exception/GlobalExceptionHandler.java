package cz.hornakova.barbora.tennisclub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @ExceptionHandler(ReservationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleReservationNotFound(
            ReservationNotFoundException ex
    ) {
        return ex.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity
                .badRequest()
                .body(errors);
    }

    @ExceptionHandler(ReservationCollisionException.class)
    public ResponseEntity<String> handleReservationCollision(ReservationCollisionException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleNotReadable(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body("Request body is missing or invalid");
    }

    @ExceptionHandler(InvalidReservationException.class)
    public ResponseEntity<String> handleBusiness(InvalidReservationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}