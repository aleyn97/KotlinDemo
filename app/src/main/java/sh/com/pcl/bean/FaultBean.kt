package sh.com.pcl.bean


/**
 * Created by Administrator on 2017/11/4.
 */

data class faultMsgList(
        val repairs: List<Repair> = listOf()
)

data class Repair(
        val id: Int = 0,
        val Repairs_User: String = "",
        val Repairs_Time: Long = 0,
        val Repairs_SendTime: Long = 0,
        val User_Site: String = ""
)


data class faultNewList(
        val BaoXiuJiLu: List<BaoXiuJiLu> = listOf()
)

data class BaoXiuJiLu(
        val id: Int = 0,
        val Repairs_User: String = "",
        val Repairs_getTime: Long = 0,
        val Process_Time: Long? = 0,
        val Process_ID: Int = 0
)

data class faultMsgDate(
        val id: Int = 0,
        val Repairs_Time: Long = 0,
        val Repairs_SendTime: Long = 0,
        val Repairs_User: String = "",
        val Repairs_Content: String = "",
        val Repairs_Name: String = "",
        val Repairs_Phone: Long = 0,
        val User_Site: String = "",
        val Repairs_Randomnum: Int = 0,
        val FILE_PATH0: String = "",
        val FILE_PATH1: String = "",
        val FILE_PATH2: String = "",
        val FILE_PATH3: String = ""
)

data class faultHandel(
        val YiChuLiBaoXiu: List<YiChuLiBaoXiu> = listOf(),
        val WeiChuLiBaoXiu: List<WeiChuLiBaoXiu> = listOf()
)

data class YiChuLiBaoXiu(
        val id: Int = 0,
        val Repairs_User: String = "",
        val Repairs_SendTime: Long = 0,
        val Process_Time: Long = 0,
        val Process_ID: Int = 0
)

data class WeiChuLiBaoXiu(
        val id: Int = 0,
        val Repairs_User: String = "",
        val Repairs_SendTime: Long = 0,
        val Process_Time: Long = 0
)


data class faultHandelDate1(
        val id: Int = 0,
        val Repairs_User: String = "",
        val Repairs_Content: String = "",
        val Repairs_Name: String = "",
        val Repairs_Phone: Long = 0,
        val User_Site: String = "",
        val Repairs_Randomnum: Int = 0,
        val Process_Time: Long = 0,
        val Process_Name: String = "",
        val Process_Content: String = "",
        val FILE_PATHRepairs0: String = "",
        val FILE_PATHProcess0: String = ""
)

data class faultHandelDate0(
        val id: Int = 0,
        val Repairs_Time: Long = 0,
        val Repairs_SendTime: Long = 0,
        val Repairs_User: String = "",
        val Repairs_Content: String = "",
        val Repairs_Name: String = "",
        val Repairs_Phone: Long = 0,
        val User_Site: String = "",
        val Repairs_Randomnum: Int = 0,
        val FILE_PATH0: String = ""
)