package ru.croacker.customproto

import ru.croacker.customproto.common.service.StateMonitor
import ru.croacker.customproto.master.MasterNode

fun main() {
    StateMonitor.init()
    MasterNode().start()
}