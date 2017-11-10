package sh.com.pcl.ui.adapter

import android.content.Context
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.ad_photo_glid.view.*
import sh.com.pcl.R

/**
 * Created by Administrator on 2017/11/5.
 */
class PhotoAdapter(var context: Context, mList: List<String>) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.ad_photo_glid, mList) {

    override fun convert(helper: BaseViewHolder?, item: String?) {
        Picasso.with(context).load(item).into(helper?.itemView?.ad_photo_defult)
    }
}