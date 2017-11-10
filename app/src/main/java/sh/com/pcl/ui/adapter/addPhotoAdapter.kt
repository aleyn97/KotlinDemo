package sh.com.pcl.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView

import com.lzy.imagepicker.bean.ImageItem
import com.squareup.picasso.Picasso

import sh.com.pcl.R
import sh.com.pcl.utils.PicassoImageLoader

/**
 * Created by Administrator on 2017/8/4.
 */

class addPhotoAdapter(private val mList: List<ImageItem>?, internal var activity: Activity, private val gridView: GridView) : BaseAdapter() {
    private val inflater: LayoutInflater

    init {
        this.inflater = LayoutInflater.from(activity)
    }

    override fun getCount(): Int {
        return if (mList == null) 1 else mList.size + 1//返回listiview数目加1;
    }

    override fun getItem(i: Int): Any {
        return mList!![i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var view = view
        val vh: ViewHolder
        if (view == null) {
            view = inflater.inflate(R.layout.ad_photo_glid, null)
            vh = ViewHolder()
            vh.imageView1 = view!!.findViewById(R.id.ad_photo_defult)
            view.tag = vh
        } else {
            vh = view.tag as ViewHolder
        }

        val size = gridView.width
        val pic = PicassoImageLoader()
        if (mList != null && i < mList.size) {
            pic.displayImage(activity, mList[i].path, vh.imageView1, size, size)
            return view
        } else {
            Picasso.with(activity).load(R.mipmap.new_add_img).into(vh.imageView1)
            return view
        }
    }

    internal class ViewHolder {
        var imageView1: ImageView? = null
    }
}
