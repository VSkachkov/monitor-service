package com.skachkov.monitor.service

import com.skachkov.monitor.*
import com.skachkov.monitor.converter.ConnectionDataConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import java.util.concurrent.ThreadLocalRandom

@Service
class ConnectionDataService {
    companion object {
        var MAX_LEN = 1 + 1
    }
    var endpoints = listOf("getById", "getAll", "save", "update", "delete")
    var statuses = listOf("OK", "BUSY", "UNAVAILABLE", "ERROR")

    val LOAD_FACTOR = 80


    @Autowired
    lateinit var connectionRepository: ConnectionRepository;

    @Autowired
    lateinit var connectionDataConverter: ConnectionDataConverter

    fun findEdges(): Any {
        val requestResponse = connectionRepository.findAll()
        val edges = requestResponse.map { request -> Edge(request.source, request.destination, getRandomEndpoint(), false, "to", Color("green"), status = 200) }
        return edges
    }

    fun findUpdates(): Any {
        val green = Color("green")
        val red = Color("red")
        val color = if (MAX_LEN++ % 2 == 0) green else red;
        val requestResponse = connectionRepository.findById(1)
        val edges = requestResponse.map { request -> Edge(request.source, request.destination, getRandomEndpoint(), false, "to", color, 200) }
        return edges
    }

    fun getRandomEndpoint(): String {
        return getRandomValue(endpoints);
    }

    fun getRandomValue(values: List<String>): String {
        val size =  values.size;
        val random = ThreadLocalRandom.current().nextInt(0, size)
        return values.get(random)
    }

    fun getRandomNodeStatus(): String {
        return getRandomValue(statuses)
    }
    fun getLoadStatus(cpuLoad: Float?, heap: Int?, heapMax: Int?): STATUS {
        if (cpuLoad == null || heap == null || heapMax == null) {
            return STATUS.UNAVAILABLE;
        }
        if (cpuLoad>=LOAD_FACTOR) {
            return STATUS.BUSY
        }
        if (heap.div((heapMax.toFloat()))*100>LOAD_FACTOR) {
            return STATUS.BUSY
        }
        return STATUS.OK
    }

    fun findNodes(): Any {
        val requestResponseList = connectionRepository.findAll()
        checkUnavailable(requestResponseList)
        val immutableSources = requestResponseList.map { request -> request.source }
        val destination = requestResponseList.map { request -> request.destination }
                .filter { destination -> !StringUtils.isEmpty(destination) }
        val nodes = immutableSources.union(destination).stream().map { service -> getNode(service)
        }
        return nodes
    }

    private fun checkUnavailable(requestResponseList: Iterable<RequestResponse>) {
        if (requestResponseList.filter { requestResponse -> requestResponse.status == 504 }.isNotEmpty()) {

        }
    }

    private fun getNode(service: String): Node {
        val randomCpu = getRandomValueByMax(100)
        val randomMemory = getRandomValueByMax(1024)
        return Node(service, service, getLoadStatus(randomCpu.toFloat(), randomMemory, 1024).name, randomCpu.toFloat(), randomMemory, 1024)
    }

    private fun getRandomValueByMax(max: Int): Int {
       return ThreadLocalRandom.current().nextInt(0, max)
    }

    fun findEdgesByTraceId(traceId: Long): Any {
        return connectionRepository.findAll().filter { entity -> entity.traceId == traceId }.map { request -> Edge(request.source, request.destination, getRandomEndpoint(), false, "to", Color("green"), status = 200) }
    }
}