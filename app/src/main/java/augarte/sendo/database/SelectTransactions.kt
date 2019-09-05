package augarte.sendo.database

class SelectTransactions {

    companion object{
/*
        val SELECT_ALL_WORKOUT = "SELECT  * FROM ${Constants.TABLE_WORKOUT} ORDER BY ${Constants.TABLE_WORKOUT_LASTOPEN} DESC"
*/
        const val SELECT_ALL_WORKOUT = "SELECT * FROM ${DatabaseConstants.TABLE_WORKOUT}"

    }
}