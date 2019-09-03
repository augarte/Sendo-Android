package augarte.sendo.utils


class Constants{
    companion object {
        var DB_NAME = "Sendo";
        var DB_VERSION = 1;

        var TABLE_EXERCISE = "exercise"
        var TABLE_DAY = "day"
        var TABLE_MEASUREMENT = "measurement"
        var TABLE_WORKOUT = "workout"
        var TABLE_SERIE = "serie"
        var TABLE_USER = "user"

        var TABLE_EXERCISE_ID = "id"
        var TABLE_EXERCISE_DAYID = "dayId"
        var TABLE_EXERCISE_NAME = "name"
        var TABLE_EXERCISE_DESCRIPTION = "description"
        var TABLE_EXERCISE_IMAGE = "image"
        var TABLE_EXERCISE_CREATEDBY = "createdBy"
        var TABLE_EXERCISE_CREATEDATE = "createDate"
        var TABLE_EXERCISE_MODIFYDATE = "modifyDate"

        var TABLE_DAY_ID = "id"
        var TABLE_DAY_WORKOUTID = "workoutId"
        var TABLE_DAY_NAME = "name"
        var TABLE_DAY_IMAGE = "image"
        var TABLE_DAY_TYPE = "type"
        var TABLE_DAY_CREATEDBY = "createdby"
        var TABLE_DAY_CREATEDATE = "createDate"
        var TABLE_DAY_MODIFYDATE = "modifyDate"

        var TABLE_MEASUREMENT_ID = "id"
        var TABLE_MEASUREMENT_TYPE = "type"
        var TABLE_MEASUREMENT_VALUE = "value"
        var TABLE_MEASUREMENT_DATE = "date"
        var TABLE_MEASUREMENT_CREATEDATE = "createDate"
        var TABLE_MEASUREMENT_MODIFYDATE = "modifyDate"

        var TABLE_WORKOUT_ID = "id"
        var TABLE_WORKOUT_NAME = "name"
        var TABLE_WORKOUT_DESCRIPTION = "description"
        var TABLE_WORKOUT_IMAGE = "image"
        var TABLE_WORKOUT_LASTOPEN = "lastOpen"
        var TABLE_WORKOUT_CREATEDBY = "createdBy"
        var TABLE_WORKOUT_CREATEDATE = "createDate"
        var TABLE_WORKOUT_MODIFYDATE = "modifyDate"

        var TABLE_SERIE_ID = "id"
        var TABLE_SERIE_EXERCISEID = "exerciseId"
        var TABLE_SERIE_REPETITION = "repetition"
        var TABLE_SERIE_WEIGHT = "weight"
        var TABLE_SERIE_CREATEDBY = "createdBY"
        var TABLE_SERIE_CREATEDATE = "modifyDate"
        var TABLE_SERIE_MODIFYDATE = "modifyDate"

        var TABLE_USER_ID = "id"
        var TABLE_USER_NAME = "name"
        var TABLE_USER_EMAIL = "email"
        var TABLE_USER_CREATEDATE = "createDate"
        var TABLE_USER_MODIFYDATE = "modifydate"
    }
}
