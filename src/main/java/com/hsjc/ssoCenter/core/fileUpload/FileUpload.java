package com.hsjc.ssoCenter.core.fileUpload;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author : zga
 * @date : 2016-01-12
 *
 * 文件上传工具类
 *
 */
public class FileUpload {
	public static String upload(MultipartFile file,String ip){
		CommonsMultipartFile cf= (CommonsMultipartFile)file;
		DiskFileItem fi = (DiskFileItem)cf.getFileItem();
		File fil = fi.getStoreLocation();
		InputStream fis = null;
		String fileAbsolutePath=null;
		try {
			//fis = new FileInputStream(fil);
			fis = file.getInputStream();

			byte[] file_buff = null;
			if (fis != null) {
				int len = fis.available();
				file_buff = new byte[len];
				fis.read(file_buff);
			}
			String suffix=fi.getName().substring(fi.getName().lastIndexOf(".") + 1, fi.getName().length());
			FastDFSFile file1 = new FastDFSFile(fil.getName(), file_buff, suffix);
			fileAbsolutePath = FileManager.upload(file1,ip);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileAbsolutePath;
	}
}
