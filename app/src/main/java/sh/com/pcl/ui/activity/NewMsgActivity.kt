package sh.com.pcl.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.gson.Gson
import com.lzy.okhttputils.OkHttpUtils
import com.lzy.okhttputils.callback.StringCallback
import kotlinx.android.synthetic.main.activity_new_msg.*
import okhttp3.Call
import okhttp3.Response

import sh.com.pcl.R
import sh.com.pcl.bean.Installation
import sh.com.pcl.bean.newMsgList
import sh.com.pcl.common.BaseActivity
import sh.com.pcl.common.ServerConfig
import sh.com.pcl.utils.Dialog
import java.lang.Exception

class NewMsgActivity : BaseActivity() {

    var bean: newMsgList? = null
    var mList = ArrayList<Installation>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_msg)
        initView()
    }

    override fun onResume() {
        super.onResume()
        request()
    }

    fun initView() {
        setTitle("接单详情")
        new_msg_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        var adapter = object : BaseQuickAdapter<Installation, BaseViewHolder>(R.layout.ad_fault_msg, mList) {
            override fun convert(helper: BaseViewHolder?, item: Installation?) {
                helper!!.setText(R.id.fault_msg_name, item?.Installation_User)
                helper.setText(R.id.fault_msg_time, item?.Installation_Address)
            }
        }
        new_msg_list.adapter = adapter
        adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            var bundle = Bundle()
            bundle.putString("id", mList.get(position).id.toString())
            startActivity(NewMsgDetilesActivity::class.java, bundle)
        }
    }

    private fun request() {
        Dialog.CreateDialog(this)
        OkHttpUtils
                .get(ServerConfig.AZ_MSG_URL)
                .params("loginName", (application as MyApplication).name)
                .execute(object : StringCallback() {
                    override fun onSuccess(t: String?, call: Call?, response: Response?) {
                        bean = Gson().fromJson(t!!, newMsgList::class.java)
                        mList.addAll(bean!!.Installation)
                        if (mList.isEmpty()) new_text.visibility = View.VISIBLE
                        else new_msg_list.adapter.notifyDataSetChanged()
                    }

                    override fun onAfter(t: String?, e: Exception?) {
                        super.onAfter(t, e)
                        Dialog.CloseDialog()
                    }

                    override fun onError(call: Call?, response: Response?, e: Exception?) {
                        super.onError(call, response, e)
                        Dialog.Error(this@NewMsgActivity)
                    }
                })
    }

    override fun onPause() {
        super.onPause()
        mList.clear()
    }
}
