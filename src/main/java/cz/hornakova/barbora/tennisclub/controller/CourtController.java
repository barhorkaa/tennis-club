package cz.hornakova.barbora.tennisclub.controller;

import cz.hornakova.barbora.tennisclub.model.dto.CourtRequest;
import cz.hornakova.barbora.tennisclub.model.dto.CourtResponse;
import cz.hornakova.barbora.tennisclub.service.CourtService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courts")
public class CourtController {
    
    private final CourtService courtService;

    public CourtController(CourtService courtService) {
        this.courtService = courtService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<CourtResponse> getAll() {
        return courtService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public CourtResponse getById(@PathVariable Long id) {
        return courtService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public CourtResponse create(
            @Valid @RequestBody CourtRequest request
    ) {
        return courtService.create(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public CourtResponse update(
            @PathVariable Long id,
            @Valid @RequestBody CourtRequest request
    ) {
        return courtService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        courtService.delete(id);
    }
}
