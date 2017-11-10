package sh.com.pcl.ui.activity

import android.app.Application
import com.blankj.utilcode.util.Utils
import com.lzy.imagepicker.ImagePicker
import com.lzy.imagepicker.view.CropImageView
import com.lzy.okhttputils.OkHttpUtils
import sh.com.pcl.utils.PicassoImageLoader


/**
 * Created by Administrator on 2017/10/29.
 */
class MyApplication : Application() {
    var name: String? = null
    override fun onCreate() {
        super.onCreate()
        Utils.init(applicationContext as Application)
        initOkhttp()
        initImage()
    }

    fun initOkhttp() {
        OkHttpUtils.init(this);
        //以下都不是必须的，根据需要自行选择
        OkHttpUtils.getInstance()//
                .debug("OkHttpUtils")                                              //是否打开调试
                .setConnectTimeout(15000)               //全局的连接超时时间
                .setReadTimeOut(15000)                  //全局的读取超时时间
                .setWriteTimeOut(15000)                 //全局的写入超时时间
                //.setCookieStore(new MemoryCookieStore())                           //cookie使用内存缓存（app退出后，cookie消失）
                //.setCookieStore(new PersistentCookieStore())                       //cookie持久化存储，如果cookie不过期，则一直有效
//                .addCommonHeaders(headers)                                         //设置全局公共头
//                .addCommonParams(params)
    }

    fun initImage() {
        var imagePicker = ImagePicker.getInstance()
        imagePicker.imageLoader = PicassoImageLoader()   //设置图片加载器
        imagePicker.isShowCamera = true  //显示拍照按钮
        imagePicker.isCrop = false        //允许裁剪（单选才有效）
        imagePicker.isSaveRectangle = true //是否按矩形区域保存
        imagePicker.selectLimit = 9    //选中数量限制
//        imagePicker.style = CropImageView.Style.RECTANGLE  //裁剪框的形状
//        imagePicker.focusWidth = 800   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
//        imagePicker.focusHeight = 800  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.outPutX = 1000//保存文件的宽度。单位像素
        imagePicker.outPutY = 1000//保存文件的高度。单位像素
    }
}