package com.skachkov.monitor

data class Edge (val from: String, val to: String, val label: String, val physics: Boolean, val arrows: String, val color: Color)

data class Node (val id: String, val label: String)
