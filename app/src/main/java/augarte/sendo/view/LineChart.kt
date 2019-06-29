package augarte.sendo.view


import java.util.ArrayList

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Style
import android.graphics.Path
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View

class LineChart @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : View(context, attrs, defStyle) {

    private val mPaint: Paint
    private val mPath: Path
    private var mCircleSize: Float
    private val mStrokeSize: Float
    private var mBorder: Float
    private var drawCircles : Boolean

    private var mValues: Array<PointF>? = null
    private val mMinY: Float = 0.toFloat()
    private var mMaxY: Float = 0.toFloat()

    init {

        val scale = context.resources.displayMetrics.density

        mCircleSize = scale * CIRCLE_SIZE
        mStrokeSize = scale * STROKE_SIZE
        mBorder = mCircleSize/2
        drawCircles = true

        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.strokeWidth = mStrokeSize

        mPath = Path()

        setData(arrayOf(PointF(15f, 39f), // {x, y}
                PointF(20f, 21f), PointF(28f, 9f), PointF(37f, 21f), PointF(40f, 25f), PointF(50f, 31f), PointF(62f, 24f), PointF(80f, 28f)))

    }

    fun setNoCircle(){
        drawCircles = false
        mBorder = 0f
        mCircleSize = 0f
    }

    fun setData(values: Array<PointF>?) {
        mValues = values

        if (values != null && values.size > 0) {
            mMaxY = values[0].y
            //mMinY = values[0].y;
            for (point in values) {
                val y = point.y
                if (y > mMaxY)
                    mMaxY = y
                /*if (y < mMinY)
					mMinY = y;*/
            }
        }

        invalidate()
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        if (mValues == null || mValues!!.size == 0)
            return

        val size = mValues!!.size

        val height = measuredHeight - 2 * mBorder
        val width = measuredWidth - 2 * mBorder

        val left = mValues!![0].x
        val right = mValues!![mValues!!.size - 1].x
        val dX : Float = if (right - left > 0) right - left else 2f
        val dY : Float = if (mMaxY - mMinY > 0) mMaxY - mMinY else 2f

        mPath.reset()

        // calculate point coordinates
        val points = ArrayList<PointF>(size)
        for (point in mValues!!) {x
            val x = mBorder + (point.x - left) * width / dX
            val y = mBorder + height - (point.y - mMinY) * height / dY
            points.add(PointF(x, y))
        }

        // calculate smooth path
        var lX = 0f
        var lY = 0f
        mPath.moveTo(points[0].x, points[0].y)
        for (i in 1 until size) {
            val p = points[i]    // current point

            // first control point
            val p0 = points[i - 1]    // previous point
            val d0 = Math.sqrt(Math.pow((p.x - p0.x).toDouble(), 2.0) + Math.pow((p.y - p0.y).toDouble(), 2.0)).toFloat()    // distance between p and p0
            val x1 = Math.min(p0.x + lX * d0, (p0.x + p.x) / 2)    // min is used to avoid going too much right
            val y1 = p0.y + lY * d0

            // second control point
            val p1 = points[if (i + 1 < size) i + 1 else i]    // next point
            val d1 = Math.sqrt(Math.pow((p1.x - p0.x).toDouble(), 2.0) + Math.pow((p1.y - p0.y).toDouble(), 2.0)).toFloat()    // distance between p1 and p0 (length of reference line)
            lX = (p1.x - p0.x) / d1 * SMOOTHNESS        // (lX,lY) is the slope of the reference line
            lY = (p1.y - p0.y) / d1 * SMOOTHNESS
            val x2 = Math.max(p.x - lX * d0, (p0.x + p.x) / 2)    // max is used to avoid going too much left
            val y2 = p.y - lY * d0

            // add line
            mPath.cubicTo(x1, y1, x2, y2, p.x, p.y)
        }

        // draw path
        mPaint.color = CHART_COLOR
        mPaint.style = Style.STROKE
        canvas.drawPath(mPath, mPaint)

        // draw area
        if (size > 0) {
            mPaint.style = Style.FILL
            mPaint.color = (CHART_COLOR and 0xFFFFFF) or 0x10000000
            mPath.lineTo(points[size - 1].x+mBorder, points[size - 1].y)
            mPath.lineTo(points[size - 1].x+mBorder, height + mCircleSize)
            mPath.lineTo(points[0].x-mBorder, height + mCircleSize)
            mPath.lineTo(points[0].x-mBorder, points[0].y)
            mPath.close()
            canvas.drawPath(mPath, mPaint)
        }

        if (drawCircles) {
            // draw circles
            mPaint.color = CHART_COLOR
            mPaint.style = Style.FILL_AND_STROKE
            for (point in points) {
                canvas.drawCircle(point.x, point.y, mCircleSize / 2, mPaint)
            }
            mPaint.style = Style.FILL
            mPaint.color = Color.WHITE
            for (point in points) {
                canvas.drawCircle(point.x, point.y, (mCircleSize - mStrokeSize) / 2, mPaint)
            }
        }
    }

    companion object {
        private val CHART_COLOR = Color.BLACK
        private val CIRCLE_SIZE = 6
        private val STROKE_SIZE = 2
        private val SMOOTHNESS = 0.3f // the higher the smoother, but don't go over 0.5
    }
}
