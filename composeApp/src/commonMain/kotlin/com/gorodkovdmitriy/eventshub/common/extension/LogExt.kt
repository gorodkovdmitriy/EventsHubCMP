package com.gorodkovdmitriy.eventshub.common.extension

import io.github.aakira.napier.Napier

fun log(tag: String = "myLog", message: String) {
    Napier.d(tag = tag, message = message)
}