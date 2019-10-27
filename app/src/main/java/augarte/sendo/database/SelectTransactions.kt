package augarte.sendo.database

class SelectTransactions {

    companion object{
        const val SELECT_ALL_WORKOUT_ORDER_NAME = "SELECT  * FROM ${DatabaseConstants.TABLE_WORKOUT} ORDER BY ${DatabaseConstants.TABLE_WORKOUT_LASTOPEN} ASC"
        const val SELECT_ALL_WORKOUT_ORDER_NAME_NOIMAGE = "SELECT _id, name, description, lastOpen, createdBy, createDate, modifyDate FROM ${DatabaseConstants.TABLE_WORKOUT} ORDER BY ${DatabaseConstants.TABLE_WORKOUT_LASTOPEN} ASC"

        const val SELECT_WORKOUTIMAGE_BY_ID = "SELECT ${DatabaseConstants.TABLE_WORKOUT_IMAGE} FROM ${DatabaseConstants.TABLE_WORKOUT} WHERE ${DatabaseConstants.TABLE_WORKOUT_ID} = ?"
        const val SELECT_WORKOUT_BY_ID = "SELECT * FROM ${DatabaseConstants.TABLE_WORKOUT} WHERE ${DatabaseConstants.TABLE_WORKOUT_ID} = ?"

        const val SELECT_EXERCISE_BY_ID = "SELECT * FROM ${DatabaseConstants.TABLE_EXERCISE} WHERE ${DatabaseConstants.TABLE_EXERCISE_ID} = ?"
        const val SELECT_EXERCISES_BY_DAY = "SELECT * FROM ${DatabaseConstants.TABLE_EXERCISE} ex INNER JOIN ${DatabaseConstants.TABLE_EXERCISEDAY} exda ON ex.${DatabaseConstants.TABLE_EXERCISE_ID} = exda.${DatabaseConstants.TABLE_EXERCISEDAY_EXERCISEID} WHERE exda.${DatabaseConstants.TABLE_EXERCISEDAY_DAYID} = ?"
        const val SELECT_ALL_EXERCISE_ORDER_NAME = "SELECT * FROM ${DatabaseConstants.TABLE_EXERCISE} WHERE ${DatabaseConstants.TABLE_EXERCISE_STATE} = ${DatabaseConstants.STATE_ACTIVE} ORDER BY ${DatabaseConstants.TABLE_EXERCISE_NAME} ASC"
        const val SELECT_ALL_EXERCISE_ORDER_TYPE = "SELECT * FROM ${DatabaseConstants.TABLE_EXERCISE} WHERE ${DatabaseConstants.TABLE_EXERCISE_STATE} = ${DatabaseConstants.STATE_ACTIVE} ORDER BY ${DatabaseConstants.TABLE_EXERCISE_TYPE} ASC"
        const val SELECT_ARCHIVED_EXERCISES_ORDER_NAME = "SELECT * FROM ${DatabaseConstants.TABLE_EXERCISE} WHERE ${DatabaseConstants.TABLE_EXERCISE_STATE} = ${DatabaseConstants.STATE_ARCHIVED} ORDER BY ${DatabaseConstants.TABLE_EXERCISE_NAME} ASC"

        const val SELECT_ALL_EXERCISETYPE = "SELECT * FROM ${DatabaseConstants.TABLE_EXERCISETYPE}"
        const val SELECT_EXERCISETYPE_BY_ID = "SELECT * FROM ${DatabaseConstants.TABLE_EXERCISETYPE} WHERE ${DatabaseConstants.TABLE_EXERCISETYPE_ID} = ?"

        const val SELECT_MEASUREMENT_BY_TYPE_ORDER_DATE = "SELECT * FROM ${DatabaseConstants.TABLE_MEASUREMENT} WHERE ${DatabaseConstants.TABLE_MEASUREMENT_TYPE} = ? ORDER BY ${DatabaseConstants.TABLE_MEASUREMENT_DATE} ASC"
        const val SELECT_MEASUREMENT_BY_TYPE_AND_DATES_ORDER_DATE = "SELECT * FROM ${DatabaseConstants.TABLE_MEASUREMENT} WHERE ${DatabaseConstants.TABLE_MEASUREMENT_TYPE} = ? AND ${DatabaseConstants.TABLE_MEASUREMENT_DATE} > ? AND ${DatabaseConstants.TABLE_MEASUREMENT_DATE} < ? ORDER BY ${DatabaseConstants.TABLE_MEASUREMENT_DATE} ASC"
        const val SELECT_MEASUREMENT_BY_TYPE_LAST_DATE = "SELECT * FROM ${DatabaseConstants.TABLE_MEASUREMENT} WHERE ${DatabaseConstants.TABLE_MEASUREMENT_TYPE} = ? ORDER BY ${DatabaseConstants.TABLE_MEASUREMENT_DATE} DESC LIMIT 1"

        const val SELECT_DAYS_BY_WORKOUT_ID = "SELECT * FROM ${DatabaseConstants.TABLE_DAY} WHERE ${DatabaseConstants.TABLE_DAY_WORKOUTID} = ?"

        const val SELECT_EXERCISEDAY_BY_DAY_ID = "SELECT * FROM ${DatabaseConstants.TABLE_EXERCISEDAY} WHERE ${DatabaseConstants.TABLE_EXERCISEDAY_DAYID} = ?"
        const val SELECT_EXERCISEDAY_BY_WORKOUT_ID = "SELECT * FROM ${DatabaseConstants.TABLE_EXERCISEDAY} ex INNER JOIN ${DatabaseConstants.TABLE_DAY} d ON ex.${DatabaseConstants.TABLE_EXERCISEDAY_DAYID} = d.${DatabaseConstants.TABLE_DAY_ID} WHERE d.${DatabaseConstants.TABLE_DAY_WORKOUTID} = ?"

        const val SELECT_ALL_DATETYPE = "SELECT * FROM ${DatabaseConstants.TABLE_DATETYPE}"

        const val SELECT_ALL_MEASURETYPE = "SELECT * FROM ${DatabaseConstants.TABLE_MEASURETYPE}"
        const val SELECT_MEASURETYPE_BY_ID = "SELECT * FROM ${DatabaseConstants.TABLE_MEASURETYPE} WHERE ${DatabaseConstants.TABLE_MEASURETYPE_ID} = ?"

        const val SELECT_ALL_WEIGHTTYPE = "SELECT * FROM ${DatabaseConstants.TABLE_WEIGHTTYPE}"
        const val SELECT_WEIGHTTYPE_BY_CHOOSED = "SELECT * FROM ${DatabaseConstants.TABLE_WEIGHTTYPE} WHERE ${DatabaseConstants.TABLE_WEIGHTTYPE_CHOOSED} = 1"

        const val SELECT_ALL_LENGTHTYPE = "SELECT * FROM ${DatabaseConstants.TABLE_LENGTHTYPE}"
        const val SELECT_LENGTHTYPE_BY_CHOOSED = "SELECT * FROM ${DatabaseConstants.TABLE_LENGTHTYPE} WHERE ${DatabaseConstants.TABLE_LENGTHTYPE_CHOOSED} = 1"

        const val SELECT_SERIE_BY_USER_AND_EXERCISEDAYID = "SELECT * FROM ${DatabaseConstants.TABLE_SERIE} WHERE ${DatabaseConstants.TABLE_SERIE_EXERCISEDAYID} = ? ORDER BY ${DatabaseConstants.TABLE_SERIE_WEEK} ASC"
    }
}