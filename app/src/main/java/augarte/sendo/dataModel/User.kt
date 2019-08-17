package augarte.sendo.dataModel

import java.sql.Blob
import java.sql.Date

class User {
    var id: Int? = null
    var name: String? = null
    var email: String? = null
    var image: Blob? = null
    var createDate: Date? = null
    var modifyDate: Date? = null
}