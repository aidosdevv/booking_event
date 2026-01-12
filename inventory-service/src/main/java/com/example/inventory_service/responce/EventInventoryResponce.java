package com.example.inventory_service.responce;

import com.example.inventory_service.entity.Venue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventInventoryResponce {
    private String event;
    private Long capacity;
    private Venue venue;
}
