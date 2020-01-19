package com.skachkov.monitor.service

import com.skachkov.monitor.ConnectionRepository
import com.skachkov.monitor.entities.RequestResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import javax.annotation.PostConstruct
import kotlin.random.Random


@Service
class MetricsAggregatorService {
    @Autowired
    lateinit var connectionRepository: ConnectionRepository

    var nodes = listOf("DS-USER", "DS-DOCUMENT", "DS-BOOKING", "FRONT_GATEWAY", "FRONTEND", "DS-POST", "CS-PAYMENTS")

    @PostConstruct
    fun init() {
        // start your monitoring in here
        for (i in 0..25) {
            var range = IntRange(0, nodes.size)
            var source = Random.nextInt(0, nodes.size)
            var dest = generateRandom(source)
            val request = RequestResponse(Date(), 200, Random(LongRange(Long.MIN_VALUE, Long.MAX_VALUE).hashCode()).nextLong(), nodes.get(source), nodes.get(dest), null)
            connectionRepository.save(request)
        }
        connectionRepository.save(RequestResponse(Date(), 504, Random(LongRange(Long.MIN_VALUE, Long.MAX_VALUE).hashCode()).nextLong(), nodes.get(1), "EXTERNAL", null)
        )
    }

    private fun generateRandom(previousRandom: Int): Int {
        val random = Random.nextInt(0, nodes.size)

        if (random == previousRandom) {
            return generateRandom(previousRandom)
        } else {
            return random
        }
    }
}