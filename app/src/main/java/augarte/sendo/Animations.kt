package augarte.sendo

import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup

class Animations {
    companion object {
        fun toggleCardBody(body: View, from : Int, to : Int) {
            body.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

            val expandAnimator = ValueAnimator
                    .ofInt(from, to)
                    .setDuration(200)

            expandAnimator.addUpdateListener {
                val value = it.animatedValue as Int
                body.layoutParams.height = value
                body.requestLayout()
            }

            expandAnimator.start()
        }

        fun rotate(view : View, rotation : Float) {
            view.animate().rotation(rotation).start()
        }
    }
}