package ru.croacker.customproto.common.marshaller

import ru.croacker.customproto.common.event.*

class Marshaller {

    fun marshal(event: Event):String =
        when(event.type()){
            0 -> toMessage(event as Heartbeat)
            1 -> toMessage(event as Acknowledgment)
            20 -> toMessage(event as Order)
            else -> ""
        }


    fun unmarshal(str: String): Event =
        when(getType(str)){
            0 -> toHeartbeat(str)
            1 -> toAcknowledgment(str)
            20 -> toOrder(str)
            else -> Undefined
        }

    private fun toAcknowledgment(str: String): Event =
        str.split(";").let {
            return Acknowledgment.create(
                it[1].split("=")[1].toLong(),
                it[3].split("=")[1].replace("\n", "").toLong(),
                it[2].split("=")[1]
            )
        }


    private fun toHeartbeat(str: String): Event =
        str.split(";").let {
            return Heartbeat.create(
                it[1].split("=")[1].toLong(),
                it[3].split("=")[1].replace("\n", "").toLong(),
                it[2].split("=")[1]
            )
        }

    private fun toOrder(str: String): Event =
        str.split(";").let {
            return Order.create(
                it[1].split("=")[1].toLong(),
                it[3].split("=")[1].replace("\n", ""),
                it[2].split("=")[1]
            )
        }


    private fun toMessage(event: Acknowledgment): String =
        "1=1;2=${event.messageId()};3=${event.sender()};4=${event.sourceId}\n"

    private fun toMessage(event: Heartbeat): String =
        "1=0;2=${event.messageId()};3=${event.sender()};5=${event.timestamp}\n"

    private fun toMessage(event: Order): String =
        "1=20;2=${event.messageId()};3=${event.sender()};20=${event.description}\n"

    private fun getType(str: String): Int{
        val a = str.split(";")[0].split("=")
        if(a.size == 1){
            println(str)
            return -1
        }
        return str.split(";")[0].split("=")[1].toInt()
    }



}