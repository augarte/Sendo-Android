package augarte.sendo.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import augarte.sendo.R
import augarte.sendo.adapter.WorkoutProgressAdapter
import augarte.sendo.dataModel.Serie
import kotlinx.android.synthetic.main.fragment_pager_progress.*

class WorkoutPagerProgressFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pager_progress, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val list: ArrayList<Serie> = ArrayList()
        val serie = Serie()
        list.add(serie)

        progress_list.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = WorkoutProgressAdapter(list)
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }
}