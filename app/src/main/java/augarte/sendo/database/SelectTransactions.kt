package augarte.sendo.database

class SelectTransactions {

    companion object{
        const val SELECT_ALL_WORKOUT = "SELECT  * FROM ${DatabaseConstants.TABLE_WORKOUT} ORDER BY ${DatabaseConstants.TABLE_WORKOUT_LASTOPEN} DESC"
/*
        const val SELECT_ALL_WORKOUT = "SELECT * FROM ${DatabaseConstants.TABLE_WORKOUT}"
*/

        const val SELECT_ALL_EXERCISE = "SELECT * FROM ${DatabaseConstants.TABLE_EXERCISE} ORDER BY ${DatabaseConstants.TABLE_EXERCISE_NAME} DESC"

    }
}