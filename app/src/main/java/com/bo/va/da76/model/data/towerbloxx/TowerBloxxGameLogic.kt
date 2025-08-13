package com.bo.va.da76.model.data.towerbloxx

import kotlin.math.abs

class TowerBloxxGameLogic(
    private val blockWidth: Float = 120f,
    private val blockHeight: Float = 40f,
    private val minOverlap: Float = 30f, // Minimum floor area for stability
    private val screenWidth: Float = 480f
) {
    val tower = TowerBloxxTower()
    var score = 0
    var highScore = 0
    var lives = 3
    var combo = 0
    var isGameOver = false
    var craneX = screenWidth / 2f
    private var craneDirection = 1 // 1 = right, -1 = left
    private var craneSpeed = 3f
    var fallingBlock: TowerBloxxBlock? = null
    var isBlockDropping = false
    private var baseTowerY: Float = 0f

    fun startGame(baseTowerY: Float) {
        tower.blocks.clear()
        score = 0
        lives = 3
        combo = 0
        isGameOver = false
        craneX = screenWidth / 2f
        craneDirection = 1
        craneSpeed = 3f
        fallingBlock = null
        isBlockDropping = false
        this.baseTowerY = baseTowerY
    }

    fun moveCrane() {
        if (isBlockDropping || isGameOver) return
        craneX += craneDirection * craneSpeed
        if (craneX < blockWidth / 2) {
            craneX = blockWidth / 2
            craneDirection = 1
        } else if (craneX > screenWidth - blockWidth / 2) {
            craneX = screenWidth - blockWidth / 2
            craneDirection = -1
        }
    }

    fun dropBlock() {
        if (isBlockDropping || isGameOver) return
        fallingBlock = TowerBloxxBlock(
            x = craneX,
            y = 0f, // animation always from the tap (Y=0)
            width = blockWidth,
            height = blockHeight,
            isFalling = true,
            isStable = false
        )
        isBlockDropping = true
    }

    // Called after the fall animation completes.
    fun onBlockLanded() {
        val block = fallingBlock ?: return
        val y = if (tower.blocks.isEmpty()) baseTowerY else tower.getTopBlock()!!.y - blockHeight
        val landedBlock = block.copy(y = y, isFalling = false)
        val stable = tower.isBlockStable(landedBlock, minOverlap)
        if (stable) {
            val prev = tower.getTopBlock()
            val offset = if (prev != null) landedBlock.x - prev.x else 0f
            if (abs(offset) < 5f) {
                combo++
                score += (10 * (tower.blocks.size + 1) * (1 + combo / 10f)).toInt()
            } else {
                combo = 0
                score += 10 * (tower.blocks.size + 1)
            }
            tower.addBlock(landedBlock.copy(isStable = true))
        } else {
            lives--
            if (lives <= 0) {
                isGameOver = true
                if (score > highScore) highScore = score
            }
        }
        fallingBlock = null
        isBlockDropping = false
    }

    fun updateHighScore(newHighScore: Int) {
        highScore = newHighScore
    }
} 