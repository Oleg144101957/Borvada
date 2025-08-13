package com.bo.va.da76.model.data.towerbloxx

class TowerBloxxTower(
    val blocks: MutableList<TowerBloxxBlock> = mutableListOf()
) {
    fun addBlock(block: TowerBloxxBlock) {
        blocks.add(block)
    }

    fun getTopBlock(): TowerBloxxBlock? = blocks.lastOrNull()

    // Stability check: if the overlap area with the previous block < minOverlap, the block falls
    fun isBlockStable(newBlock: TowerBloxxBlock, minOverlap: Float): Boolean {
        val top = getTopBlock() ?: return true
        val left1 = top.x - top.width / 2
        val right1 = top.x + top.width / 2
        val left2 = newBlock.x - newBlock.width / 2
        val right2 = newBlock.x + newBlock.width / 2
        val overlap = (minOf(right1, right2) - maxOf(left1, left2)).coerceAtLeast(0f)
        return overlap >= minOverlap
    }
} 