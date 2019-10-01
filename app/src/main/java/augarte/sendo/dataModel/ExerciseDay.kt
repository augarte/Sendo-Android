package augarte.sendo.dataModel

import java.util.*

class ExerciseDay {

    var id: Int? = null
    var dayId: Int? = null
    var exercise: Exercise? = null
    var series: ArrayList<Serie> = ArrayList()
    var serieNum: Int = 0
    var repNum: Int = 0
    var createdBy: String? = null
    var createDate: Date? = null
    var modifyDate: Date? = null
}