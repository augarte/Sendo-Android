package augarte.sendo.dataModel

import android.os.Parcel
import android.os.Parcelable
import java.util.*

class Serie() : Parcelable{

    var id: Int? = null
    var exerciseDayId: Int? = null
    var repetitions: Int = 0
    var weight: Double? = null
    var week: Int = 1
    var createdBy: String? = null
    var createDate: Date? = null
    var modifyDate: Date? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        exerciseDayId = parcel.readValue(Int::class.java.classLoader) as? Int
        repetitions = parcel.readInt()
        weight = parcel.readValue(Double::class.java.classLoader) as? Double
        week = parcel.readInt()
        createdBy = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeValue(exerciseDayId)
        parcel.writeInt(repetitions)
        parcel.writeValue(weight)
        parcel.writeInt(week)
        parcel.writeString(createdBy)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Serie> {
        override fun createFromParcel(parcel: Parcel): Serie {
            return Serie(parcel)
        }

        override fun newArray(size: Int): Array<Serie?> {
            return arrayOfNulls(size)
        }
    }
}