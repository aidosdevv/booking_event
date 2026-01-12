package com.example.booking_service.service;

import com.example.booking_service.client.InventoryServiceClient;
import com.example.booking_service.entity.Customer;
import com.example.booking_service.repository.BookingRepository;
import com.example.booking_service.repository.CustomerRepository;
import com.example.booking_service.request.BookingRequest;
import com.example.booking_service.response.BookingResponse;
import com.example.booking_service.response.InventoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final InventoryServiceClient inventoryServiceClient;

    public BookingResponse createBooking(final BookingRequest request){
        Customer customer = customerRepository.findById(request.getUserId()).orElse(null);
        if(customer==null){
            throw new RuntimeException("User not found");
        }
        InventoryResponse inventoryResponse = inventoryServiceClient.getInventoryResponse(request.getEventId());
        System.out.println("Inventory Service Reponse "+inventoryResponse);
        if(inventoryResponse.getCapacity()<request.getTicketCount()){
            throw new RuntimeException("Not enough inventory");
        }

        return BookingResponse.builder().build();
    }
}
