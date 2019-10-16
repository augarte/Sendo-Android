package augarte.sendo.activity

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
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
import augarte.sendo.R
import augarte.sendo.fragment.ExerciseChooserFragment
import androidx.core.content.ContextCompat
import augarte.sendo.utils.Animations
import augarte.sendo.dataModel.Workout
import android.provider.MediaStore
import android.view.MenuItem
import com.google.android.material.bottomsheet.BottomSheetBehavior

class CreateWorkoutActivity : AppCompatActivity() {

    private val initialDayNum = 3
    private var thisWorkout: Workout = Workout()

    companion object {
        private const val IMAGE_PICK_CODE = 1000 //Image pick code
        private const val PERMISSION_CODE = 1001 //Permission code
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_workout)

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        for (i in 0 until initialDayNum) {
            val day = Day()
            day.name = getString(R.string.sendo_day_num, (i+1))
            day.createdBy = MainActivity.user?.uid
            thisWorkout.dayList.add(day)
        }

        val lManager = LinearLayoutManager(applicationContext)
        val workoutAdapter = CreateWorkoutAdapter(thisWorkout.dayList, day_rv)

        workoutAdapter.onDayEdit = { title, selectedExercises, listener ->
            bottomsheet.setFragment(ExerciseChooserFragment(title, selectedExercises,  listener))
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
                d.createdBy = MainActivity.user?.uid
                thisWorkout.dayList.add(oldVal, d)
                workoutAdapter.newItem = true
                workoutAdapter.notifyItemInserted(oldVal)
            }
            else if (newVal - oldVal < 0) {
                thisWorkout.dayList.removeAt(newVal)
                workoutAdapter.deleteItem(newVal)
                workoutAdapter.notifyItemRemoved(newVal)
            }
        }

        val shake = loadAnimation(this, R.anim.hovering)
        create_workout_button.startAnimation(shake)

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
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, getString(R.string.sendo_permision_denied), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //handle result of picked image
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
        when {
            bottomsheet.getState() == BottomSheetBehavior.STATE_EXPANDED -> bottomsheet.setState(BottomSheetBehavior.STATE_HIDDEN)
            else -> finish()
        }
    }
}