package sh.com.pcl.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import sh.com.pcl.R
import sh.com.pcl.bean.YiChuLiBaoXiu
import sh.com.pcl.utils.DatetoString

/**
 * Created by Administrator on 2017/11/5.
 */
class FaultHandelAdapter(mList: List<YiChuLiBaoXiu>) : BaseQuickAdapter<YiChuLiBaoXiu, BaseViewHolder>(R.layout.ad_fault_msg, mList) {
    override fun convert(helper: BaseViewHolder?, item: YiChuLiBaoXiu?) {
        helper!!.setText(R.id.fault_msg_name, item?.Repairs_User)
        helper!!.setText(R.id.fault_msg_time, DatetoString.StringtoStringLong(item?.Process_Time.toString()))
    }
}