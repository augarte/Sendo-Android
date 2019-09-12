package augarte.sendo.database

class SelectTransactions {

    companion object{
        const val SELECT_ALL_WORKOUT_ORDER_NAME = "SELECT  * FROM ${DatabaseConstants.TABLE_WORKOUT} ORDER BY ${DatabaseConstants.TABLE_WORKOUT_LASTOPEN} ASC"
/*
        const val SELECT_ALL_WORKOUT = "SELECT * FROM ${DatabaseConstants.TABLE_WORKOUT}"
*/

        const val SELECT_ALL_EXERCISE_ORDER_NAME = "SELECT * FROM ${DatabaseConstants.TABLE_EXERCISE} WHERE ${DatabaseConstants.TABLE_EXERCISE_STATE} = ${DatabaseConstants.STATE_ACTIVE} ORDER BY ${DatabaseConstants.TABLE_EXERCISE_NAME} ASC"
        const val SELECT_ALL_EXERCISE_ORDER_TYPE = "SELECT * FROM ${DatabaseConstants.TABLE_EXERCISE} WHERE ${DatabaseConstants.TABLE_EXERCISE_STATE} = ${DatabaseConstants.STATE_ACTIVE} ORDER BY ${DatabaseConstants.TABLE_EXERCISE_TYPE} ASC"
        const val SELECT_ARCHIVED_EXERCISES_ORDER_NAME = "SELECT * FROM ${DatabaseConstants.TABLE_EXERCISE} WHERE ${DatabaseConstants.TABLE_EXERCISE_STATE} = ${DatabaseConstants.STATE_ARCHIVED} ORDER BY ${DatabaseConstants.TABLE_EXERCISE_NAME} ASC"

        const val SELECT_ALL_EXERCISETYPE = "SELECT * FROM ${DatabaseConstants.TABLE_EXERCISETYPE}"
        const val SELECT_EXERCISETYPE_BY_ID = "SELECT * FROM ${DatabaseConstants.TABLE_EXERCISETYPE} WHERE ${DatabaseConstants.TABLE_EXERCISETYPE_ID} = ?"

        const val SELECT_ALL_DATETYPE = "SELECT * FROM ${DatabaseConstants.TABLE_DATETYPE}"
        const val SELECT_ALL_MEASURETYPE = "SELECT * FROM ${DatabaseConstants.TABLE_MEASURETYPE}"

        const val SELECT_ALL_WEIGHTTYPE = "SELECT * FROM ${DatabaseConstants.TABLE_WEIGHTTYPE}"
        const val SELECT_WEIGHTTYPE_BY_CHOOSED = "SELECT * FROM ${DatabaseConstants.TABLE_WEIGHTTYPE} WHERE ${DatabaseConstants.TABLE_WEIGHTTYPE_CHOOSED} = 1"

        const val SELECT_ALL_LENGTHTYPE = "SELECT * FROM ${DatabaseConstants.TABLE_LENGTHTYPE}"
        const val SELECT_LENGTHTYPE_BY_CHOOSED = "SELECT * FROM ${DatabaseConstants.TABLE_LENGTHTYPE} WHERE ${DatabaseConstants.TABLE_LENGTHTYPE_CHOOSED} = 1"
    }
}