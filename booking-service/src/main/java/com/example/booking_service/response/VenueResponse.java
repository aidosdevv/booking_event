package com.example.booking_service.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class VenueResponse {
    private Long id;
    private String name;
    private String address;
    private Long totalCapacity;
}
