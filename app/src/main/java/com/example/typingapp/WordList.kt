package com.example.typingapp

import java.io.File

fun main() {
    val words = arrayListOf<String>()
    File("app\\src\\main\\assets\\Words.txt").forEachLine {
        words.add(it.lowercase())
    }
    println(words.joinToString(", "))
}
