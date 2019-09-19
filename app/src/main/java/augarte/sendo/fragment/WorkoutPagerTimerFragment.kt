package augarte.sendo.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import augarte.sendo.R
import kotlinx.android.synthetic.main.fragment_pager_timer.*
import java.util.*
import kotlin.concurrent.timer

class WorkoutPagerTimerFragment : Fragment() {

    var timerTime: Int = 0
    lateinit  var timer: Timer

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pager_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        play.setOnClickListener{
            play.visibility = View.GONE
            pause.visibility = View.VISIBLE
            timer = timer("timer", true, 1000, 1000) {updateTimer()}
        }

        pause.setOnClickListener {
            pause.visibility = View.GONE
            reset.visibility = View.VISIBLE
            timer.cancel()
        }

        reset.setOnClickListener {
            reset.visibility = View.GONE
            play.visibility = View.VISIBLE
            timer_time.text = "00:00"
            timerTime = 0
        }

    }

    private fun updateTimer(){
        timerTime++
        timer_time.text = secondsToMinutes(timerTime)
    }

    private fun secondsToMinutes(s: Int): String {
        val minutes = s/60
        val seconds = s%60
        val minutesString: String
        val secondsString: String

        minutesString = if (minutes<10) "0$minutes"
        else "$minutes"

        secondsString = if (seconds<10) "0$seconds"
        else "$seconds"

        return "$minutesString:$secondsString"
    }
}