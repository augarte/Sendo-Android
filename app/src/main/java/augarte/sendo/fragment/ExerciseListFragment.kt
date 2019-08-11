package augarte.sendo.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import augarte.sendo.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ExerciseListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_exercise_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fab : FloatingActionButton = view.findViewById(R.id.fab_add)
        fab.setOnClickListener {
            fab.animate().rotation(if (fab.rotation==0f) fab.rotation+45 else fab.rotation-45).start()
        }

        setHasOptionsMenu(true)
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        activity?.menuInflater?.inflate(R.menu.option_menu, menu)
        menu?.findItem(R.id.group)?.isVisible = true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId){
            R.id.group -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}