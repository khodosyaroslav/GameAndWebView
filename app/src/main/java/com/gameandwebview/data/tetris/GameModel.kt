package com.gameandwebview.data.tetris

interface GameModel {
    fun init()
    val gameSize: Int

    fun newGame()
    fun startGame(onGameDrawnListener: (Array<Array<Point>>) -> Unit)
    fun pauseGame()
    fun turn(turn: GameTurn)
    fun setGameOverListener(onGameOverListener: () -> Unit)
    fun setScoreUpdatedListener(onScoreUpdatedListener: (Int) -> Unit)

    companion object {
        const val FPS = 60
        const val SPEED = 25
    }
}