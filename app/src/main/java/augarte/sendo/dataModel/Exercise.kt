package augarte.sendo.dataModel

import java.sql.Blob
import java.sql.Date

class Exercise{
    var id: Int? = null
    var day: Day? = null
    var name: String? = null
    var description: String? = null
    var image: Blob? = null
    var createdBy: User? = null
    var createDate: Date? = null
    var modifyDate: Date? = null
}