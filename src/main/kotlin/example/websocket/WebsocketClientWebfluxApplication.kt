package example.websocket

import example.websocket.repository.TickerRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component

@SpringBootApplication
class WebsocketClientWebfluxApplication

fun main(args: Array<String>) {
    runApplication<WebsocketClientWebfluxApplication>(*args)
}

@Component
class TickerSubscriber(private val tickerRepository: TickerRepository) : CommandLineRunner {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    override fun run(vararg args: String?) {
        runBlocking {
            tickerRepository.stream("BTC_JPY")
                .collect {
                    log.info(it.toString())
                }
        }
    }
}
