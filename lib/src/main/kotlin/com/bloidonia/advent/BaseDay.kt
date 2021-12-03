package com.bloidonia.advent

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import java.io.InputStream

fun <T> readList(resourceName: String, fn: (String) -> T) = BaseDay::class.java.getResourceAsStream(resourceName)!!
    .bufferedReader()
    .lines()
    .map(fn)
    .toList()

abstract class BaseDay {

    fun <T> readFlow(resourceName: String, fn: (String) -> T) = BaseDay::class.java.getResourceAsStream(resourceName)!!
        .bufferedReader()
        .lineSequence()
        .asFlow()
        .map(fn)

    fun <T> readList(input: InputStream, fn: (String) -> T) = input
        .bufferedReader()
        .lines()
        .map(fn)
        .toList()
}