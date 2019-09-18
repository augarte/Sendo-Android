package augarte.sendo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import augarte.sendo.R
import kotlinx.android.synthetic.main.item_workout_card.*

class WorkoutPagerWorkoutFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pager_workout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        workout_card.isClickable = false
    }
}