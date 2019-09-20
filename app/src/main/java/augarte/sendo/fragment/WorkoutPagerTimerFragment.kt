package augarte.sendo.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import augarte.sendo.R
import augarte.sendo.utils.Constants
import kotlinx.android.synthetic.main.fragment_pager_timer.*
import java.util.*
import kotlin.concurrent.timer

class WorkoutPagerTimerFragment : Fragment() {

    private var timerTime: Int = 0
    private var timerFirstStart: Boolean = true
    private lateinit  var timer: Timer
    private var sharedPreferences: SharedPreferences? = null
    private var sharedTimer: Long = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pager_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sharedPreferences = activity?.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE)
        sharedTimer = sharedPreferences?.getLong(Constants.SHARED_TIMER, 0)!!

        if (sharedTimer.compareTo(0L)==1){
            play.visibility = View.GONE
            pause.visibility = View.VISIBLE
            timerTime = ((System.currentTimeMillis() - sharedTimer) / 1000).toInt()
            if(timer_time!=null) timer_time.text = secondsToMinutes(timerTime)

            timerFirstStart = false
            timer = timer("timer", true, 0, 1000) {updateTimer()}
        }

        play.setOnClickListener{
            play.visibility = View.GONE
            pause.visibility = View.VISIBLE
            timer = timer("timer", true, 0, 1000) {updateTimer()}
        }

        pause.setOnClickListener {
            pause.visibility = View.GONE
            reset.visibility = View.VISIBLE
            timer.cancel()
            sharedPreferences!!.edit().putLong(Constants.SHARED_TIMER, 0).apply()
        }

        reset.setOnClickListener {
            reset.visibility = View.GONE
            play.visibility = View.VISIBLE
            timer_time.text = "00:00"
            timerTime = 0
            sharedPreferences!!.edit().putLong(Constants.SHARED_TIMER, 0).apply()
        }

    }
    
    private fun updateTimer(){
        if(timerFirstStart) {
            sharedPreferences!!.edit().putLong(Constants.SHARED_TIMER, System.currentTimeMillis()).apply()
            timerFirstStart = false
        } else {
            timerTime++
            if(timer_time!=null) timer_time.text = secondsToMinutes(timerTime)
        }
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