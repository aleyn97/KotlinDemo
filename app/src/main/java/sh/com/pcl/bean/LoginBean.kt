package sh.com.pcl.bean

/**
 * Created by Administrator on 2017/10/29.
 */

class LoginBean {

    /**
     * gnmc : [{"GNMC":"实时监控"},{"GNMC":"抄表监控"},{"GNMC":"APP抄表信息"},{"GNMC":"APP报装处理"},{"GNMC":"APP报修处理"},{"GNMC":"抄表定位"},{"GNMC":"抄表历史轨迹"}]
     * loginResult : SUCCESS
     * msg : 登录成功！
     * sjhm : 18636365858
     * zsxm : 超级管理员
     */

    var loginResult: String? = null
    var msg: String? = null
    var sjhm: String? = null
    var zsxm: String? = null
    var gnmc: List<GnmcBean>? = null

    class GnmcBean {
        /**
         * GNMC : 实时监控
         */
        var gnmc: String? = null
    }
}
