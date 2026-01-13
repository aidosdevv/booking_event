package com.example.booking_service.service;

import com.example.booking_service.client.InventoryServiceClient;
import com.example.booking_service.entity.Customer;
import com.example.booking_service.event.BookingEvent;
import com.example.booking_service.repository.BookingRepository;
import com.example.booking_service.repository.CustomerRepository;
import com.example.booking_service.request.BookingRequest;
import com.example.booking_service.response.BookingResponse;
import com.example.booking_service.response.InventoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final InventoryServiceClient inventoryServiceClient;
    private final KafkaTemplate<String,BookingEvent> kafkaTemplate;

    public BookingResponse createBooking(final BookingRequest request){
        // check user exist
        Customer customer = customerRepository.findById(request.getUserId()).orElse(null);
        if(customer==null){
            throw new RuntimeException("User not found");
        }
        // check if there is enough inventory
        InventoryResponse inventoryResponse = inventoryServiceClient.getInventoryResponse(request.getEventId());
        log.info("Inventory Service Response: {}",inventoryResponse);
        if(inventoryResponse.getCapacity()<request.getTicketCount()){
            throw new RuntimeException("Not enough inventory");
        }
        // create booking
        BookingEvent bookingEvent = createBookingEvent(request,customer,inventoryResponse);

        // send booking to Order Service on a Kafka Topic
        kafkaTemplate.send("booking",bookingEvent);
        log.info("Booking sent to Kafka: {}",bookingEvent);

        return BookingResponse.builder()
                .userId(bookingEvent.getUserId())
                .eventId(bookingEvent.getEventId())
                .totalPrice(bookingEvent.getTotalPrice())
                .ticketCount(bookingEvent.getTicketCount())
                .build();
    }

    public BookingEvent createBookingEvent(BookingRequest request,Customer customer,InventoryResponse inventoryResponse){
        System.out.print(inventoryResponse.getTicketPrice()+">>>>>>>>>");
        return BookingEvent.builder()
                .userId(customer.getId())
                .eventId(request.getEventId())
                .ticketCount(request.getTicketCount())
                .totalPrice(inventoryResponse.getTicketPrice().multiply(BigDecimal.valueOf(request.getTicketCount())))
                .build();
    }
}
