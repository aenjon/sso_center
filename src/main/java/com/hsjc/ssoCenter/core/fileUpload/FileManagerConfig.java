package com.hsjc.ssoCenter.core.fileUpload;

import java.io.Serializable;

/**
 * @author : zga
 * @date : 2016-01-12
 *
 * 文件上传配置类
 *
 */
public class FileManagerConfig implements Serializable {
	public static final String FILE_DEFAULT_WIDTH 	= "120";
	public static final String FILE_DEFAULT_HEIGHT 	= "120";
	public static final String FILE_DEFAULT_AUTHOR 	= "zhuzi";

	public static final String PROTOCOL = "http://";
	public static final String SEPARATOR = "/";
	public static final String COLON = ":";

	public static final String TRACKER_NGNIX_PORT 	= "8081";

	public static final String CLIENT_CONFIG_FILE   = "fdfs_client.conf";
}
