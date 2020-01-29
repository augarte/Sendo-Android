package augarte.sendo.dataModel

import android.os.Parcel
import android.os.Parcelable
import java.util.*


class Exercise() : Parcelable{
    var id: Int? = null
    var name: String? = null
    var description: String? = null
    //var image: Bitmap? = null
    var imageURL: String? = null
    var type: ExerciseType? = null
    var state: Int? = 1
    var selected: Boolean = false
    var favorite: Boolean = false;
    var createdBy: String? = null
    var createDate: Date? = null
    var modifyDate: Date? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        name = parcel.readString()
        description = parcel.readString()
        //image = parcel.readParcelable(Bitmap::class.java.classLoader)
        imageURL = parcel.readString()
        state = parcel.readValue(Int::class.java.classLoader) as? Int
        favorite = parcel.readByte() != 0.toByte()
        selected = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeString(description)
        //parcel.writeParcelable(image, flags)
        parcel.writeString(imageURL)
        parcel.writeValue(state)
        parcel.writeByte(if (favorite) 1 else 0)
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