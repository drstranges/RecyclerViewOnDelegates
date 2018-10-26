/*
 *  Copyright Roman Donchenko. All Rights Reserved.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.drprog.recyclerviewondelegates.util

import android.graphics.*
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapResource

/**
 * Custom Glide Transform for make circle image with colored border
 */
class CircleBorderedTransform(private val mBitmapPool: BitmapPool, private val mBorderColor: Int) : Transformation<Bitmap> {
    private val mPathEffect = CornerPathEffect(10f)


    override fun transform(resource: Resource<Bitmap>, outWidth: Int, outHeight: Int): Resource<Bitmap> {
        val source = resource.get()
        val size = Math.min(source.width, source.height)

        val width = (source.width - size) / 2
        val height = (source.height - size) / 2

        var bitmap: Bitmap? = mBitmapPool.get(size, size, Bitmap.Config.ARGB_8888)
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        }

        val canvas = Canvas(bitmap!!)
        val paint = Paint()
        val shader = BitmapShader(source, Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP)
        if (width != 0 || height != 0) {
            val matrix = Matrix()
            matrix.setTranslate((-width).toFloat(), (-height).toFloat())
            shader.setLocalMatrix(matrix)
        }
        paint.shader = shader
        paint.isAntiAlias = true

        val r = size / 2f
        val stroke = getStrokeWidth(r)
        canvas.drawCircle(r, r, r, paint)

        preparePaintForCircleBorder(paint, stroke)

        canvas.drawCircle(r, r, r - stroke / 2f, paint)

        return BitmapResource.obtain(bitmap, mBitmapPool) as Resource<Bitmap>
    }

    private fun preparePaintForCircleBorder(_paint: Paint, _stroke: Float) {
        _paint.shader = null
        _paint.color = mBorderColor
        _paint.style = Paint.Style.STROKE
        _paint.strokeJoin = Paint.Join.ROUND
        _paint.strokeCap = Paint.Cap.ROUND
        _paint.pathEffect = mPathEffect
        _paint.strokeWidth = _stroke
    }

    /**
     * @param radius - circle radius
     * @return - the stroke width based on the radius size
     */
    private fun getStrokeWidth(radius: Float): Float {
        return radius * 0.06f
    }

    override fun getId() = "CircleBorderedTransform"
}
