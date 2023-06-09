package com.gameandwebview.data.tetris

class Point(val x: Int, val y: Int, var isFallingPoint: Boolean, var type: PointType) {

    constructor(x: Int, y: Int) : this(x, y, false, PointType.EMPTY)

    val isStablePoint: Boolean
        get() = !isFallingPoint && type == PointType.BOX
}