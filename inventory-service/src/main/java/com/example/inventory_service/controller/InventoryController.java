package com.example.inventory_service.controller;

import com.example.inventory_service.responce.EventInventoryResponce;
import com.example.inventory_service.responce.VenueInventoryResponce;
import com.example.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/inventory/events")
    public @ResponseBody List<EventInventoryResponce> inventoryGetAllEvents(){
        return inventoryService.getAllEvents();
    }

    @GetMapping("/inventory/venue/{venueId}")
    public @ResponseBody VenueInventoryResponce inventoryByVenueId(@PathVariable("venueId") Long venueId){
        return inventoryService.getVenueInformation(venueId);
    }
}
