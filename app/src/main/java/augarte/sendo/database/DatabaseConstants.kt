package augarte.sendo.database

class DatabaseConstants {

    companion object{
        const val DB_NAME = "Sendo"
        const val DB_VERSION = 1

        const val TABLE_EXERCISE = "exercise"
        const val TABLE_DAY = "day"
        const val TABLE_EXERCISEDAY = "exercise_day"
        const val TABLE_MEASUREMENT = "measurement"
        const val TABLE_WORKOUT = "workout"
        const val TABLE_SERIE = "serie"
        const val TABLE_USER = "user"
        const val TABLE_MEASURETYPE = "measureType"
        const val TABLE_DATETYPE = "dateType"
        const val TABLE_EXERCISETYPE = "exerciseType"
        const val TABLE_WEIGHTTYPE = "weightseType"
        const val TABLE_LENGTHTYPE = "lengthType"

        const val TABLE_EXERCISE_ID = "_id"
        const val TABLE_EXERCISE_NAME = "name"
        const val TABLE_EXERCISE_DESCRIPTION = "description"
        const val TABLE_EXERCISE_IMAGE = "image"
        const val TABLE_EXERCISE_TYPE = "type"
        const val TABLE_EXERCISE_STATE = "state"
        const val TABLE_EXERCISE_CREATEDBY = "createdBy"
        const val TABLE_EXERCISE_CREATEDATE = "createDate"
        const val TABLE_EXERCISE_MODIFYDATE = "modifyDate"

        const val TABLE_DAY_ID = "_id"
        const val TABLE_DAY_WORKOUTID = "workoutId"
        const val TABLE_DAY_NAME = "name"
        const val TABLE_DAY_CREATEDBY = "createdBy"
        const val TABLE_DAY_CREATEDATE = "createDate"
        const val TABLE_DAY_MODIFYDATE = "modifyDate"

        const val TABLE_EXERCISEDAY_ID = "_id"
        const val TABLE_EXERCISEDAY_EXERCISEID = "exerciseId"
        const val TABLE_EXERCISEDAY_DAYID = "dayId"
        const val TABLE_EXERCISEDAY_CREATEDATE = "createDate"
        const val TABLE_EXERCISEDAY_MODIFYDATE = "modifydate"

        const val TABLE_EXERCISETYPE_ID = "_id"
        const val TABLE_EXERCISETYPE_CODE = "code"
        const val TABLE_EXERCISETYPE_NAME = "name"
        const val TABLE_EXERCISETYPE_CREATEDATE = "createDate"
        const val TABLE_EXERCISETYPE_MODIFYDATE = "modifydate"

        const val TABLE_MEASUREMENT_ID = "_id"
        const val TABLE_MEASUREMENT_TYPE = "type"
        const val TABLE_MEASUREMENT_VALUE = "value"
        const val TABLE_MEASUREMENT_DATE = "date"
        const val TABLE_MEASUREMENT_CREATEDBY = "createBy"
        const val TABLE_MEASUREMENT_CREATEDATE = "createDate"
        const val TABLE_MEASUREMENT_MODIFYDATE = "modifyDate"

        const val TABLE_WORKOUT_ID = "_id"
        const val TABLE_WORKOUT_NAME = "name"
        const val TABLE_WORKOUT_DESCRIPTION = "description"
        const val TABLE_WORKOUT_IMAGE = "image"
        const val TABLE_WORKOUT_LASTOPEN = "lastOpen"
        const val TABLE_WORKOUT_CREATEDBY = "createdBy"
        const val TABLE_WORKOUT_CREATEDATE = "createDate"
        const val TABLE_WORKOUT_MODIFYDATE = "modifyDate"

        const val TABLE_SERIE_ID = "_id"
        const val TABLE_SERIE_EXERCISEID = "exerciseId"
        const val TABLE_SERIE_REPETITION = "repetition"
        const val TABLE_SERIE_WEIGHT = "weight"
        const val TABLE_SERIE_CREATEDBY = "createdBy"
        const val TABLE_SERIE_CREATEDATE = "createDate"
        const val TABLE_SERIE_MODIFYDATE = "modifyDate"

        const val TABLE_USER_ID = "_id"
        const val TABLE_USER_NAME = "name"
        const val TABLE_USER_USERNAME = "username"
        const val TABLE_USER_EMAIL = "email"
        const val TABLE_USER_PASSWORD = "password"
        const val TABLE_USER_CREATEDATE = "createDate"
        const val TABLE_USER_MODIFYDATE = "modifydate"

        const val TABLE_DATETYPE_ID = "_id"
        const val TABLE_DATETYPE_NAME = "name"
        const val TABLE_DATETYPE_CODE = "code"
        const val TABLE_DATETYPE_CREATEDATE = "createDate"
        const val TABLE_DATETYPE_MODIFYDATE = "modifydate"

        const val TABLE_MEASURETYPE_ID = "_id"
        const val TABLE_MEASURETYPE_NAME = "name"
        const val TABLE_MEASURETYPE_CODE = "code"
        const val TABLE_MEASURETYPE_CREATEDATE = "createDate"
        const val TABLE_MEASURETYPE_MODIFYDATE = "modifydate"

        const val TABLE_WEIGHTTYPE_ID = "_id"
        const val TABLE_WEIGHTTYPE_NAME = "name"
        const val TABLE_WEIGHTTYPE_CODE = "code"
        const val TABLE_WEIGHTTYPE_CHOOSED = "choosed"
        const val TABLE_WEIGHTTYPE_CREATEDATE = "createDate"
        const val TABLE_WEIGHTTYPE_MODIFYDATE = "modifydate"

        const val TABLE_LENGTHTYPE_ID = "_id"
        const val TABLE_LENGTHTYPE_NAME = "name"
        const val TABLE_LENGTHTYPE_CODE = "code"
        const val TABLE_LENGTHTYPE_CHOOSED = "choosed"
        const val TABLE_LENGTHTYPE_CREATEDATE = "createDate"
        const val TABLE_LENGTHTYPE_MODIFYDATE = "modifydate"

        const val STATE_DELETED = 0
        const val STATE_ACTIVE = 1
        const val STATE_ARCHIVED = 2

        val LIST_EXERCISETYPES = listOf(Pair("ABS","Abs"), Pair("BCK","Back"), Pair("BCP","Biceps"), Pair("CLV","Calves"), Pair("CHT","Chest"),
                                                                Pair("DLT","Deltoids"), Pair("FRA","Forearms"), Pair("GLT","Glutes"), Pair("HMS","Hamstrings"),
                                                                Pair("NCK","Neck"), Pair("OBL","Oblique"), Pair("TRP","Traps"), Pair("TRC","Triceps"), Pair("QDS","Quads"))

        val LIST_DATETYPES = listOf(Pair("ALL","All"), Pair("1WE","1 Week"), Pair("3WE","3 Weeks"), Pair("1MO","1 Month"), Pair("2MO","2 Months"), Pair("3MO","3 Month"),
                                                            Pair("6MO","6 Months"), Pair("1YR","1 Year"))

        val LIST_MEASURETYPES = listOf(Pair("WGT","Weight"), Pair("WST","Waist"), Pair("HPS","Hips"), Pair("CHT","Chest"), Pair("NCK","Neck"),
                                                                Pair("ARM","Arms"), Pair("LEG","Legs"))

        val LIST_WEIGHTTYPE = listOf(Pair("KG","Kilogram"), Pair("LBS","Waist"))

        val LIST_LENGTHTYPE = listOf(Pair("CM","Centimeter"), Pair("INCH","Inch"), Pair("MTR","Metre"), Pair("FOT","Foot"))
    }
}