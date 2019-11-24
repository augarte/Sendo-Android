package augarte.sendo.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import augarte.sendo.R
import augarte.sendo.database.DatabaseHandler
import augarte.sendo.fragment.ExerciseListFragment
import augarte.sendo.fragment.HomeFragment
import augarte.sendo.fragment.MeasurementsFragment
import augarte.sendo.fragment.SettingsFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var auth: FirebaseAuth

    companion object {
        var user: FirebaseUser? = null
        lateinit var dbHandler: DatabaseHandler
    }

    private var fragment: Fragment = HomeFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        auth = FirebaseAuth.getInstance()
        dbHandler = DatabaseHandler(this)
        //dbHandler.deleteAllTables()

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.sendo_navigation_drawer_open, R.string.sendo_navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_frame, fragment)
        transaction.commit()
    }

    fun changeTheme(themeId: Int){
        nav_view.menu.getItem(0).isChecked = true
        setTheme(themeId)
        recreate()
    }

    public override fun onStart() {
        super.onStart()
        user = auth.currentUser
        if (user == null) {
            auth.signInAnonymously().addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    user = auth.currentUser
                } else {
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onBackPressed() {
        when {
            drawer_layout.isDrawerOpen(GravityCompat.START) -> drawer_layout.closeDrawer(GravityCompat.START)
            nav_view.checkedItem?.itemId != R.id.nav_home -> {
                nav_view.menu.getItem(0).isChecked = true
                onNavigationItemSelected(nav_view.menu.getItem(0))
            }
            else -> androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle(getString(R.string.sendo_alert_close_app_title))
                    .setMessage(getString(R.string.sendo_alert_close_app_message))
                    .setPositiveButton(getString(R.string.sendo_accept)) { _, _ -> finishAndRemoveTask() }
                    .setNegativeButton(getString(R.string.sendo_cancel)) { _, _ -> }
                    .show()
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

        if (replace)  {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.main_frame, fragment)
            transaction.commit()
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}