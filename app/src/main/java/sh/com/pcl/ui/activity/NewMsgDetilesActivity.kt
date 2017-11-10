package sh.com.pcl.ui.activity

import android.os.Bundle
import android.view.View
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.gson.Gson
import com.lzy.okhttputils.OkHttpUtils
import com.lzy.okhttputils.callback.StringCallback
import kotlinx.android.synthetic.main.activity_new_msg_detiles.*
import okhttp3.Call
import okhttp3.Response

import sh.com.pcl.R
import sh.com.pcl.bean.newMsgDetilse
import sh.com.pcl.common.BaseActivity
import sh.com.pcl.common.ServerConfig
import sh.com.pcl.utils.DatetoString
import sh.com.pcl.utils.Dialog
import java.lang.Exception

class NewMsgDetilesActivity : BaseActivity() {

    var bean: newMsgDetilse? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_msg_detiles)
        initView()
    }

    fun initView() {
        request()
        bt_true.setOnClickListener { response("1") }
        bt_false.setOnClickListener { NotDialog() }
    }

    fun request() {
        Dialog.CreateDialog(this)
        OkHttpUtils
                .get(ServerConfig.AZ_MES_DETAILS_URL)
                .params("userID", intent.extras.getString("id"))
                .execute(object : StringCallback() {
                    override fun onSuccess(t: String?, call: Call?, response: Response?) {
                        bean = Gson().fromJson(t!!, newMsgDetilse::class.java)
                        initDate()
                    }

                    override fun onError(call: Call?, response: Response?, e: Exception?) {
                        super.onError(call, response, e)
                        Dialog.Error(this@NewMsgDetilesActivity)
                    }

                    override fun onAfter(t: String?, e: Exception?) {
                        super.onAfter(t, e)
                        Dialog.CloseDialog()
                    }
                })
    }

    private fun initDate() {
        Lin_main.visibility = View.VISIBLE
        msg_det_names.text = bean?.Installation_Name
        msg_det_address.text = bean?.Installation_Address
        msg_det_instatime.text = DatetoString.StringtoStringLong(bean?.Installation_Time.toString())
        msg_det_phone.text = bean?.Installation_Userphone.toString()
        msg_det_water_type.text = bean?.WaterType_Name
    }

    private fun response(type: String) {
        Dialog.CreateDialog(this)
        OkHttpUtils
                .get(ServerConfig.WORK_ORDERS_URL)
                .params("state", type)
                .params("installationID", intent.extras.getString("id"))
                .params("loginName", (application as MyApplication).name)
                .execute(object : StringCallback() {
                    override fun onSuccess(t: String?, call: Call?, response: Response?) {
                        ComDialog()
                    }

                    override fun onError(call: Call?, response: Response?, e: Exception?) {
                        super.onError(call, response, e)
                        Dialog.Error(this@NewMsgDetilesActivity)
                    }

                    override fun onAfter(t: String?, e: Exception?) {
                        super.onAfter(t, e)
                        Dialog.CloseDialog()
                    }
                })
    }

    private fun ComDialog() {
        SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("完成")
                .setConfirmClickListener { finish() }
                .show()
    }

    private fun NotDialog() {
        SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("您确定要拒绝？")
                .setConfirmClickListener { response("2") }
                .setConfirmText("确定")
                .show()
    }
}
