package example.websocket.model

data class TickerRequestParams(val channel: String) {

    companion object {
        fun of(productCode: String) = TickerRequestParams(String.format("lightning_ticker_%s", productCode))
    }
}
