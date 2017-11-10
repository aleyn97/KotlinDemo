package sh.com.pcl.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.lzy.okhttputils.OkHttpUtils
import com.lzy.okhttputils.callback.StringCallback
import kotlinx.android.synthetic.main.at_meter_qr.*
import okhttp3.Call
import okhttp3.Response
import sh.com.pcl.R
import sh.com.pcl.common.BaseActivity
import sh.com.pcl.common.ServerConfig

class MeterQRActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.at_meter_qr)
    }

    fun QronClick(view: View) {
        when (view.id) {
            R.id.bt_ok -> {
                var number = ed_input.text.toString()
                if (!number.equals("")) {
                    var intent = Intent()
                    intent.setClass(this, MeterSubActivity::class.java)
                    intent.putExtra("id", number)
                    startActivity(intent)
                } else {
                    ToastUtils.showShort("水表号为空")
                    ed_input.setShakeAnimation()
                }
            }
            R.id.bt_no -> {
                finish()
            }
        }
    }

}
