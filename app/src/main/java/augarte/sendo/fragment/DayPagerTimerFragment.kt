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
import java.util.concurrent.TimeUnit
import kotlin.concurrent.timer

class DayPagerTimerFragment : Fragment() {

    private var startTime: Long = 0
    private var stopTime: Long = 0
    private lateinit  var timer: Timer
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pager_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sharedPreferences = activity?.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE)
        startTime = sharedPreferences?.getLong(Constants.SHARED_TIMER_START, 0)!!
        stopTime = sharedPreferences?.getLong(Constants.SHARED_TIMER_STOP, 0)!!

        if (startTime > 0 && stopTime > 0) {
            play.visibility = View.GONE
            reset.visibility = View.VISIBLE

            updateTimer()
        } else if (startTime > 0) {
            play.visibility = View.GONE
            pause.visibility = View.VISIBLE

            timer = timer("timer", false, 0, 10) {activity?.runOnUiThread {updateTimer()}}
        } else {
            startTime = 0
            stopTime = 0
        }

        play.setOnClickListener{
            startTime = System.currentTimeMillis()
            timer = timer("timer", false, 0, 10) {activity?.runOnUiThread {updateTimer()}}

            play.visibility = View.GONE
            pause.visibility = View.VISIBLE

            sharedPreferences?.edit()?.putLong(Constants.SHARED_TIMER_START, startTime)?.apply()
        }

        pause.setOnClickListener {
            stopTime = System.currentTimeMillis()
            cleanTimer()

            pause.visibility = View.GONE
            reset.visibility = View.VISIBLE

            sharedPreferences?.edit()?.putLong(Constants.SHARED_TIMER_STOP, stopTime)?.apply()
        }

        reset.setOnClickListener {
            startTime = 0
            stopTime = 0
            cleanTimer()

            reset.visibility = View.GONE
            play.visibility = View.VISIBLE

            timer_time.text = "00:00"
            timer_milliseconds.text = "00"

            sharedPreferences?.edit()?.putLong(Constants.SHARED_TIMER_START, 0)?.apply()
            sharedPreferences?.edit()?.putLong(Constants.SHARED_TIMER_STOP, 0)?.apply()
        }
    }

    private fun updateTimer(){
        if (stopTime > 0) {
            if(timer_time!=null) milisecondsToTimeFormat((stopTime - startTime))
        } else {
            if(timer_time!=null) milisecondsToTimeFormat((System.currentTimeMillis() - startTime))
        }
    }

    private fun milisecondsToTimeFormat(ms: Long) {
        val hours = TimeUnit.MILLISECONDS.toHours(ms)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(ms) - TimeUnit.HOURS.toMinutes(hours)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(ms) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(ms))
        val millisecs = ((ms - TimeUnit.SECONDS.toMillis(TimeUnit.HOURS.toSeconds(hours) + TimeUnit.MINUTES.toSeconds(minutes) + seconds))/10)
        val hoursString: String
        val minutesString: String
        val secondsString: String
        val millisecsString: String

        hoursString = when {
            hours==0L -> ""
            hours<10 -> "0$hours:"
            else -> "$hours:"
        }

        minutesString = if (minutes<10) "0$minutes"
        else "$minutes"

        secondsString = if (seconds<10) "0$seconds"
        else "$seconds"

        millisecsString = if (millisecs<10) "0$millisecs"
        else "$millisecs"

        timer_time.text = "$hoursString$minutesString:$secondsString"
        timer_milliseconds.text = millisecsString
    }

    private fun cleanTimer() {
        if (::timer.isInitialized) {
            timer.cancel()
            timer.purge()
        }
    }

    override fun onStop() {
        super.onStop()
        cleanTimer()
    }
}