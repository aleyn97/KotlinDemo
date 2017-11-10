package sh.com.pcl.utils

import android.app.Activity
import android.net.Uri
import android.widget.ImageView
import com.lzy.imagepicker.loader.ImageLoader
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import sh.com.pcl.R
import java.io.File

/**
 * Created by Administrator on 2017/11/7.
 */
class PicassoImageLoader : ImageLoader {
    override fun displayImagePreview(activity: Activity?, path: String?, imageView: ImageView?, width: Int, height: Int) {

    }

    override fun displayImage(activity: Activity?, path: String?, imageView: ImageView?, width: Int, height: Int) {
        Picasso.with(activity)//
                .load(Uri.fromFile(File(path)))//
                .placeholder(R.drawable.default_img)//
                .error(R.drawable.default_img)//
                .resize(width, height)//
                .centerInside()//
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)//
                .into(imageView);
    }

    override fun clearMemoryCache() {

    }

}