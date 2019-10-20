package augarte.sendo.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import augarte.sendo.R
import augarte.sendo.adapter.ExerciseChooseAdapter
import augarte.sendo.dataModel.Exercise
import augarte.sendo.dataModel.ExerciseDay
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.dialog_exercise_add_to_workout.view.*
import kotlinx.android.synthetic.main.dialog_exercise_info.view.exercise_image
import kotlinx.android.synthetic.main.dialog_exercise_info.view.exercise_name

class AddExerciseToWorkoutDialogFragment(private val exercise: Exercise, private val listener: ExerciseChooseAdapter.ExerciseAddDialogListener): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.dialog_exercise_add_to_workout, null)

        Glide.with(context!!).load(exercise.imageURL).placeholder(R.drawable.ic_sendo_placeholder).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(view.exercise_image)
        view.exercise_name.text = exercise.name

        return AlertDialog.Builder(activity)
                .setPositiveButton(getString(R.string.sendo_add)) { _, _ ->
                    val exerciseDay = ExerciseDay()
                    exerciseDay.exercise = exercise
                    exerciseDay.serieNum = view.serieET.text.toString().toInt()
                    exerciseDay.repNum = view.repET.text.toString().toInt()

                    listener.onExerciseAdded(exerciseDay)
                }
                .setNegativeButton(getString(R.string.sendo_cancel)) { _, _ -> }
                .setView(view)
                .create()
    }
}