package augarte.sendo.connection

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import augarte.sendo.Constants
import augarte.sendo.dataModel.User
import augarte.sendo.dataModel.Workout
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DatabaseHandler(context: Context?) : SQLiteOpenHelper(context, Constants.DB_NAME, null, Constants.DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_USER = "CREATE TABLE ${Constants.TABLE_USER} " +
                "(${Constants.TABLE_USER_ID} Integer PRIMARY KEY AUTOINCREMENT," +
                "${Constants.TABLE_USER_NAME} TEXT NOT NULL," +
                "${Constants.TABLE_USER_EMAIL} TEXT," +
                "${Constants.TABLE_USER_CREATEDATE} TEXT NOT NULL," +
                "${Constants.TABLE_USER_MODIFYDATE} TEXT NOT NULL"

        val CREATE_TABLE_WORKOUT = "CREATE TABLE ${Constants.TABLE_WORKOUT} " +
                "(${Constants.TABLE_WORKOUT_ID} Integer PRIMARY KEY AUTOINCREMENT," +
                "${Constants.TABLE_WORKOUT_NAME} TEXT NOT NULL," +
                "${Constants.TABLE_WORKOUT_DESCRIPTION} TEXT," +
                "${Constants.TABLE_WORKOUT_IMAGE} blob," +
                "${Constants.TABLE_WORKOUT_LASTOPEN} TEXT NOT NULL," +
                "${Constants.TABLE_WORKOUT_CREATEDBY} TEXT NOT NULL," +
                "${Constants.TABLE_WORKOUT_CREATEDATE} TEXT NOT NULL," +
                "${Constants.TABLE_WORKOUT_MODIFYDATE} TEXT NOT NULL," +
                "FOREIGN KEY(${Constants.TABLE_WORKOUT_CREATEDBY}) REFERENCES USER(${Constants.TABLE_USER_ID}))"

        db?.execSQL(CREATE_TABLE_USER)
        db?.execSQL(CREATE_TABLE_WORKOUT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Called when the database needs to be upgraded
    }

    //Inserting (Creating) data
    @SuppressLint("SimpleDateFormat")
    fun addWorkout(workout: Workout): Boolean {
        //Create and/or open a database that will be used for reading and writing.
        val db = this.writableDatabase

        val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm")
        val currentDate = sdf.format(Date())

        val values = ContentValues()
        values.put(Constants.TABLE_WORKOUT_NAME, workout.name)
        values.put(Constants.TABLE_WORKOUT_DESCRIPTION, workout.description)
        //values.put(Constants.TABLE_WORKOUT_IMAGE, null)
        values.put(Constants.TABLE_WORKOUT_LASTOPEN, currentDate)
        values.put(Constants.TABLE_WORKOUT_CREATEDBY, 0)
        values.put(Constants.TABLE_WORKOUT_CREATEDATE, currentDate)
        values.put(Constants.TABLE_WORKOUT_MODIFYDATE, currentDate)

        val success = db.insert(Constants.TABLE_WORKOUT, null, values)
        db.close()
        Log.v("InsertedID", "$success")
        return (Integer.parseInt("$success") != -1)
    }

    fun getWorkoutsForUser(user: User): ArrayList<Workout> {
        val workouts: ArrayList<Workout> = ArrayList()


        return workouts
    }

    //get all users
    fun getAllUsers(): String {
/*        var allUser: String = "";
        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectALLQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    var id = cursor.getString(cursor.getColumnIndex(ID))
                    var firstName = cursor.getString(cursor.getColumnIndex(FIRST_NAME))
                    var lastName = cursor.getString(cursor.getColumnIndex(LAST_NAME))

                    allUser = "$allUser\n$id $firstName $lastName"
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return allUser*/
        return ""
    }
}