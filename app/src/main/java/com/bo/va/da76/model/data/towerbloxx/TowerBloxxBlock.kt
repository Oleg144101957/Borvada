package com.bo.va.da76.model.data.towerbloxx

// Class to represent one tower block

data class TowerBloxxBlock(
    val x: Float, // Block center along X
    val y: Float, // The top edge of the block along Y
    val width: Float,
    val height: Float,
    val isFalling: Boolean = false,
    val isStable: Boolean = true
) 