package sh.com.pcl.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.ActivityUtils
import kotlinx.android.synthetic.main.fg_exit.*
import sh.com.pcl.R
import sh.com.pcl.common.BaseFragment

/**
 * Created by Administrator on 2017/10/30.
 */
class ExitFragment : BaseFragment() {
    override fun initData() {
        bt_exit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                ActivityUtils.finishAllActivities()
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fg_exit, null)
    }
}