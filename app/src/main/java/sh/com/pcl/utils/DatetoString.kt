package sh.com.pcl.utils

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Path
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import com.blankj.utilcode.util.ImageUtils
import java.net.URI
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Administrator on 2017/10/31.
 */
object DatetoString {
    fun strToDate(sDate: String): Date {
        var formatter = SimpleDateFormat("yyyyMMddHHmmss")
        var pos = ParsePosition(0)
        var date = formatter.parse(sDate, pos)
        return date
    }

    fun DatetoString(date: Date): String {
        var formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var string = formatter.format(date)
        return string
    }

    fun StringtoStringLong(str: String): String {
        return DatetoString(strToDate(str))
    }

    fun PathToBase64(path: String): String {
        var bitmap = BitmapFactory.decodeFile(path)
        var byte = ImageUtils.bitmap2Bytes(bitmap, Bitmap.CompressFormat.PNG)
        return Base64.encodeToString(byte, Base64.DEFAULT)
    }
}