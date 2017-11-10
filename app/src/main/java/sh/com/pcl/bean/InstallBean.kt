package sh.com.pcl.bean

/**
 * Created by Administrator on 2017/11/9.
 */

data class newMsgList(
        val Installation: List<Installation> = listOf()
)

data class Installation(
        val id: Int = 0,
        val installation_SendTime: Long = 0,
        val Installation_User: String = "",
        val Installation_Userphone: Long = 0,
        val Installation_Address: String = ""
)


data class newMsgDetilse(
        val Installation_Time: Long = 0,
        val Installation_User: String = "",
        val Installation_Userphone: Long = 0,
        val Installation_Address: String = "",
        val installation_SendTime: Long = 0,
        val Installation_Name: String = "",
        val WaterType_Name: String = "",
        val Installation_UserIDcard: String = "",
        val WaterSort_Name: String = ""
)


data class NewNotHandle(
        val WeichuLiBaoZhuang: List<BaoZhuang> = listOf(),
        val YiChuLiBaoZhuang: List<BaoZhuang> = listOf()
)

data class BaoZhuang(
        val id: Int = 0,
        val installation_SendTime: Long = 0,
        val installation_GetTime: Long = 0,
        val Installation_User: String = "",
        val Installation_Userphone: Long = 0,
        val Installation_Address: String = ""
)


data class notDetilse(
        val Installation_Time: Long = 0,
        val Installation_User: String = "",
        val Installation_Userphone: Long = 0,
        val Installation_Address: String = "",
        val installation_SendTime: Long = 0,
        val Installation_Name: String = "",
        val WaterType_Name: String = "",
        val Installation_UserIDcard: String = "",
        val WaterSort_Name: String = ""
)


data class NewAddList(
        val XinZengBaoZhuang: List<XinZengBaoZhuang> = listOf()
)

data class XinZengBaoZhuang(
        val id: Int = 0,
        val installation_SendTime: Long = 0,
        val installation_GetTime: Long = 0,
        val Installation_User: String = "",
        val Installation_Userphone: Long = 0,
        val Installation_Address: String = ""
)


data class NewRecord(
        val ZhuangBiaoJiLu: List<XinZengBaoZhuang> = listOf()
)
