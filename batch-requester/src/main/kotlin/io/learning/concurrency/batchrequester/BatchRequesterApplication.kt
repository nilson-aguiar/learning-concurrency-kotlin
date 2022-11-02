package io.learning.concurrency.batchrequester

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@SpringBootApplication
@EnableFeignClients
class BatchRequesterApplication(
    private val requesterService: RequesterService
): ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        requesterService.doRequests()
    }

}

const val numberOfRequests = 10

@OptIn(ExperimentalTime::class)
fun main(args: Array<String>) {
    val elapsed = measureTime {
        runApplication<BatchRequesterApplication>(*args) {
            webApplicationType = WebApplicationType.NONE
        }
    }

    println("Application took ${elapsed.inWholeSeconds} seconds to execute")
}

@Service
class RequesterService(
    private val webApi: WebApi
) {

    fun doRequests() {
        for (current in 1..numberOfRequests) {
            println( webApi.seconds(current.toString()))
        }
    }

}

@FeignClient(value = "webApi", url = "localhost:8080")
interface WebApi {

    @GetMapping("/millis")
    fun millis(@RequestParam addition: String = ""): String

    @GetMapping("/seconds")
    fun seconds(@RequestParam addition: String = ""): String

}

