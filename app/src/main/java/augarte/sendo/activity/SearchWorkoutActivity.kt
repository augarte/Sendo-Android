package augarte.sendo.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import augarte.sendo.R
import kotlinx.android.synthetic.main.app_bar_main.*

class SearchWorkoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_workout)

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }
}