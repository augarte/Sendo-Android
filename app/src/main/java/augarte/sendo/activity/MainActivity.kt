package augarte.sendo.activity

import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.fragment.app.Fragment
import androidx.core.view.GravityCompat
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import augarte.sendo.database.DatabaseHandler
import augarte.sendo.fragment.ExerciseListFragment
import augarte.sendo.fragment.HomeFragment
import augarte.sendo.fragment.MeasurementsFragment
import augarte.sendo.fragment.SettingsFragment
import augarte.sendo.R
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    companion object {
        var dbHandler: DatabaseHandler? = null
    }

    private var fragment: Fragment? = HomeFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        //init db
        dbHandler = DatabaseHandler(this)
        //dbHandler!!.deleteAllTables()

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_frame, HomeFragment(), "Home")
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
        var replace = false

        when (menuItem.itemId) {
            R.id.nav_home -> {
                if (fragment !is HomeFragment){
                    fragment = HomeFragment()
                    replace = true
                }
            }

            R.id.nav_measurement -> {
                if (fragment !is MeasurementsFragment){
                    fragment = MeasurementsFragment()
                    replace = true
                }
            }

            R.id.nav_exercise_list -> {
                if (fragment !is ExerciseListFragment){
                    fragment = ExerciseListFragment()
                    replace = true
                }
            }

            R.id.nav_settings -> {
                if (fragment !is SettingsFragment){
                    fragment = SettingsFragment()
                    replace = true
                }
            }
        }

        if (fragment != null && replace)  {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.main_frame, fragment!!)
            transaction.commit()
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}