package augarte.sendo.fragment

import android.os.Bundle
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import augarte.sendo.R
import augarte.sendo.activity.MainActivity
import augarte.sendo.adapter.WorkoutAdapter
import augarte.sendo.adapter.WorkoutProgressAdapter
import augarte.sendo.dataModel.Day
import augarte.sendo.database.SelectTransactions
import kotlinx.android.synthetic.main.fragment_pager_progress.*

class DayPagerProgressFragment(private val day: Day) : Fragment() {

    private lateinit var workoutAdapter: WorkoutProgressAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pager_progress, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        workoutAdapter = WorkoutProgressAdapter(day.exerciseDayList)
        progress_list.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = workoutAdapter
        }

        setHasOptionsMenu(true)
        ActivityCompat.startPostponedEnterTransition(requireActivity())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    override fun onResume() {
        super.onResume()
        day.exerciseDayList.clear()
        day.exerciseDayList.addAll(MainActivity.dbHandler.getDays(SelectTransactions.SELECT_DAYS_BY_WORKOUT_ID, arrayOf(day.workoutId.toString())).first().exerciseDayList)
        workoutAdapter.notifyDataSetChanged()
    }
}