package augarte.sendo.Activity

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import augarte.sendo.Connection.DatabaseHandler
import augarte.sendo.Fragment.ExerciseListFragment
import augarte.sendo.Fragment.HomeFragment
import augarte.sendo.Fragment.MeasurementsFragment
import augarte.sendo.Fragment.SettingsFragment
import augarte.sendo.R
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var dbHandler: DatabaseHandler? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        //init db
        dbHandler = DatabaseHandler(this)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_frame, HomeFragment())
        transaction.commit()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null

        when (item.itemId) {
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