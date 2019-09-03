package augarte.sendo.database

import augarte.sendo.utils.Constants

class DatabaseTransactions {

    companion object {

        val CREATE_TABLE_USER = "CREATE TABLE ${Constants.TABLE_USER} " +
                "(${Constants.TABLE_USER_ID} Integer PRIMARY KEY AUTOINCREMENT," +
                "${Constants.TABLE_USER_NAME} TEXT NOT NULL," +
                "${Constants.TABLE_USER_EMAIL} TEXT," +
                "${Constants.TABLE_USER_CREATEDATE} TEXT NOT NULL," +
                "${Constants.TABLE_USER_MODIFYDATE} TEXT NOT NULL)"

/*        val CREATE_TABLE_USER = "CREATE TABLE ${Constants.TABLE_USER} " +
                "(${Constants.TABLE_USER_ID} Integer PRIMARY KEY AUTOINCREMENT," +
                "${Constants.TABLE_USER_NAME} TEXT," +
                "${Constants.TABLE_USER_EMAIL} TEXT," +
                "${Constants.TABLE_USER_CREATEDATE} TEXT," +
                "${Constants.TABLE_USER_MODIFYDATE} TEXT)"*/

/*        val CREATE_TABLE_WORKOUT = "CREATE TABLE ${Constants.TABLE_WORKOUT} " +
                "(${Constants.TABLE_WORKOUT_ID} Integer PRIMARY KEY AUTOINCREMENT," +
                "${Constants.TABLE_WORKOUT_NAME} TEXT NOT NULL," +
                "${Constants.TABLE_WORKOUT_DESCRIPTION} TEXT," +
                "${Constants.TABLE_WORKOUT_IMAGE} blob," +
                "${Constants.TABLE_WORKOUT_LASTOPEN} TEXT NOT NULL," +
                "${Constants.TABLE_WORKOUT_CREATEDBY} TEXT NOT NULL," +
                "${Constants.TABLE_WORKOUT_CREATEDATE} TEXT NOT NULL," +
                "${Constants.TABLE_WORKOUT_MODIFYDATE} TEXT NOT NULL," +
                "FOREIGN KEY(${Constants.TABLE_WORKOUT_CREATEDBY}) REFERENCES USER(${Constants.TABLE_USER_ID}))"*/

        val CREATE_TABLE_WORKOUT = "CREATE TABLE IF NOT EXISTS ${Constants.TABLE_WORKOUT} " +
                "(${Constants.TABLE_WORKOUT_ID} Integer PRIMARY KEY AUTOINCREMENT," +
                "${Constants.TABLE_WORKOUT_NAME} TEXT NOT NULL," +
                "${Constants.TABLE_WORKOUT_DESCRIPTION} TEXT," +
                "${Constants.TABLE_WORKOUT_IMAGE} blob," +
                "${Constants.TABLE_WORKOUT_LASTOPEN} TEXT," +
                "${Constants.TABLE_WORKOUT_CREATEDBY} TEXT," +
                "${Constants.TABLE_WORKOUT_CREATEDATE} TEXT," +
                "${Constants.TABLE_WORKOUT_MODIFYDATE} TEXT)"
    }

}