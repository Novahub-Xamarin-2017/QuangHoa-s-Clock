package com.qh.myclock.controls

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.util.*


/**
 * Created by osx on 12/31/17.
 */
class Clock : View{
    private val clockBorderWidth = 20f
    private val clockBorderMargin = clockBorderWidth /2

    private val minuteStraightLineStrokeLength = 30f
    private val hourStraightLineStrokeLength = 40f

    private val minuteStraightLineStrokeWidth = 2f
    private val hourStraightLineStrokeWidth = 5f

    private val clockNumberToBorderSpacing = 80

    private val hourAxisToBorderSpacing = 150f
    private val minuteAxisToBorderSpacing = 120f
    private val secondAxisToBorderSpacing = 110f

    constructor(context: Context) : super(context)
    constructor(context: Context?, attrs: AttributeSet) : super(context, attrs)

    private val paint = Paint()

    init {
        paint.isAntiAlias = true
        paint.textSize = 30f
        paint.style = Paint.Style.STROKE
        paint.textAlign = Paint.Align.CENTER
    }

    private fun transformToMathAngeUnit(deg: Float): Double{
        return (180 - deg) * Math.PI / 180
    }

    private fun drawLineToCenter(canvas: Canvas,
                                 startSpacing: Float,
                                 endSpacing: Float,
                                 radius: Float,
                                 ange: Float,
                                 centerX: Float,
                                 centerY: Float,
                                 strokeWidth: Float){

        paint.strokeWidth = strokeWidth
        val mathAnge = transformToMathAngeUnit(ange)

        val x1 = (radius - startSpacing) * Math.sin(mathAnge) + centerX
        val y1 = (radius - startSpacing) * Math.cos(mathAnge) + centerY

        val x2  = (radius - endSpacing) * Math.sin(mathAnge) + centerX
        val y2 = (radius - endSpacing) * Math.cos(mathAnge) + centerY

        canvas.drawLine(x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat(), paint)
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        if(canvas != null){

            val centerX = canvas.width/2f
            val centerY = canvas.height/2f
            val radius = Math.min(centerX, centerY) - clockBorderMargin

            paint.color = Color.BLACK

            drawMinutes(canvas, radius, centerX, centerY)

            drawHours(canvas, radius, centerX, centerY)

            drawClockBorder(canvas, centerX, centerY, radius)

            drawClockAxises(canvas, radius, centerX, centerY)
        }
    }

    private fun drawHours(canvas: Canvas, radius: Float, centerX: Float, centerY: Float) {
        for (hour in 1..12) {
            drawLineToCenter(canvas,
                    0f,
                    hourStraightLineStrokeLength,
                    radius,
                    hour * 30f,
                    centerX,
                    centerY,
                    hourStraightLineStrokeWidth
            )
            drawClockNumber(canvas, hour, centerX, centerY, radius)
        }
    }

    private fun drawMinutes(canvas: Canvas, radius: Float, centerX: Float, centerY: Float) {
        for (number in 1..60) {
            drawLineToCenter(canvas,
                    0f,
                    minuteStraightLineStrokeLength,
                    radius,
                    number * 6f,
                    centerX,
                    centerY,
                    minuteStraightLineStrokeWidth
            )
        }
    }

    private fun drawClockNumber(canvas: Canvas, number: Int, centerX: Float, centerY: Float, radius: Float) {
        val mathAnge = transformToMathAngeUnit(number * 30f)

        val textX = (radius - clockNumberToBorderSpacing) * Math.sin(mathAnge) + centerX
        val textY = (radius - clockNumberToBorderSpacing) * Math.cos(mathAnge) + centerY

        canvas.drawText(number.toString(), textX.toFloat(), textY.toFloat(), paint)
    }

    private fun drawClockBorder(canvas: Canvas, centerX: Float, centerY: Float, radius: Float) {
        paint.color = Color.rgb(0x37, 0x98, 0xDA)
        paint.strokeWidth = clockBorderWidth

        canvas.drawCircle(centerX, centerY, radius, paint)
    }

    private fun drawClockAxises(canvas: Canvas, radius: Float, centerX: Float, centerY: Float) {
        fun drawClockAxis(spacing: Float, ange: Float, strokeWidth: Float){
            drawLineToCenter(canvas,
                    spacing,
                    radius,
                    radius,
                    ange,
                    centerX,
                    centerY,
                    strokeWidth)
        }
        val now = Calendar.getInstance()
        val hour = now.get(Calendar.HOUR_OF_DAY)
        val minute = now.get(Calendar.MINUTE)
        val second = now.get(Calendar.SECOND)

        drawClockAxis(hourAxisToBorderSpacing,hour * 30 + minute / 2f, 15f)
        drawClockAxis(minuteAxisToBorderSpacing,minute * 6 + second / 10f,6f)
        drawClockAxis(secondAxisToBorderSpacing,second * 6f,4f)
    }
}