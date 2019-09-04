package augarte.sendo.utils

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream
import java.util.*

class Utils {

    val dateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'"

    companion object{
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
    }
}