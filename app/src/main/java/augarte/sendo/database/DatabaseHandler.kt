package augarte.sendo.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import augarte.sendo.dataModel.Workout
import augarte.sendo.utils.Constants
import android.content.ContentValues
import android.util.Log

class DatabaseHandler(context: Context?) : SQLiteOpenHelper(context, Constants.DB_NAME, null, Constants.DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DatabaseTransactions.CREATE_TABLE_USER)
        db?.execSQL(DatabaseTransactions.CREATE_TABLE_WORKOUT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_WORKOUT)
        onCreate(db)
    }


    fun insertWorkout(workout: Workout): Long {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(Constants.TABLE_WORKOUT_NAME, workout.name)

        var id : Long = -1
        try {
            id = db.insert(Constants.TABLE_WORKOUT, null, values)
        } catch (e: Exception) {
            Log.e("DB ERROR", e.toString())
            e.printStackTrace()
        }

        db.close()
        return id
    }

    fun getAllWorkouts(): ArrayList<Workout> {
        val workouts = ArrayList<Workout>()

        val selectQuery = "SELECT  * FROM " + Constants.TABLE_WORKOUT + " ORDER BY " + Constants.TABLE_WORKOUT_LASTOPEN + " DESC"

        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val workout = Workout()
                workout.id = cursor.getInt(cursor.getColumnIndex(Constants.TABLE_WORKOUT_ID))
                workout.name = cursor.getString(cursor.getColumnIndex(Constants.TABLE_WORKOUT_NAME))

                workouts.add(workout)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return workouts
    }
}