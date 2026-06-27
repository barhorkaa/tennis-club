package cz.hornakova.barbora.tennisclub.controller;

import cz.hornakova.barbora.tennisclub.model.dto.ReservationCreateRequest;
import cz.hornakova.barbora.tennisclub.model.dto.ReservationResponse;
import cz.hornakova.barbora.tennisclub.model.dto.ReservationUpdateRequest;
import cz.hornakova.barbora.tennisclub.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/court/{courtId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<ReservationResponse> getByCourtId(@PathVariable long courtId) {
        return reservationService.getByCourtId(courtId);
    }

    @GetMapping("/customer/{phone}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<ReservationResponse> getByCustomerPhone(
            @PathVariable String phone,
            @RequestParam(required = false, defaultValue = "false") boolean onlyFuture
    ) {
        return reservationService.getByCustomerPhone(phone, onlyFuture);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<ReservationResponse> getAll() {
        return reservationService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ReservationResponse getById(@PathVariable Long id) {
        return reservationService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ReservationResponse create(
            @Valid @RequestBody ReservationCreateRequest request
    ) {
        return reservationService.create(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ReservationResponse update(
            @PathVariable Long id,
            @Valid @RequestBody ReservationUpdateRequest request
    ) {
        return reservationService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public void delete(@PathVariable Long id) {
        reservationService.delete(id);
    }
}
