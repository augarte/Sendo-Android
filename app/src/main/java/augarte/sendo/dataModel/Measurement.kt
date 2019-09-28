package augarte.sendo.dataModel

import java.util.*

class Measurement {
    var id: Int? = null
    var type: MeasureType? = null
    var value: Double? = null
    var date: Date? = null
    var createdBy: User? = null
    var createDate: Date? = null
    var modifyDate: Date? = null
}