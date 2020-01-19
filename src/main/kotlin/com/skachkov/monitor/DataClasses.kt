package com.skachkov.monitor

import com.skachkov.monitor.enums.Color

data class Edge (val from: String, val to: String, val label: String, val physics: Boolean, val arrows: String, val color: Color, val status: Int)

data class Node (val id: String, val label: String, val state: String, val CPU: Float, val heapCurrent: Int, val heapMax: Int)
