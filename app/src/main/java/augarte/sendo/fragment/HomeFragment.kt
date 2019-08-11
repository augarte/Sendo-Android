package augarte.sendo.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import augarte.sendo.R
import augarte.sendo.activity.WorkoutActivity
import augarte.sendo.adapter.WorkoutAdapter
import augarte.sendo.dataModel.Workout
import kotlinx.android.synthetic.main.fragment_home.*
import androidx.core.app.ActivityOptionsCompat



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

        val workoutList : ArrayList<Workout> = ArrayList()
        val w = Workout()
        w.name = "Workout"
        workoutList.add(w)
        workoutList.add(w)
        workoutList.add(w)
        workoutList.add(w)
        workoutList.add(w)
        workoutList.add(w)
        workoutList.add(w)
        workoutList.add(w)

        val adapter = WorkoutAdapter(workoutList)
        var layoutManager = LinearLayoutManager(context)
        workout_rv.layoutManager = layoutManager
        workout_rv.adapter = adapter
        adapter.onItemClick = { pair ->
            val intent = Intent(activity, WorkoutActivity::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                intent.putExtra("workout", pair.first.id)
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(), pair.second, "workoutCard")
                startActivity(intent, options.toBundle())
            }
            else {
                startActivity(intent)
            }


        }


        fab_add.setOnClickListener {
            fab_add.animate().rotation(if (fab_add.rotation==0f) fab_add.rotation+45 else fab_add.rotation-45).start()
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        activity?.menuInflater?.inflate(R.menu.option_menu, menu)
        menu?.findItem(R.id.search)?.isVisible = true

    }
}