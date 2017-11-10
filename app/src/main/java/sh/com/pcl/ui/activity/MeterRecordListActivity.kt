package sh.com.pcl.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.fastjson.JSON
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.lzy.okhttputils.OkHttpUtils
import com.lzy.okhttputils.callback.StringCallback
import kotlinx.android.synthetic.main.activity_meter_record_list.*
import okhttp3.Call
import okhttp3.Response

import sh.com.pcl.R
import sh.com.pcl.bean.CBListbean
import sh.com.pcl.common.BaseActivity
import sh.com.pcl.common.ServerConfig
import sh.com.pcl.ui.adapter.CBListAdapter
import sh.com.pcl.utils.Dialog
import java.lang.Exception

class MeterRecordListActivity : BaseActivity() {

    var bcID: String = ""
    var str: String = ""
    var mList: MutableList<CBListbean>? = null
    var adapter: CBListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meter_record_list)
        initDate()
    }

    fun initDate() {
        str = intent.extras.getString("str")
        bcID = intent.extras.getString("bcID")
        if (str.equals("1")) {
            setTitle("本月已抄")
        } else {
            setTitle("本月未抄")
        }
        //设置RecyclerView布局
        meter_cb_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onResume() {
        super.onResume()
        request()
    }

    fun request() {
        Dialog.CreateDialog(this)
        OkHttpUtils
                .get(ServerConfig.CB_Meter_URL)
                .params("str", str)
                .params("bcID", bcID)
                .execute(object : StringCallback() {
                    override fun onSuccess(t: String?, call: Call?, response: Response?) {
                        mList = JSON.parseArray(t, CBListbean::class.java)
                        if (mList!!.isEmpty()) record_null_text.visibility = View.VISIBLE
                        else {
                            initAdapter()
                        }
                    }

                    override fun onAfter(t: String?, e: Exception?) {
                        super.onAfter(t, e)
                        Dialog.CloseDialog()
                    }

                    override fun onError(call: Call?, response: Response?, e: Exception?) {
                        super.onError(call, response, e)
                        Dialog.Error(this@MeterRecordListActivity)
                    }
                })
    }

    override fun onPause() {
        super.onPause()
        mList?.clear()
    }

    fun initAdapter() {
        adapter = CBListAdapter(mList)
        meter_cb_list.adapter = adapter
        adapter!!.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            ToastUtils.showShort(mList!!.get(position).User_Name)
        }
    }
}
