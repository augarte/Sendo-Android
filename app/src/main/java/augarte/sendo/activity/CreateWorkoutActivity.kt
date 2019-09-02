package augarte.sendo.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.app_bar_main.*
import androidx.recyclerview.widget.LinearLayoutManager
import augarte.sendo.adapter.CreateWorkoutAdapter
import augarte.sendo.dataModel.Day
import kotlinx.android.synthetic.main.activity_create_workout.*
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DefaultItemAnimator
import android.view.animation.AnimationUtils.loadAnimation
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import augarte.sendo.R
import augarte.sendo.fragment.ExerciseChooserFragment
import androidx.core.content.ContextCompat
import augarte.sendo.Animations
import augarte.sendo.dataModel.Workout


class CreateWorkoutActivity : AppCompatActivity() {

    private val initialDayNum = 3
    private var thisWorkout: Workout = Workout()
    companion object {
        private const val IMAGE_PICK_CODE = 1000 //image pick code
        private const val PERMISSION_CODE = 1001 //Permission code
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_workout)

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val dayList : ArrayList<Day> = ArrayList()
        for (i in 0 until initialDayNum) {
            val day = Day()
            day.name = "DAY " + (i+1)
            dayList.add(day)
        }

        val lManager = LinearLayoutManager(applicationContext)
        val workoutAdapter = CreateWorkoutAdapter(dayList, day_rv)
        workoutAdapter.onDayEdit = { title ->
            bottomsheet.setFragment(ExerciseChooserFragment(title))
        }
        val animator = object : DefaultItemAnimator() {
            override fun canReuseUpdatedViewHolder(viewHolder: RecyclerView.ViewHolder): Boolean {
                return true
            }
        }
        day_rv.apply {
            itemAnimator = animator
            layoutManager = lManager
            adapter = workoutAdapter
        }

        number_picker.minVal = 1
        number_picker.maxVal = 7
        number_picker.value = initialDayNum
        number_picker.listener = {oldVal, newVal ->
            if (newVal - oldVal > 0) {
                val d = Day()
                d.name = "DAY $newVal"
                dayList.add(oldVal, d)
                workoutAdapter.newItem = true
                workoutAdapter.notifyItemInserted(oldVal)
            }
            else if (newVal - oldVal < 0) {
                dayList.removeAt(newVal)
                workoutAdapter.deleteItem(newVal)
                workoutAdapter.notifyItemRemoved(newVal)
            }
        }

        val shake = loadAnimation(this, R.anim.hovering)
        create_workout_button.startAnimation(shake)

        create_workout_button.setOnClickListener{
            thisWorkout.name = workout_title.text.toString()

            val intent = Intent(this, WorkoutActivity::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //intent.putExtra("workout", pair.first.id)
                workoutName.visibility = View.VISIBLE
                val translation1 = androidx.core.util.Pair<View, String>(workout_card, "workoutCard")
                val translation2 = androidx.core.util.Pair<View?, String?>(workoutName, "workoutName")
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, translation1, translation2)

                startActivity(intent, options.toBundle())
            }
            else {
                startActivity(intent)
            }
        }

        add_image.setOnClickListener{
            pickImageFromGallery()
        }

        delete_image.setOnClickListener {
            addImage(null)
        }
    }

    override fun onResume() {
        super.onResume()

        workoutName.visibility = View.GONE
    }


    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            addImage(data?.data)
        }
    }

    private fun addImage(image: Uri?) {
        if (image != null) {
            workout_image.setImageURI(image)
            val fadeAnim = loadAnimation(this, R.anim.fade_in)
            fadeAnim.duration = 300
            fadeAnim.startOffset = 500
            fadeAnim.fillAfter = true
            workout_image.startAnimation(fadeAnim)

            title_layout.background = ContextCompat.getDrawable(this, R.drawable.background_rounded_white)
            day_layout.background = ContextCompat.getDrawable(this, R.drawable.background_rounded_white)

            var anim1 = Animations.compress(add_image)
            var anim2 = Animations.expand(delete_image)
            anim1.startOffset = 500
            anim2.startOffset = 600
            add_image.startAnimation(anim1)
            delete_image.startAnimation(anim2)
        }
        else{
            val fadeAnim = loadAnimation(this, R.anim.fade_out)
            fadeAnim.duration = 300
            fadeAnim.fillAfter = true
            workout_image.startAnimation(fadeAnim)

            title_layout.background = null
            day_layout.background = null

            var anim1 = Animations.compress(delete_image)
            var anim2 = Animations.expand(add_image)
            anim2.startOffset = 100
            delete_image.startAnimation(anim1)
            add_image.startAnimation(anim2)
        }
    }
}