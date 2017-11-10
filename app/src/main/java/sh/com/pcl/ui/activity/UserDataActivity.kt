package sh.com.pcl.ui.activity

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.at_user_data.*

import sh.com.pcl.R
import sh.com.pcl.common.BaseActivity
import com.lzy.okhttputils.OkHttpUtils
import com.lzy.okhttputils.callback.StringCallback
import okhttp3.Call
import okhttp3.Response
import sh.com.pcl.bean.UserInfolist
import sh.com.pcl.common.ServerConfig
import sh.com.pcl.utils.DatetoString
import sh.com.pcl.utils.Dialog
import sh.com.pcl.utils.JsonMananger


class UserDataActivity : BaseActivity() {

    var userinfo: UserInfolist? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.at_user_data)
        initDate()
    }

    fun initDate() {
        bt_search.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if (!user_search.text.toString().equals("")) {
                    request(user_search.text.toString())
                } else {
                    Dialog.Notice(this@UserDataActivity, "内容不能为空")
                }
            }
        })
    }

    private fun request(text: String) {
        OkHttpUtils
                .get(ServerConfig.USER_URL)
                .params("strWhere", text)
                .execute(object : StringCallback() {
                    override fun onSuccess(t: String?, call: Call?, response: Response?) {
                        println(t.toString())
                        userinfo = JsonMananger.jsonToBean(t!!, UserInfolist::class.java)
                        println(userinfo!!.userInfo?.user_Name)
                        if (userinfo!!.userInfo?.user_Name == null) {
                            Dialog.Notice(this@UserDataActivity, "没有查到对应信息")
                        } else {
                            initView(userinfo!!.userInfo)
                        }
                    }
                })
    }

    private fun initView(bean: UserInfolist.UserInfoBean?) {
        layout_user_main.visibility = View.VISIBLE
        tv_name2.text = bean!!.user_Name
        tv_time2.text = DatetoString.StringtoStringLong(bean.user_Time.toString())
        tv_address2.text = bean.user_Site
        tv_number2.text = bean.waterMeter_Number.toString()
        tv_peopel2.text = bean.user_Quantity.toString()
        if (bean.waterMeter_State == 1) {
            tv_state2.setText("正常")
        } else {
            tv_state2.setText("停水")
        }
        tv_water_num2.text = bean.waterMeter_Number.toString()
        tv_az_time2.text = DatetoString.StringtoStringLong(bean.waterMeter_Time.toString())
        tv_modle2.text = bean.waterType_Name
    }
}
