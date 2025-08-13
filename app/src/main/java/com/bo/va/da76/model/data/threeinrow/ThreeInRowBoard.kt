package com.bo.va.da76.model.data.threeinrow

/**
 * Model of the playing field for the game "3 in Row".
 * @property cells - a two-dimensional array of gems (can be null for an empty cell)
 */
data class ThreeInRowBoard(
    val rows: Int,
    val columns: Int,
    val cells: List<List<Gem?>>
) 