package sh.com.pcl.ui.activity

import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.lzy.okhttputils.OkHttpUtils
import com.lzy.okhttputils.callback.StringCallback
import kotlinx.android.synthetic.main.activity_meter_sub.*
import okhttp3.Call
import okhttp3.Response

import sh.com.pcl.R
import sh.com.pcl.bean.CBsubBean
import sh.com.pcl.bean.ShangCiChaoBiaoJiLu
import sh.com.pcl.common.BaseActivity
import sh.com.pcl.common.ServerConfig
import sh.com.pcl.utils.Dialog
import sh.com.pcl.utils.JsonMananger
import java.lang.Exception

class MeterSubActivity : BaseActivity() {

    var subBean: CBsubBean? = null
    private var ladder: String? = null
    private var water_LiuLiang: String? = null
    private var sort: String? = null
    private var BC_LiuLiang: String? = null
    private var app: MyApplication? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meter_sub)
        request()
    }

    fun request() {
        Dialog.CreateDialog(this)
        OkHttpUtils
                .get(ServerConfig.CB_SC_WATERREC_URL)
                .params("WaterMeterNumber", intent.getStringExtra("id"))
                .execute(object : StringCallback() {
                    override fun onSuccess(t: String?, call: Call?, response: Response?) {
                        subBean = JsonMananger.jsonToBean(t!!, CBsubBean::class.java)
                        if (subBean!!.shangCiChaoBiaoJiLu?.User_Name == null) {
                            ToastUtils.showShort("没有该水表")
                            finish()
                        } else {
                            initDate(subBean!!.shangCiChaoBiaoJiLu!!)
                        }
                    }

                    override fun onAfter(t: String?, e: Exception?) {
                        super.onAfter(t, e)
                        Dialog.CloseDialog()
                    }

                    override fun onError(call: Call?, response: Response?, e: Exception?) {
                        super.onError(call, response, e)
                        Dialog.Error(this@MeterSubActivity)
                    }
                })
    }

    fun initDate(bean: ShangCiChaoBiaoJiLu) {
        ladder = bean.User_Ladder.toString()
        water_LiuLiang = bean.MeterReading_Number.toString()
        sort = bean.WaterSort_Name

        user_Name.setText(bean.User_Name.toString())
        user_number.text = bean.User_Number.toString()
        water_sort.text = sort
        user_address.text = bean.User_Site
        if (ladder.equals("0")) {
            water_price_type.setText("非阶梯")
        } else {
            water_price_type.setText("阶梯")
        }
        water_sc_num.setText(water_LiuLiang)
    }

    fun SubonClick(view: View) {
        when (view.id) {
            R.id.bt_sub -> {
                BC_LiuLiang = water_bc_num.text.toString()
                if (!BC_LiuLiang.equals("")) {
                    response(BC_LiuLiang!!)
                } else {
                    water_bc_num.setShakeAnimation();
                    ToastUtils.showShort("水表数不能为空")
                }
            }
            R.id.bt_not -> {
                finish()
            }
        }
    }

    fun response(num: String) {
        Dialog.CreateDialog(this)
        OkHttpUtils
                .get(ServerConfig.CB_SC_WATER_SUB_URL)
                .params("loginName", app?.name)
                .params("UserLadder", ladder)
                .params("WaterMeterNumber", intent.getStringExtra("id"))
                .params("WaterSortID", sort)
                .params("bcMeterReadingNumber", num)
                .params("scMeterReadingNumber", water_LiuLiang)
                .execute(object : StringCallback() {
                    override fun onSuccess(t: String?, call: Call?, response: Response?) {
                        Dialog.CloseDialog()
                        Dialog.Complete(this@MeterSubActivity)
                    }

                    override fun onError(call: Call?, response: Response?, e: Exception?) {
                        super.onError(call, response, e)
                        Dialog.CloseDialog()
                        Dialog.Error(this@MeterSubActivity)
                    }
                })

    }
}
