package augarte.sendo.dataModel

import android.os.Parcel
import android.os.Parcelable
import java.util.*
import kotlin.collections.ArrayList

class Day() : Parcelable{

    var id: Int? = null
   //var exercises: ArrayList<Exercise> = ArrayList()
    var workoutId: Int? = null
    var exerciseDayList: ArrayList<ExerciseDay> = ArrayList()
    var name: String? = null
    var createdBy: String? = null
    var createDate: Date? = null
    var modifyDate: Date? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        workoutId = parcel.readValue(Int::class.java.classLoader) as? Int
        name = parcel.readString()
        exerciseDayList = parcel.readArrayList(ExerciseDay::class.java.classLoader) as ArrayList<ExerciseDay>
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeValue(workoutId)
        parcel.writeString(name)
        parcel.writeList(exerciseDayList)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Day> {
        override fun createFromParcel(parcel: Parcel): Day {
            return Day(parcel)
        }

        override fun newArray(size: Int): Array<Day?> {
            return arrayOfNulls(size)
        }
    }
}