package augarte.sendo.database

import augarte.sendo.utils.Constants

class CreateTableTransactions {

    companion object {
        val CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS  ${Constants.TABLE_USER} " +
                "(${Constants.TABLE_USER_ID} Integer PRIMARY KEY AUTOINCREMENT, " +
                "${Constants.TABLE_USER_NAME} TEXT, " +
                "${Constants.TABLE_USER_USERNAME} TEXT, " +
                "${Constants.TABLE_USER_EMAIL} TEXT, " +
                "${Constants.TABLE_USER_PASSWORD} TEXT, " +
                "${Constants.TABLE_USER_CREATEDATE} Integer, " +
                "${Constants.TABLE_USER_MODIFYDATE} Integer)"

        val CREATE_TABLE_WORKOUT = "CREATE TABLE IF NOT EXISTS ${Constants.TABLE_WORKOUT} " +
                "(${Constants.TABLE_WORKOUT_ID} Integer PRIMARY KEY AUTOINCREMENT, " +
                "${Constants.TABLE_WORKOUT_NAME} TEXT NOT NULL, " +
                "${Constants.TABLE_WORKOUT_DESCRIPTION} TEXT, " +
                "${Constants.TABLE_WORKOUT_IMAGE} TEXT, " +
                "${Constants.TABLE_WORKOUT_CREATEDBY} TEXT, " +
                "${Constants.TABLE_WORKOUT_LASTOPEN} Integer, " +
                "${Constants.TABLE_WORKOUT_CREATEDATE} Integer, " +
                "${Constants.TABLE_WORKOUT_MODIFYDATE} Integer)"

        val CREATE_TABLE_DAY = "CREATE TABLE IF NOT EXISTS ${Constants.TABLE_DAY} " +
                "(${Constants.TABLE_DAY_ID} Integer PRIMARY KEY AUTOINCREMENT, " +
                "${Constants.TABLE_DAY_NAME} TEXT NOT NULL, " +
                "${Constants.TABLE_DAY_WORKOUTID} Integer, " +
                "${Constants.TABLE_DAY_CREATEDBY} TEXT, " +
                "${Constants.TABLE_DAY_CREATEDATE} Integer, " +
                "${Constants.TABLE_DAY_MODIFYDATE} Integer)"

        val CREATE_TABLE_EXERCISE = "CREATE TABLE IF NOT EXISTS ${Constants.TABLE_EXERCISE} " +
                "(${Constants.TABLE_EXERCISE_ID} Integer PRIMARY KEY AUTOINCREMENT, " +
                "${Constants.TABLE_EXERCISE_NAME} TEXT NOT NULL, " +
                "${Constants.TABLE_EXERCISE_DAYID} Integer, " +
                "${Constants.TABLE_EXERCISE_DESCRIPTION} TEXT, " +
                "${Constants.TABLE_EXERCISE_IMAGE} TEXT, " +
                "${Constants.TABLE_EXERCISE_CREATEDBY} TEXT, " +
                "${Constants.TABLE_EXERCISE_CREATEDATE} Integer, " +
                "${Constants.TABLE_EXERCISE_MODIFYDATE} Integer)"

        val CREATE_TABLE_SERIE = "CREATE TABLE IF NOT EXISTS ${Constants.TABLE_SERIE} " +
                "(${Constants.TABLE_SERIE_ID} Integer PRIMARY KEY AUTOINCREMENT, " +
                "${Constants.TABLE_SERIE_EXERCISEID} Integer, " +
                "${Constants.TABLE_SERIE_REPETITION} TEXT, " +
                "${Constants.TABLE_SERIE_WEIGHT} TEXT, " +
                "${Constants.TABLE_SERIE_CREATEDBY} TEXT, " +
                "${Constants.TABLE_SERIE_CREATEDATE} Integer, " +
                "${Constants.TABLE_SERIE_MODIFYDATE} Integer)"

        val CREATE_TABLE_MEASUREMENT = "CREATE TABLE IF NOT EXISTS  ${Constants.TABLE_MEASUREMENT} " +
                "(${Constants.TABLE_MEASUREMENT_ID} Integer PRIMARY KEY AUTOINCREMENT, " +
                "${Constants.TABLE_MEASUREMENT_TYPE} TEXT, " +
                "${Constants.TABLE_MEASUREMENT_VALUE} TEXT, " +
                "${Constants.TABLE_MEASUREMENT_DATE} TEXT," +
                "${Constants.TABLE_MEASUREMENT_CREATEDBY} TEXT, " +
                "${Constants.TABLE_MEASUREMENT_CREATEDATE} Integer, " +
                "${Constants.TABLE_MEASUREMENT_MODIFYDATE} Integer)"

        val CREATE_TABLE_MEASUREMENTTYPE = "CREATE TABLE IF NOT EXISTS  ${Constants.TABLE_MEASURETYPE} " +
                "(${Constants.TABLE_MEASURETYPE_ID} Integer PRIMARY KEY AUTOINCREMENT, " +
                "${Constants.TABLE_MEASURETYPE_NAME} TEXT, " +
                "${Constants.TABLE_MEASURETYPE_CODE} TEXT, " +
                "${Constants.TABLE_MEASURETYPE_CREATEDATE} Integer, " +
                "${Constants.TABLE_MEASURETYPE_MODIFYDATE} Integer)"

        val CREATE_TABLE_DATETYPE = "CREATE TABLE IF NOT EXISTS  ${Constants.TABLE_DATETYPE} " +
                "(${Constants.TABLE_DATETYPE_ID} Integer PRIMARY KEY AUTOINCREMENT, " +
                "${Constants.TABLE_DATETYPE_NAME} TEXT, " +
                "${Constants.TABLE_DATETYPE_CODE} TEXT, " +
                "${Constants.TABLE_DATETYPE_CREATEDATE} Integer, " +
                "${Constants.TABLE_DATETYPE_MODIFYDATE} Integer)"
    }
}