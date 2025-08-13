package com.bo.va.da76.model.data.threeinrow

import com.bo.va.da76.R

/**
 * Type of item for the game "3 in Row". Each type has its own picture.
 */
enum class ThreeInRowElementType(val imageRes: Int) {
    RED(R.drawable.b_gem_red),
    BLUE(R.drawable.b_gem_blue),
    GREEN(R.drawable.b_gem_green),
    YELLOW(R.drawable.b_gem_yellow),
    PURPLE(R.drawable.b_gem_purple),
    ORANGE(R.drawable.b_gem_orange);

    companion object {
        val defaultTypes = listOf(RED, BLUE, GREEN, YELLOW, PURPLE, ORANGE)
    }
}