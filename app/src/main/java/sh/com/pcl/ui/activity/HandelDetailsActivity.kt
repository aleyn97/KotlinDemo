package sh.com.pcl.ui.activity

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.google.gson.Gson
import com.lzy.okhttputils.OkHttpUtils
import com.lzy.okhttputils.callback.StringCallback
import kotlinx.android.synthetic.main.activity_handel_details.*
import okhttp3.Call
import okhttp3.Response

import sh.com.pcl.R
import sh.com.pcl.bean.faultHandelDate0
import sh.com.pcl.bean.faultHandelDate1
import sh.com.pcl.common.BaseActivity
import sh.com.pcl.common.ServerConfig
import sh.com.pcl.ui.adapter.PhotoAdapter
import sh.com.pcl.utils.DatetoString
import sh.com.pcl.utils.Dialog
import java.lang.Exception

class HandelDetailsActivity : BaseActivity() {
    var reqUrl: String = ""
    var value: String = ""
    var type: String = ""
    var dataBean0: faultHandelDate0? = null
    var dataBean1: faultHandelDate1? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handel_details)
        initView()
    }

    fun initView() {
        type = intent.extras.getString("type")
        detil_gridView.layoutManager = GridLayoutManager(this, 4)
        when (type) {
            "0" -> {
                reqUrl = ServerConfig.BX_MSG_DATA_URL
                value = "repairsID"
            }
            "1" -> {
                reqUrl = ServerConfig.BX_HANDEL_DITEAL
                value = "processID"
            }
        }
        request()
    }

    private fun request() {
        Dialog.CreateDialog(this)
        OkHttpUtils
                .get(reqUrl)
                .params(value, intent.extras.getString("handelId"))
                .execute(object : StringCallback() {
                    override fun onSuccess(t: String?, call: Call?, response: Response?) {
                        Liner_Not_Det.visibility = View.VISIBLE
                        when (type) {
                            "0" -> {
                                dataBean0 = Gson().fromJson(t, faultHandelDate0::class.java)
                                initDate0(dataBean0!!)
                            }
                            "1" -> {
                                dataBean1 = Gson().fromJson(t, faultHandelDate1::class.java)
                                initDate1(dataBean1!!)
                            }
                        }
                    }

                    override fun onAfter(t: String?, e: Exception?) {
                        super.onAfter(t, e)
                        Dialog.CloseDialog()
                    }

                    override fun onError(call: Call?, response: Response?, e: Exception?) {
                        super.onError(call, response, e)
                        Dialog.Error(this@HandelDetailsActivity)
                    }
                })
    }

    var mList = ArrayList<String>()
    private fun initDate0(bean: faultHandelDate0) {
        handel_user_name.text = bean.Repairs_User
        fault_count.text = bean.Repairs_Content
        fault_name.text = bean.Repairs_Name
        fault_time.text = DatetoString.StringtoStringLong(bean.Repairs_Time.toString())
        mList.add(bean.FILE_PATH0)
        detil_gridView.adapter = PhotoAdapter(this, mList)
    }

    private fun initDate1(bean: faultHandelDate1) {
        handel_user_name.text = bean.Repairs_User
        fault_count.text = bean.Repairs_Content
        fault_name.text = bean.Repairs_Name
        fault_time.text = DatetoString.StringtoStringLong(bean.Process_Time.toString())
        if (!bean.FILE_PATHProcess0.equals("")) mList.add(bean.FILE_PATHProcess0)
        if (!bean.FILE_PATHRepairs0.equals("")) mList.add(bean.FILE_PATHRepairs0)
        detil_gridView.adapter = PhotoAdapter(this, mList)
    }

}
