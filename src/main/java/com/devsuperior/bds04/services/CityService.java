package com.devsuperior.bds04.services;

import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.repositories.CityRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {

    final CityRepository repository;

    public CityService(CityRepository repository) {
        this.repository = repository;
    }

    public List<CityDTO> findAll() {
        return repository.findAll(Sort.by("name")).stream().map(CityDTO::new).collect(Collectors.toList());
    }

    public CityDTO insert(CityDTO dto) {
        return new CityDTO(repository.save(new City(null, dto.getName())));
    }
}
