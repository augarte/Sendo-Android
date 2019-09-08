package augarte.sendo.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import augarte.sendo.dataModel.Workout
import android.content.ContentValues
import android.util.Log
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import augarte.sendo.dataModel.Day
import augarte.sendo.dataModel.Exercise
import augarte.sendo.dataModel.User
import augarte.sendo.utils.Utils
import kotlin.collections.ArrayList
import java.util.*


class DatabaseHandler(context: Context?) : SQLiteOpenHelper(context, DatabaseConstants.DB_NAME, null, DatabaseConstants.DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CreateTableTransactions.CREATE_TABLE_USER)
        db?.execSQL(CreateTableTransactions.CREATE_TABLE_WORKOUT)
        db?.execSQL(CreateTableTransactions.CREATE_TABLE_DAY)
        db?.execSQL(CreateTableTransactions.CREATE_TABLE_EXERCISE)
        db?.execSQL(CreateTableTransactions.CREATE_TABLE_EXERCISEDAY)
        db?.execSQL(CreateTableTransactions.CREATE_TABLE_SERIE)
        db?.execSQL(CreateTableTransactions.CREATE_TABLE_MEASUREMENT)
        db?.execSQL(CreateTableTransactions.CREATE_TABLE_MEASUREMENTTYPE)
        db?.execSQL(CreateTableTransactions.CREATE_TABLE_DATETYPE)
        insertInitialData()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun deleteAllTables() {
        val db = this.writableDatabase
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstants.TABLE_USER)
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstants.TABLE_WORKOUT)
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstants.TABLE_DAY)
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstants.TABLE_EXERCISEDAY)
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstants.TABLE_SERIE)
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstants.TABLE_EXERCISE)
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstants.TABLE_MEASUREMENT)
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstants.TABLE_MEASUREMENT_TYPE)
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstants.TABLE_DATETYPE)
        onCreate(db)
    }

    fun insertInitialData() {
        for (i in 0..50) {
            val exercise = Exercise()
            exercise.name = "Press"
            insertExercise(exercise)
        }
    }


      /******************************/
     /********** WORKOUTS **********/
    /******************************/

    fun getAllWorkouts(): ArrayList<Workout> {
        val workouts = ArrayList<Workout>()

        val db = this.writableDatabase
        val cursor = db.rawQuery(SelectTransactions.SELECT_ALL_WORKOUT, null)

        if (cursor.moveToFirst()) {
            do {
                val workout = Workout()
                val id = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.TABLE_WORKOUT_ID))
                val blob = cursor.getBlob(cursor.getColumnIndex(DatabaseConstants.TABLE_WORKOUT_IMAGE))
                var image: Bitmap?
                image = if (blob!= null) BitmapFactory.decodeByteArray(blob, 0, blob.size)
                else null
                val userId = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.TABLE_WORKOUT_CREATEDBY))
                val lastOpenUnixSeconds =  cursor.getLong(cursor.getColumnIndex(DatabaseConstants.TABLE_WORKOUT_LASTOPEN))
                val createDateUnixSeconds = cursor.getLong(cursor.getColumnIndex(DatabaseConstants.TABLE_WORKOUT_CREATEDATE))
                val modifyDateUnixSeconds = cursor.getLong(cursor.getColumnIndex(DatabaseConstants.TABLE_WORKOUT_MODIFYDATE))

                workout.id = id
                workout.name = cursor.getString(cursor.getColumnIndex(DatabaseConstants.TABLE_WORKOUT_NAME))
                workout.image = image
                workout.description = cursor.getString(cursor.getColumnIndex(DatabaseConstants.TABLE_WORKOUT_DESCRIPTION))
                workout.createdBy = getUserByUserId(userId)
                workout.lastOpen = Date((lastOpenUnixSeconds * 1000))
                workout.createDate = Date((createDateUnixSeconds * 1000))
                workout.modifyDate = Date((modifyDateUnixSeconds * 1000))

                //workout.dayList = getDaysByWorkoutId(id)

                workouts.add(workout)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return workouts
    }

    fun insertWorkout(workout: Workout): Long {
        val db = this.writableDatabase
        val values = ContentValues()

        val unixTime = Utils.getUnixSeconds()

        values.put(DatabaseConstants.TABLE_WORKOUT_NAME, workout.name)
        if (workout.image!=null) values.put(DatabaseConstants.TABLE_WORKOUT_IMAGE, Utils.getBiteArrayFromBitmap(workout.image!!))
        values.put(DatabaseConstants.TABLE_WORKOUT_DESCRIPTION, workout.name)
        values.put(DatabaseConstants.TABLE_WORKOUT_CREATEDBY, 0)
        values.put(DatabaseConstants.TABLE_WORKOUT_LASTOPEN, unixTime)
        values.put(DatabaseConstants.TABLE_WORKOUT_CREATEDATE, unixTime)
        values.put(DatabaseConstants.TABLE_WORKOUT_MODIFYDATE, unixTime)

        var id : Long = -1
        try {
            id = db.insert(DatabaseConstants.TABLE_WORKOUT, null, values)
        } catch (e: Exception) {
            Log.e("DB ERROR", e.toString())
            e.printStackTrace()
        }

        db.close()
        return id
    }

    fun deleteWorkoutById(workoutId: Int) {
        val db = this.writableDatabase
        db.delete(DatabaseConstants.TABLE_WORKOUT, "${DatabaseConstants.TABLE_WORKOUT_ID} = ?", arrayOf(workoutId.toString()))
    }


      /*******************************/
     /********** EXERCISES **********/
    /*******************************/

    fun getAllExercises(): ArrayList<Exercise> {
        val exercises = ArrayList<Exercise>()

        val db = this.writableDatabase
        val cursor = db.rawQuery(SelectTransactions.SELECT_ALL_EXERCISE, null)

        if (cursor.moveToFirst()) {
            do {
                val exercise = Exercise()
                val id = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.TABLE_EXERCISE_ID))
                val blob = cursor.getBlob(cursor.getColumnIndex(DatabaseConstants.TABLE_EXERCISE_IMAGE))
                var image: Bitmap?
                image = if (blob!= null) BitmapFactory.decodeByteArray(blob, 0, blob.size)
                else null
                val userId = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.TABLE_EXERCISE_CREATEDBY))
                val createDateUnixSeconds = cursor.getLong(cursor.getColumnIndex(DatabaseConstants.TABLE_EXERCISE_CREATEDATE))
                val modifyDateUnixSeconds = cursor.getLong(cursor.getColumnIndex(DatabaseConstants.TABLE_EXERCISE_MODIFYDATE))

                exercise.id = id
                exercise.name = cursor.getString(cursor.getColumnIndex(DatabaseConstants.TABLE_EXERCISE_NAME))
                exercise.description = cursor.getString(cursor.getColumnIndex(DatabaseConstants.TABLE_EXERCISE_DESCRIPTION))
                exercise.image = image
                exercise.createdBy = getUserByUserId(userId)
                exercise.createDate = Date((createDateUnixSeconds * 1000))
                exercise.modifyDate = Date((modifyDateUnixSeconds * 1000))

                //workout.dayList = getDaysByWorkoutId(id)

                exercises.add(exercise)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return exercises
    }

    fun insertExercise(exercise: Exercise): Long {
        val db = this.writableDatabase
        val values = ContentValues()

        val unixTime = Utils.getUnixSeconds()

        values.put(DatabaseConstants.TABLE_EXERCISE_NAME, exercise.name)
        if (exercise.image!=null) values.put(DatabaseConstants.TABLE_EXERCISE_IMAGE, Utils.getBiteArrayFromBitmap(exercise.image!!))
        values.put(DatabaseConstants.TABLE_EXERCISE_DESCRIPTION, exercise.description)
        values.put(DatabaseConstants.TABLE_EXERCISE_DESCRIPTION, exercise.name)
        values.put(DatabaseConstants.TABLE_EXERCISE_CREATEDBY, 0)
        values.put(DatabaseConstants.TABLE_EXERCISE_CREATEDATE, unixTime)
        values.put(DatabaseConstants.TABLE_EXERCISE_MODIFYDATE, unixTime)

        var id : Long = -1
        try {
            id = db.insert(DatabaseConstants.TABLE_EXERCISE, null, values)
        } catch (e: Exception) {
            Log.e("DB ERROR", e.toString())
            e.printStackTrace()
        }

        db.close()
        return id
    }


      /***************************/
     /********** USERS **********/
    /***************************/

    fun getUserByUserId(userId: Int): User {
        val user = User()
        user.id = userId
        return user
    }


      /**************************/
     /********** DAYS **********/
    /**************************/

    fun getDaysByWorkoutId(workoutId: Int): ArrayList<Day> {
        return ArrayList()
    }
}