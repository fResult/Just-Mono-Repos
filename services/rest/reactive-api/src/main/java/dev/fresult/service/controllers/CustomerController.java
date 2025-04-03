package dev.fresult.service.controllers;

import dtos.CustomerRequest;
import dtos.CustomerResponse;
import jakarta.validation.Valid;
import java.util.function.Function;
import models.Customer;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customers")
public class CustomerController {
  @PostMapping
  public Mono<ResponseEntity<CustomerResponse>> create(@Valid @RequestBody CustomerRequest body) {
    final var toDomain = (Function<CustomerRequest, Customer>) CustomerRequest::toDomain;
    final var fromDomain = (Function<Customer, CustomerResponse>) CustomerResponse::fromDomain;

    return Mono.justOrEmpty(body).map((fromDomain).compose(toDomain).andThen(ResponseEntity::ok));
  }
}
