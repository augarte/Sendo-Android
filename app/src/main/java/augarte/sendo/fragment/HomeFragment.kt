package augarte.sendo.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import augarte.sendo.R
import augarte.sendo.activity.WorkoutActivity
import augarte.sendo.adapter.WorkoutAdapter
import augarte.sendo.dataModel.Workout
import kotlinx.android.synthetic.main.fragment_home.*
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import augarte.sendo.activity.CreateWorkoutActivity
import augarte.sendo.activity.MainActivity
import augarte.sendo.activity.SearchWorkoutActivity
import augarte.sendo.view.CustomTapTargetView
import com.getkeepsafe.taptargetview.TapTargetView
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView

class HomeFragment : Fragment(){

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val workoutList : ArrayList<Workout> = MainActivity.dbHandler!!.getAllWorkouts()

        if (workoutList.size > 0) {
            val workoutAdapter = WorkoutAdapter(workoutList)
            workoutAdapter.onItemClick = { pair ->
                val intent = Intent(activity, WorkoutActivity::class.java)
                intent.putExtra("workout", pair.first)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(), pair.second, "workoutCard")
                    startActivity(intent, options.toBundle())
                } else {
                    startActivity(intent)
                }
            }
            workout_rv.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = workoutAdapter
            }
        } else{
            workout_rv.visibility = View.GONE
            new_workokut_layout.visibility = View.VISIBLE
            showTapTarget()
        }

        speedDial.addActionItem(
            SpeedDialActionItem.Builder(R.id.fab_new, R.drawable.ic_dumbbell_add)
                    .setFabBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorAccentLight, null))
                    .setFabImageTintColor(ResourcesCompat.getColor(resources, R.color.black, null))
                    .setLabel("New")
                    .setLabelColor(ResourcesCompat.getColor(resources, R.color.black, null))
                    .setLabelBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorAccentLight, null))
                    .setLabelClickable(false)
                    .create()
        )

        speedDial.addActionItem(
            SpeedDialActionItem.Builder(R.id.fab_search, R.drawable.ic_dumbbell_search)
                    .setFabBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorAccentLight, null))
                    .setFabImageTintColor(ResourcesCompat.getColor(resources, R.color.black, null))
                    .setLabel("Search")
                    .setLabelColor(ResourcesCompat.getColor(resources, R.color.black, null))
                    .setLabelBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorAccentLight, null))
                    .setLabelClickable(false)
                    .create()
        )
        speedDial.setOnActionSelectedListener { speedDialActionItem ->
            when (speedDialActionItem.id) {
                R.id.fab_new -> {
                    val intent = Intent(activity, CreateWorkoutActivity::class.java)
                    startActivity(intent)
                    Log.d("tag","Link action clicked!")
                    false // true to keep the Speed Dial open
                }
                R.id.fab_search -> {
                    val intent = Intent(activity, SearchWorkoutActivity::class.java)
                    startActivity(intent)
                    Log.d("tag","Link action clicked!")
                    false // true to keep the Speed Dial open
                }
                else -> false
            }
        }

        speedDial.setOnChangeListener(object : SpeedDialView.OnChangeListener {
            override fun onMainActionSelected() : Boolean {
                // Call your main action here
                Log.d("TAG", "Speed dial toggle state changed. Open = ")
                return false // true to keep the Speed Dial open
            }

            override fun onToggleChanged(isOpen: Boolean) {
                Log.d("TAG", "Speed dial toggle state changed. Open = $isOpen")
            }
        })

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        activity?.menuInflater?.inflate(R.menu.option_menu, menu)
        menu.findItem(R.id.search)?.isVisible = true
    }

    private fun showTapTarget() {
        val listener = object : TapTargetView.Listener() {
            override fun onTargetClick(view: TapTargetView) {
                super.onTargetClick(view)
                speedDial.open()
            }
        }
       CustomTapTargetView.showCustomTapTarget(requireActivity(), speedDial, "Add a new workout", "Click the button to add your first workout", listener)
    }
}