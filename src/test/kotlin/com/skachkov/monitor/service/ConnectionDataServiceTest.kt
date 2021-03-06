package com.skachkov.monitor.service

import com.skachkov.monitor.enums.STATUS
import org.junit.Assert
import org.junit.jupiter.api.Test

internal class ConnectionDataServiceTest {

    @Test
    fun getLoadStatus() {
        var connectionDataService = ConnectionDataService()
        Assert.assertEquals(STATUS.BUSY, connectionDataService.getLoadStatus(90.0f, 300, 800))
        Assert.assertEquals(STATUS.OK, connectionDataService.getLoadStatus(30.0f, 300, 800))
        Assert.assertEquals(STATUS.BUSY, connectionDataService.getLoadStatus(30.0f, 700, 800))
        Assert.assertEquals(STATUS.UNAVAILABLE, connectionDataService.getLoadStatus(null, 700, 800))
    }
}