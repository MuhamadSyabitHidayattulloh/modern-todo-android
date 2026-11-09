package com.example.moderntodo.domain.model

enum class Priority(val displayName: String, val level: Int) {
    LOW("Rendah", 1),
    MEDIUM("Sedang", 2),
    HIGH("Tinggi", 3);

    companion object {
        fun fromString(value: String): Priority {
            return entries.find { it.name.equals(value, ignoreCase = true) } ?: MEDIUM
        }
    }
}
