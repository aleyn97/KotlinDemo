package sh.com.pcl.common

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.transition.Fade
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.blankj.utilcode.util.SizeUtils
import kotlinx.android.synthetic.main.activity_base.*
import sh.com.pcl.R
import sh.com.pcl.common.manager.ActivityManager

open class BaseActivity : AppCompatActivity() {
    /** 是否允许全屏  */
    private val mAllowFullScreen = false
    /** 是否禁止旋转屏幕  */
    private val isAllowScreenRoate = false

    lateinit var mFadeTransition: Fade

    protected var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_base)
        initView()
        //5.0+的专场动画
        setupWindowAnimation()
    }

    private fun initView() {
        mContext = this
        //初始化公共头部
        btn_left.setBackgroundResource(R.drawable.svg_back)
        btn_left.setOnClickListener(View.OnClickListener {
            finish()
        })
        //Activity管理
        ActivityManager.getInstance().addActivity(this)
    }

    override fun setContentView(view: View) {
        val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f)
        layout_container!!.addView(view, lp)
    }

    override fun setContentView(layoutResID: Int) {
        val view = LayoutInflater.from(this).inflate(layoutResID, null)
        setContentView(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityManager.unbindReferences(layout_container)
        ActivityManager.getInstance().removeActivity(this)
//        layout_container = null
        mContext = null
    }

    /**
     * 设置头部是否可见
     *
     * @param visibility
     */
    fun setHeadVisibility(visibility: Int) {
        layout_head.visibility = visibility
    }

    /**
     * 设置标题
     */
    override fun setTitle(titleId: Int) {
        tv_title.text = getString(titleId)
    }

    /**
     * 设置标题
     *
     * @param title
     */
    fun setTitle(title: String) {
        tv_title.text = title
    }

    /**
     * 设置左边按钮是否显示
     *
     * @param visibility
     */
    fun setLeftVisibility(visibility: Int) {
        btn_left.visibility = visibility
    }

    /**
     * 设置右边按钮是否显示
     *
     * @param visibility
     */
    fun setRightVisibility(visibility: Int) {
        btn_right.visibility = visibility
    }

    /**
     * 设置右边按钮的文字
     *
     * @param titleId
     */
    fun setRightText(titleId: Int) {
        btn_right.text = getString(titleId)
    }

    /**
     * 设置右边按钮的文字
     *
     * @param text
     */
    fun setRightText(text: String) {
        btn_right.text = text
    }

    /**
     * 设置右边按钮的图标
     *
     * @param resId
     */
    fun setRightDrawable(resId: Int) {
        btn_right.text = ""
        btn_right.width = SizeUtils.dp2px(36f)
        btn_right.height = SizeUtils.dp2px(36f)
        btn_right.setBackgroundResource(resId)
    }

    /**
     * 设置右边按钮的监听器
     *
     * @param listenr
     */
    fun setRightOnClickListener(listenr: View.OnClickListener) {
        btn_right.setOnClickListener(listenr)
    }

    /**
     * [页面跳转]
     * @param clz
     */
    fun startActivity(clz: Class<*>) {
        startActivity(Intent(this@BaseActivity, clz))
    }

    /**
     * [携带数据的页面跳转]
     * @param clz
     * @param bundle
     */
    fun startActivity(clz: Class<*>, bundle: Bundle?) {
        val intent = Intent()
        intent.setClass(this, clz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     * @param cls
     * @param bundle
     * @param requestCode
     */
    fun startActivityForResult(cls: Class<*>, bundle: Bundle?, requestCode: Int) {
        val intent = Intent()
        intent.setClass(this, cls)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivityForResult(intent, requestCode)
    }

    protected fun setupWindowAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mFadeTransition = Fade()
            mFadeTransition.duration = SLIDE_TRANSITION_TIME.toLong()
            window.enterTransition = mFadeTransition
            window.exitTransition = mFadeTransition
        }
    }

    companion object {
        private val SLIDE_TRANSITION_TIME = 1 * 1000//滑动转场的时间

        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }
}
