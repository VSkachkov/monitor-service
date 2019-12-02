package com.skachkov.monitor.service

import com.skachkov.monitor.ConnectionRepository
import com.skachkov.monitor.RequestResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import javax.annotation.PostConstruct
import kotlin.random.Random
import kotlin.random.nextInt


@Service
class MetricsAggregatorService {
    @Autowired
    lateinit var connectionRepository: ConnectionRepository

    var nodes = listOf("SERVICE 1", "SERVICE 2", "SERVICE 3", "SERVICE 4", "SERVICE 5" )

    @PostConstruct
    fun init() {
        // start your monitoring in here
        for (i in 0..10) {
            var range = IntRange(0, nodes.size)
            var source = Random.nextInt(0, nodes.size)
            var dest = generateRandom(source)
            val request = RequestResponse(Date(), 500, Random(LongRange(Long.MIN_VALUE, Long.MAX_VALUE).hashCode()).nextLong(), nodes.get(source), nodes.get(dest), null)
            connectionRepository.save(request)
    }
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