package augarte.sendo.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import augarte.sendo.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_home, container, false)

//        fab_add.setOnClickListener {
//            fab_add.animate().rotation(fab_add.rotation+45).start()
//        }

        return view
    }
}