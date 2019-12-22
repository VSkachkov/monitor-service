package com.skachkov.monitor.service

import com.skachkov.monitor.*
import com.skachkov.monitor.converter.ConnectionDataConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import kotlin.random.Random

@Service
class ConnectionDataService {
    companion object {
        var MAX_LEN = 1 + 1
    }

    @Autowired
    lateinit var connectionRepository: ConnectionRepository;

    @Autowired
    lateinit var connectionDataConverter: ConnectionDataConverter

    fun findEdges(): Any {
        val requestResponse = connectionRepository.findAll()
        val edges = requestResponse.map { request -> Edge(request.source, request.destination, "getById", false, "to", Color("green"), status = 200) }
        return edges
    }

    fun findUpdates(): Any {
        val green = Color("green")
        val red = Color("red")
        val color = if (MAX_LEN++ % 2 == 0) green else red;
        val requestResponse = connectionRepository.findById(1)
        val edges = requestResponse.map { request -> Edge(request.source, request.destination, "getById", false, "to", color, 200) }
        return edges
    }

    fun findNodes(): Any {
        val requestResponse = connectionRepository.findAll()
        val immutableSources = requestResponse.map { request -> request.source }
        val destination = requestResponse.map { request -> request.destination }
                .filter { destination -> !StringUtils.isEmpty(destination) }
        val nodes = immutableSources.union(destination).stream().map { service -> Node(service, service, "OK", Random(10).nextInt(1, 100).toFloat(), 350, 1024) }
        return nodes
    }

    fun findEdgesByTraceId(traceId: Long): Any {
        return connectionRepository.findAll().filter { entity -> entity.traceId == traceId }.map { request -> Edge(request.source, request.destination, "getById", false, "to", Color("green"), status = 200) }
    }
}