package sh.com.pcl.bean

/**
 * Created by Administrator on 2017/11/3.
 */
class UserInfolist {
    /**
     * userInfo : {"User_Number":1000124,"User_Name":"娄素洁","User_Quantity":5,"User_Site":"河南省郑州市郑东新区郑东商业中心","User_Time":20171012165516,"WaterMeter_Number":"10621117","WaterMeter_Time":20171013174247,"WaterMeter_State":1,"WaterType_Name":"液体定量流量表"}
     */

    var userInfo: UserInfoBean? = null

    class UserInfoBean {
        /**
         * User_Number : 1000124
         * User_Name : 娄素洁
         * User_Quantity : 5
         * User_Site : 河南省郑州市郑东新区郑东商业中心
         * User_Time : 20171012165516
         * WaterMeter_Number : 10621117
         * WaterMeter_Time : 20171013174247
         * WaterMeter_State : 1
         * WaterType_Name : 液体定量流量表
         */

        var user_Number: Int = 0
        var user_Name: String? = null
        var user_Quantity: Int = 0
        var user_Site: String? = null
        var user_Time: Long = 0
        var waterMeter_Number: String? = null
        var waterMeter_Time: Long = 0
        var waterMeter_State: Int = 0
        var waterType_Name: String? = null
    }
}