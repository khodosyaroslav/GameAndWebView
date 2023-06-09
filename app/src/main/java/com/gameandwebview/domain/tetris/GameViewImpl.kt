package com.gameandwebview.domain.tetris

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.gameandwebview.data.tetris.GameStatus
import com.gameandwebview.data.tetris.GameView
import com.gameandwebview.data.tetris.Point
import com.gameandwebview.presentation.tetris.GameFrame

class GameViewImpl(
    private val gameFrame: GameFrame,
    private val gameScoreText: TextView,
    private val gameStatusText: TextView,
    private val gameControlButton: Button
) : GameView {

    override fun init(gameSize: Int) {
        gameFrame.init(gameSize)
    }

    override fun draw(points: Array<Array<Point>>) {
        gameFrame.setPoints(points)
        gameFrame.invalidate()
    }

    override fun setScore(score: Int) {
        gameScoreText.text = "Score: $score"
    }

    override fun setStatus(status: GameStatus) {
        gameStatusText.text = status.value
        gameStatusText.visibility =
            if (status == GameStatus.PLAYING) View.INVISIBLE else View.VISIBLE
        gameControlButton.text = if (status == GameStatus.PLAYING) "Pause" else "Start"
    }

    companion object {
        fun newInstance(
            gameFrame: GameFrame,
            gameScoreText: TextView,
            gameStatusText: TextView,
            gameControlButton: Button
        ): GameView {
            return GameViewImpl(gameFrame, gameScoreText, gameStatusText, gameControlButton)
        }
    }
}