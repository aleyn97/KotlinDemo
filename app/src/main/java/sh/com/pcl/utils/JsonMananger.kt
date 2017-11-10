package sh.com.pcl.utils

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.util.TypeUtils


/**
 * Created by Administrator on 2017/7/14.
 */

object JsonMananger {
    init {
        TypeUtils.compatibleWithJavaBean = true
    }

    /**
     * 将json字符串转换成java对象
     *
     * @param json
     * @param cls
     * @return
     * @throws Exception
     */
    fun <T> jsonToBean(json: String, cls: Class<T>): T {
        return JSON.parseObject(json, cls)
    }

    /**
     * 将json字符串转换成java List对象
     *
     * @param json
     * @param cls
     * @return
     * @throws Exception
     */
    fun <T> jsonToList(json: String, cls: Class<T>): List<T> {
        return JSON.parseArray(json, cls)
    }

    /**
     * 将bean对象转化成json字符串
     *
     * @param obj
     * @return
     * @throws Exception
     */
    fun beanToJson(obj: Any): String {
        return JSON.toJSONString(obj)
    }


}