package augarte.sendo.dataModel

import java.sql.Blob
import java.sql.Date

class Day{
    var id: Int? = null
    var workout: Workout? = null
    var name: String? = null
    var image: Blob? = null
    var createdBy: User? = null
    var createDate: Date? = null
    var modifyDate: Date? = null
}