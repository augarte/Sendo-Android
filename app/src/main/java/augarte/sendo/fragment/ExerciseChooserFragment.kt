package augarte.sendo.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import augarte.sendo.R
import augarte.sendo.activity.MainActivity
import augarte.sendo.adapter.CreateWorkoutAdapter
import augarte.sendo.adapter.ExerciseChooseAdapter
import augarte.sendo.dataModel.Exercise
import augarte.sendo.database.SelectTransactions
import kotlinx.android.synthetic.main.bottomsheet_choose_exercise.*

class ExerciseChooserFragment(private val title: String, private var selectedExercises: ArrayList<Exercise>, private val exerciseSelectedListener: CreateWorkoutAdapter.OnExerciseSelectedListener) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottomsheet_choose_exercise, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        day_title.text = title

        val exerciseList = MainActivity.dbHandler.getExercise(SelectTransactions.SELECT_ALL_EXERCISE_ORDER_NAME, null)

        for (e in exerciseList) {
            if(selectedExercises.any {x-> x.id == e.id }) e.selected = true
        }

        exercise_list.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = ExerciseChooseAdapter(exerciseList, exerciseSelectedListener, context!!)
        }
    }
}