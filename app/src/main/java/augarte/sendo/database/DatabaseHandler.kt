package augarte.sendo.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.util.Log
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import augarte.sendo.activity.MainActivity
import augarte.sendo.dataModel.*
import augarte.sendo.utils.Utils
import kotlin.collections.ArrayList
import java.util.*
import java.io.BufferedReader
import java.io.InputStreamReader


class DatabaseHandler(context: Context?) : SQLiteOpenHelper(context, DatabaseConstants.DB_NAME, null, DatabaseConstants.DB_VERSION) {

    private val mContext = context!!

    override fun onCreate(db: SQLiteDatabase?) {
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

        insertFromFile(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun deleteAllTables() {
        val db = this.writableDatabase

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

    private fun insertFromFile(db: SQLiteDatabase?): Int {
        var result = 0
        val insertsStream = InputStreamReader(mContext.assets.open("initial_data.sqlite"))
        val insertReader = BufferedReader(insertsStream)

        while (insertReader.ready()) {
            val insertStmt = insertReader.readLine()
            db?.execSQL(insertStmt)
            result++
        }
        insertReader.close()

        return result
    }


      /******************************/
     /********** WORKOUTS **********/
    /******************************/

    fun getWorkouts(query: String, array: Array<String>?): ArrayList<Workout> {
        val workouts = ArrayList<Workout>()

        val db = this.writableDatabase
        val cursor = db.rawQuery(query, array)

        if (cursor.moveToFirst()) {
            do {
                val workout = Workout()
                val blob = cursor.getBlob(cursor.getColumnIndex(DatabaseConstants.TABLE_WORKOUT_IMAGE))
                val image: Bitmap? = if (blob!= null) BitmapFactory.decodeByteArray(blob, 0, blob.size) else null
                val lastOpenUnixSeconds =  cursor.getLong(cursor.getColumnIndex(DatabaseConstants.TABLE_WORKOUT_LASTOPEN))
                val createDateUnixSeconds = cursor.getLong(cursor.getColumnIndex(DatabaseConstants.TABLE_WORKOUT_CREATEDATE))
                val modifyDateUnixSeconds = cursor.getLong(cursor.getColumnIndex(DatabaseConstants.TABLE_WORKOUT_MODIFYDATE))

                workout.id = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.TABLE_WORKOUT_ID))
                workout.name = cursor.getString(cursor.getColumnIndex(DatabaseConstants.TABLE_WORKOUT_NAME))
                workout.dayList = getDays(SelectTransactions.SELECT_DAYS_BY_WORKOUT_ID, arrayOf(workout.id.toString()))
                workout.image = image
                workout.description = cursor.getString(cursor.getColumnIndex(DatabaseConstants.TABLE_WORKOUT_DESCRIPTION))
                workout.createdBy = cursor.getString(cursor.getColumnIndex(DatabaseConstants.TABLE_WORKOUT_CREATEDBY))
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

    fun getWorkoutImage(workoutId: Int): Bitmap? {
        var image: Bitmap? = null

        val db = this.writableDatabase
        val cursor = db.rawQuery(SelectTransactions.SELECT_WORKOUTIMAGE_BY_ID, arrayOf(workoutId.toString()))

        if (cursor.moveToFirst()) {
            do {
                val blob = cursor.getBlob(cursor.getColumnIndex(DatabaseConstants.TABLE_WORKOUT_IMAGE))
                image = if (blob!= null) BitmapFactory.decodeByteArray(blob, 0, blob.size) else null
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return image
    }

    fun insertWorkout(workout: Workout): Long {
        val db = this.writableDatabase
        val values = ContentValues()

        val unixTime = Utils.getUnixSeconds()

        values.put(DatabaseConstants.TABLE_WORKOUT_NAME, workout.name)
        if (workout.image!=null) values.put(DatabaseConstants.TABLE_WORKOUT_IMAGE, Utils.getBiteArrayFromBitmap(workout.image!!))
        values.put(DatabaseConstants.TABLE_WORKOUT_DESCRIPTION, workout.name)
        values.put(DatabaseConstants.TABLE_WORKOUT_CREATEDBY, MainActivity.user?.uid)
        values.put(DatabaseConstants.TABLE_WORKOUT_LASTOPEN, unixTime)
        values.put(DatabaseConstants.TABLE_WORKOUT_CREATEDATE, unixTime)
        values.put(DatabaseConstants.TABLE_WORKOUT_MODIFYDATE, unixTime)

        var id : Long = -1
        try {
            id = db.insertOrThrow(DatabaseConstants.TABLE_WORKOUT, null, values)
        } catch (e: Exception) {
            Log.e("DB ERROR", e.toString())
            e.printStackTrace()
        }

        if (id>=0) {
            for (day in workout.dayList) {
                day.workoutId = id.toInt()
                insertDay(day)
            }
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

    fun getExercise(query: String, array: Array<String>?): ArrayList<Exercise> {
        val exercises = ArrayList<Exercise>()

        val db = this.writableDatabase
        val cursor : Cursor
        cursor = db.rawQuery(query, array)

        if (cursor.moveToFirst()) {
            do {
                val exercise = Exercise()
                val exerciseTypeId = cursor.getString(cursor.getColumnIndex(DatabaseConstants.TABLE_EXERCISE_TYPE))
                val exerciseTypeList = getExerciseType(SelectTransactions.SELECT_EXERCISETYPE_BY_ID, arrayOf(exerciseTypeId))
                val exerciseType = if (exerciseTypeList!!.size >= 1) exerciseTypeList[0] else null
                // val blob = cursor.getBlob(cursor.getColumnIndex(DatabaseConstants.TABLE_EXERCISE_IMAGE))
                //val image: Bitmap? = if (blob!= null) BitmapFactory.decodeByteArray(blob, 0, blob.size) else null
                val userId = cursor.getString(cursor.getColumnIndex(DatabaseConstants.TABLE_EXERCISE_CREATEDBY))
                val createDateUnixSeconds = cursor.getLong(cursor.getColumnIndex(DatabaseConstants.TABLE_EXERCISE_CREATEDATE))
                val modifyDateUnixSeconds = cursor.getLong(cursor.getColumnIndex(DatabaseConstants.TABLE_EXERCISE_MODIFYDATE))

                exercise.id = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.TABLE_EXERCISE_ID))
                exercise.name = cursor.getString(cursor.getColumnIndex(DatabaseConstants.TABLE_EXERCISE_NAME))
                exercise.description = cursor.getString(cursor.getColumnIndex(DatabaseConstants.TABLE_EXERCISE_DESCRIPTION))
                //exercise.image = image
                exercise.imageURL = cursor.getString(cursor.getColumnIndex(DatabaseConstants.TABLE_EXERCISE_IMAGE))
                exercise.type = exerciseType
                exercise.createdBy = userId
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
        values.put(DatabaseConstants.TABLE_EXERCISE_DESCRIPTION, exercise.description)
        //if (exercise.image!=null) values.put(DatabaseConstants.TABLE_EXERCISE_IMAGE, Utils.getBiteArrayFromBitmap(exercise.image!!))
        values.put(DatabaseConstants.TABLE_EXERCISE_TYPE, exercise.type?.id)
        values.put(DatabaseConstants.TABLE_EXERCISE_STATE, exercise.state)
        values.put(DatabaseConstants.TABLE_EXERCISE_CREATEDBY, exercise.createdBy)
        values.put(DatabaseConstants.TABLE_EXERCISE_CREATEDATE, unixTime)
        values.put(DatabaseConstants.TABLE_EXERCISE_MODIFYDATE, unixTime)

        var id : Long = -1
        try {
            id = db.insertOrThrow(DatabaseConstants.TABLE_EXERCISE, null, values)
        } catch (e: Exception) {
            Log.e("DB ERROR", e.toString())
            e.printStackTrace()
        }

        db.close()
        return id
    }

    fun updateExerciseState(exercise: Exercise){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(DatabaseConstants.TABLE_EXERCISE_STATE, exercise.state)

        db.update(DatabaseConstants.TABLE_EXERCISE, values, "${DatabaseConstants.TABLE_EXERCISE_ID} = ${exercise.id}", null)
        db.close()
    }


      /*********************************/
     /********** MEASUREMENT **********/
    /*********************************/

    fun getMeasurement(query: String, array: Array<String>?): ArrayList<Measurement> {
        val measurements = ArrayList<Measurement>()

        val db = this.writableDatabase
        val cursor : Cursor
        cursor = db.rawQuery(query, array)

        if (cursor.moveToFirst()) {
            do {
                val measurement = Measurement()
                val measureTypeId = cursor.getString(cursor.getColumnIndex(DatabaseConstants.TABLE_MEASUREMENT_TYPE))
                val measureTypeList = getMeasureType(SelectTransactions.SELECT_MEASURETYPE_BY_ID, arrayOf(measureTypeId))
                val measureType = if (measureTypeList.size >= 1) measureTypeList[0] else null
                val valueDate = cursor.getLong(cursor.getColumnIndex(DatabaseConstants.TABLE_MEASUREMENT_DATE))
                val userId = cursor.getString(cursor.getColumnIndex(DatabaseConstants.TABLE_MEASUREMENT_CREATEDBY))
                val createDateUnixSeconds = cursor.getLong(cursor.getColumnIndex(DatabaseConstants.TABLE_MEASUREMENT_CREATEDATE))
                val modifyDateUnixSeconds = cursor.getLong(cursor.getColumnIndex(DatabaseConstants.TABLE_MEASUREMENT_MODIFYDATE))

                measurement.id = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.TABLE_MEASUREMENT_ID))
                measurement.type = measureType
                measurement.value = cursor.getDouble(cursor.getColumnIndex(DatabaseConstants.TABLE_MEASUREMENT_VALUE))
                measurement.date = Date((valueDate * 1000))
                measurement.createdBy = userId
                measurement.createDate = Date((createDateUnixSeconds * 1000))
                measurement.modifyDate = Date((modifyDateUnixSeconds * 1000))

                measurements.add(measurement)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return measurements
    }

    fun insertMeasurement(measurement: Measurement): Long {
        val db = this.writableDatabase
        val values = ContentValues()

        val unixTime = Utils.getUnixSeconds()

        values.put(DatabaseConstants.TABLE_MEASUREMENT_TYPE, measurement.type!!.id)
        values.put(DatabaseConstants.TABLE_MEASUREMENT_VALUE, measurement.value)
        values.put(DatabaseConstants.TABLE_MEASUREMENT_DATE, measurement.date!!.time/1000)
        values.put(DatabaseConstants.TABLE_MEASUREMENT_CREATEDBY, MainActivity.user?.uid)
        values.put(DatabaseConstants.TABLE_MEASUREMENT_CREATEDATE, unixTime)
        values.put(DatabaseConstants.TABLE_MEASUREMENT_MODIFYDATE, unixTime)

        var id : Long = -1
        try {
            id = db.insertOrThrow(DatabaseConstants.TABLE_MEASUREMENT, null, values)
        } catch (e: Exception) {
            Log.e("DB ERROR", e.toString())
            e.printStackTrace()
        }

        db.close()
        return id
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
            id = db.insertOrThrow(DatabaseConstants.TABLE_EXERCISETYPE, null, values)
        } catch (e: Exception) {
            Log.e("DB ERROR", e.toString())
            e.printStackTrace()
        }

        db.close()
        return id
    }

      /**************************/
     /********** DAYS **********/
    /**************************/

    private fun getDays(query: String, array: Array<String>?): ArrayList<Day> {
        val db = this.writableDatabase
        val cursor : Cursor
        cursor = db.rawQuery(query, array)

        val dayList = ArrayList<Day>()
        if (cursor.moveToFirst()) {
            do {
                val day = Day()
                val userId = cursor.getString(cursor.getColumnIndex(DatabaseConstants.TABLE_DAY_CREATEDBY))
                val createDateUnixSeconds = cursor.getLong(cursor.getColumnIndex(DatabaseConstants.TABLE_DAY_CREATEDATE))
                val modifyDateUnixSeconds = cursor.getLong(cursor.getColumnIndex(DatabaseConstants.TABLE_DAY_MODIFYDATE))

                day.id = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.TABLE_DAY_ID))
                day.workoutId = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.TABLE_DAY_WORKOUTID))
                day.exerciseDayList = getExerciseDay(SelectTransactions.SELECT_EXERCISEDAY_BY_DAY_ID, arrayOf(day.id.toString()))
                day.name = cursor.getString(cursor.getColumnIndex(DatabaseConstants.TABLE_DAY_NAME))
                day.createdBy = userId
                day.createDate = Date((createDateUnixSeconds * 1000))
                day.modifyDate = Date((modifyDateUnixSeconds * 1000))

                dayList.add(day)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return dayList
    }

    private fun insertDay(day: Day): Long {
        val db = this.writableDatabase
        val values = ContentValues()

        val unixTime = Utils.getUnixSeconds()

        values.put(DatabaseConstants.TABLE_DAY_NAME, day.name)
        values.put(DatabaseConstants.TABLE_DAY_WORKOUTID, day.workoutId)
        values.put(DatabaseConstants.TABLE_DAY_CREATEDBY, MainActivity.user?.uid)
        values.put(DatabaseConstants.TABLE_DAY_CREATEDATE, unixTime)
        values.put(DatabaseConstants.TABLE_DAY_MODIFYDATE, unixTime)

        var id: Long = -1
        try {
            id = db.insertOrThrow(DatabaseConstants.TABLE_DAY, null, values)
        } catch (e: Exception) {
            Log.e("DB ERROR", e.toString())
            e.printStackTrace()
        }

        for (exerciseDay in day.exerciseDayList) {
            exerciseDay.dayId = id.toInt()
            insertExerciseDay(exerciseDay)
        }

        //db.close()
        return id
    }


      /**********************************/
     /********** EXERCISE DAY **********/
    /**********************************/

    private fun getExerciseDay(query: String, array: Array<String>?): ArrayList<ExerciseDay> {
        val db = this.writableDatabase
        val cursor : Cursor
        cursor = db.rawQuery(query, array)

        val exerciseDayList = ArrayList<ExerciseDay>()
        if (cursor.moveToFirst()) {
            do {
                val exerciseDay = ExerciseDay()
                val exerciseId = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.TABLE_EXERCISEDAY_EXERCISEID))
                val createDateUnixSeconds = cursor.getLong(cursor.getColumnIndex(DatabaseConstants.TABLE_EXERCISEDAY_CREATEDATE))
                val modifyDateUnixSeconds = cursor.getLong(cursor.getColumnIndex(DatabaseConstants.TABLE_EXERCISEDAY_MODIFYDATE))

                exerciseDay.id = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.TABLE_EXERCISE_ID))
                exerciseDay.exercise = getExercise(SelectTransactions.SELECT_EXERCISE_BY_ID, arrayOf(exerciseId.toString())).first()
                exerciseDay.dayId = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.TABLE_EXERCISEDAY_DAYID))
                exerciseDay.series = getSerie(SelectTransactions.SELECT_SERIE_BY_USER_AND_EXERCISEDAYID, arrayOf(exerciseDay.id.toString()))
                exerciseDay.serieNum = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.TABLE_EXERCISEDAY_SERIENUM))
                exerciseDay.repNum = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.TABLE_EXERCISEDAY_REPNUM))
                exerciseDay.createdBy = cursor.getString(cursor.getColumnIndex(DatabaseConstants.TABLE_EXERCISEDAY_CREATEDBY))
                exerciseDay.createDate = Date((createDateUnixSeconds * 1000))
                exerciseDay.modifyDate = Date((modifyDateUnixSeconds * 1000))

                exerciseDayList.add(exerciseDay)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return exerciseDayList
    }

    private fun insertExerciseDay(exerciseDay: ExerciseDay): Long {
        val db = this.writableDatabase
        val values = ContentValues()

        val unixTime = Utils.getUnixSeconds()

        values.put(DatabaseConstants.TABLE_EXERCISEDAY_EXERCISEID, exerciseDay.exercise!!.id)
        values.put(DatabaseConstants.TABLE_EXERCISEDAY_DAYID, exerciseDay.dayId)
        values.put(DatabaseConstants.TABLE_EXERCISEDAY_SERIENUM, exerciseDay.serieNum)
        values.put(DatabaseConstants.TABLE_EXERCISEDAY_REPNUM, exerciseDay.repNum)
        values.put(DatabaseConstants.TABLE_EXERCISEDAY_CREATEDBY, MainActivity.user!!.uid)
        values.put(DatabaseConstants.TABLE_EXERCISEDAY_CREATEDATE, unixTime)
        values.put(DatabaseConstants.TABLE_EXERCISEDAY_MODIFYDATE, unixTime)

        var id: Long = -1
        try {
            id = db.insertOrThrow(DatabaseConstants.TABLE_EXERCISEDAY, null, values)
        } catch (e: Exception) {
            Log.e("DB ERROR", e.toString())
            e.printStackTrace()
        }

        for (serie in exerciseDay.series){
            serie.exerciseDayId = id.toInt()
            insertSerie(serie)
        }

        //db.close()
        return id
    }

      /***********************************/
     /********** MEASURE TYPES **********/
    /***********************************/

    fun getMeasureType(query: String, array: Array<String>?): ArrayList<MeasureType> {
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
            id = db.insertOrThrow(DatabaseConstants.TABLE_MEASURETYPE, null, values)
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
                weightType.choosed = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.TABLE_WEIGHTTYPE_CHOOSED)) == 1
                weightType.createDate = Date((createDateUnixSeconds * 1000))
                weightType.modifyDate = Date((modifyDateUnixSeconds * 1000))

                weightTypeList.add(weightType)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return weightTypeList
    }

    fun updateWeightTypeChoosed(weightType: WeightType): Int {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(DatabaseConstants.TABLE_WEIGHTTYPE_CHOOSED, if (weightType.choosed) 0 else 1)

        var id : Int = -1
        try {
            id = db.update(DatabaseConstants.TABLE_WEIGHTTYPE, values, "${DatabaseConstants.TABLE_WEIGHTTYPE_ID}=?", arrayOf(weightType.id.toString()))
        } catch (e: Exception) {
            Log.e("DB ERROR", e.toString())
            e.printStackTrace()
        }

        db.close()

        return id
    }

    private fun insertWeightType(weightType: WeightType): Long {
        val db = this.writableDatabase
        val values = ContentValues()

        val unixTime = Utils.getUnixSeconds()

        values.put(DatabaseConstants.TABLE_WEIGHTTYPE_CODE, weightType.code)
        values.put(DatabaseConstants.TABLE_WEIGHTTYPE_NAME, weightType.name)
        values.put(DatabaseConstants.TABLE_WEIGHTTYPE_CHOOSED, if (weightType.choosed) 1 else 0)
        values.put(DatabaseConstants.TABLE_WEIGHTTYPE_CREATEDATE, unixTime)
        values.put(DatabaseConstants.TABLE_MEASURETYPE_MODIFYDATE, unixTime)

        var id : Long = -1
        try {
            id = db.insertOrThrow(DatabaseConstants.TABLE_WEIGHTTYPE, null, values)
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

    fun getLengthType(query: String, array: Array<String>?): ArrayList<LengthType>? {
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
                lengthType.choosed = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.TABLE_LENGTHTYPE_CHOOSED)) == 1
                lengthType.createDate = Date((createDateUnixSeconds * 1000))
                lengthType.modifyDate = Date((modifyDateUnixSeconds * 1000))

                lengthTypeList.add(lengthType)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return lengthTypeList
    }

    fun updateLengthTypeChoosed(lengthType: LengthType): Int {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(DatabaseConstants.TABLE_LENGTHTYPE_CHOOSED, if (lengthType.choosed) 0 else 1)

        var id : Int = -1
        try {
            id = db.update(DatabaseConstants.TABLE_LENGTHTYPE, values, "${DatabaseConstants.TABLE_LENGTHTYPE_ID}=?", arrayOf(lengthType.id.toString()))
        } catch (e: Exception) {
            Log.e("DB ERROR", e.toString())
            e.printStackTrace()
        }

        db.close()

        return id
    }

    private fun insertLengthType(lengthType: LengthType): Long {
        val db = this.writableDatabase
        val values = ContentValues()

        val unixTime = Utils.getUnixSeconds()

        values.put(DatabaseConstants.TABLE_LENGTHTYPE_CODE, lengthType.code)
        values.put(DatabaseConstants.TABLE_LENGTHTYPE_NAME, lengthType.name)
        values.put(DatabaseConstants.TABLE_LENGTHTYPE_CHOOSED, if (lengthType.choosed) 1 else 0)
        values.put(DatabaseConstants.TABLE_LENGTHTYPE_CREATEDATE, unixTime)
        values.put(DatabaseConstants.TABLE_LENGTHTYPE_MODIFYDATE, unixTime)

        var id : Long = -1
        try {
            id = db.insertOrThrow(DatabaseConstants.TABLE_LENGTHTYPE, null, values)
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
            id = db.insertOrThrow(DatabaseConstants.TABLE_DATETYPE, null, values)
        } catch (e: Exception) {
            Log.e("DB ERROR", e.toString())
            e.printStackTrace()
        }

        db.close()
        return id
    }


      /***************************/
     /********** SERIE **********/
    /***************************/

    private fun getSerie(query: String, array: Array<String>?): ArrayList<Serie> {
        val db = this.writableDatabase
        val cursor : Cursor
        cursor = db.rawQuery(query, array)

        val serieList = ArrayList<Serie>()
        if (cursor.moveToFirst()) {
            do {
                val serie = Serie()
                val createDateUnixSeconds = cursor.getLong(cursor.getColumnIndex(DatabaseConstants.TABLE_SERIE_CREATEDATE))
                val modifyDateUnixSeconds = cursor.getLong(cursor.getColumnIndex(DatabaseConstants.TABLE_SERIE_MODIFYDATE))

                serie.id = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.TABLE_SERIE_ID))
                serie.exerciseDayId = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.TABLE_SERIE_EXERCISEDAYID))
                serie.repetitions = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.TABLE_SERIE_REPETITIONS))
                serie.weight = cursor.getDouble(cursor.getColumnIndex(DatabaseConstants.TABLE_SERIE_WEIGHT))
                serie.week = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.TABLE_SERIE_WEEK))
                serie.createdBy = cursor.getString(cursor.getColumnIndex(DatabaseConstants.TABLE_SERIE_CREATEDBY))
                serie.createDate = Date((createDateUnixSeconds * 1000))
                serie.modifyDate = Date((modifyDateUnixSeconds * 1000))

                serieList.add(serie)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return serieList
    }

    fun insertSerie(serie: Serie): Long {
        val db = this.writableDatabase
        val values = ContentValues()

        val unixTime = Utils.getUnixSeconds()

        values.put(DatabaseConstants.TABLE_SERIE_EXERCISEDAYID, serie.exerciseDayId)
        values.put(DatabaseConstants.TABLE_SERIE_REPETITIONS, serie.repetitions)
        values.put(DatabaseConstants.TABLE_SERIE_WEIGHT, serie.weight)
        values.put(DatabaseConstants.TABLE_SERIE_WEEK, serie.week)
        values.put(DatabaseConstants.TABLE_SERIE_CREATEDBY, MainActivity.user?.uid)
        values.put(DatabaseConstants.TABLE_SERIE_CREATEDATE, unixTime)
        values.put(DatabaseConstants.TABLE_SERIE_MODIFYDATE, unixTime)

        var id : Long = -1
        try {
            id = db.insertOrThrow(DatabaseConstants.TABLE_SERIE, null, values)
        } catch (e: Exception) {
            Log.e("DB ERROR", e.toString())
            e.printStackTrace()
        }

        db.close()
        return id
    }
}