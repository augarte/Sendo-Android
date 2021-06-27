package augarte.sendo.fragment.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.DialogFragment
import augarte.sendo.R
import augarte.sendo.activity.MainActivity
import augarte.sendo.dataModel.Exercise
import augarte.sendo.utils.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.dialog_exercise_info.view.*

class ExerciseInfoDialogFragment(private val exercise: Exercise, private val listener: ExerciseInfoDialogListener?): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.dialog_exercise_info, null)

        Glide.with(context!!).load(exercise.imageURL).placeholder(R.drawable.ic_sendo_placeholder).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(view.exercise_image)
        view.exercise_name.text = exercise.name
        view.exercise_description.text = exercise.description

        setStarColor(view)

        view.fav.setOnClickListener {
            exercise.favorite = !exercise.favorite
            MainActivity.dbHandler.updateExerciseFav(exercise)
            setStarColor(view)
            listener?.onStarred()
        }

        return AlertDialog.Builder(activity).setView(view).create()
    }

    private fun setStarColor(view : View){
        if (exercise.favorite) {
            view.fav.setImageResource(R.drawable.ic_star_full)
            ImageViewCompat.setImageTintList(view.fav, ColorStateList.valueOf(Utils.getColorFromAttr(context, R.attr.starIcon)))
        } else {
            view.fav.setImageResource(R.drawable.ic_star_filled)
            ImageViewCompat.setImageTintList(view.fav, null)
        }
    }

    interface ExerciseInfoDialogListener{
        fun onStarred()
    }
}