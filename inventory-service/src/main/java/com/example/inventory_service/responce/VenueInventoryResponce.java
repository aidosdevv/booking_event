package com.example.inventory_service.responce;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VenueInventoryResponce {
    private Long venueId;
    private String venueName;
    private Long totalCapacity;

}
