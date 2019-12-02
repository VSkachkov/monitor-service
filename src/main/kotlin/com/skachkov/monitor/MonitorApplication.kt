package com.skachkov.monitor

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MonitorApplication

fun main(args: Array<String>) {
	runApplication<MonitorApplication>(*args)
}
