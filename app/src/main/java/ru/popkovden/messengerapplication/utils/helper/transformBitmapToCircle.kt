package ru.popkovden.messengerapplication.utils.helper

import android.graphics.*

fun getCircleBitmap(bitmap: Bitmap): Bitmap {
    val output: Bitmap
    val srcRect: Rect
    val dstRect: Rect
    val r: Float
    val width = bitmap.width
    val height = bitmap.height
    if (width > height) {
        output = Bitmap.createBitmap(height, height, Bitmap.Config.ARGB_8888)
        val left = (width - height) / 2
        val right = left + height
        srcRect = Rect(left, 0, right, height)
        dstRect = Rect(0, 0, height, height)
        r = height / 2.toFloat()
    } else {
        output = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888)
        val top = (height - width) / 2
        val bottom = top + width
        srcRect = Rect(0, top, width, bottom)
        dstRect = Rect(0, 0, width, width)
        r = width / 2.toFloat()
    }
    val canvas = Canvas(output)
    val color = -0xbdbdbe
    val paint = Paint()
    paint.isAntiAlias = true
    canvas.drawARGB(0, 0, 0, 0)
    paint.color = color
    canvas.drawCircle(r, r, r, paint)
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(bitmap, srcRect, dstRect, paint)
    bitmap.recycle()
    return output
}