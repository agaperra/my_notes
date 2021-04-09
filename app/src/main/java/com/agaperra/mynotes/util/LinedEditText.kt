package com.agaperra.mynotes.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.agaperra.mynotes.R

class LinedEditText(context: Context?, attrs: AttributeSet?) : AppCompatEditText(
    context!!, attrs
) {
    private val mRect: Rect = Rect()
    private val mPaint: Paint = Paint()
    override fun onDraw(canvas: Canvas) {
        val height = height
        val lineHeight = lineHeight
        var count = height / lineHeight
        if (lineCount > count) {
            count = lineCount
        }
        val r = mRect
        val paint = mPaint
        var baseline = getLineBounds(0, mRect) //first line
        for (i in 0 until count) {
            canvas.drawLine(
                r.left.toFloat(),
                (baseline + 1).toFloat(),
                r.right.toFloat(),
                (baseline + 1).toFloat(),
                paint
            )
            baseline += lineHeight //next line
        }
        super.onDraw(canvas)
    }

    init {
        mPaint.style = Paint.Style.FILL_AND_STROKE
        mPaint.color = resources.getColor(R.color.grey_umber)
    }
}