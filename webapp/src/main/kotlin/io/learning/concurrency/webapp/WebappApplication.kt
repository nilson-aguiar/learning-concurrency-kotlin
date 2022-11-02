package io.learning.concurrency.webapp

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
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
    fun millis() = Mono
        .just(ResponseEntity.ok("ok!"))
        .delayElement(Duration.ofMillis(Random.nextLong(100, 500)))


    @GetMapping("/seconds")
    fun seconds() = Mono
        .just(ResponseEntity.ok("ok!"))
        .delayElement(Duration.ofMillis(Random.nextLong(1000, 5000)))

}