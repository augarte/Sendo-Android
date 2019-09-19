package augarte.sendo.fragment

import android.os.Bundle
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import augarte.sendo.R
import augarte.sendo.dataModel.Workout
import kotlinx.android.synthetic.main.item_workout_card.*


class WorkoutPagerWorkoutFragment(private val workout: Workout) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pager_workout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        card_text.text = workout.name
        card_image.setImageBitmap(workout.image)
        workout_card.isClickable = false

        ActivityCompat.startPostponedEnterTransition(requireActivity())

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)
        menu.findItem(R.id.delete).isVisible = true
    }
}