package sh.com.pcl.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.gson.Gson
import com.lzy.okhttputils.OkHttpUtils
import com.lzy.okhttputils.callback.StringCallback
import kotlinx.android.synthetic.main.activity_new_add.*
import okhttp3.Call
import okhttp3.Response
import sh.com.pcl.R
import sh.com.pcl.bean.NewAddList
import sh.com.pcl.bean.XinZengBaoZhuang
import sh.com.pcl.common.BaseActivity
import sh.com.pcl.common.ServerConfig
import sh.com.pcl.utils.DatetoString
import sh.com.pcl.utils.Dialog
import java.lang.Exception

class NewAddActivity : BaseActivity() {
    var type: String? = null
    var mList = ArrayList<XinZengBaoZhuang>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_add)
        initView()
    }

    fun initView() {
        type = intent.getStringExtra("id")
        when (type) {
            "0" -> {
                setTitle("新装")
            }
            "1" -> {
                setTitle("新装记录")
            }
        }
        request()
        list_new_add.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        var adapter = object : BaseQuickAdapter<XinZengBaoZhuang, BaseViewHolder>(R.layout.ad_handle_list, mList) {
            override fun convert(helper: BaseViewHolder?, item: XinZengBaoZhuang?) {
                helper!!.setText(R.id.tv_name, item?.Installation_User)
                helper.setText(R.id.tv_phone, item?.Installation_Userphone.toString())
                helper.setText(R.id.tv_gettime, DatetoString.StringtoStringLong(item?.installation_GetTime.toString()))
                helper.setText(R.id.tv_sendtime, DatetoString.StringtoStringLong(item?.installation_SendTime.toString()))
                helper.setText(R.id.tv_address, item?.Installation_Address)
            }
        }
        list_new_add.adapter = adapter
        adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            var bundle = Bundle()
            bundle.putString("id", mList.get(position).id.toString())
            bundle.putString("type", type)
            when (type) {
                "0" -> {
                    startActivity(NewSubActivity::class.java, bundle)
                }
                "1" -> {
                    startActivity(NewHandDetActivity::class.java, bundle)
                }
            }
        }
    }

    fun request() {
        Dialog.CreateDialog(this)
        OkHttpUtils
                .get(ServerConfig.AZ_JL_URL)
                .params("loginName", (application as MyApplication).name)
                .params("state", type)
                .execute(object : StringCallback() {
                    override fun onSuccess(t: String?, call: Call?, response: Response?) {
                        println(t.toString())
                        mList.addAll(Gson().fromJson(t!!, NewAddList::class.java).XinZengBaoZhuang)
                        if (mList.isEmpty()) add_text.visibility = View.VISIBLE
                        else list_new_add.adapter.notifyDataSetChanged()
                    }

                    override fun onError(call: Call?, response: Response?, e: Exception?) {
                        super.onError(call, response, e)
                        Dialog.Error(this@NewAddActivity)
                    }

                    override fun onAfter(t: String?, e: Exception?) {
                        super.onAfter(t, e)
                        Dialog.CloseDialog()
                    }
                })
    }
}
