package augarte.sendo.dataModel

import java.sql.Blob
import java.sql.Date

class Measurement {
    var id: Int? = null
    var type: Int? = null
    var value: Int? = null
    var date: Blob? = null
    var createdBy: User? = null
    var createDate: Date? = null
    var modifyDate: Date? = null
}