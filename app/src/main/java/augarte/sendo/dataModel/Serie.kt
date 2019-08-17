package augarte.sendo.dataModel

import java.sql.Blob
import java.sql.Date

class Serie{
    var id: Int? = null
    var exercise: Exercise? = null
    var repetition: String? = null
    var weight: String? = null
    var createdBy: User? = null
    var createDate: Date? = null
    var modifyDate: Date? = null

}