package sh.com.pcl.ui.activity

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.widget.GridLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lzy.imagepicker.ImagePicker
import com.lzy.imagepicker.bean.ImageItem
import com.lzy.okhttputils.OkHttpUtils
import com.lzy.okhttputils.callback.StringCallback
import okhttp3.Call
import okhttp3.Response

import sh.com.pcl.R
import sh.com.pcl.common.BaseActivity
import sh.com.pcl.common.ServerConfig
import kotlinx.android.synthetic.main.activity_fault_sub.*
import kotlinx.android.synthetic.main.ad_photo_glid.view.*
import com.lzy.imagepicker.ui.ImageGridActivity
import com.squareup.picasso.Picasso
import java.io.File
import android.util.Base64
import android.view.View
import com.google.gson.Gson
import com.google.gson.JsonObject
import sh.com.pcl.ui.adapter.addPhotoAdapter
import sh.com.pcl.utils.DatetoString
import sh.com.pcl.utils.Dialog
import java.lang.Exception

class FaultSubActivity : BaseActivity() {
    var IMAGE_PICKER = 1
    var mList = ArrayList<ImageItem>()
    var adapter: addPhotoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fault_sub)
        initView()
    }

    private fun initView() {
        tv_name.text = (application as MyApplication).name
        adapter = addPhotoAdapter(mList, this, pic_gridView)
        pic_gridView.adapter = adapter
        pic_gridView.setOnItemClickListener { adapterView, view, i, l ->
            when (i) {
                mList.size -> {
                    if (mList.size > 3) {
                        ToastUtils.showShort("最多四张")
                    } else {
                        photo()
                    }
                }
                else -> DialogPhoto(i)
            }
        }
        fault_sub.setOnClickListener { ToJson() }
    }

    private fun response(json: String) {
        Dialog.CreateDialog(this)
        OkHttpUtils
                .post(ServerConfig.BX_SAVE_URL)
                .upJson(json)
                .execute(object : StringCallback() {
                    override fun onSuccess(t: String?, call: Call?, response: Response?) {
                        println(t.toString())
                    }

                    override fun onAfter(t: String?, e: Exception?) {
                        super.onAfter(t, e)
                        Dialog.CloseDialog()
                    }

                    override fun onError(call: Call?, response: Response?, e: Exception?) {
                        super.onError(call, response, e)
                        Dialog.Error(this@FaultSubActivity)
                    }
                })
    }

    private fun ToJson() {
        if (!ed_input_explain.text.equals("") && mList.size > 0) {
            var map = HashMap<String, String>()
            map.put("processID", intent.extras.getString("id"))
            map.put("processContent", ed_input_explain.text.toString())
            var Pic = ""
            for (a in mList) {
                if (Pic.equals("")) {
                    Pic = DatetoString.PathToBase64(a.path)
                } else {
                    Pic = Pic + "," + DatetoString.PathToBase64(a.path)
                }
            }
            map.put("processPic", Pic)
            var json = Gson().toJson(map)
            println(json)
            response(json)
        } else {
            ToastUtils.showShort("维修内容或图片为空！")
        }
    }

    private fun photo() {
        val intent = Intent(this, ImageGridActivity::class.java)
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true) // 是否是直接打开相机
        startActivityForResult(intent, IMAGE_PICKER)
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

    private fun DialogPhoto(size: Int) {
        var mDialog = SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
        mDialog.setTitleText("您确定要移除该照片？")
        mDialog.setConfirmText("确定")
        mDialog.setConfirmClickListener {
            mList.removeAt(size)
            adapter!!.notifyDataSetChanged()
            mDialog.dismiss()
        }
        mDialog.show()
    }
}