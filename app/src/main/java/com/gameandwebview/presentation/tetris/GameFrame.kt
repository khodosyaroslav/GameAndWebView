package com.gameandwebview.presentation.tetris

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.gameandwebview.data.tetris.Point
import com.gameandwebview.data.tetris.PointType
import kotlin.math.min

class GameFrame : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private lateinit var points: Array<Array<Point>>
    private var boxSize = 0
    private var boxPadding = 0
    private var gameSize = 0

    private val paint = Paint()

    fun init(gameSize: Int) {
        this.gameSize = gameSize
        viewTreeObserver.addOnGlobalLayoutListener {
            boxSize = min(width, height) / gameSize
            boxPadding = boxSize / 10
        }
    }

    private fun getPoint(x: Int, y: Int): Point {
        return points[y][x]
    }

    fun setPoints(points: Array<Array<Point>>) {
        this.points = points
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.color = Color.BLACK
        canvas.drawRect(0F, 0F, gameSize.toFloat(), gameSize.toFloat(), paint)
        if (!::points.isInitialized) {
            return
        }
        for (i in 0 until gameSize) {
            for (j in 0 until gameSize) {
                val point: Point = getPoint(i, j)
                var left: Int
                var right: Int
                var top: Int
                var bottom: Int
                paint.color = Color.WHITE
                when (point.type) {
                    PointType.BOX -> {
                        left = boxSize * point.x + boxPadding
                        right = left + boxSize - boxPadding
                        top = boxSize * point.y + boxPadding
                        bottom = top + boxSize - boxPadding
                    }

                    PointType.VERTICAL_LINE -> {
                        left = boxSize * point.x
                        right = left + boxPadding
                        top = boxSize * point.y
                        bottom = top + boxSize
                    }

                    PointType.HORIZONTAL_LINE -> {
                        left = boxSize * point.y
                        right = left + boxSize
                        top = boxSize * point.y
                        bottom = top + boxPadding
                    }

                    PointType.EMPTY -> {
                        left = boxSize * point.x
                        right = left + boxSize
                        top = boxSize * point.y
                        bottom = top + boxSize
                        paint.color = Color.BLACK
                    }
                }
                canvas.drawRect(
                    left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint
                )
            }
        }
    }
}