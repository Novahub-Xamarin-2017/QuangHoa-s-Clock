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
    private val arcWidth = 20f
    private val padding = arcWidth/2

    private val minuteStrokeLength = 30f
    private val hourStrokeLength = 40f

    private val minuteStrokeWidth = 2f
    private val hourStrokeWidth = 5f

    private val textSpacing = 80

    private val hourAxisSpacing = 150f
    private val minuteAxisSpacing = 120f
    private val secondAxisSpacing = 110f


    constructor(context: Context) : super(context)
    constructor(context: Context?, attrs: AttributeSet) : super(context, attrs)

    private val paint = Paint()

    init {
        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE
        paint.textSize = 30f
        paint.textAlign = Paint.Align.CENTER
    }

    private fun transformToMathAngeUnit(deg: Int): Double{
        return (180 - deg) * Math.PI / 180
    }

    private fun drawLineToCenter(canvas: Canvas,
                                 startSpacing: Float,
                                 endSpacing: Float,
                                 radius: Float,
                                 ange: Int,
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
            val radius = Math.min(centerX, centerY) - padding

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
                    hourStrokeLength,
                    radius,
                    hour * 30,
                    centerX,
                    centerY,
                    hourStrokeWidth
            )
            drawClockNumbers(radius, hour, centerX, centerY, canvas)
        }
    }

    private fun drawMinutes(canvas: Canvas, radius: Float, centerX: Float, centerY: Float) {
        for (number in 1..60) {
            drawLineToCenter(canvas,
                    0f,
                    minuteStrokeLength,
                    radius,
                    number * 6,
                    centerX,
                    centerY,
                    minuteStrokeWidth
            )
        }
    }

    private fun drawClockNumbers(radius: Float, number: Int, centerX: Float, centerY: Float, canvas: Canvas) {
        val mathAnge = transformToMathAngeUnit(number * 30)
        val textX = (radius - textSpacing) * Math.sin(mathAnge) + centerX
        val textY = (radius - textSpacing) * Math.cos(mathAnge) + centerY

        canvas.drawText(number.toString(), textX.toFloat(), textY.toFloat(), paint)
    }

    private fun drawClockBorder(canvas: Canvas, centerX: Float, centerY: Float, radius: Float) {
        paint.color = Color.rgb(0x37, 0x98, 0xDA)
        paint.strokeWidth = arcWidth
        canvas.drawCircle(centerX, centerY, radius, paint)
    }

    private fun drawClockAxises(canvas: Canvas, radius: Float, centerX: Float, centerY: Float) {
        val now = Calendar.getInstance()
        val hour = now.get(Calendar.HOUR_OF_DAY)
        val minute = now.get(Calendar.MINUTE)
        val second = now.get(Calendar.SECOND)

        drawLineToCenter(canvas,
                hourAxisSpacing,
                radius,
                radius,
                hour * 30 + minute / 2,
                centerX,
                centerY,
                15f)

        drawLineToCenter(canvas,
                minuteAxisSpacing,
                radius,
                radius,
                minute * 6 + second / 10,
                centerX,
                centerY,
                6f)

        drawLineToCenter(canvas,
                secondAxisSpacing,
                radius,
                radius,
                second * 6,
                centerX,
                centerY,
                4f)
    }
}