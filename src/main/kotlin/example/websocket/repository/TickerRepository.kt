package example.websocket.repository

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import example.websocket.model.JsonRPC2Request
import example.websocket.model.TickerMessage
import example.websocket.model.TickerRequestParams
import example.websocket.model.TickerSubscribeParams
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import org.springframework.stereotype.Repository
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient
import reactor.core.publisher.EmitterProcessor
import reactor.core.publisher.Mono
import java.net.URI

@Repository
class TickerRepository {

    companion object {
        private val endpoint = URI("wss://ws.lightstream.bitflyer.com/json-rpc")
        private val objectMapper = jacksonObjectMapper()
        private val webSocketClient = ReactorNettyWebSocketClient()
    }

    fun stream(productCode: String): Flow<TickerMessage> {
        val output = EmitterProcessor.create<TickerMessage>()

        val sessionMono = webSocketClient.execute(endpoint) { session ->
            val request = JsonRPC2Request(params = TickerRequestParams.of(productCode))
            val requestMessage = Mono.fromCallable {
                session.textMessage(objectMapper.writeValueAsString(request))
            }

            session.send(requestMessage)
                .thenMany(
                    session.receive()
                        .map(WebSocketMessage::getPayloadAsText)
                        .map { objectMapper.readValue<JsonRPC2Request<TickerSubscribeParams>>(it).params!!.message }
                        .subscribeWith(output)
                        .then()
                )
                .then()
        }

        return output.doOnSubscribe { sessionMono.subscribe() }
            .asFlow()
    }
}
