package cn.itcast.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public class AppFileUtils {

	/**
	 * 使用时间作为文件名路径下载
	 * 路径都是用时间
	 * @param mf
	 * @throws IOException
	 */
	public static String uploadUseTime(MultipartFile mf) throws IOException{
		//文件上传的父目录
		String parentPath=AppFileUtils.PATH;
		//得到当前日期作为文件夹名称
		String dirName=RandomUtils.getCurrentDateForString();
		//构造文件夹对象
		File dirFile=new File(parentPath,dirName);
		if(!dirFile.exists()) {
			dirFile.mkdirs();//创建文件夹
		}
		//得到文件原名
		String oldName=mf.getOriginalFilename();
		//根据文件原名得到新名
		String newName=RandomUtils.createFileNameUseTime(oldName);
		File dest=new File(dirFile,newName);
		mf.transferTo(dest);


		//文件上传，返回下载的url地址
		return "file/download?path="+dirName+"/"+newName;
	}

	/**
	 * 使用uuid作为文件名下载
	 * 路径都是用时间
	 * @param mf
	 * @throws IOException
	 */
	public static String uploadUseUuid(MultipartFile mf) throws IOException{
		//文件上传的父目录
		String parentPath=AppFileUtils.PATH;
		//得到当前日期作为文件夹名称
		String dirName=RandomUtils.getCurrentDateForString();
		//构造文件夹对象
		File dirFile=new File(parentPath,dirName);
		if(!dirFile.exists()) {
			dirFile.mkdirs();//创建文件夹
		}
		//得到文件原名
		String oldName=mf.getOriginalFilename();
		//根据文件原名得到新名
		String newName=RandomUtils.createFileNameUseUUID(oldName);
		File dest=new File(dirFile,newName);
		mf.transferTo(dest);

		//文件上传，返回下载的url地址
		return "file/download?path="+dirName+"/"+newName;
	}


	
	/**
	 * 得到文件上传的路径
	 */
	public static String PATH;
	static {
		InputStream stream = AppFileUtils.class.getClassLoader().getResourceAsStream("file.properties");
		Properties properties=new Properties();
		try {
			properties.load(stream);
			PATH=properties.getProperty("path");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

	/**
	 * 文件下载
	 * @param response
	 * @param path
	 * @param oldName
	 * @return
	 */
	public static  ResponseEntity<Object> downloadFile(HttpServletResponse response, String path, String oldName) {
		//4,使用绝对路径+相对路径去找到文件对象
		File file=new File(AppFileUtils.PATH,path);
		//5,判断文件是否存在
		if(file.exists()) {
			try {
				try {
					//如果名字有中文 要处理编码
					oldName=URLEncoder.encode(oldName, "UTF-8");
				} catch (Exception e) {
					e.printStackTrace();
				}
				//把file转成一个bytes
				byte [] bytes=FileUtils.readFileToByteArray(file);
				HttpHeaders header=new HttpHeaders();
				//封装响应内容类型(APPLICATION_OCTET_STREAM 响应的内容不限定)
				header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				//设置下载的文件的名称
				header.setContentDispositionFormData("attachment", oldName);
				//创建ResponseEntity对象
				ResponseEntity<Object> entity=
						new ResponseEntity<Object>(bytes, header, HttpStatus.CREATED);
				return entity;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}else {
			PrintWriter out;
			try {
				out = response.getWriter();
				out.write("文件不存在");
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}
}
