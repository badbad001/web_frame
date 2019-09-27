package cn.itcast.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: badbad
 * @Date: 2019/9/21 11:51
 * @Version 1.0
 */
public class LayuiUtils {
    /**
     * 文件上传成功的返回
     * /**
     *          * {
     *          *   "code": 0
     *          *   ,"msg": ""
     *          *   ,"data": {
     *          *     "src": "http://cdn.layui.com/123.jpg"
     *          *     返回的数据格式
     *          *   }
     *          * }
     *
     * @return
     */
    public static Map<String,Object> fileUploadSuccess(String url){
        /*如果不穿回调信息，默认为空字符串*/
        /*把下载的url返回去*/

        Map<String,Object> map=new HashMap<>();
        map.put("code", 0);
        map.put("msg", "上传成功");
        Map<String,Object> data=new HashMap<>();
        data.put("src", url);
        map.put("data", data);
        return map;
    }

    /**
     * 文件上传失败的返回  失败code不为0，我们写1即可
     * 失败url为空url
     * /**
     *          * {
     *          *   "code": 0
     *          *   ,"msg": ""
     *          *   ,"data": {
     *          *     "src": "http://cdn.layui.com/123.jpg"
     *          *     返回的数据格式
     *          *   }
     *          * }
     *
     * @return
     */
    public static Map<String,Object> fileUploadFail(){

        Map<String,Object> map=new HashMap<>();
        map.put("code", 1);
        map.put("msg", "上传失败");
        Map<String,Object> data=new HashMap<>();
        data.put("src", "");
        map.put("data", data);
        return map;
    }
}
