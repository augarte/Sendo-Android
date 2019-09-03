package augarte.sendo.dataModel

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import augarte.sendo.utils.Constants
import java.io.Serializable
import java.sql.Date

class Workout() : Parcelable{

    var id: Int? = null
    var name: String? = null
    var description: String? = null
    var dayList: ArrayList<Day>? = ArrayList()
    var image: Bitmap? = null
    var lastOpen: String? = null
    var createdBy: User? = null
    var createDate: Date? = null
    var modifyDate: Date? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        name = parcel.readString()
        description = parcel.readString()
        image = parcel.readParcelable(Bitmap::class.java.classLoader)
        lastOpen = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeParcelable(image, flags)
        parcel.writeString(lastOpen)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Workout> {
        override fun createFromParcel(parcel: Parcel): Workout {
            return Workout(parcel)
        }

        override fun newArray(size: Int): Array<Workout?> {
            return arrayOfNulls(size)
        }
    }


}