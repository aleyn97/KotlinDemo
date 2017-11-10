package sh.com.pcl.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import sh.com.pcl.R
import sh.com.pcl.bean.Repair
import sh.com.pcl.utils.DatetoString

/**
 * Created by Administrator on 2017/11/4.
 */
class FaultMsgAdapter(mList: List<Repair>) : BaseQuickAdapter<Repair, BaseViewHolder>(R.layout.ad_fault_msg, mList) {
    override fun convert(helper: BaseViewHolder?, item: Repair?) {
        helper?.setText(R.id.fault_msg_name, item?.Repairs_User)
        helper?.setText(R.id.fault_msg_time, DatetoString.StringtoStringLong(item?.Repairs_Time.toString()))
    }
}