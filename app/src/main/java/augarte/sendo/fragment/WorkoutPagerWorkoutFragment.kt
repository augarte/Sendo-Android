package augarte.sendo.fragment

import android.os.Bundle
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import augarte.sendo.R
import augarte.sendo.adapter.WorkoutDayAdapter
import augarte.sendo.dataModel.Workout
import kotlinx.android.synthetic.main.fragment_pager_workout.*
import kotlinx.android.synthetic.main.item_workout_card.*


class WorkoutPagerWorkoutFragment(private val workout: Workout) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pager_workout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        card_text.text = workout.name
        card_image.setImageBitmap(workout.image)
        workout_card.isClickable = false

        day_list.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = WorkoutDayAdapter(workout.dayList)
        }


        setHasOptionsMenu(true)
        ActivityCompat.startPostponedEnterTransition(requireActivity())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)
        menu.findItem(R.id.delete).isVisible = true
    }
}