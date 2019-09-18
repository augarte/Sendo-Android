package augarte.sendo.dataModel

import java.sql.Blob
import java.sql.Date

class Day{
    var id: Int? = null
    var workout: Workout? = null
    var exercises: ArrayList<Exercise> = ArrayList()
    var name: String? = null
    var image: Blob? = null
    var createdBy: User? = null
    var createDate: Date? = null
    var modifyDate: Date? = null


/*    fun getExercises(): ArrayList<Exercise> {
        val exercises = ArrayList<Exercise>()
        val e = Exercise()
        e.name = "Exercise 1\nExercise 2\nExercise 3\nExercise 4"
        exercises.add(e)
       return exercises
    }*/
}