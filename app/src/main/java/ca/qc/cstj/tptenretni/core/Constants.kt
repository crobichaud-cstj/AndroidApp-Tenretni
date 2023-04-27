package ca.qc.cstj.tptenretni.core

object Constants {

    object BaseURL {
        //private const val BASE_API = "http://10.0.2.2:5000"
        private const val BASE_API = "https://api.andromia.science"
        const val TICKETS = "$BASE_API/tickets"
        const val CUSTOMERS = "$BASE_API/customers"
        const val GATEWAYS = "$BASE_API/gateways"
        const val NETWORK = "$BASE_API/network"
    }

    object Refresh_Delay {
        const val TICKET_DETAIL_REFRESH=1000L * 30
        const val GATEWAY_REFRESH = 1000L * 60
        const val LOADING_TIME = 1000L * 1
    }


    object Gateway{
        const val ONLINE="Online"
    }
    const val FLAG_API_URL = "https://flagcdn.com/h40/%s.png"

    enum class TicketPriority {
        Low, Normal, High, Critical
    }

    enum class TicketStatus {
        Open, Solved
    }

    enum class ConnectionStatus {
        Online, Offline
    }

    object RecyclerAdapters {
        const val Gateways = 2
    }

}