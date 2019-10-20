package augarte.sendo.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import augarte.sendo.R
import augarte.sendo.dataModel.Exercise
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.dialog_exercise_info.view.*

class ExerciseInfoDialogFragment(private val exercise: Exercise): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.dialog_exercise_info, null)

        Glide.with(context!!).load(exercise.imageURL).placeholder(R.drawable.ic_sendo_placeholder).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(view.exercise_image)
        view.exercise_name.text = exercise.name
        view.exercise_description.text = exercise.description

        return AlertDialog.Builder(activity).setView(view).create()
    }
}