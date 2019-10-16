package augarte.sendo.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import augarte.sendo.R
import kotlinx.android.synthetic.main.app_bar_main.*
import android.view.MenuItem

class SearchWorkoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_workout)

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> false
        }
    }

    override fun onBackPressed() {
        finish()
    }
}