package sh.com.pcl.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fg_meter.*
import sh.com.pcl.R
import sh.com.pcl.common.BaseFragment
import sh.com.pcl.ui.activity.*

/**
 * Created by Administrator on 2017/10/30.
 */
class MeterFragment : BaseFragment(), View.OnClickListener {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater?.inflate(R.layout.fg_meter, null)
        return view
    }

    override fun initData() {
        var list = listOf(bt_qrcode, bt_complete_img, bt_not_complete_img, bt_user_img, bt_record_img)
        for (item in list) {
            item.setOnClickListener(this)
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.bt_qrcode -> startActivity(QrActivity::class.java)
            R.id.bt_complete_img -> startActivity("id", "1", MeterAlbumActivity::class.java)
            R.id.bt_not_complete_img -> startActivity("id", "0", MeterAlbumActivity::class.java)
            R.id.bt_user_img -> startActivity(UserDataActivity::class.java)
            R.id.bt_record_img -> startActivity(MeterRecordActivity::class.java)
        }
    }
}