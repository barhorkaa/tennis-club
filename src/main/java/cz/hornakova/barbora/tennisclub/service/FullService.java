package cz.hornakova.barbora.tennisclub.service;

import java.util.List;

public interface FullService<TCreateRequest, TUpdateRequest, TResponse> {

    List<TResponse> getAll();

    TResponse getById(Long id);

    TResponse create(TCreateRequest request);

    TResponse update(Long id, TUpdateRequest request);

    void delete(Long id);
}
