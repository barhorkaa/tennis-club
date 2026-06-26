package cz.hornakova.barbora.tennisclub.controller;

import cz.hornakova.barbora.tennisclub.model.dto.SurfaceTypeCreateRequest;
import cz.hornakova.barbora.tennisclub.model.dto.SurfaceTypeResponse;
import cz.hornakova.barbora.tennisclub.model.dto.SurfaceTypeUpdateRequest;
import cz.hornakova.barbora.tennisclub.service.SurfaceTypeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/surface-types")
public class SurfaceTypeController {

    private final SurfaceTypeService surfaceTypeService;

    public SurfaceTypeController(SurfaceTypeService surfaceTypeService) {
        this.surfaceTypeService = surfaceTypeService;
    }

    @GetMapping
    public List<SurfaceTypeResponse> getAll() {
        return surfaceTypeService.getAll();
    }

    @GetMapping("/{id}")
    public SurfaceTypeResponse getById(@PathVariable Long id) {
        return surfaceTypeService.getById(id);
    }

    @PostMapping
    public SurfaceTypeResponse create(
            @RequestBody SurfaceTypeCreateRequest request
    ) {
        return surfaceTypeService.create(request);
    }

    @PutMapping("/{id}")
    public SurfaceTypeResponse update(
            @PathVariable Long id,
            @RequestBody SurfaceTypeUpdateRequest request
    ) {
        return surfaceTypeService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        surfaceTypeService.delete(id);
    }
}
