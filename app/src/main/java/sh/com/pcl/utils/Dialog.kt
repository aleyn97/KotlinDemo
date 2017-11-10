package sh.com.pcl.utils

import android.app.Activity
import android.content.Context
import cn.pedant.SweetAlert.SweetAlertDialog
import sh.com.pcl.R

/**
 * Created by Administrator on 2017/10/31.
 */
object Dialog {
    lateinit var pDialog: SweetAlertDialog

    fun CreateDialog(context: Context) {
        pDialog = SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
        pDialog.setTitleText("请稍等...")
        pDialog.setCancelable(false)
        pDialog.show()
    }

    fun CloseDialog() {
        pDialog.dismiss()
    }

    fun Complete(activity: Activity) {
        var dialog = SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
        dialog.setTitleText("完成")
                .setConfirmClickListener { dialog.dismiss() }
                .show()
    }

    fun Error(activity: Activity) {
        SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("错误")
                .setContentText(activity.getString(R.string.ServerError).toString())
                .setConfirmClickListener { activity.finish() }
                .show()
    }

    fun Notice(context: Context, count: String) {
        SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("提示")
                .setContentText(count)
                .setConfirmText("关闭")
                .show()
    }
}