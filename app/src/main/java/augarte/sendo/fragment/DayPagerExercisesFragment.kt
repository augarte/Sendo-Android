package augarte.sendo.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import augarte.sendo.R
import augarte.sendo.activity.AddProgressActivity
import augarte.sendo.adapter.ExerciseDayAdapter
import augarte.sendo.adapter.WorkoutDayAdapter
import augarte.sendo.dataModel.ExerciseDay
import kotlinx.android.synthetic.main.fragment_pager_exercise.*
import kotlinx.android.synthetic.main.item_workout_card.*


class DayPagerExercisesFragment(private val exerciseDays: ArrayList<ExerciseDay>) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pager_exercise, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val workoutDayAdapter = ExerciseDayAdapter(exerciseDays)
        /*workoutDayAdapter.onItemClick = { pair ->
            val intent = Intent(activity, AddProgressActivity::class.java)
            intent.putExtra("day", pair.first)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(), pair.second, "dayCard")
                startActivity(intent, options.toBundle())
            } else {
                startActivity(intent)
            }
        }*/
        day_list.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = workoutDayAdapter
        }
        ActivityCompat.startPostponedEnterTransition(requireActivity())
    }
}