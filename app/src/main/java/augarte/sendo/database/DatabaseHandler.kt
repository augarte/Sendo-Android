package augarte.sendo.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.util.Log
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import augarte.sendo.dataModel.*
import augarte.sendo.utils.Utils
import kotlin.collections.ArrayList
import java.util.*


class DatabaseHandler(context: Context?) : SQLiteOpenHelper(context, DatabaseConstants.DB_NAME, null, DatabaseConstants.DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CreateTableTransactions.CREATE_TABLE_USER)

        db?.execSQL(CreateTableTransactions.CREATE_TABLE_MEASURETYPE)
        db?.execSQL(CreateTableTransactions.CREATE_TABLE_DATETYPE)
        db?.execSQL(CreateTableTransactions.CREATE_TABLE_EXERCISETYPE)
        db?.execSQL(CreateTableTransactions.CREATE_TABLE_WEIGHTYPE)
        db?.execSQL(CreateTableTransactions.CREATE_TABLE_LENGTHTYPE)

        db?.execSQL(CreateTableTransactions.CREATE_TABLE_WORKOUT)
        db?.execSQL(CreateTableTransactions.CREATE_TABLE_DAY)
        db?.execSQL(CreateTableTransactions.CREATE_TABLE_EXERCISE)
        db?.execSQL(CreateTableTransactions.CREATE_TABLE_EXERCISEDAY)
        db?.execSQL(CreateTableTransactions.CREATE_TABLE_SERIE)
        db?.execSQL(CreateTableTransactions.CREATE_TABLE_MEASUREMENT)
        insertInitialData()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun deleteAllTables() {
        val db = this.writableDatabase
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstants.TABLE_USER)

        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstants.TABLE_MEASURETYPE)
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstants.TABLE_DATETYPE)
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstants.TABLE_EXERCISETYPE)
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstants.TABLE_WEIGHTTYPE)
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstants.TABLE_LENGTHTYPE)

        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstants.TABLE_WORKOUT)
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstants.TABLE_DAY)
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstants.TABLE_EXERCISE)
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstants.TABLE_EXERCISEDAY)
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstants.TABLE_SERIE)
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstants.TABLE_MEASUREMENT)
        onCreate(db)
    }

    private fun insertInitialData() {
        var dateType: DateType
        for (type in DatabaseConstants.LIST_DATETYPES){
            dateType = DateType()
            dateType.code = type.first
            dateType.name = type.second
            insertDateType(dateType)
        }

        var measureType: MeasureType
        for (type in DatabaseConstants.LIST_MEASURETYPES){
            measureType = MeasureType()
            measureType.code = type.first
            measureType.name = type.second
            insertMeasureType(measureType)
        }

        var weightType: WeightType
        for (type in DatabaseConstants.LIST_WEIGHTTYPE){
            weightType = WeightType()
            weightType.code = type.first
            weightType.name = type.second
            insertWeightType(weightType)
        }

        var lengthType: LengthType
        for (type in DatabaseConstants.LIST_LENGTHTYPE){
            lengthType = LengthType()
            lengthType.code = type.first
            lengthType.name = type.second
            insertLenghtType(lengthType)
        }

        val exerciseTypes: ArrayList<ExerciseType> = getExerciseType(SelectTransactions.SELECT_ALL_EXERCISETYPE, null)!!
        for (i in 0..50) {
            val exercise = Exercise()
            exercise.name = UUID.randomUUID().toString()
            exercise.type = exerciseTypes[(0 until exerciseTypes.size).random()]
            exercise.description = "Description"
            val user = User()
            user.id = 0
            exercise.createdBy = user
            insertExercise(exercise)
        }
    }


      /******************************/
     /********** WORKOUTS **********/
    /******************************/

    fun getAllWorkouts(): ArrayList<Workout> {
        val workouts = ArrayList<Workout>()

        val db = this.writableDatabase
        val cursor = db.rawQuery(SelectTransactions.SELECT_ALL_WORKOUT_ORDER_NAME, null)

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

    fun getExercise(query: String): ArrayList<Exercise> {
        val exercises = ArrayList<Exercise>()

        val db = this.writableDatabase
        val cursor : Cursor
        cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val exercise = Exercise()
                val id = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.TABLE_EXERCISE_ID))
                val exerciseTypeId = cursor.getString(cursor.getColumnIndex(DatabaseConstants.TABLE_EXERCISE_TYPE))
                val exerciseTypeList = getExerciseType(SelectTransactions.SELECT_EXERCISETYPE_BY_ID, arrayOf(exerciseTypeId))
                val exerciseType = if (exerciseTypeList!!.size >= 1){
                    exerciseTypeList[0]
                }
                else null
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
                exercise.type = exerciseType
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
        values.put(DatabaseConstants.TABLE_EXERCISE_TYPE, exercise.type?.id)
        values.put(DatabaseConstants.TABLE_EXERCISE_STATE, exercise.state)
        values.put(DatabaseConstants.TABLE_EXERCISE_CREATEDBY, exercise.createdBy?.id)
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
    fun updateExerciseState(exercise: Exercise){
        val db = this.writableDatabase
        var values = ContentValues()
        values.put(DatabaseConstants.TABLE_EXERCISE_STATE, exercise.state)

        db.update(DatabaseConstants.TABLE_EXERCISE, values, "${DatabaseConstants.TABLE_EXERCISE_ID} = ${exercise.id}", null)
        db.close()
    }

      /************************************/
     /********** EXERCISE TYPES **********/
    /************************************/

    private fun getExerciseType(query: String, array: Array<String>?): ArrayList<ExerciseType>? {
        val db = this.writableDatabase
        val cursor : Cursor
        cursor = db.rawQuery(query, array)

        val exerciseTypeList = ArrayList<ExerciseType>()
        if (cursor.moveToFirst()) {
            do {
                val exerciseType = ExerciseType()
                val createDateUnixSeconds = cursor.getLong(cursor.getColumnIndex(DatabaseConstants.TABLE_EXERCISETYPE_CREATEDATE))
                val modifyDateUnixSeconds = cursor.getLong(cursor.getColumnIndex(DatabaseConstants.TABLE_EXERCISETYPE_MODIFYDATE))

                exerciseType.id = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.TABLE_EXERCISETYPE_ID))
                exerciseType.name = cursor.getString(cursor.getColumnIndex(DatabaseConstants.TABLE_EXERCISETYPE_NAME))
                exerciseType.code = cursor.getString(cursor.getColumnIndex(DatabaseConstants.TABLE_EXERCISETYPE_CODE))
                exerciseType.createDate = Date((createDateUnixSeconds * 1000))
                exerciseType.modifyDate = Date((modifyDateUnixSeconds * 1000))

                exerciseTypeList.add(exerciseType)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return exerciseTypeList
    }

    private fun insertExerciseType(exerciseType: ExerciseType): Long {
        val db = this.writableDatabase
        val values = ContentValues()

        val unixTime = Utils.getUnixSeconds()

        values.put(DatabaseConstants.TABLE_EXERCISETYPE_CODE, exerciseType.code)
        values.put(DatabaseConstants.TABLE_EXERCISETYPE_NAME, exerciseType.name)
        values.put(DatabaseConstants.TABLE_EXERCISETYPE_CREATEDATE, unixTime)
        values.put(DatabaseConstants.TABLE_EXERCISETYPE_MODIFYDATE, unixTime)

        var id : Long = -1
        try {
            id = db.insert(DatabaseConstants.TABLE_EXERCISETYPE, null, values)
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


      /***********************************/
     /********** MEASURE TYPES **********/
    /***********************************/

    fun getMeasureType(query: String, array: Array<String>?): ArrayList<MeasureType>? {
        val db = this.writableDatabase
        val cursor : Cursor
        cursor = db.rawQuery(query, array)

        val measurementTypeList = ArrayList<MeasureType>()
        if (cursor.moveToFirst()) {
            do {
                val measurementType = MeasureType()
                val createDateUnixSeconds = cursor.getLong(cursor.getColumnIndex(DatabaseConstants.TABLE_MEASURETYPE_CREATEDATE))
                val modifyDateUnixSeconds = cursor.getLong(cursor.getColumnIndex(DatabaseConstants.TABLE_MEASURETYPE_MODIFYDATE))

                measurementType.id = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.TABLE_MEASURETYPE_ID))
                measurementType.name = cursor.getString(cursor.getColumnIndex(DatabaseConstants.TABLE_MEASURETYPE_NAME))
                measurementType.code = cursor.getString(cursor.getColumnIndex(DatabaseConstants.TABLE_MEASURETYPE_CODE))
                measurementType.createDate = Date((createDateUnixSeconds * 1000))
                measurementType.modifyDate = Date((modifyDateUnixSeconds * 1000))

                measurementTypeList.add(measurementType)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return measurementTypeList
    }

    private fun insertMeasureType(measureType: MeasureType): Long {
        val db = this.writableDatabase
        val values = ContentValues()

        val unixTime = Utils.getUnixSeconds()

        values.put(DatabaseConstants.TABLE_MEASURETYPE_CODE, measureType.code)
        values.put(DatabaseConstants.TABLE_MEASURETYPE_NAME, measureType.name)
        values.put(DatabaseConstants.TABLE_MEASURETYPE_CREATEDATE, unixTime)
        values.put(DatabaseConstants.TABLE_MEASURETYPE_MODIFYDATE, unixTime)

        var id : Long = -1
        try {
            id = db.insert(DatabaseConstants.TABLE_MEASURETYPE, null, values)
        } catch (e: Exception) {
            Log.e("DB ERROR", e.toString())
            e.printStackTrace()
        }

        db.close()
        return id
    }


      /**********************************/
     /********** WEIGHT TYPES **********/
    /**********************************/

    fun getWeightType(query: String, array: Array<String>?): ArrayList<WeightType>? {
        val db = this.writableDatabase
        val cursor : Cursor
        cursor = db.rawQuery(query, array)

        val weightTypeList = ArrayList<WeightType>()
        if (cursor.moveToFirst()) {
            do {
                val weightType = WeightType()
                val createDateUnixSeconds = cursor.getLong(cursor.getColumnIndex(DatabaseConstants.TABLE_WEIGHTTYPE_CREATEDATE))
                val modifyDateUnixSeconds = cursor.getLong(cursor.getColumnIndex(DatabaseConstants.TABLE_WEIGHTTYPE_MODIFYDATE))

                weightType.id = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.TABLE_WEIGHTTYPE_ID))
                weightType.name = cursor.getString(cursor.getColumnIndex(DatabaseConstants.TABLE_WEIGHTTYPE_NAME))
                weightType.code = cursor.getString(cursor.getColumnIndex(DatabaseConstants.TABLE_WEIGHTTYPE_CODE))
                weightType.createDate = Date((createDateUnixSeconds * 1000))
                weightType.modifyDate = Date((modifyDateUnixSeconds * 1000))

                weightTypeList.add(weightType)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return weightTypeList
    }

    private fun insertWeightType(weightType: WeightType): Long {
        val db = this.writableDatabase
        val values = ContentValues()

        val unixTime = Utils.getUnixSeconds()

        values.put(DatabaseConstants.TABLE_WEIGHTTYPE_CODE, weightType.code)
        values.put(DatabaseConstants.TABLE_WEIGHTTYPE_NAME, weightType.name)
        values.put(DatabaseConstants.TABLE_WEIGHTTYPE_CREATEDATE, unixTime)
        values.put(DatabaseConstants.TABLE_MEASURETYPE_MODIFYDATE, unixTime)

        var id : Long = -1
        try {
            id = db.insert(DatabaseConstants.TABLE_WEIGHTTYPE, null, values)
        } catch (e: Exception) {
            Log.e("DB ERROR", e.toString())
            e.printStackTrace()
        }

        db.close()
        return id
    }

      /**********************************/
     /********** LENGTH TYPES **********/
    /**********************************/

    fun getLenghtType(query: String, array: Array<String>?): ArrayList<LengthType>? {
        val db = this.writableDatabase
        val cursor : Cursor
        cursor = db.rawQuery(query, array)

        val lengthTypeList = ArrayList<LengthType>()
        if (cursor.moveToFirst()) {
            do {
                val lengthType = LengthType()
                val createDateUnixSeconds = cursor.getLong(cursor.getColumnIndex(DatabaseConstants.TABLE_LENGTHTYPE_CREATEDATE))
                val modifyDateUnixSeconds = cursor.getLong(cursor.getColumnIndex(DatabaseConstants.TABLE_LENGTHTYPE_MODIFYDATE))

                lengthType.id = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.TABLE_LENGTHTYPE_ID))
                lengthType.name = cursor.getString(cursor.getColumnIndex(DatabaseConstants.TABLE_LENGTHTYPE_NAME))
                lengthType.code = cursor.getString(cursor.getColumnIndex(DatabaseConstants.TABLE_LENGTHTYPE_CODE))
                lengthType.createDate = Date((createDateUnixSeconds * 1000))
                lengthType.modifyDate = Date((modifyDateUnixSeconds * 1000))

                lengthTypeList.add(lengthType)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return lengthTypeList
    }

    private fun insertLenghtType(lengthType: LengthType): Long {
        val db = this.writableDatabase
        val values = ContentValues()

        val unixTime = Utils.getUnixSeconds()

        values.put(DatabaseConstants.TABLE_LENGTHTYPE_CODE, lengthType.code)
        values.put(DatabaseConstants.TABLE_LENGTHTYPE_NAME, lengthType.name)
        values.put(DatabaseConstants.TABLE_LENGTHTYPE_CREATEDATE, unixTime)
        values.put(DatabaseConstants.TABLE_LENGTHTYPE_MODIFYDATE, unixTime)

        var id : Long = -1
        try {
            id = db.insert(DatabaseConstants.TABLE_LENGTHTYPE, null, values)
        } catch (e: Exception) {
            Log.e("DB ERROR", e.toString())
            e.printStackTrace()
        }

        db.close()
        return id
    }


      /*******************************/
     /********** DATE TYPE **********/
    /*******************************/

    fun getDateType(query: String, array: Array<String>?): ArrayList<DateType>? {
        val db = this.writableDatabase
        val cursor : Cursor
        cursor = db.rawQuery(query, array)

        val dateTypeList = ArrayList<DateType>()
        if (cursor.moveToFirst()) {
            do {
                val dateType = DateType()
                val createDateUnixSeconds = cursor.getLong(cursor.getColumnIndex(DatabaseConstants.TABLE_DATETYPE_CREATEDATE))
                val modifyDateUnixSeconds = cursor.getLong(cursor.getColumnIndex(DatabaseConstants.TABLE_DATETYPE_MODIFYDATE))

                dateType.id = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.TABLE_DATETYPE_ID))
                dateType.name = cursor.getString(cursor.getColumnIndex(DatabaseConstants.TABLE_DATETYPE_NAME))
                dateType.code = cursor.getString(cursor.getColumnIndex(DatabaseConstants.TABLE_DATETYPE_CODE))
                dateType.createDate = Date((createDateUnixSeconds * 1000))
                dateType.modifyDate = Date((modifyDateUnixSeconds * 1000))

                dateTypeList.add(dateType)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return dateTypeList
    }

    private fun insertDateType(dateType: DateType): Long {
        val db = this.writableDatabase
        val values = ContentValues()

        val unixTime = Utils.getUnixSeconds()

        values.put(DatabaseConstants.TABLE_DATETYPE_CODE, dateType.code)
        values.put(DatabaseConstants.TABLE_DATETYPE_NAME, dateType.name)
        values.put(DatabaseConstants.TABLE_DATETYPE_CREATEDATE, unixTime)
        values.put(DatabaseConstants.TABLE_DATETYPE_MODIFYDATE, unixTime)

        var id : Long = -1
        try {
            id = db.insert(DatabaseConstants.TABLE_DATETYPE, null, values)
        } catch (e: Exception) {
            Log.e("DB ERROR", e.toString())
            e.printStackTrace()
        }

        db.close()
        return id
    }

}