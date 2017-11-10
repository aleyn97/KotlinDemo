package sh.com.pcl.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.gson.Gson
import com.lzy.okhttputils.OkHttpUtils
import com.lzy.okhttputils.callback.StringCallback
import kotlinx.android.synthetic.main.activity_fault_new.*
import okhttp3.Call
import okhttp3.Response
import sh.com.pcl.R
import sh.com.pcl.bean.BaoXiuJiLu
import sh.com.pcl.bean.faultNewList
import sh.com.pcl.common.BaseActivity
import sh.com.pcl.common.ServerConfig
import sh.com.pcl.utils.DatetoString
import sh.com.pcl.utils.Dialog
import java.lang.Exception

class FaultNewActivity : BaseActivity() {

    var dateBean: faultNewList? = null
    var mList = ArrayList<BaoXiuJiLu>()
    var type: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fault_new)
        initView()
    }

    override fun onResume() {
        super.onResume()
        request()
    }

    private fun initView() {
        type = intent.getStringExtra("id")
        when (type) {
            "0" -> setTitle("新增故障")
            "1" -> setTitle("报修记录")
        }
        fault_new_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun request() {
        Dialog.CreateDialog(this)
        OkHttpUtils
                .get(ServerConfig.BX_RECORD_URL)
                .params("loginName", (application as MyApplication).name)
                .params("state", type)
                .execute(object : StringCallback() {
                    override fun onSuccess(t: String?, call: Call?, response: Response?) {
                        println(t.toString())
                        dateBean = Gson().fromJson(t!!, faultNewList::class.java)
                        mList.addAll(dateBean!!.BaoXiuJiLu)
                        if (mList.isEmpty()) {
                            fail_text.visibility = View.VISIBLE
                        } else {
                            initAdapter()
                        }
                    }

                    override fun onAfter(t: String?, e: Exception?) {
                        super.onAfter(t, e)
                        Dialog.CloseDialog()
                    }

                    override fun onError(call: Call?, response: Response?, e: Exception?) {
                        super.onError(call, response, e)
                        Dialog.Error(this@FaultNewActivity)
                    }
                })
    }

    private fun initAdapter() {
        var adapter = object : BaseQuickAdapter<BaoXiuJiLu, BaseViewHolder>(R.layout.ad_fault_msg, mList) {
            override fun convert(helper: BaseViewHolder?, item: BaoXiuJiLu?) {
                helper!!.setText(R.id.fault_msg_name, item!!.Repairs_User)
//                helper.setText(R.id.fault_msg_time, item.Process_Time.toString())
                helper!!.setText(R.id.fault_msg_time, DatetoString.StringtoStringLong(item!!.Repairs_getTime.toString()))
            }
        }
        fault_new_list.adapter = adapter
        adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            var bundle = Bundle()
            bundle.putString("type", type)
            bundle.putString("handelId", mList.get(position).Process_ID.toString())
            when (type) {
                "0" -> startActivity(FaultSubActivity::class.java, bundle)
                "1" -> startActivity(HandelDetailsActivity::class.java, bundle)
            }
        }

    }

    override fun onPause() {
        super.onPause()
        mList.clear()
    }
}
