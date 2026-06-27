package cz.hornakova.barbora.tennisclub.exception.helper;

import cz.hornakova.barbora.tennisclub.exception.*;
import cz.hornakova.barbora.tennisclub.model.dto.TestDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class GlobalExceptionHandlerTestController {

    @GetMapping("/customer/id")
    public void customerById() {
        throw new CustomerNotFoundException(1L);
    }

    @GetMapping("/customer/phone")
    public void customerByPhone() {
        throw new CustomerNotFoundException("1");
    }

    @GetMapping("/court")
    public void court() {
        throw new CourtNotFoundException(123456789L);
    }

    @GetMapping("/surface")
    public void surface() {
        throw new SurfaceTypeNotFoundException(123456789L);
    }

    @GetMapping("/reservation")
    public void reservation() {
        throw new ReservationNotFoundException(1L);
    }

    @GetMapping("/collision")
    public void collision() {
        throw new ReservationCollisionException();
    }

    @GetMapping("/invalid")
    public void invalid() {
        throw new InvalidReservationException("Invalid reservation");
    }

    @PostMapping("/validation")
    public void validate(@Valid @RequestBody TestDto dto) {
        // triggers validation only
    }
}