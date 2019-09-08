package augarte.sendo.database

class SelectTransactions {

    companion object{
        const val SELECT_ALL_WORKOUT_ORDER_NAME = "SELECT  * FROM ${DatabaseConstants.TABLE_WORKOUT} ORDER BY ${DatabaseConstants.TABLE_WORKOUT_LASTOPEN} DESC"
/*
        const val SELECT_ALL_WORKOUT = "SELECT * FROM ${DatabaseConstants.TABLE_WORKOUT}"
*/

        const val SELECT_ALL_EXERCISE_ORDER_NAME = "SELECT * FROM ${DatabaseConstants.TABLE_EXERCISE} WHERE ${DatabaseConstants.TABLE_EXERCISE_STATE} = ${DatabaseConstants.STATE_ACTIVE} ORDER BY ${DatabaseConstants.TABLE_EXERCISE_NAME} DESC"
        const val SELECT_ALL_EXERCISE_ORDER_TYPE = "SELECT * FROM ${DatabaseConstants.TABLE_EXERCISE} WHERE ${DatabaseConstants.TABLE_EXERCISE_STATE} = ${DatabaseConstants.STATE_ACTIVE} ORDER BY ${DatabaseConstants.TABLE_EXERCISE_TYPE} DESC"
        const val SELECT_ARCHIVED_EXERCISES_ORDER_NAME = "SELECT * FROM ${DatabaseConstants.TABLE_EXERCISE} WHERE ${DatabaseConstants.TABLE_EXERCISE_STATE} = ${DatabaseConstants.STATE_ARCHIVED} ORDER BY ${DatabaseConstants.TABLE_EXERCISE_NAME} DESC"

        const val SELECT_ALL_EXERCISETYPE = "SELECT * FROM ${DatabaseConstants.TABLE_EXERCISETYPE}"
        const val SELECT_EXERCISETYPE_BY_ID = "SELECT * FROM ${DatabaseConstants.TABLE_EXERCISETYPE} WHERE ${DatabaseConstants.TABLE_EXERCISETYPE_ID} = ?"
    }
}