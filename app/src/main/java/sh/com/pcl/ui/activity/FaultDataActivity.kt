package sh.com.pcl.ui.activity

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.gson.Gson
import com.lzy.okhttputils.OkHttpUtils
import com.lzy.okhttputils.callback.StringCallback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_fault_data.*
import okhttp3.Call
import okhttp3.Response
import sh.com.pcl.R
import sh.com.pcl.bean.faultMsgDate
import sh.com.pcl.common.BaseActivity
import sh.com.pcl.common.ServerConfig
import sh.com.pcl.ui.adapter.PhotoAdapter
import sh.com.pcl.utils.DatetoString
import sh.com.pcl.utils.Dialog
import java.lang.Exception

class FaultDataActivity : BaseActivity() {

    var dataBean: faultMsgDate? = null
    var app: MyApplication? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fault_data)
        setTitle("接单详情")
        initView()
    }

    fun initView() {
        app = application as MyApplication?
        request()
        fault_msg_yes.setOnClickListener { response("1") }
        fault_msg_no.setOnClickListener { refuseDialog() }
    }

    fun request() {
        OkHttpUtils
                .get(ServerConfig.BX_MSG_DATA_URL)
                .params("repairsID", intent.extras.getString("id"))
                .execute(object : StringCallback() {
                    override fun onSuccess(t: String?, call: Call?, response: Response?) {
                        dataBean = Gson().fromJson(t, faultMsgDate::class.java)
                        if (dataBean!!.Repairs_Name == null) {

                        } else {
                            initDate()
                        }
                    }
                })
    }

    fun initDate() {
        tv_user_name.text = dataBean!!.Repairs_Name
        tv_phone.text = dataBean!!.Repairs_Phone.toString()
        tv_time.text = DatetoString.StringtoStringLong(dataBean!!.Repairs_Time.toString())
        tv_shuoming.text = dataBean!!.Repairs_Content
        tv_address.text = dataBean!!.User_Site
        fault_gridView.layoutManager = GridLayoutManager(this, 4)
        var list = ArrayList<String>()
        if (!dataBean!!.FILE_PATH0.equals("")) list.add(dataBean!!.FILE_PATH0)
        if (!dataBean!!.FILE_PATH1.equals("")) list.add(dataBean!!.FILE_PATH1)
        if (!dataBean!!.FILE_PATH2.equals("")) list.add(dataBean!!.FILE_PATH2)
        if (!dataBean!!.FILE_PATH3.equals("")) list.add(dataBean!!.FILE_PATH3)
        fault_gridView.adapter = PhotoAdapter(this, list)
    }

    private fun response(type: String) {
        Dialog.CreateDialog(this)
        OkHttpUtils
                .get(ServerConfig.BX_MSG_DATA_SUB_URL)
                .params("repairsID", intent.extras.getString("id"))
                .params("loginName", app!!.name)
                .params("state", type)
                .execute(object : StringCallback() {
                    override fun onAfter(t: String?, e: Exception?) {
                        super.onAfter(t, e)
                        Dialog.CloseDialog()
                    }

                    override fun onSuccess(t: String?, call: Call?, response: Response?) {
                        when (type) {
                            "1" -> DialogFinish("接单成功")
                            "2" -> DialogFinish("拒绝完成")
                        }
                    }

                    override fun onError(call: Call?, response: Response?, e: Exception?) {
                        super.onError(call, response, e)
                        Dialog.Error(this@FaultDataActivity)
                    }
                })
    }

    fun DialogFinish(msg: String) {
        SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("完成")
                .setContentText(msg)
                .setConfirmClickListener { finish() }
                .show()
    }

    fun refuseDialog() {
        SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("您确定要拒绝吗？")
                .setCancelText("再想想")
                .setConfirmText("是的")
                .setConfirmClickListener { sDialog ->
                    response("2")
                }
                .show()
    }
}
