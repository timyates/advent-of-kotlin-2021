package com.bloidonia.advent

import java.io.InputStream
import kotlin.streams.toList

abstract class BaseDay {

    fun <T> readList(input: InputStream, fn: (String) -> T): List<T> = input
        .bufferedReader()
        .use {
            it.lines().map(fn).toList()
        }
}