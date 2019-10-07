package augarte.sendo.fragment

import android.os.Bundle
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import augarte.sendo.R
import augarte.sendo.activity.WorkoutDayActivity
import augarte.sendo.adapter.ExerciseDayAdapter
import augarte.sendo.dataModel.ExerciseDay
import kotlinx.android.synthetic.main.fragment_pager_exercise.*


class DayPagerExercisesFragment(private val exerciseDays: ArrayList<ExerciseDay>, private val listener: WorkoutDayActivity.OnExerciseDayClickListener) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pager_exercise, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val workoutDayAdapter = ExerciseDayAdapter(exerciseDays, listener)
        day_list.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = workoutDayAdapter
        }

        ActivityCompat.startPostponedEnterTransition(requireActivity())
    }
}