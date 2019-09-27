package cn.itcast.controller;

import cn.itcast.utils.AppFileUtils;
import cn.itcast.utils.LayuiUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: badbad
 * @Date: 2019/9/21 11:26
 * @Version 1.0
 */
@Controller
@RequestMapping("file")
public class FileController {

    /**
     * 文件上传
     * @param mf
     * @return
     */
    @RequestMapping("upload")
    @ResponseBody
    public Map<String,Object> upload(MultipartFile mf){

        String downloadUrl = null;
        try {
            //System.out.println(mf);
            /*上传文件成功返回一个下载的url*/
           downloadUrl = AppFileUtils.uploadUseTime(mf);
        } catch (IOException e) {
            e.printStackTrace();
            LayuiUtils.fileUploadFail();
        }
        //文件上传成功就返回上传成功的url
        return LayuiUtils.fileUploadSuccess(downloadUrl);
    }

    /**
     * 批量文件上传
     * @param mfs
     * @return
     */
    @RequestMapping("uploadBatch")
    @ResponseBody
    public Map<String,Object> upload(MultipartFile[] mfs){

        String downloadUrl = null;
        try {
            //System.out.println(mf);
            /*上传文件成功返回一个下载的url*/
            for (MultipartFile mf : mfs) {
                downloadUrl = AppFileUtils.uploadUseTime(mf);
            }

        } catch (IOException e) {
            e.printStackTrace();
            LayuiUtils.fileUploadFail();
        }
        //文件上传成功就返回上传成功的url
        return LayuiUtils.fileUploadSuccess(downloadUrl);
    }


    /**
     * 下载文件
     * @param path
     * @param response
     * @return
     */
    /**
     * 不下载只显示
     */
    @RequestMapping("downloadShowFile")
    public ResponseEntity<Object> dowloadFile(String path, HttpServletResponse response){

        //3,拿到文件的老名字
        return AppFileUtils.downloadFile(response, path, "");
    }

}
