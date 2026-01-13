package com.example.inventory_service.service;

import com.example.inventory_service.entity.Event;
import com.example.inventory_service.entity.Venue;
import com.example.inventory_service.repository.EventRepository;
import com.example.inventory_service.repository.VenueRepository;
import com.example.inventory_service.responce.EventInventoryResponse;
import com.example.inventory_service.responce.VenueInventoryResponce;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;

    public List<EventInventoryResponse> getAllEvents(){
        final List<Event> events = eventRepository.findAll();

        return events.stream().map(event -> EventInventoryResponse.builder()
                .event(event.getName())
                .capacity(event.getLeftCapacity())
                .venue(event.getVenue())
                .build()).collect(Collectors.toList());
    }

    public VenueInventoryResponce getVenueInformation(Long venueId){
        Venue venue = venueRepository.findById(venueId).orElse(null);
        return VenueInventoryResponce.builder()
                .venueId(venue.getId())
                .venueName(venue.getName())
                .totalCapacity(venue.getTotalCapacity())
                .build();
    }

    public EventInventoryResponse getEventInventory(Long eventId){
        Event event = eventRepository.findById(eventId).orElse(null);
        if(event == null){
            throw new RuntimeException("Event not found");
        }

        return EventInventoryResponse.builder()
                .event(event.getName())
                .capacity(event.getLeftCapacity())
                .venue(event.getVenue())
                .ticketPrice(event.getTicketPrice())
                .eventId(event.getId())
                .build();

    }
}
