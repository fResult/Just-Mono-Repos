package dev.fresult.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class ReactiveApiApplication {
  public static void main(String[] args) {
    SpringApplication.run(ReactiveApiApplication.class, args);
  }
}

@RestController
class ReactiveController {
  @GetMapping("/hello")
  public Mono<ResponseEntity<String>> hello() {
    return Mono.just(ResponseEntity.ok("Hello"));
  }
}
