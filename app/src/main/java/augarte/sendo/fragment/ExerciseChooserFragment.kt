package augarte.sendo.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import augarte.sendo.R
import kotlinx.android.synthetic.main.botttomsheet_choose_exercise.*

class ExerciseChooserFragment(var title: String) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.botttomsheet_choose_exercise, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        day_title.text = title
    }
}