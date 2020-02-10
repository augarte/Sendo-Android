package augarte.sendo.database

class CreateTableTransactions {

    companion object {
        const val CREATE_TABLE_WORKOUT = "CREATE TABLE IF NOT EXISTS ${DatabaseConstants.TABLE_WORKOUT} " +
                "(${DatabaseConstants.TABLE_WORKOUT_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${DatabaseConstants.TABLE_WORKOUT_NAME} TEXT NOT NULL NOT NULL, " +
                "${DatabaseConstants.TABLE_WORKOUT_DESCRIPTION} TEXT, " +
                "${DatabaseConstants.TABLE_WORKOUT_IMAGE} BLOB, " +
                "${DatabaseConstants.TABLE_WORKOUT_CREATEDBY} TEXT NOT NULL, " +
                "${DatabaseConstants.TABLE_WORKOUT_LASTOPEN} INTEGER, " +
                "${DatabaseConstants.TABLE_WORKOUT_CREATEDATE} INTEGER NOT NULL, " +
                "${DatabaseConstants.TABLE_WORKOUT_MODIFYDATE} INTEGER NOT NULL)"

        const val CREATE_TABLE_DAY = "CREATE TABLE IF NOT EXISTS ${DatabaseConstants.TABLE_DAY} " +
                "(${DatabaseConstants.TABLE_DAY_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${DatabaseConstants.TABLE_DAY_NAME} TEXT NOT NULL, " +
                "${DatabaseConstants.TABLE_DAY_WORKOUTID} INTEGER, " +
                "${DatabaseConstants.TABLE_DAY_CREATEDBY} TEXT NOT NULL, " +
                "${DatabaseConstants.TABLE_DAY_CREATEDATE} INTEGER NOT NULL, " +
                "${DatabaseConstants.TABLE_DAY_MODIFYDATE} INTEGER NOT NULL, " +
                "FOREIGN KEY (${DatabaseConstants.TABLE_DAY_WORKOUTID}) REFERENCES ${DatabaseConstants.TABLE_WORKOUT} (${DatabaseConstants.TABLE_WORKOUT_ID}))"

        const val CREATE_TABLE_EXERCISE = "CREATE TABLE IF NOT EXISTS ${DatabaseConstants.TABLE_EXERCISE} " +
                "(${DatabaseConstants.TABLE_EXERCISE_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${DatabaseConstants.TABLE_EXERCISE_NAME} TEXT NOT NULL, " +
                "${DatabaseConstants.TABLE_EXERCISE_DESCRIPTION} TEXT, " +
                "${DatabaseConstants.TABLE_EXERCISE_IMAGE} TEXT, " +
                "${DatabaseConstants.TABLE_EXERCISE_TYPE} INTEGER NOT NULL, " +
                "${DatabaseConstants.TABLE_EXERCISE_STATE} INTEGER NOT NULL, " +
                "${DatabaseConstants.TABLE_EXERCISE_FAVORITE} INTEGER NOT NULL DEFAULT 0, " +
                "${DatabaseConstants.TABLE_EXERCISE_CREATEDBY} TEXT NOT NULL, " +
                "${DatabaseConstants.TABLE_EXERCISE_CREATEDATE} INTEGER NOT NULL, " +
                "${DatabaseConstants.TABLE_EXERCISE_MODIFYDATE} INTEGER NOT NULL, " +
                "FOREIGN KEY (${DatabaseConstants.TABLE_EXERCISE_TYPE}) REFERENCES ${DatabaseConstants.TABLE_EXERCISETYPE} (${DatabaseConstants.TABLE_EXERCISETYPE_ID}))"

        const val CREATE_TABLE_EXERCISEDAY = "CREATE TABLE IF NOT EXISTS ${DatabaseConstants.TABLE_EXERCISEDAY} " +
                "(${DatabaseConstants.TABLE_EXERCISEDAY_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${DatabaseConstants.TABLE_EXERCISEDAY_DAYID} INTEGER NOT NULL, " +
                "${DatabaseConstants.TABLE_EXERCISEDAY_EXERCISEID} INTEGER NOT NULL, " +
                "${DatabaseConstants.TABLE_EXERCISEDAY_SERIENUM} INTEGER NOT NULL, " +
                "${DatabaseConstants.TABLE_EXERCISEDAY_REPNUM} INTEGER NOT NULL, " +
                "${DatabaseConstants.TABLE_EXERCISEDAY_CREATEDBY} TEXT NOT NULL, " +
                "${DatabaseConstants.TABLE_EXERCISEDAY_CREATEDATE} INTEGER NOT NULL, " +
                "${DatabaseConstants.TABLE_EXERCISEDAY_MODIFYDATE} INTEGER NOT NULL, " +
                "FOREIGN KEY (${DatabaseConstants.TABLE_EXERCISEDAY_DAYID}) REFERENCES ${DatabaseConstants.TABLE_DAY} (${DatabaseConstants.TABLE_DAY_ID})," +
                "FOREIGN KEY (${DatabaseConstants.TABLE_EXERCISEDAY_EXERCISEID}) REFERENCES ${DatabaseConstants.TABLE_EXERCISE} (${DatabaseConstants.TABLE_EXERCISE_ID}))"

        const val CREATE_TABLE_SERIE = "CREATE TABLE IF NOT EXISTS ${DatabaseConstants.TABLE_SERIE} " +
                "(${DatabaseConstants.TABLE_SERIE_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${DatabaseConstants.TABLE_SERIE_EXERCISEDAYID} INTEGER NOT NULL, " +
                "${DatabaseConstants.TABLE_SERIE_REPETITIONS} INTEGER, " +
                "${DatabaseConstants.TABLE_SERIE_WEIGHT} REAL, " +
                "${DatabaseConstants.TABLE_SERIE_WEEK} INTEGER, " +
                "${DatabaseConstants.TABLE_SERIE_CREATEDBY} TEXT NOT NULL NOT NULL, " +
                "${DatabaseConstants.TABLE_SERIE_CREATEDATE} INTEGER NOT NULL, " +
                "${DatabaseConstants.TABLE_SERIE_MODIFYDATE} INTEGER NOT NULL," +
                "FOREIGN KEY (${DatabaseConstants.TABLE_SERIE_EXERCISEDAYID}) REFERENCES ${DatabaseConstants.TABLE_EXERCISEDAY} (${DatabaseConstants.TABLE_EXERCISEDAY_ID}))"

        const val CREATE_TABLE_MEASUREMENT = "CREATE TABLE IF NOT EXISTS  ${DatabaseConstants.TABLE_MEASUREMENT} " +
                "(${DatabaseConstants.TABLE_MEASUREMENT_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${DatabaseConstants.TABLE_MEASUREMENT_TYPE} TEXT NOT NULL, " +
                "${DatabaseConstants.TABLE_MEASUREMENT_VALUE} REAL NOT NULL, " +
                "${DatabaseConstants.TABLE_MEASUREMENT_DATE} TEXT NOT NULL," +
                "${DatabaseConstants.TABLE_MEASUREMENT_CREATEDBY} TEXT NOT NULL, " +
                "${DatabaseConstants.TABLE_MEASUREMENT_CREATEDATE} INTEGER NOT NULL, " +
                "${DatabaseConstants.TABLE_MEASUREMENT_MODIFYDATE} INTEGER NOT NULL," +
                "FOREIGN KEY (${DatabaseConstants.TABLE_MEASUREMENT_TYPE}) REFERENCES ${DatabaseConstants.TABLE_MEASURETYPE} (${DatabaseConstants.TABLE_MEASURETYPE_ID}))"

        const val CREATE_TABLE_EXERCISETYPE = "CREATE TABLE IF NOT EXISTS ${DatabaseConstants.TABLE_EXERCISETYPE} " +
                "(${DatabaseConstants.TABLE_EXERCISETYPE_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${DatabaseConstants.TABLE_EXERCISETYPE_CODE} TEXT NOT NULL, " +
                "${DatabaseConstants.TABLE_EXERCISETYPE_NAME} TEXT NOT NULL, " +
                "${DatabaseConstants.TABLE_EXERCISETYPE_CREATEDATE} INTEGER NOT NULL, " +
                "${DatabaseConstants.TABLE_EXERCISETYPE_MODIFYDATE} INTEGER NOT NULL)"

        const val CREATE_TABLE_MEASURETYPE = "CREATE TABLE IF NOT EXISTS  ${DatabaseConstants.TABLE_MEASURETYPE} " +
                "(${DatabaseConstants.TABLE_MEASURETYPE_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${DatabaseConstants.TABLE_MEASURETYPE_NAME} TEXT NOT NULL, " +
                "${DatabaseConstants.TABLE_MEASURETYPE_CODE} TEXT NOT NULL, " +
                "${DatabaseConstants.TABLE_MEASURETYPE_CREATEDATE} INTEGER NOT NULL, " +
                "${DatabaseConstants.TABLE_MEASURETYPE_MODIFYDATE} INTEGER NOT NULL)"

        const val CREATE_TABLE_DATETYPE = "CREATE TABLE IF NOT EXISTS  ${DatabaseConstants.TABLE_DATETYPE} " +
                "(${DatabaseConstants.TABLE_DATETYPE_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${DatabaseConstants.TABLE_DATETYPE_NAME} TEXT NOT NULL, " +
                "${DatabaseConstants.TABLE_DATETYPE_CODE} TEXT NOT NULL, " +
                "${DatabaseConstants.TABLE_DATETYPE_CREATEDATE} INTEGER NOT NULL, " +
                "${DatabaseConstants.TABLE_DATETYPE_MODIFYDATE} INTEGER NOT NULL)"

        const val CREATE_TABLE_WEIGHTYPE = "CREATE TABLE IF NOT EXISTS  ${DatabaseConstants.TABLE_WEIGHTTYPE} " +
                "(${DatabaseConstants.TABLE_WEIGHTTYPE_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${DatabaseConstants.TABLE_WEIGHTTYPE_NAME} TEXT NOT NULL, " +
                "${DatabaseConstants.TABLE_WEIGHTTYPE_CODE} TEXT NOT NULL, " +
                "${DatabaseConstants.TABLE_WEIGHTTYPE_CHOOSED} INTEGER NOT NULL, " +
                "${DatabaseConstants.TABLE_WEIGHTTYPE_CREATEDATE} INTEGER NOT NULL, " +
                "${DatabaseConstants.TABLE_WEIGHTTYPE_MODIFYDATE} INTEGER NOT NULL)"

        const val CREATE_TABLE_LENGTHTYPE = "CREATE TABLE IF NOT EXISTS  ${DatabaseConstants.TABLE_LENGTHTYPE} " +
                "(${DatabaseConstants.TABLE_LENGTHTYPE_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${DatabaseConstants.TABLE_LENGTHTYPE_NAME} TEXT NOT NULL, " +
                "${DatabaseConstants.TABLE_LENGTHTYPE_CODE} TEXT NOT NULL, " +
                "${DatabaseConstants.TABLE_LENGTHTYPE_CHOOSED} INTEGER NOT NULL, " +
                "${DatabaseConstants.TABLE_LENGTHTYPE_CREATEDATE} INTEGER NOT NULL, " +
                "${DatabaseConstants.TABLE_LENGTHTYPE_MODIFYDATE} INTEGER NOT NULL)"

        const val CREATE_TABLE_WORKLOADTYPE = "CREATE TABLE IF NOT EXISTS  ${DatabaseConstants.TABLE_WORKLOADTYPE} " +
                "(${DatabaseConstants.TABLE_LENGTHTYPE_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${DatabaseConstants.TABLE_LENGTHTYPE_NAME} TEXT NOT NULL, " +
                "${DatabaseConstants.TABLE_LENGTHTYPE_CODE} TEXT NOT NULL, " +
                "${DatabaseConstants.TABLE_LENGTHTYPE_CHOOSED} INTEGER NOT NULL, " +
                "${DatabaseConstants.TABLE_LENGTHTYPE_CREATEDATE} INTEGER NOT NULL, " +
                "${DatabaseConstants.TABLE_LENGTHTYPE_MODIFYDATE} INTEGER NOT NULL)"
    }
}