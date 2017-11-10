package sh.com.pcl.ui.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager
import android.view.KeyEvent
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_home.*
import sh.com.pcl.R
import sh.com.pcl.ui.adapter.ViewPagerAdapter
import sh.com.pcl.common.BaseActivity
import sh.com.pcl.ui.fragment.ExitFragment
import sh.com.pcl.ui.fragment.FaultFragment
import sh.com.pcl.ui.fragment.InstallFragment
import sh.com.pcl.ui.fragment.MeterFragment
import sh.com.pcl.utils.BottomNavigationViewHelper
import sh.com.pcl.utils.Dialog

class HomeActivity : BaseActivity() {
    private var mExitTime: Long = 0
    var menuItem: MenuItem? = null
    var mList = listOf(R.string.cb_text, R.string.bx_text, R.string.bz_text, R.string.exit_text)
    var a: Int = 0
    var b: Int = 0
    var c: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initDate()
    }

    override fun onResume() {
        super.onResume()
    }

    fun initDate() {
        for (item in intent.getStringArrayListExtra("list")) {
            when (item) {
                "APP报修处理" -> a = 1
                "APP报装处理" -> b = 1
                "APP抄表信息" -> c = 1
            }
        }
        BottomNavigation()
        setDefaultFragment()
        btn_right.setOnClickListener {
            when (viewpager.currentItem) {
                1 -> startActivity(FaultMsgActivity::class.java)
                2 -> startActivity(NewMsgActivity::class.java)
            }
        }
    }

    fun BottomNavigation() {
        setTitle(mList.get(0))
        BottomNavigationViewHelper.disableShiftMode(bottom_navigation)
        bottom_navigation.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.item_cb -> {
                        if (a == 1) {
                            viewpager.setCurrentItem(0)
                            setTitle(mList.get(0))
                            btn_right.visibility = View.INVISIBLE
                        } else {
                            Dialog.Notice(this@HomeActivity, "抱歉您没有该权限")
                        }
                    }
                    R.id.item_bx -> {
                        if (b == 1) {
                            setTitle(mList.get(1))
                            viewpager.setCurrentItem(1)
                            btn_right.visibility = View.VISIBLE
                            btn_right.setBackgroundResource(R.drawable.svg_msg_img)
                        } else {
                            Dialog.Notice(this@HomeActivity, "抱歉您没有该权限")
                        }
                    }
                    R.id.item_bz -> {
                        if (c == 1) {
                            setTitle(mList.get(2))
                            viewpager.setCurrentItem(2)
                            btn_right.visibility = View.VISIBLE
                            btn_right.setBackgroundResource(R.drawable.svg_msg_img)
                        } else {
                            Dialog.Notice(this@HomeActivity, "抱歉您没有该权限")
                        }

                    }
                    R.id.item_exit -> {
                        setTitle(mList.get(3))
                        viewpager.setCurrentItem(3)
                        btn_right.visibility = View.INVISIBLE
                    }
                }
                return false
            }
        })

        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageSelected(position: Int) {
                if (menuItem != null) {
                    menuItem!!.setChecked(false)
                } else {
                    bottom_navigation.getMenu().getItem(0).setChecked(false)
                }
                when (position) {
                    0 -> {
                        if (a == 1) {
                            menuItem = bottom_navigation.getMenu().getItem(position)
                            btn_right.visibility = View.INVISIBLE
                            setTitle(mList.get(position))
                            menuItem!!.setChecked(true)
                        } else {
                            Dialog.Notice(this@HomeActivity, "抱歉您没有该权限")
                        }
                    }
                    1 -> {
                        if (b == 1) {
                            menuItem = bottom_navigation.getMenu().getItem(position)
                            btn_right.visibility = View.VISIBLE
                            btn_right.setBackgroundResource(R.drawable.svg_msg_img)
                            setTitle(mList.get(position))
                            menuItem!!.setChecked(true)
                        } else {
                            Dialog.Notice(this@HomeActivity, "抱歉您没有该权限")
                        }
                    }
                    2 -> {
                        if (c == 1) {
                            menuItem = bottom_navigation.getMenu().getItem(position)
                            btn_right.visibility = View.VISIBLE
                            btn_right.setBackgroundResource(R.drawable.svg_msg_img)
                            setTitle(mList.get(position))
                            menuItem!!.setChecked(true)
                        } else {
                            Dialog.Notice(this@HomeActivity, "抱歉您没有该权限")
                        }
                    }
                    3 -> {
                        menuItem = bottom_navigation.getMenu().getItem(position)
                        btn_right.visibility = View.INVISIBLE
                        setTitle(mList.get(position))
                        menuItem!!.setChecked(true)
                    }
                }
//                menuItem = bottom_navigation.getMenu().getItem(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }
        })
        //禁止ViewPager滑动
        viewpager.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                return true
            }
        })
        setupViewPager(viewpager)
    }

    fun setupViewPager(viewPagers: ViewPager) {
        var adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(MeterFragment::class.java.newInstance())
        adapter.addFragment(FaultFragment::class.java.newInstance())
        adapter.addFragment(InstallFragment::class.java.newInstance())
        adapter.addFragment(ExitFragment::class.java.newInstance())
        viewPagers.adapter = adapter
    }

    fun setDefaultFragment() {
        setTitle(mList.get(3))
        viewpager.setCurrentItem(3)
        btn_right.visibility = View.INVISIBLE
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//1.点击返回键条件成立
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event?.getAction() == KeyEvent.ACTION_DOWN
                && event.getRepeatCount() == 0) {
            //2.点击的时间差如果大于2000，则提示用户点击两次退出
            if (System.currentTimeMillis() - mExitTime > 2000) {
                //3.保存当前时间
                mExitTime = System.currentTimeMillis()
                //4.提示
                ToastUtils.showShort("再按一次退出应用")
            } else {
                //5.点击的时间差小于2000，退出。
                finish()
                System.exit(0)
            }
            return true
        }
        return false
    }
    }
