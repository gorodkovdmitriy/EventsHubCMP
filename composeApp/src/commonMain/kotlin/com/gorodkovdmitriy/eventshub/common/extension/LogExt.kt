package com.gorodkovdmitriy.eventshub.common.extension

import io.github.aakira.napier.Napier

fun log(message: String, tag: String = "myLog") {
    Napier.d(tag = tag, message = message)
}