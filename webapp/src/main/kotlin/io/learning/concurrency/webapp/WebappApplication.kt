package io.learning.concurrency.webapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.time.Duration
import kotlin.random.Random

@SpringBootApplication
class WebappApplication

fun main(args: Array<String>) {
    runApplication<WebappApplication>(*args)
}

@RestController
class DelayableController {

    @GetMapping("/millis")
    fun millis(@RequestParam addition: String = "") = Mono
        .just(ResponseEntity.ok("$addition -> ok!"))
        .delayElement(Duration.ofMillis(Random.nextLong(100, 500)))


    @GetMapping("/seconds")
    fun seconds(@RequestParam addition: String = "") = Mono
        .just(ResponseEntity.ok("$addition -> ok!"))
        .delayElement(Duration.ofMillis(Random.nextLong(1000, 5000)))

}