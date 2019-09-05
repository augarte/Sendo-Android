package augarte.sendo.view

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import augarte.sendo.R
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetView

class CustomTapTargetView(context: Context, parent: ViewManager, boundingParent: ViewGroup, target: TapTarget, userListener: Listener) : TapTargetView(context, parent, boundingParent, target, userListener) {

    companion object{

        fun showCustomTapTarget(activity: Activity, view: View, title: String, description: String, listener: Listener) {
            showFor(activity,
                TapTarget.forView(view, title, description)
                    // All options below are optional
                    .outerCircleColor(R.color.tapTargetOuterCircle)      // Specify a color for the outer circle
                    .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                    .targetCircleColor(R.color.tapTargetInnerCircle)   // Specify a color for the target circle
                    .titleTextSize(28)                  // Specify the size (in sp) of the title text
                    .titleTextColor(R.color.primaryText)      // Specify the color of the title text
                    .descriptionTextSize(18)            // Specify the size (in sp) of the description text
                    .descriptionTextColor(R.color.black)  // Specify the color of the description text
                    .textColor(R.color.primaryText)            // Specify a color for both the title and description text
                    //.textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
                    .dimColor(R.color.black)            // If set, will dim behind the view with 30% opacity of the given color
                    .drawShadow(true)                   // Whether to draw a drop shadow or not
                    .cancelable(true)                  // Whether tapping outside the outer circle dismisses the view
                    .tintTarget(false)                   // Whether to tint the target view's color
                    .transparentTarget(false)           // Specify whether the target is transparent (displays the content underneath)
                    //.icon(Drawable)                     // Specify a custom drawable to draw as the target
                    .targetRadius(60),                  // Specify the target radius (in dp)
                    listener
            )
        }
    }
}