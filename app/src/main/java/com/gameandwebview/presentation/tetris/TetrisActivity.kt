package com.gameandwebview.presentation.tetris

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.gameandwebview.data.tetris.GamePresenter
import com.gameandwebview.data.tetris.GameTurn
import com.gameandwebview.databinding.ActivityTetrisBinding
import com.gameandwebview.domain.tetris.GameModelImpl
import com.gameandwebview.domain.tetris.GameViewImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TetrisActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTetrisBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTetrisBinding.inflate(layoutInflater)

        val gameFrame: GameFrame = binding.gameContainer
        val scoreText: TextView = binding.score
        val statusText: TextView = binding.status
        val controlButton: Button = binding.btnControl

        val gamePresenter = GamePresenter()
        gamePresenter.setGameModel(GameModelImpl())
        gamePresenter.setGameView(
            GameViewImpl.newInstance(
                gameFrame,
                scoreText,
                statusText,
                controlButton
            )
        )

        val downButton = binding.btnDown
        downButton.setOnClickListener { gamePresenter.turn(GameTurn.DOWN) }

        val leftButton = binding.btnLeft
        leftButton.setOnClickListener { gamePresenter.turn(GameTurn.LEFT) }

        val rightButton = binding.btnRight
        rightButton.setOnClickListener { gamePresenter.turn(GameTurn.RIGHT) }

        val rotateButton = binding.btnRotate
        rotateButton.setOnClickListener { gamePresenter.turn(GameTurn.ROTATE) }

        controlButton.setOnClickListener { gamePresenter.changeStatus() }

        gamePresenter.init()

        setContentView(binding.root)
    }
}