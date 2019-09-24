package augarte.sendo.dataModel

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import java.util.*

class Workout() : Parcelable{

    var id: Int? = null
    var name: String? = null
    var description: String? = null
    var dayList: ArrayList<Day> = ArrayList()
    var image: Bitmap? = null
    var createdBy: User? = null
    var lastOpen: Date? = null
    var createDate: Date? = null
    var modifyDate: Date? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        name = parcel.readString()
        description = parcel.readString()
        dayList = parcel.readArrayList(Day::class.java.classLoader) as ArrayList<Day>
        image = parcel.readParcelable(Bitmap::class.java.classLoader)
        createDate = Date(parcel.readLong())
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeList(dayList)
        parcel.writeParcelable(image, flags)
        parcel.writeLong(createDate!!.time)
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