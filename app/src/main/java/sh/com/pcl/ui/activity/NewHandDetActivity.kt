package sh.com.pcl.ui.activity

import android.os.Bundle
import com.google.gson.Gson
import com.lzy.okhttputils.OkHttpUtils
import com.lzy.okhttputils.callback.StringCallback
import kotlinx.android.synthetic.main.activity_new_hand_det.*
import okhttp3.Call
import okhttp3.Response

import sh.com.pcl.R
import sh.com.pcl.bean.notDetilse
import sh.com.pcl.common.BaseActivity
import sh.com.pcl.common.ServerConfig
import sh.com.pcl.utils.DatetoString

class NewHandDetActivity : BaseActivity() {
    var url: String? = null
    var type: String? = null
    var notBean: notDetilse? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_hand_det)
        initView()
    }

    fun initView() {
        type = intent.extras.getString("type")
        when (type) {
            "0" -> {
                setTitle("未处理详情")
                url = ServerConfig.AZ_MES_DETAILS_URL
            }
            "1" -> {
                setTitle("已处理详情")
                url = ServerConfig.BAO_ZHUANG_YES_DETILSE_URL
            }
        }
        request()
    }

    fun request() {
        OkHttpUtils
                .get(url)
                .params("userID", intent.extras.getString("id"))
                .execute(object : StringCallback() {
                    override fun onSuccess(t: String?, call: Call?, response: Response?) {
                        notBean = Gson().fromJson(t!!, notDetilse::class.java)
                        when (type) {
                            "0" -> {
                                if (!notBean?.Installation_User.equals("")) {
                                    initDate()
                                }
                            }
                            "1" -> {
                                initDate()
                            }
                        }
                    }
                })
    }

    fun initDate() {
        hand_user_name.text = notBean?.Installation_User
        fault_phone.text = notBean?.Installation_Userphone.toString()
        fault_name.text = notBean?.Installation_Name
        fault_time.text = DatetoString.StringtoStringLong(notBean?.Installation_Time.toString())
        fault_address.text = notBean?.Installation_Address
        fault_type.text = notBean?.WaterType_Name
        fault_send_time.text = DatetoString.StringtoStringLong(notBean?.installation_SendTime.toString())
        fault_water_number.text = notBean?.Installation_UserIDcard
        fault_water_yongtu.text = notBean?.WaterSort_Name
    }
}
