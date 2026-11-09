package com.example.moderntodo.domain.model

enum class Category(val displayName: String) {
    ALL("Semua"),
    WORK("Kerja"),
    PERSONAL("Pribadi"),
    STUDY("Belajar"),
    SHOPPING("Belanja"),
    HEALTH("Kesehatan");

    companion object {
        fun defaultCategories(): List<Category> = listOf(
            WORK, PERSONAL, STUDY, SHOPPING, HEALTH
        )

        fun fromString(value: String): Category {
            return entries.find { it.name.equals(value, ignoreCase = true) } ?: PERSONAL
        }
    }
}
