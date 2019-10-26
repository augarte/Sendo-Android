package augarte.sendo.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import augarte.sendo.R
import augarte.sendo.activity.CreateWorkoutActivity
import augarte.sendo.activity.MainActivity
import augarte.sendo.activity.SearchWorkoutActivity
import augarte.sendo.activity.WorkoutActivity
import augarte.sendo.adapter.WorkoutAdapter
import augarte.sendo.dataModel.Workout
import augarte.sendo.database.SelectTransactions
import augarte.sendo.utils.Utils
import augarte.sendo.view.CustomTapTargetView
import com.getkeepsafe.taptargetview.TapTargetView
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val workoutList : ArrayList<Workout> = MainActivity.dbHandler.getWorkouts(SelectTransactions.SELECT_ALL_WORKOUT_ORDER_NAME, null)

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
                layoutManager = GridLayoutManager(context, 3, GridLayoutManager.HORIZONTAL,false)
                adapter = workoutAdapter
            }
        } else{
            workout_rv.visibility = View.GONE
            new_workokut_layout.visibility = View.VISIBLE
            showTapTarget()
        }

        if (workoutList.size>=3) fab.visibility = View.GONE
        fab.setOnClickListener {
            val intent = Intent(activity, CreateWorkoutActivity::class.java)
            startActivity(intent)
        }
/*        speedDial.mainFabOpenedBackgroundColor = Utils.getColorFromAttr(context, R.attr.fab)
        speedDial.mainFabClosedBackgroundColor = Utils.getColorFromAttr(context, R.attr.fab)
        speedDial.addActionItem(
            SpeedDialActionItem.Builder(R.id.fab_new, R.drawable.ic_dumbbell_add)
                .setFabBackgroundColor(Utils.getColorFromAttr(context, R.attr.fabChild))
                .setFabImageTintColor(ResourcesCompat.getColor(resources, R.color.black, null))
                .setLabel(getString(R.string.sendo_new))
                .setLabelColor(ResourcesCompat.getColor(resources, R.color.black, null))
                .setLabelBackgroundColor(Utils.getColorFromAttr(context, R.attr.fabChild))
                .setLabelClickable(false)
                .create()
        )

        speedDial.addActionItem(
            SpeedDialActionItem.Builder(R.id.fab_search, R.drawable.ic_dumbbell_search)
                .setFabBackgroundColor(Utils.getColorFromAttr(context, R.attr.fabChild))
                .setFabImageTintColor(ResourcesCompat.getColor(resources, R.color.black, null))
                .setLabel(getString(R.string.sendo_search))
                .setLabelColor(ResourcesCompat.getColor(resources, R.color.black, null))
                .setLabelBackgroundColor(Utils.getColorFromAttr(context, R.attr.fabChild))
                .setLabelClickable(false)
                .create()
        )
        speedDial.setOnActionSelectedListener { speedDialActionItem ->
            when (speedDialActionItem.id) {
                R.id.fab_new -> {
                    val intent = Intent(activity, CreateWorkoutActivity::class.java)
                    startActivity(intent)
                    false // true to keep the Speed Dial open
                }
                R.id.fab_search -> {
                    val intent = Intent(activity, SearchWorkoutActivity::class.java)
                    startActivity(intent)
                    false // true to keep the Speed Dial open
                }
                else -> false
            }
        }
        speedDial.setOnChangeListener(object : SpeedDialView.OnChangeListener {
            override fun onMainActionSelected(): Boolean {
                return false
            }

            override fun onToggleChanged(isOpen: Boolean) {
            }
        })*/

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        activity?.menuInflater?.inflate(R.menu.option_menu, menu)
        //menu.findItem(R.id.search)?.isVisible = true
    }

    private fun showTapTarget() {
        val listener = object : TapTargetView.Listener() {
            override fun onTargetClick(view: TapTargetView) {
                super.onTargetClick(view)
                //speedDial.open()
                fab.performClick()
            }
        }
       CustomTapTargetView.showCustomTapTarget(requireActivity(), fab, getString(R.string.sendo_taptarget_workout_title), getString(R.string.sendo_taptarget_workout_description), listener)
    }
}