package sh.com.pcl.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.lzy.okhttputils.OkHttpUtils
import com.lzy.okhttputils.callback.StringCallback
import kotlinx.android.synthetic.main.at_meter_album.*
import okhttp3.Call
import okhttp3.Response

import sh.com.pcl.R
import sh.com.pcl.bean.CBBookBean
import sh.com.pcl.bean.MeterBookListBean
import sh.com.pcl.common.BaseActivity
import sh.com.pcl.common.ServerConfig
import sh.com.pcl.ui.adapter.CbBookAdapter
import sh.com.pcl.utils.Dialog
import sh.com.pcl.utils.JsonMananger
import java.lang.Exception

class MeterAlbumActivity : BaseActivity() {

    var list = ArrayList<MeterBookListBean>()
    var bookBean: CBBookBean? = null
    var app: MyApplication? = null
    var adapter: CbBookAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.at_meter_album)
        initView()
    }

    fun initView() {
        //设置RecyclerView布局
        list_item.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        //设置Title
        setTitle("表册分类")
        app = application as MyApplication?
        //设置Adapter
        adapter = CbBookAdapter(list)
        list_item.adapter = adapter
        //设置Item点击事件
        adapter!!.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            var bcID = list.get(position).id.toString()
            var bunder = Bundle()
            bunder.putString("str", intent.getStringExtra("id"))
            bunder.putString("bcID", bcID)
            startActivity(MeterRecordListActivity::class.java, bunder)
        }
    }

    override fun onResume() {
        super.onResume()
        reauest()
    }

    fun reauest() {
        Dialog.CreateDialog(this)
        OkHttpUtils
                .get(ServerConfig.CB_Meter_BOOK_URL)
                .params("loginName", app?.name.toString())
                .execute(object : StringCallback() {
                    override fun onSuccess(t: String?, call: Call?, response: Response?) {
                        list.addAll(JsonMananger.jsonToBean(t!!, CBBookBean::class.java).meterBookList!!)
                        adapter!!.notifyDataSetChanged()
                    }

                    override fun onAfter(t: String?, e: Exception?) {
                        super.onAfter(t, e)
                        Dialog.CloseDialog()
                    }

                    override fun onError(call: Call?, response: Response?, e: Exception?) {
                        super.onError(call, response, e)
                        Dialog.Error(this@MeterAlbumActivity)
                    }
                })
    }

    override fun onPause() {
        super.onPause()
        list.clear()
    }
}
