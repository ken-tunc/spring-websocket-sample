package example.websocket.model

import com.fasterxml.jackson.annotation.JsonProperty

data class TickerMessage(
    @field:JsonProperty("product_code")
    val productCode: String,
    val timestamp: String,
    @field:JsonProperty("tick_id")
    val tickId: Int,
    @field:JsonProperty("best_bid")
    val bestBid: Double,
    @field:JsonProperty("best_ask")
    val bestAsk: Double,
    @field:JsonProperty("best_bid_size")
    val bestBidSize: Double,
    @field:JsonProperty("best_ask_size")
    val bestAskSize: Double,
    @field:JsonProperty("total_bid_depth")
    val totalBidDepth: Double,
    @field:JsonProperty("total_ask_depth")
    val totalAskDepth: Double,
    val ltp: Double,
    val volume: Double,
    @field:JsonProperty("volume_by_product")
    val volumeByProduct: Double
)
