package augarte.sendo.utils

import android.content.res.Resources

val Int.toDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.toPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Int.toDpF: Float
    get() = (this / Resources.getSystem().displayMetrics.density)

val Int.toPxF: Float
    get() = (this * Resources.getSystem().displayMetrics.density)

