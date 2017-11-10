package sh.com.pcl.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lzy.okhttputils.OkHttpUtils
import com.lzy.okhttputils.callback.StringCallback
import kotlinx.android.synthetic.main.at_meter_record.*
import okhttp3.Call
import okhttp3.Response

import sh.com.pcl.R
import sh.com.pcl.bean.CBRecordBean
import sh.com.pcl.common.BaseActivity
import sh.com.pcl.common.ServerConfig
import sh.com.pcl.utils.JsonMananger
import java.lang.Exception

class MeterRecordActivity : BaseActivity() {

    var list: List<CBRecordBean>? = null
    var app: MyApplication? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.at_meter_record)
        initDate()
    }

    fun initDate() {
        app = application as MyApplication?
        setTitle("已抄列表")
        request()
        meter_record_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        meter_record_list.adapter = object : BaseQuickAdapter<CBRecordBean, BaseViewHolder>(R.layout.ad_meter_record, list) {
            override fun convert(helper: BaseViewHolder?, item: CBRecordBean?) {
                helper!!.setText(R.id.record_user_id, item?.User_Number.toString())
                helper.setText(R.id.record_user_name, item?.User_Name)
                helper.setText(R.id.record_user_address, item?.User_Site)
            }

        }
    }

    fun request() {
        println(app?.name)
        OkHttpUtils
                .get(ServerConfig.CB_RECORD_URL)
                .params("loginName", app?.name)
                .execute(object : StringCallback() {
                    override fun onError(call: Call?, response: Response?, e: Exception?) {
                        super.onError(call, response, e)
                        ToastUtils.showShort("请求错误")
                    }

                    override fun onSuccess(t: String?, call: Call?, response: Response?) {
                        println(t.toString())
                        list = JsonMananger.jsonToList(t!!, CBRecordBean::class.java)
                        if (list!!.isEmpty()) {
                            record_null_text.visibility = View.VISIBLE
                        } else {

                        }
                    }
                })
    }
}
