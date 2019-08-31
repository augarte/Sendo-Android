package augarte.sendo.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import augarte.sendo.R

class ExerciseChooserFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.botttomsheet_choose_exercise, container, false)
    }
}