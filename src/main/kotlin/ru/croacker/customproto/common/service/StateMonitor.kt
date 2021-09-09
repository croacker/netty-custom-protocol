package ru.croacker.customproto.common.service

import ru.croacker.customproto.master.MasterState
import java.io.*
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

object StateMonitor {

    private const val FILE_NAME = "./master-state.txt"

    private val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss.SSS");

    private val state = MasterState

    fun init() {
        Executors.newScheduledThreadPool(1)
            .scheduleAtFixedRate({ write() }, 10, 2, TimeUnit.SECONDS)
    }

    private fun write() {
        theFile()
        val file = File(FILE_NAME)
        try {
            val printWriter = PrintWriter(file)
            printWriter.println("Server startup at ${timestamp(state.startup)}")
            printWriter.println("   |Client                              |Online |Hearbeat               |  Orders count|")
            state.clientStates().forEach { client ->
                val id = client.id.padStart(3, ' ')
                val uuid = client.name
                val online = client.online.toString().padEnd(7, ' ')
                val timestamp = timestamp(client.heartbeat).padStart(23, ' ')
                val ordersCount = client.ordersCount.toString().padStart(14, ' ')
                printWriter.println("${id}|${uuid}|${online}|${timestamp}|${ordersCount}|")
            }
            printWriter.flush()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun timestamp(heartbeat: Long): String =
        Instant.ofEpochMilli(heartbeat).atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter)

    private fun theFile() {
        try {
            val fwOb = FileWriter(FILE_NAME, false)
            val pwOb = PrintWriter(fwOb, false)
            pwOb.flush()
            pwOb.close()
            fwOb.close()
        } catch (e: IOException) {
            println("Create new file")
        }
    }
}