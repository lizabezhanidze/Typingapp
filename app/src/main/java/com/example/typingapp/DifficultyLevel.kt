package com.example.typingapp

enum class DifficultyLevel(val value: Int) {
    EASY(0),
    MEDIUM(1),
    HARD(2);

    companion object {
        fun fromInt(value: Int) = values().firstOrNull { it.value == value } ?: EASY
        fun fromString(name: String) = values().firstOrNull { it.name.equals(name, ignoreCase = true) } ?: EASY
    }
}
