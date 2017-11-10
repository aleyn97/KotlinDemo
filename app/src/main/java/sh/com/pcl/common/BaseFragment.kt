package sh.com.pcl.common

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup

import com.blankj.utilcode.util.LogUtils


import java.io.Serializable
import java.util.ArrayList


/**
 * Created by Administrator on 2017/5/2 0002.
 * [Fragment基础类，实现异步框架，Activity堆栈的管理，destroy时候销毁所有资源]
 */

abstract class BaseFragment : Fragment() {

    /**
     * Fragment content view
     */
    private var inflateView: View? = null

    /**
     * 记录是否已经创建了,防止重复创建
     */
    private val viewCreated: Boolean = false

    private val argument: Bundle
        get() {
            var data: Bundle? = arguments
            if (data == null)
                data = Bundle()
            return data
        }

    /**
     * 跳转到指定的Activity
     *
     * @param targetActivity 要跳转的目标Activity
     */
    protected fun startActivity(targetActivity: Class<*>) {
        startActivity(Intent(context, targetActivity))
    }

    /**
     * 跳转到指定的Activity
     * @param extraName      要传递的值的键名称
     * @param extraValue     要传递的String类型值
     * @param targetActivity 要跳转的目标Activity
     */
    fun startActivity(extraName: String, extraValue: String, targetActivity: Class<*>) {
        if (extraName != null && extraName == "")
            throw NullPointerException("传递的值的键名称为null或空")
        val intent = Intent(context, targetActivity)
        intent.putExtra(extraName, extraValue)
        startActivity(intent)
    }

    /**
     * 跳转到指定的Activity
     *
     * @param extraName      要传递的值的键名称
     * @param extraValue     要传递的int类型值
     * @param targetActivity 要跳转的目标Activity
     */
    fun startActivity(extraName: String, extraValue: Int, targetActivity: Class<*>) {
        if (extraName != null && extraName == "")
            throw NullPointerException("传递的值的键名称为null或空")
        val intent = Intent(context, targetActivity)
        intent.putExtra(extraName, extraValue)
        startActivity(intent)
    }

    fun getStringArgument(key: String): String? {
        val data = arguments ?: return null
        return data.getString(key)
    }

    fun getStringArgument(key: String, defaultValue: String): String? {
        val data = arguments ?: return null
        val value = data.getString(key)
        return if (data == null) defaultValue else value
    }

    fun getCharArgument(key: String): Char {
        val data = arguments ?: return '\u0000'
        return data.getChar(key)
    }

    fun getBooleanArgument(key: String): Boolean {
        val data = arguments ?: return false
        return data.getBoolean(key)
    }

    fun getBooleanArgument(key: String, defaultValue: Boolean): Boolean {
        val data = arguments ?: return false
        return data.getBoolean(key, defaultValue)
    }

    fun getIntArgument(key: String): Int {
        val data = arguments ?: return 0
        return data.getInt(key)
    }

    fun getIntArgument(key: String, defaultValue: Int): Int {
        val data = arguments ?: return 0
        return data.getInt(key, defaultValue)
    }

    fun getFloatArgument(key: String): Float {
        val data = arguments ?: return 0.0f
        return data.getFloat(key)
    }

    fun getFloatArgument(key: String, defaultValue: Float): Float {
        val data = arguments ?: return 0.0f
        return data.getFloat(key, defaultValue)
    }

    fun getDoubleArgument(key: String): Double {
        val data = arguments ?: return 0.0
        return data.getDouble(key)
    }

    fun getDoubleArgument(key: String, defaultValue: Double): Double {
        val data = arguments ?: return 0.0
        return data.getDouble(key, defaultValue)
    }

    fun getStringArrayArgument(key: String): Array<String>? {
        val data = arguments ?: return null
        return data.getStringArray(key)
    }

    fun getStringArrayListArgument(key: String): ArrayList<String>? {
        val data = arguments ?: return null
        return data.getStringArrayList(key)
    }

    fun getParcelableArgument(key: String): Parcelable? {
        val data = arguments ?: return null
        return data.getParcelable(key)
    }

    fun getSerializableArgument(key: String): Serializable? {
        val data = arguments ?: return null
        return data.getSerializable(key)
    }

    fun setArgument(key: String, value: String) {
        val data = argument
        data.putString(key, value)
        // 防止报错：java.lang.IllegalStateException: Fragment already active
        if (!isAdded)
            arguments = data
    }

    fun setArgument(key: String, value: Byte) {
        val data = argument
        data.putByte(key, value)
        // 防止报错：java.lang.IllegalStateException: Fragment already active
        if (!isAdded)
            arguments = data
    }

    fun setArgument(key: String, value: Char) {
        val data = argument
        data.putChar(key, value)
        // 防止报错：java.lang.IllegalStateException: Fragment already active
        if (!isAdded)
            arguments = data
    }

    fun setArgument(key: String, value: Boolean) {
        val data = argument
        data.putBoolean(key, value)
        // 防止报错：java.lang.IllegalStateException: Fragment already active
        if (!isAdded)
            arguments = data
    }

    fun setArgument(key: String, value: Int) {
        val data = argument
        data.putInt(key, value)
        // 防止报错：java.lang.IllegalStateException: Fragment already active
        if (!isAdded)
            arguments = data
    }

    fun setArgument(key: String, value: Short) {
        val data = argument
        data.putShort(key, value)
        // 防止报错：java.lang.IllegalStateException: Fragment already active
        if (!isAdded)
            arguments = data
    }

    fun setArgument(key: String, value: Long) {
        val data = argument
        data.putLong(key, value)
        // 防止报错：java.lang.IllegalStateException: Fragment already active
        if (!isAdded)
            arguments = data
    }

    fun setArgument(key: String, value: Float) {
        val data = argument
        data.putFloat(key, value)
        // 防止报错：java.lang.IllegalStateException: Fragment already active
        if (!isAdded)
            arguments = data
    }

    fun setArgument(key: String, value: Double) {
        val data = argument
        data.putDouble(key, value)
        // 防止报错：java.lang.IllegalStateException: Fragment already active
        if (!isAdded)
            arguments = data
    }

    fun setArgument(key: String, value: Parcelable) {
        val data = argument
        data.putParcelable(key, value)
        // 防止报错：java.lang.IllegalStateException: Fragment already active
        if (!isAdded)
            arguments = data
    }

    fun setArgument(key: String, value: Serializable) {
        val data = argument
        data.putSerializable(key, value)
        // 防止报错：java.lang.IllegalStateException: Fragment already active
        if (!isAdded)
            arguments = data
    }

    fun setArgument(key: String, value: Bundle) {
        val data = argument
        data.putBundle(key, value)
        // 防止报错：java.lang.IllegalStateException: Fragment already active
        if (!isAdded)
            arguments = data
    }

    fun setArgument(key: String, value: ArrayList<String>) {
        val data = argument
        data.putStringArrayList(key, value)
        // 防止报错：java.lang.IllegalStateException: Fragment already active
        if (!isAdded)
            arguments = data
    }

    fun setArgument(key: String, value: Array<String>) {
        val data = argument
        data.putStringArray(key, value)
        // 防止报错：java.lang.IllegalStateException: Fragment already active
        if (!isAdded)
            arguments = data
    }

    override fun onResume() {
        super.onResume()
        initData()
    }

    protected abstract fun initData()


    fun setInflateView(view: View) {
        inflateView = view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LogUtils.d("onDestroyView...")
        // 解决ViewPager中的问题
        if (null != inflateView) {
            val parent = inflateView!!.parent
            if (parent != null)
                (parent as ViewGroup).removeView(inflateView)
        }
    }
}
