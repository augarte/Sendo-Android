package augarte.sendo.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import augarte.sendo.R
import augarte.sendo.adapter.WorkoutAdapter
import augarte.sendo.adapter.WorkoutRVAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeFragment : Fragment(){

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_home, container, false)

        val rv : RecyclerView = view.findViewById(R.id.rv)
        workout_rv.adapter = WorkoutAdapter


        val fab : FloatingActionButton = view.findViewById(R.id.fab_add)
        fab.setOnClickListener {
            fab.animate().rotation(if (fab.rotation==0f) fab.rotation+45 else fab.rotation-45).start()
        }

        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        activity?.menuInflater?.inflate(R.menu.option_menu, menu)

    }


}