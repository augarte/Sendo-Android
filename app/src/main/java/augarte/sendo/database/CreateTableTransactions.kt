package augarte.sendo.database

class CreateTableTransactions {

    companion object {
        const val CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS  ${DatabaseConstants.TABLE_USER} " +
                "(${DatabaseConstants.TABLE_USER_ID} Integer PRIMARY KEY AUTOINCREMENT, " +
                "${DatabaseConstants.TABLE_USER_NAME} TEXT, " +
                "${DatabaseConstants.TABLE_USER_USERNAME} TEXT, " +
                "${DatabaseConstants.TABLE_USER_EMAIL} TEXT, " +
                "${DatabaseConstants.TABLE_USER_PASSWORD} TEXT, " +
                "${DatabaseConstants.TABLE_USER_CREATEDATE} Integer, " +
                "${DatabaseConstants.TABLE_USER_MODIFYDATE} Integer)"

        const val CREATE_TABLE_WORKOUT = "CREATE TABLE IF NOT EXISTS ${DatabaseConstants.TABLE_WORKOUT} " +
                "(${DatabaseConstants.TABLE_WORKOUT_ID} Integer PRIMARY KEY AUTOINCREMENT, " +
                "${DatabaseConstants.TABLE_WORKOUT_NAME} TEXT NOT NULL, " +
                "${DatabaseConstants.TABLE_WORKOUT_DESCRIPTION} TEXT, " +
                "${DatabaseConstants.TABLE_WORKOUT_IMAGE} TEXT, " +
                "${DatabaseConstants.TABLE_WORKOUT_CREATEDBY} TEXT, " +
                "${DatabaseConstants.TABLE_WORKOUT_LASTOPEN} Integer, " +
                "${DatabaseConstants.TABLE_WORKOUT_CREATEDATE} Integer, " +
                "${DatabaseConstants.TABLE_WORKOUT_MODIFYDATE} Integer)"

        const val CREATE_TABLE_DAY = "CREATE TABLE IF NOT EXISTS ${DatabaseConstants.TABLE_DAY} " +
                "(${DatabaseConstants.TABLE_DAY_ID} Integer PRIMARY KEY AUTOINCREMENT, " +
                "${DatabaseConstants.TABLE_DAY_NAME} TEXT NOT NULL, " +
                "${DatabaseConstants.TABLE_DAY_WORKOUTID} Integer, " +
                "${DatabaseConstants.TABLE_DAY_CREATEDBY} TEXT, " +
                "${DatabaseConstants.TABLE_DAY_CREATEDATE} Integer, " +
                "${DatabaseConstants.TABLE_DAY_MODIFYDATE} Integer)"

        const val CREATE_TABLE_EXERCISE = "CREATE TABLE IF NOT EXISTS ${DatabaseConstants.TABLE_EXERCISE} " +
                "(${DatabaseConstants.TABLE_EXERCISE_ID} Integer PRIMARY KEY AUTOINCREMENT, " +
                "${DatabaseConstants.TABLE_EXERCISE_NAME} TEXT NOT NULL, " +
                "${DatabaseConstants.TABLE_EXERCISE_DESCRIPTION} TEXT, " +
                "${DatabaseConstants.TABLE_EXERCISE_IMAGE} TEXT, " +
                "${DatabaseConstants.TABLE_EXERCISE_CREATEDBY} TEXT, " +
                "${DatabaseConstants.TABLE_EXERCISE_CREATEDATE} Integer, " +
                "${DatabaseConstants.TABLE_EXERCISE_MODIFYDATE} Integer)"

        const val CREATE_TABLE_EXERCISEDAY = "CREATE TABLE IF NOT EXISTS ${DatabaseConstants.TABLE_EXERCISEDAY} " +
                "(${DatabaseConstants.TABLE_EXERCISEDAY_ID} Integer PRIMARY KEY AUTOINCREMENT, " +
                "${DatabaseConstants.TABLE_EXERCISEDAY_DAYID} Integer, " +
                "${DatabaseConstants.TABLE_EXERCISEDAY_EXERCISEID} Integer)"

        const val CREATE_TABLE_SERIE = "CREATE TABLE IF NOT EXISTS ${DatabaseConstants.TABLE_SERIE} " +
                "(${DatabaseConstants.TABLE_SERIE_ID} Integer PRIMARY KEY AUTOINCREMENT, " +
                "${DatabaseConstants.TABLE_SERIE_EXERCISEID} Integer, " +
                "${DatabaseConstants.TABLE_SERIE_REPETITION} TEXT, " +
                "${DatabaseConstants.TABLE_SERIE_WEIGHT} TEXT, " +
                "${DatabaseConstants.TABLE_SERIE_CREATEDBY} TEXT, " +
                "${DatabaseConstants.TABLE_SERIE_CREATEDATE} Integer, " +
                "${DatabaseConstants.TABLE_SERIE_MODIFYDATE} Integer)"

        const val CREATE_TABLE_MEASUREMENT = "CREATE TABLE IF NOT EXISTS  ${DatabaseConstants.TABLE_MEASUREMENT} " +
                "(${DatabaseConstants.TABLE_MEASUREMENT_ID} Integer PRIMARY KEY AUTOINCREMENT, " +
                "${DatabaseConstants.TABLE_MEASUREMENT_TYPE} TEXT, " +
                "${DatabaseConstants.TABLE_MEASUREMENT_VALUE} TEXT, " +
                "${DatabaseConstants.TABLE_MEASUREMENT_DATE} TEXT," +
                "${DatabaseConstants.TABLE_MEASUREMENT_CREATEDBY} TEXT, " +
                "${DatabaseConstants.TABLE_MEASUREMENT_CREATEDATE} Integer, " +
                "${DatabaseConstants.TABLE_MEASUREMENT_MODIFYDATE} Integer)"

        const val CREATE_TABLE_MEASUREMENTTYPE = "CREATE TABLE IF NOT EXISTS  ${DatabaseConstants.TABLE_MEASURETYPE} " +
                "(${DatabaseConstants.TABLE_MEASURETYPE_ID} Integer PRIMARY KEY AUTOINCREMENT, " +
                "${DatabaseConstants.TABLE_MEASURETYPE_NAME} TEXT, " +
                "${DatabaseConstants.TABLE_MEASURETYPE_CODE} TEXT, " +
                "${DatabaseConstants.TABLE_MEASURETYPE_CREATEDATE} Integer, " +
                "${DatabaseConstants.TABLE_MEASURETYPE_MODIFYDATE} Integer)"

        const val CREATE_TABLE_DATETYPE = "CREATE TABLE IF NOT EXISTS  ${DatabaseConstants.TABLE_DATETYPE} " +
                "(${DatabaseConstants.TABLE_DATETYPE_ID} Integer PRIMARY KEY AUTOINCREMENT, " +
                "${DatabaseConstants.TABLE_DATETYPE_NAME} TEXT, " +
                "${DatabaseConstants.TABLE_DATETYPE_CODE} TEXT, " +
                "${DatabaseConstants.TABLE_DATETYPE_CREATEDATE} Integer, " +
                "${DatabaseConstants.TABLE_DATETYPE_MODIFYDATE} Integer)"
    }
}