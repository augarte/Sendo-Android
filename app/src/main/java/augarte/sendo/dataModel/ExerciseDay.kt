package augarte.sendo.dataModel

import android.os.Parcel
import android.os.Parcelable
import java.util.*

class ExerciseDay() : Parcelable {

    var id: Int? = null
    var dayId: Int? = null
    var exercise: Exercise? = null
    var series: ArrayList<Serie> = ArrayList()
    var serieNum: Int = 0
    var repNum: Int = 0
    var createdBy: String? = null
    var createDate: Date? = null
    var modifyDate: Date? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        dayId = parcel.readValue(Int::class.java.classLoader) as? Int
        exercise = parcel.readParcelable(Exercise::class.java.classLoader)
        serieNum = parcel.readInt()
        repNum = parcel.readInt()
        series = parcel.readArrayList(Serie::class.java.classLoader) as ArrayList<Serie>
        createdBy = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeValue(dayId)
        parcel.writeParcelable(exercise, flags)
        parcel.writeInt(serieNum)
        parcel.writeInt(repNum)
        parcel.writeList(series)
        parcel.writeString(createdBy)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ExerciseDay> {
        override fun createFromParcel(parcel: Parcel): ExerciseDay {
            return ExerciseDay(parcel)
        }

        override fun newArray(size: Int): Array<ExerciseDay?> {
            return arrayOfNulls(size)
        }
    }
}