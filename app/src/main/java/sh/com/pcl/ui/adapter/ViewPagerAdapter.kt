package sh.com.pcl.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by Administrator on 2017/10/30.
 */
class ViewPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    val mFragmentList = ArrayList<Fragment>()    //返回的是kotlin的MutableList，可读写

    override fun getItem(position: Int): Fragment {
        return mFragmentList!!.get(position)
    }

    override fun getCount(): Int {
        return mFragmentList!!.size
    }

    fun addFragment(fragment: Fragment) {
        mFragmentList.add(fragment)
    }
}