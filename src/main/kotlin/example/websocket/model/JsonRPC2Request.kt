package example.websocket.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class JsonRPC2Request<T>(
    @field:JsonProperty("jsonrpc")
    val version: String = "2.0",
    val method: String = "subscribe",
    val params: T?,
    val id: Int? = null
)
