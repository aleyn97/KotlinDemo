package sh.com.pcl.ui.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.google.zxing.Result
import com.lzy.okhttputils.OkHttpUtils
import com.lzy.okhttputils.callback.StringCallback
import kotlinx.android.synthetic.main.activity_qr.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import okhttp3.Call
import okhttp3.Response

import sh.com.pcl.R
import sh.com.pcl.common.ServerConfig

class QrActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private var mFlash: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr)
        bt_lamp.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                toggleFlash()
            }
        })
        bt_hand.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var intent = Intent(this@QrActivity, MeterQRActivity::class.java)
                startActivity(intent)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        zxing_qr?.setResultHandler(this) // Register ourselves as a handler for scan results.
        zxing_qr?.startCamera()
    }

    override fun onPause() {
        super.onPause()
        zxing_qr?.stopCamera()
    }

    override fun handleResult(rawResult: Result?) {
        if (!rawResult?.getText().equals("")) {
            var intent = Intent(this@QrActivity, MeterSubActivity::class.java)
            intent.putExtra("id", rawResult?.getText())
            startActivity(intent)
        } else {
            ToastUtils.showShort("扫描结果为空")
        }
        //如果要恢复扫描，请调用以下方法：
        //zxing_qr?.resumeCameraPreview(this)
    }

    private fun toggleFlash() {
        mFlash = !mFlash
        zxing_qr?.setFlash(mFlash)
    }
}
