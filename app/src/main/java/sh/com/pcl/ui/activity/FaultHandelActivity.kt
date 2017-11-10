package sh.com.pcl.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.gson.Gson
import com.lzy.okhttputils.OkHttpUtils
import com.lzy.okhttputils.callback.StringCallback
import kotlinx.android.synthetic.main.activity_fault_handel.*
import okhttp3.Call
import okhttp3.Response

import sh.com.pcl.R
import sh.com.pcl.bean.Repair
import sh.com.pcl.bean.WeiChuLiBaoXiu
import sh.com.pcl.bean.YiChuLiBaoXiu
import sh.com.pcl.bean.faultHandel
import sh.com.pcl.common.BaseActivity
import sh.com.pcl.common.ServerConfig
import sh.com.pcl.ui.adapter.FaultHandelAdapter
import sh.com.pcl.utils.DatetoString
import sh.com.pcl.utils.Dialog
import java.lang.Exception

class FaultHandelActivity : BaseActivity() {
    var app: MyApplication? = null
    var dataBean: faultHandel? = null
    var type: String = ""
    var mList1 = ArrayList<YiChuLiBaoXiu>()
    var mList0 = ArrayList<WeiChuLiBaoXiu>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fault_handel)
        initDate()
    }

    private fun initDate() {
        type = intent.getStringExtra("id")
        app = application as MyApplication
        when (type) {
            "0" -> setTitle("未处理")
            "1" -> setTitle("已处理")
        }
        request()
        faultAdapter()
    }

    private fun request() {
        Dialog.CreateDialog(this)
        OkHttpUtils
                .get(ServerConfig.BX_HANDEL_URL)
                .params("loginName", app!!.name)
                .params("state", type)
                .execute(object : StringCallback() {
                    override fun onSuccess(t: String?, call: Call?, response: Response?) {
                        dataBean = Gson().fromJson(t!!, faultHandel::class.java)
                        when (type) {
                            "1" -> {
                                mList1.addAll(dataBean!!.YiChuLiBaoXiu)
                                if (mList1.isEmpty()) fail_text.visibility = View.VISIBLE
                            }
                            "0" -> {
                                mList0.addAll(dataBean!!.WeiChuLiBaoXiu)
                                if (mList0.isEmpty()) fail_text.visibility = View.VISIBLE
                            }
                        }
                    }

                    override fun onAfter(t: String?, e: Exception?) {
                        super.onAfter(t, e)
                        Dialog.CloseDialog()
                    }

                    override fun onError(call: Call?, response: Response?, e: Exception?) {
                        super.onError(call, response, e)
                        Dialog.Error(this@FaultHandelActivity)
                    }
                })
    }

    fun faultAdapter() {
        fault_handel_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        if (type.equals("1")) {
            var adapterYes = object : BaseQuickAdapter<YiChuLiBaoXiu, BaseViewHolder>(R.layout.ad_fault_msg, mList1) {
                override fun convert(helper: BaseViewHolder?, item: YiChuLiBaoXiu?) {
                    helper!!.setText(R.id.fault_msg_name, item?.Repairs_User)
                    helper!!.setText(R.id.fault_msg_time, DatetoString.StringtoStringLong(item?.Repairs_SendTime.toString()))
                }
            }
            fault_handel_list.adapter = adapterYes
            adapterYes.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
                var bundel = Bundle()
                bundel.putString("handelId", mList1.get(position).Process_ID.toString())
                bundel.putString("type", type)
                startActivity(HandelDetailsActivity::class.java, bundel)
            }
        }
        if (type.equals("0")) {
            var adapterNo = object : BaseQuickAdapter<WeiChuLiBaoXiu, BaseViewHolder>(R.layout.ad_fault_msg, mList0) {
                override fun convert(helper: BaseViewHolder?, item: WeiChuLiBaoXiu?) {
                    helper!!.setText(R.id.fault_msg_name, item?.Repairs_User)
                    helper!!.setText(R.id.fault_msg_time, DatetoString.StringtoStringLong(item?.Repairs_SendTime.toString()))
                }
            }
            fault_handel_list.adapter = adapterNo
            adapterNo.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
                var bundel = Bundle()
                bundel.putString("handelId", mList0.get(position).id.toString())
                bundel.putString("type", type)
                startActivity(HandelDetailsActivity::class.java, bundel)
            }
        }
    }
}
