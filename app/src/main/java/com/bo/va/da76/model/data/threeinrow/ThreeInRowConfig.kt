package com.bo.va.da76.model.data.threeinrow

/**
 * Configuration of the game "3 in Row": field size, types of elements.
 */
data class ThreeInRowConfig(
    val rows: Int = 8,
    val columns: Int = 8,
    val elementTypes: List<ThreeInRowElementType> = ThreeInRowElementType.Companion.defaultTypes
) 