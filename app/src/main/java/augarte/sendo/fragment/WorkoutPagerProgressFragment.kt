package augarte.sendo.fragment

import android.os.Bundle
import android.util.SparseArray
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import augarte.sendo.R
import augarte.sendo.activity.MainActivity
import augarte.sendo.adapter.WorkoutProgressAdapter
import augarte.sendo.dataModel.Workout
import augarte.sendo.database.SelectTransactions
import kotlinx.android.synthetic.main.fragment_pager_progress.*

class WorkoutPagerProgressFragment(private val workout: Workout) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pager_progress, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        
        val exerciseDayList = MainActivity.dbHandler.getExerciseDay(SelectTransactions.SELECT_EXERCISEDAY_BY_WORKOUT_ID, arrayOf(workout.id.toString()))

/*        val progressHash = SparseArray<Progress>()

        for (exerciseDay in exerciseDayList){
            val serieGroup = progressHash[serie.exercise!!.id!!]
            if (serieGroup!=null){
                serieGroup.series.add(serie)
            } else {
                val newSerieGroup = Progress()
                newSerieGroup.exercise = serie.exercise
                newSerieGroup.series.add(serie)
                progressHash.put(serie.exercise!!.id!!, newSerieGroup)
            }
        }*/

        progress_list.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = WorkoutProgressAdapter(exerciseDayList)
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }
}