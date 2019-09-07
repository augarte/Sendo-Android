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

        const val TABLE_EXERCISE_ID = "id"
        const val TABLE_EXERCISE_NAME = "name"
        const val TABLE_EXERCISE_DESCRIPTION = "description"
        const val TABLE_EXERCISE_IMAGE = "image"
        const val TABLE_EXERCISE_CREATEDBY = "createdBy"
        const val TABLE_EXERCISE_CREATEDATE = "createDate"
        const val TABLE_EXERCISE_MODIFYDATE = "modifyDate"

        const val TABLE_DAY_ID = "id"
        const val TABLE_DAY_WORKOUTID = "workoutId"
        const val TABLE_DAY_NAME = "name"
        const val TABLE_DAY_CREATEDBY = "createdBy"
        const val TABLE_DAY_CREATEDATE = "createDate"
        const val TABLE_DAY_MODIFYDATE = "modifyDate"

        const val TABLE_EXERCISEDAY_ID = "id"
        const val TABLE_EXERCISEDAY_EXERCISEID = "exerciseId"
        const val TABLE_EXERCISEDAY_DAYID = "dayId"

        const val TABLE_MEASUREMENT_ID = "id"
        const val TABLE_MEASUREMENT_TYPE = "type"
        const val TABLE_MEASUREMENT_VALUE = "value"
        const val TABLE_MEASUREMENT_DATE = "date"
        const val TABLE_MEASUREMENT_CREATEDBY = "createBy"
        const val TABLE_MEASUREMENT_CREATEDATE = "createDate"
        const val TABLE_MEASUREMENT_MODIFYDATE = "modifyDate"

        const val TABLE_WORKOUT_ID = "id"
        const val TABLE_WORKOUT_NAME = "name"
        const val TABLE_WORKOUT_DESCRIPTION = "description"
        const val TABLE_WORKOUT_IMAGE = "image"
        const val TABLE_WORKOUT_LASTOPEN = "lastOpen"
        const val TABLE_WORKOUT_CREATEDBY = "createdBy"
        const val TABLE_WORKOUT_CREATEDATE = "createDate"
        const val TABLE_WORKOUT_MODIFYDATE = "modifyDate"

        const val TABLE_SERIE_ID = "id"
        const val TABLE_SERIE_EXERCISEID = "exerciseId"
        const val TABLE_SERIE_REPETITION = "repetition"
        const val TABLE_SERIE_WEIGHT = "weight"
        const val TABLE_SERIE_CREATEDBY = "createdBy"
        const val TABLE_SERIE_CREATEDATE = "createDate"
        const val TABLE_SERIE_MODIFYDATE = "modifyDate"

        const val TABLE_USER_ID = "id"
        const val TABLE_USER_NAME = "name"
        const val TABLE_USER_USERNAME = "username"
        const val TABLE_USER_EMAIL = "email"
        const val TABLE_USER_PASSWORD = "password"
        const val TABLE_USER_CREATEDATE = "createDate"
        const val TABLE_USER_MODIFYDATE = "modifydate"

        const val TABLE_DATETYPE_ID = "id"
        const val TABLE_DATETYPE_NAME = "name"
        const val TABLE_DATETYPE_CODE = "username"
        const val TABLE_DATETYPE_CREATEDATE = "createDate"
        const val TABLE_DATETYPE_MODIFYDATE = "modifydate"

        const val TABLE_MEASURETYPE_ID = "id"
        const val TABLE_MEASURETYPE_NAME = "name"
        const val TABLE_MEASURETYPE_CODE = "username"
        const val TABLE_MEASURETYPE_CREATEDATE = "createDate"
        const val TABLE_MEASURETYPE_MODIFYDATE = "modifydate"
    }
}