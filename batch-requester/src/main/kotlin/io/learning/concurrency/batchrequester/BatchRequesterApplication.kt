package io.learning.concurrency.batchrequester

import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.Banner
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

@Service
class RequesterService(
    private val webApi: WebApi
) {

    val log = LoggerFactory.getLogger(this::class.java.simpleName)!!

    fun doRequests() {
        for (current in 1..amountOfRequests) {
            log.info(webApi.seconds(current.toString()))
        }
    }

}

const val amountOfRequests = 5

@OptIn(ExperimentalTime::class)
fun main(args: Array<String>) {
    println("Press any button to start batching!")
    readln()

    val elapsed = measureTime {
        runApplication<BatchRequesterApplication>(*args) {
            webApplicationType = WebApplicationType.NONE
            setBannerMode(Banner.Mode.OFF)
        }
    }

    LoggerFactory.getLogger("BatchRequesterApplication")
        .info("Application took ${elapsed.inWholeSeconds} seconds to execute")
}



@SpringBootApplication
@EnableFeignClients
class BatchRequesterApplication(
    private val requesterService: RequesterService
): ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        requesterService.doRequests()
    }

}

@FeignClient(value = "webApi", url = "localhost:8080")
interface WebApi {

    @GetMapping("/millis")
    fun millis(@RequestParam addition: String = ""): String

    @GetMapping("/seconds")
    fun seconds(@RequestParam addition: String = ""): String

}

