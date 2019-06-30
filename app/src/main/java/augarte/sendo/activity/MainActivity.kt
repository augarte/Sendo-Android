package augarte.sendo.activity

import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.fragment.app.Fragment
import androidx.core.view.GravityCompat
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import augarte.sendo.connection.DatabaseHandler
import augarte.sendo.fragment.ExerciseListFragment
import augarte.sendo.fragment.HomeFragment
import augarte.sendo.fragment.MeasurementsFragment
import augarte.sendo.fragment.SettingsFragment
import augarte.sendo.R
import kotlinx.android.synthetic.main.activity_home.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var dbHandler: DatabaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        //setSupportActionBar(toolbar)

        //init db
        dbHandler = DatabaseHandler(this)

        nav_view.setNavigationItemSelectedListener(this)
        nav_view.bringToFront()


        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_frame, HomeFragment.newInstance(), "Home")
        transaction.commit()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        var fragment: Fragment? = null

        when (menuItem.itemId) {
            R.id.nav_home -> {
                fragment = HomeFragment()
            }

            R.id.nav_measurement -> {
                fragment = MeasurementsFragment()
            }

            R.id.nav_exercise_list -> {
                fragment = ExerciseListFragment()
            }

            R.id.nav_settings -> {
                fragment = SettingsFragment()
            }
        }

        if (fragment != null)  {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.main_frame, fragment)
            transaction.commit()
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}