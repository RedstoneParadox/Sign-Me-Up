package io.github.redstoneparadox.signmeup.block.enums

import net.minecraft.util.StringIdentifiable

enum class TallSignShape(private val id: String): StringIdentifiable {
    TOP("top"),
    BOTTOM("bottom");

    override fun asString(): String {
        return id
    }
}