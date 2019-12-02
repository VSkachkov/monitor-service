package com.skachkov.monitor.service

import com.skachkov.monitor.Color
import com.skachkov.monitor.ConnectionRepository
import com.skachkov.monitor.Edge
import com.skachkov.monitor.Node
import com.skachkov.monitor.converter.ConnectionDataConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils

@Service
class ConnectionDataService {
    @Autowired
    lateinit var connectionRepository: ConnectionRepository;

    @Autowired
    lateinit var connectionDataConverter: ConnectionDataConverter

    fun findEdges(): Any {
        val requestResponse = connectionRepository.findAll()
        val edges = requestResponse.map { request ->  Edge(request.source, request.destination, "getById", false, "to", Color("green"))}
        return edges
    }

    fun findNodes(): Any {
        val requestResponse = connectionRepository.findAll()
        val immutableSources = requestResponse.map { request -> request.source }
        val destination = requestResponse.map { request -> request.destination }
                .filter { destination -> !StringUtils.isEmpty(destination) }
        val nodes = immutableSources.union(destination).stream().map { service -> Node(service, service) }
        return nodes
    }
}