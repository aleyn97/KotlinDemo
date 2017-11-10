package sh.com.pcl.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.gson.Gson
import com.lzy.okhttputils.OkHttpUtils
import com.lzy.okhttputils.callback.StringCallback
import kotlinx.android.synthetic.main.activity_fault_msg.*
import okhttp3.Call
import okhttp3.Response

import sh.com.pcl.R
import sh.com.pcl.bean.Repair
import sh.com.pcl.bean.faultMsgList
import sh.com.pcl.common.BaseActivity
import sh.com.pcl.common.ServerConfig
import sh.com.pcl.ui.adapter.FaultMsgAdapter
import sh.com.pcl.utils.Dialog
import java.lang.Exception

class FaultMsgActivity : BaseActivity() {
    var app: MyApplication? = null
    var faultBean: faultMsgList? = null
    var list = ArrayList<Repair>()
    var adapter: FaultMsgAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fault_msg)
        initDate()
    }

    override fun onResume() {
        super.onResume()
        request()
    }

    fun initDate() {
        setTitle("故障消息")
        app = application as MyApplication?
        fault_msg_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = FaultMsgAdapter(list)
        fault_msg_list.adapter = adapter
        adapter!!.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            var bunder = Bundle()
            bunder.putString("id", faultBean!!.repairs!!.get(position).id.toString())
            startActivity(FaultDataActivity::class.java, bunder)
        }
    }

    fun request() {
        Dialog.CreateDialog(this)
        OkHttpUtils
                .get(ServerConfig.BX_MSG_URL)
                .params("loginName", app!!.name)
                .execute(object : StringCallback() {
                    override fun onSuccess(t: String?, call: Call?, response: Response?) {
                        println(t.toString())
                        faultBean = Gson().fromJson(t!!, faultMsgList::class.java)
                        list.addAll(faultBean!!.repairs!!)
                    }

                    override fun onAfter(t: String?, e: Exception?) {
                        super.onAfter(t, e)
                        Dialog.CloseDialog()
                    }

                    override fun onError(call: Call?, response: Response?, e: Exception?) {
                        super.onError(call, response, e)
                        Dialog.Error(this@FaultMsgActivity)
                    }
                })
    }

    override fun onPause() {
        super.onPause()
        list.clear()
    }
}

