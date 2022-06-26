package com.devsuperior.bds04.services;

import com.devsuperior.bds04.dto.EventDTO;
import com.devsuperior.bds04.entities.Event;
import com.devsuperior.bds04.repositories.CityRepository;
import com.devsuperior.bds04.repositories.EventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    final EventRepository eventRepository;
    final CityRepository cityRepository;

    public EventService(EventRepository repository, CityRepository cityRepository) {
        this.eventRepository = repository;
        this.cityRepository = cityRepository;
    }

    public Page<EventDTO> findAll(Pageable pageable) {
        return eventRepository.findAll(pageable).map(EventDTO::new);
    }

    public EventDTO insert(EventDTO dto) {
        var city = cityRepository.findById(dto.getCityId()).orElseThrow(() -> new RuntimeException("City not Found"));
        return new EventDTO(eventRepository.save(new Event(null, dto.getName(), dto.getDate(), dto.getUrl(), city)));
    }
}
