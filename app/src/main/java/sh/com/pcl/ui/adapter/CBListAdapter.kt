package sh.com.pcl.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import sh.com.pcl.R
import sh.com.pcl.bean.CBListbean

/**
 * Created by Administrator on 2017/11/2.
 */
class CBListAdapter(mList: List<CBListbean>?) : BaseQuickAdapter<CBListbean, BaseViewHolder>(R.layout.ad_meter_record, mList) {

    override fun convert(helper: BaseViewHolder?, item: CBListbean?) {
        helper?.setText(R.id.record_user_id, item?.User_Number.toString())
        helper?.setText(R.id.record_user_name, item?.User_Name)
        helper?.setText(R.id.record_user_address, item?.User_Site)
    }
}