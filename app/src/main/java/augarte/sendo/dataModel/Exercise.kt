package augarte.sendo.dataModel

import android.graphics.Bitmap
import java.util.Date

class Exercise{
    var id: Int? = null
    var name: String? = null
    var description: String? = null
    var image: Bitmap? = null
    var createdBy: User? = null
    var createDate: Date? = null
    var modifyDate: Date? = null
}