package sh.com.pcl.common

/**
 * Created by Administrator on 2017/10/29.
 */
object ServerConfig {
    val IP: String = "http://192.168.1.119:8080"
    val IP_WAI: String = "http://120.76.137.198:9090"
    val IP_NEI: String = "http://192.168.1.119:8080"
    val LOGIN_URL: String = IP + "/login/login.html"
    val USER_URL: String = IP + "/hold/userinfo/userinfo.html"
    val AZ_MSG_URL: String = IP + "/hold/Installation/installationinfolist.html"
    val AZ_MES_DETAILS_URL: String = IP + "/hold/Installation/installationinfo.html"
    val WORK_ORDERS_URL: String = IP + "/hold/Installation/installationOK.html"
    val AZ_HANDLE_URL: String = IP + "/hold/Installation/disposeInstallation.html"
    val BAO_ZHUANG_YES_DETILSE_URL: String = IP + "/hold/Installation/Installationfulfill.html"
    val AZ_JL_URL: String = IP + "/hold/Installation/Installationlog.html"
    val AZ_BC_URL: String = IP + "/hold/Installation/addwatermeter.html"
    val BX_MSG_URL: String = IP + "/hold/repairs/repairslist.html"
    val BX_MSG_DATA_URL: String = IP + "/hold/repairs/repairsinfo.html"
    val BX_MSG_DATA_SUB_URL: String = IP + "/hold/repairs/repairsOK.html"
    val BX_HANDEL_URL: String = IP + "/hold/repairs/processOK.html"
    val BX_HANDEL_DITEAL: String = IP + "/hold/repairs/processinfo.html"
    val BX_RECORD_URL: String = IP + "/hold/repairs/processlog.html"
    val BX_SAVE_URL: String = IP + "/hold/repairs/saveprocess.html"
    val CB_Meter_URL: String = IP + "/hold/userinfo/monthuserinfo.html"
    val CB_Meter_BOOK_URL: String = IP + "/hold/userinfo/queryBookList.html"
    val CB_RECORD_URL: String = IP + "/hold/userinfo/queryreadwaterList.html"
    val CB_SC_WATERREC_URL: String = IP + "/hold/userinfo/scmeterreading.html"
    val CB_SC_WATER_SUB_URL: String = IP + "/hold/userinfo/savemeterreading.html"
    val CB_WATER_Yes_Detilse_URL: String = IP + "/hold/userinfo/readingWaterInfo.html"
}