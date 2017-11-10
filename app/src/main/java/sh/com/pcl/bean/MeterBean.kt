package sh.com.pcl.bean

/**
 * Created by Administrator on 2017/11/5.
 */

data class CBsubBean(
        val shangCiChaoBiaoJiLu: ShangCiChaoBiaoJiLu? = null
)

data class ShangCiChaoBiaoJiLu(
        val User_Number: Int = 0,
        val User_Name: String = "",
        val User_Ladder: Int = 0,
        val User_Site: String = "",
        val User_Phone: Long = 0,
        val WaterSort_Name: String = "",
        val WaterMeter_State: Int = 0,
        val MeterBook_Name: String = "",
        val MeterReading_Number: Int = 0
)

data class CBBookBean(
        var meterBookList: List<MeterBookListBean>? = null
)

data class MeterBookListBean(
        var id: Int = 0,
        var meterBook_Name: String? = null,
        var staff_Name: String? = null,
        var meterBook_Time: Long = 0,
        var meterBook_Nmb: Int = 0
)