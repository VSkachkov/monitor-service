package com.skachkov.monitor

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("monitor")
data class MonitorProperties(var title: String, val banner: Banner) {
	data class Banner(val title: String? = null, val content: String)
}
