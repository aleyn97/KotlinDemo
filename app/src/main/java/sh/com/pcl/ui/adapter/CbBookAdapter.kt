package sh.com.pcl.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import sh.com.pcl.R
import sh.com.pcl.bean.CBBookBean

/**
 * Created by Administrator on 2017/11/2.
 */
class CbBookAdapter(list: List<CBBookBean.MeterBookListBean>) : BaseQuickAdapter<CBBookBean.MeterBookListBean, BaseViewHolder>(R.layout.ad_cb_book, list) {

    override fun convert(helper: BaseViewHolder?, item: CBBookBean.MeterBookListBean?) {
        helper?.setText(R.id.tv_book_name, item?.meterBook_Name)
    }
}