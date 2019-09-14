package augarte.sendo.utils

import android.graphics.Bitmap
import android.widget.TextView
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class Utils {

    //val dateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'"

    companion object{

        private var dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd")

        fun unixTimeSecondsToDate(unixTimeSecond: Int): Date {
            val date = Date((unixTimeSecond * 1000).toLong())
            return  date
        }

        fun getUnixSeconds(): Long{
            return System.currentTimeMillis() / 1000L
        }

        fun getBiteArrayFromBitmap(bmp: Bitmap): ByteArray {
            val stream = ByteArrayOutputStream()
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream)
            //bmp.recycle()
            return stream.toByteArray()
        }

        fun partseDatePickerValues(year: Int, month: Int, day: Int): Date {
            val dateString = "$year/$month/$day"
            return dateFormat.parse(dateString)
        }
    }
}