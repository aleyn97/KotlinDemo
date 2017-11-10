package sh.com.pcl

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import cn.pedant.SweetAlert.SweetAlertDialog
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.lzy.okhttputils.OkHttpUtils
import com.lzy.okhttputils.callback.StringCallback
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Call
import okhttp3.Response
import sh.com.pcl.ui.activity.HomeActivity
import sh.com.pcl.bean.LoginBean
import sh.com.pcl.common.ServerConfig
import sh.com.pcl.ui.activity.MyApplication
import sh.com.pcl.utils.Dialog
import sh.com.pcl.utils.JsonMananger
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    var loginBean = LoginBean()
    var name: String = ""
    var pwd: String = ""
    var app: MyApplication? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    fun initView() {
        app = application as MyApplication?
        if (SPUtils.getInstance().getBoolean("ISCHECK", false)) {
            cb_rmb_pwd.isChecked = true
            Login_name.setText(SPUtils.getInstance().getString("USER_NAME", ""))
            Login_pwd.setText(SPUtils.getInstance().getString("PASSWORD", ""))
        }
    }

    fun onClick(v: View) {
        name = Login_name.text.toString()
        pwd = Login_pwd.text.toString()
        when (v.id) {
            R.id.bt_login -> {
                if (!name.equals("") && !pwd.equals("")) request()
                else ToastUtils.showShort("密码或账户为空")
            }
        }
    }

    fun Remember() {
        if (cb_rmb_pwd.isChecked) {
            //记住用户名、密码、
            SPUtils.getInstance().put("USER_NAME", name)
            SPUtils.getInstance().put("PASSWORD", pwd)
            SPUtils.getInstance().put("ISCHECK", true)
        } else {
            SPUtils.getInstance().put("ISCHECK", false)
            SPUtils.getInstance().clear()
        }
    }

    fun request() {
        Dialog.CreateDialog(this)
        OkHttpUtils
                .get(ServerConfig.LOGIN_URL)
                .params("username", name)
                .params("password", pwd)
                .execute(object : StringCallback() {
                    override fun onSuccess(t: String?, call: Call?, response: Response?) {
                        Remember()
                        println(t.toString())
                        loginBean = JsonMananger.jsonToBean(t!!, LoginBean::class.java)
                        if (loginBean.loginResult.toString().equals("SUCCESS")) {
                            app!!.name = name
                            var intent = Intent()
                            intent.setClass(this@MainActivity, HomeActivity::class.java)
                            var list = ArrayList<String>()
                            for (a in loginBean.gnmc.orEmpty()) {
                                list.add(a.gnmc!!)
                            }
                            intent.putStringArrayListExtra("list", list)
                            startActivity(intent)
                        } else if (loginBean.loginResult.toString().equals("ERROR")) {
                            Dialog.Notice(this@MainActivity, loginBean.msg.toString())
                        }
                    }

                    override fun onAfter(t: String?, e: Exception?) {
                        super.onAfter(t, e)
                        Dialog.CloseDialog()
                    }

                    override fun onError(call: Call?, response: Response?, e: Exception?) {
                        super.onError(call, response, e)
                        mDialog()
                    }
                })
    }

    fun mDialog() {
        SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("错误")
                .setContentText(getString(R.string.ServerError).toString())
                .show()
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}
