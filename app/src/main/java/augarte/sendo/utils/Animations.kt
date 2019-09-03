package augarte.sendo.utils

import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.RELATIVE_TO_SELF
import android.view.animation.ScaleAnimation



class Animations {
    companion object {
        fun toggleCardBody(body: View, from: Int, to: Int) {
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

        fun rotate(view: View, rotation: Float) {
            view.animate().rotation(rotation).start()
        }

        fun compress(view: View): Animation {
            val anim = ScaleAnimation(
                    1f, 0f,
                    1f, 0f,
                    RELATIVE_TO_SELF, 0.5f,
                    RELATIVE_TO_SELF, 0.5f)
            anim.duration = 200

            anim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationEnd(p0: Animation?) {
                    view.visibility = View.GONE
                }

                override fun onAnimationStart(p0: Animation?) {}
                override fun onAnimationRepeat(p0: Animation?) {}
            })
            return anim
        }

        fun expand(view: View): Animation {
            val anim = ScaleAnimation(
                    0f, 1f,
                    0f, 1f,
                    RELATIVE_TO_SELF, 0.5f,
                    RELATIVE_TO_SELF, 0.5f)
            anim.duration = 200

            anim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationEnd(p0: Animation?) {
                    view.visibility = View.VISIBLE
                }

                override fun onAnimationStart(p0: Animation?) {}
                override fun onAnimationRepeat(p0: Animation?) {}
            })
            return anim
        }
    }
}