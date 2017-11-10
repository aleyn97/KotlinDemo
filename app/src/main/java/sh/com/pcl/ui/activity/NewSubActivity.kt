package sh.com.pcl.ui.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import cn.pedant.SweetAlert.SweetAlertDialog
import com.blankj.utilcode.util.ToastUtils
import com.google.gson.Gson
import com.lzy.imagepicker.ImagePicker
import com.lzy.imagepicker.bean.ImageItem
import com.lzy.imagepicker.ui.ImageGridActivity
import com.lzy.okhttputils.OkHttpUtils
import com.lzy.okhttputils.callback.StringCallback
import kotlinx.android.synthetic.main.activity_new_sub.*
import okhttp3.Call
import okhttp3.Response

import sh.com.pcl.R
import sh.com.pcl.common.BaseActivity
import sh.com.pcl.common.ServerConfig
import sh.com.pcl.ui.adapter.addPhotoAdapter
import sh.com.pcl.utils.DatetoString
import sh.com.pcl.utils.Dialog
import java.lang.Exception

class NewSubActivity : BaseActivity() {
    var mList = ArrayList<ImageItem>()
    var adapter: addPhotoAdapter? = null
    var IMAGE_PICKER: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_sub)
        initView()
    }

    fun initView() {
        setTitle("提交页面")
        water_id.text = intent.extras.getString("id")
        adapter = addPhotoAdapter(mList, this, new_add_grid)
        new_add_grid.adapter = adapter
        new_add_grid.setOnItemClickListener { adapterView, view, i, l ->
            PlayPhoto(i)
        }
        add_sub.setOnClickListener {
            ToJson()
        }
    }

    fun PlayPhoto(i: Int) {
        if (i == mList.size) {
            if (i == 4) ToastUtils.showShort("最多添加四张")
            else {
                val intent = Intent(this, ImageGridActivity::class.java)
                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true) // 是否是直接打开相机
                startActivityForResult(intent, IMAGE_PICKER)
            }
        } else mDialog(i)
    }

    fun request(json: String) {
        Dialog.CreateDialog(this)
        OkHttpUtils
                .post(ServerConfig.AZ_BC_URL)
                .upJson(json)
                .execute(object : StringCallback() {
                    override fun onSuccess(t: String?, call: Call?, response: Response?) {
                        Dialog.Complete(this@NewSubActivity)
                    }

                    override fun onAfter(t: String?, e: Exception?) {
                        super.onAfter(t, e)
                        Dialog.CloseDialog()
                    }

                    override fun onError(call: Call?, response: Response?, e: Exception?) {
                        super.onError(call, response, e)
                        Dialog.Error(this@NewSubActivity)
                    }
                })
    }

    fun ToJson() {
        var number = ed_water_number.text.toString()
        if (!number.equals("")) {
            if (mList.size > 0) {
                var map = HashMap<String, String>()
                map.put("WaterMeterID", intent.getStringExtra("id"))
                map.put("WaterMeterNumber", number)
                map.put("remarks", water_sub_beizhu.text.toString())
                var Pic = ""
                for (a in mList) {
                    if (Pic.equals("")) {
                        Pic = DatetoString.PathToBase64(a.path)
                    } else {
                        Pic = Pic + "," + DatetoString.PathToBase64(a.path)
                    }
                }
                map.put("WaterMeterPic", Pic)
                var json = Gson().toJson(map)
                request(json)
            } else {
                ToastUtils.showShort("图片至少为一张")
            }
        } else {
            ToastUtils.showShort("水表编号不能为空")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                var list: ArrayList<ImageItem> = data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS) as ArrayList<ImageItem>
                mList.addAll(list)
                adapter!!.notifyDataSetChanged()
            }
        }
    }

    fun mDialog(i: Int) {
        var dialog = SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
        dialog.setTitleText("您确定要移除该照片？")
                .setConfirmText("确定")
                .setConfirmClickListener {
                    mList.removeAt(i)
                    adapter!!.notifyDataSetChanged()
                    dialog.dismiss()
                }
                .show()
    }
}
