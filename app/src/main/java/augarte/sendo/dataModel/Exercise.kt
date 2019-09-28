package augarte.sendo.dataModel

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import java.util.Date

class Exercise() : Parcelable{
    var id: Int? = null
    var name: String? = null
    var description: String? = null
    var image: Bitmap? = null
    var type: ExerciseType? = null
    var state: Int? = 1
    var createdBy: String? = null
    var createDate: Date? = null
    var modifyDate: Date? = null

    var selected: Boolean = false

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        name = parcel.readString()
        description = parcel.readString()
        image = parcel.readParcelable(Bitmap::class.java.classLoader)
        state = parcel.readValue(Int::class.java.classLoader) as? Int
        selected = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeParcelable(image, flags)
        parcel.writeValue(state)
        parcel.writeByte(if (selected) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Exercise> {
        override fun createFromParcel(parcel: Parcel): Exercise {
            return Exercise(parcel)
        }

        override fun newArray(size: Int): Array<Exercise?> {
            return arrayOfNulls(size)
        }
    }
}