package sh.com.pcl.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.gson.Gson
import com.lzy.okhttputils.OkHttpUtils
import com.lzy.okhttputils.callback.StringCallback
import kotlinx.android.synthetic.main.activity_new_handle.*
import okhttp3.Call
import okhttp3.Response

import sh.com.pcl.R
import sh.com.pcl.bean.BaoZhuang
import sh.com.pcl.bean.NewNotHandle
import sh.com.pcl.common.BaseActivity
import sh.com.pcl.common.ServerConfig
import sh.com.pcl.utils.DatetoString
import sh.com.pcl.utils.Dialog
import java.lang.Exception

class NewHandleActivity : BaseActivity() {
    var type: String? = null
    var bean: NewNotHandle? = null
    var mList = ArrayList<BaoZhuang>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_handle)
        initView()
    }

    override fun onResume() {
        super.onResume()
        request()
    }

    fun initView() {
        type = intent.getStringExtra("id")
        println(type)
        when (type) {
            "0" -> setTitle("未处理")
            "1" -> setTitle("已处理")
        }
        list_not_handel.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        var adapter = object : BaseQuickAdapter<BaoZhuang, BaseViewHolder>(R.layout.ad_handle_list, mList) {
            override fun convert(helper: BaseViewHolder?, item: BaoZhuang?) {
                helper!!.setText(R.id.tv_name, item?.Installation_User)
                helper.setText(R.id.tv_phone, item?.Installation_Userphone.toString())
                helper.setText(R.id.tv_gettime, DatetoString.StringtoStringLong(item?.installation_GetTime.toString()))
                helper.setText(R.id.tv_sendtime, DatetoString.StringtoStringLong(item?.installation_SendTime.toString()))
                helper.setText(R.id.tv_address, item?.Installation_Address)
            }
        }
        list_not_handel.adapter = adapter
        adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            var bundle = Bundle()
            bundle.putString("id", mList.get(position).id.toString())
            bundle.putString("type", type)
            startActivity(NewHandDetActivity::class.java, bundle)
        }

    }

    fun request() {
        Dialog.CreateDialog(this)
        OkHttpUtils
                .get(ServerConfig.AZ_HANDLE_URL)
                .params("loginName", (application as MyApplication).name)
                .params("state", type)
                .execute(object : StringCallback() {
                    override fun onSuccess(t: String?, call: Call?, response: Response?) {
                        bean = Gson().fromJson(t!!, NewNotHandle::class.java)
                        if (type.equals("0")) mList.addAll(bean!!.WeichuLiBaoZhuang)
                        else mList.addAll(bean!!.YiChuLiBaoZhuang)

                        if (mList.isEmpty()) Dialog.Notice(this@NewHandleActivity, "暂无数据")
                        else list_not_handel.adapter.notifyDataSetChanged()
                    }

                    override fun onAfter(t: String?, e: Exception?) {
                        super.onAfter(t, e)
                        Dialog.CloseDialog()
                    }

                    override fun onError(call: Call?, response: Response?, e: Exception?) {
                        super.onError(call, response, e)
                        Dialog.Error(this@NewHandleActivity)
                    }
                })
    }

    override fun onPause() {
        super.onPause()
        mList.clear()
    }
}
