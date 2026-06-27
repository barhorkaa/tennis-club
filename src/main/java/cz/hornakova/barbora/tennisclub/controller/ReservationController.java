package cz.hornakova.barbora.tennisclub.controller;

import cz.hornakova.barbora.tennisclub.model.dto.ReservationCreateRequest;
import cz.hornakova.barbora.tennisclub.model.dto.ReservationResponse;
import cz.hornakova.barbora.tennisclub.model.dto.ReservationUpdateRequest;
import cz.hornakova.barbora.tennisclub.service.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/court/{courtId}")
    public List<ReservationResponse> getByCourtId(@PathVariable long courtId) {
        return reservationService.getByCourtId(courtId);
    }

    @GetMapping("/customer/{phone}")
    public List<ReservationResponse> getByCustomerPhone(
            @PathVariable String phone,
            @RequestParam(required = false, defaultValue = "false") boolean onlyFuture
    ) {
        return reservationService.getByCustomerPhone(phone, onlyFuture);
    }

    @GetMapping
    public List<ReservationResponse> getAll() {
        return reservationService.getAll();
    }

    @GetMapping("/{id}")
    public ReservationResponse getById(@PathVariable Long id) {
        return reservationService.getById(id);
    }

    @PostMapping
    public ReservationResponse create(@RequestBody ReservationCreateRequest request) {
        return reservationService.create(request);
    }

    @PutMapping("/{id}")
    public ReservationResponse update(
            @PathVariable Long id,
            @RequestBody ReservationUpdateRequest request
    ) {
        return reservationService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        reservationService.delete(id);
    }
}
