package augarte.sendo.activity

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.app_bar_main.*
import androidx.recyclerview.widget.LinearLayoutManager
import augarte.sendo.adapter.CreateWorkoutAdapter
import augarte.sendo.dataModel.Day
import kotlinx.android.synthetic.main.activity_create_workout.*
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DefaultItemAnimator
import android.view.animation.AnimationUtils.loadAnimation
import android.widget.Toast
import augarte.sendo.R
import augarte.sendo.fragment.ExerciseChooserFragment
import androidx.core.content.ContextCompat
import augarte.sendo.utils.Animations
import augarte.sendo.dataModel.Workout
import android.provider.MediaStore
import android.view.MenuItem
import augarte.sendo.dataModel.Exercise
import com.google.android.material.bottomsheet.BottomSheetBehavior

class CreateWorkoutActivity : BaseActivity() {

    private val MIN_DAYS = 1
    private val MAX_DAYS = 7
    private val INIT_DAYS = 3

    private var thisWorkout: Workout = Workout()

    private lateinit var lManager: LinearLayoutManager
    private lateinit var workoutAdapter: CreateWorkoutAdapter

    companion object {
        private const val IMAGE_PICK_CODE = 1000 //Image pick code
        private const val PERMISSION_CODE = 1001 //Permission code
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_workout)

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        for (i in 0 until INIT_DAYS) {
            val day = Day()
            day.name = getString(R.string.sendo_day_num, (i+1))
            day.createdBy = MainActivity.user?.uid
            thisWorkout.dayList.add(day)
        }

        lManager = LinearLayoutManager(applicationContext)
        workoutAdapter = CreateWorkoutAdapter(thisWorkout.dayList, day_rv)
        workoutAdapter.setDayEditListener(object : CreateWorkoutAdapter.WorkoutdayEditListener {
            override fun onEditPressed(title: String, selectedExercises: ArrayList<Exercise>, listener: CreateWorkoutAdapter.OnExerciseSelectedListener) {
                bottomSheet.setFragment(ExerciseChooserFragment(title, selectedExercises,  listener))
            }
        })

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

        number_picker.minVal = MIN_DAYS
        number_picker.maxVal = MAX_DAYS
        number_picker.value = INIT_DAYS

        val shake = loadAnimation(this, R.anim.hovering)
        create_workout_button.startAnimation(shake)

        setListeners()
    }

    override fun onResume() {
        super.onResume()

        workoutName.visibility = View.GONE
    }

    private fun setListeners () {
        number_picker.listener = {oldVal, newVal ->
            if (newVal - oldVal > 0) {
                val d = Day()
                d.name = "DAY $newVal"
                d.createdBy = MainActivity.user?.uid
                thisWorkout.dayList.add(oldVal, d)
                workoutAdapter.addItem()
            }
            else if (newVal - oldVal < 0) {
                thisWorkout.dayList.removeAt(newVal)
                workoutAdapter.deleteItem(newVal)
            }
        }

        create_workout_button.setOnClickListener{
            thisWorkout.name = workout_title.text.toString()
            MainActivity.dbHandler.insertWorkout(thisWorkout)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        add_image.setOnClickListener{
            pickImageFromGallery()
        }

        delete_image.setOnClickListener {
            thisWorkout.image = null
            addImage(null)
        }
    }

    //Request permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //Granted
                    pickImageFromGallery()
                }
                else{
                    //Denied
                    Toast.makeText(this, getString(R.string.sendo_permision_denied), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    //Image picked result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            val imageUri = data?.data
            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
            thisWorkout.image = bitmap

            addImage(imageUri)
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

            val anim1 = Animations.compress(add_image)
            val anim2 = Animations.expand(delete_image)
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

            val anim1 = Animations.compress(delete_image)
            val anim2 = Animations.expand(add_image)
            anim2.startOffset = 100
            delete_image.startAnimation(anim1)
            add_image.startAnimation(anim2)
        }
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
        when (BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheet.getState() -> bottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN)
            else -> finish()
        }
    }
}