package com.hsjc.ssoCenter.core.fileUpload;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import java.io.File;
import java.io.IOException;

/**
 * @author : zga
 * @date : 2016-01-12
 *
 * 文件上传管理类
 */
public class FileManager extends FileManagerConfig {
	private static final long serialVersionUID = 1L;
	private static TrackerClient trackerClient;
	private static TrackerServer trackerServer;
	private static StorageServer storageServer;
	private static StorageClient storageClient;
	static {
		try {
			String classPath = new File(FileManager.class.getResource("/").getFile()).getCanonicalPath();
			String fdfsClientConfigFilePath = classPath + File.separator + CLIENT_CONFIG_FILE;
			ClientGlobal.init(fdfsClientConfigFilePath);

			trackerClient = new TrackerClient();
			trackerServer = trackerClient.getConnection();
			storageClient = new StorageClient(trackerServer, storageServer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String upload(FastDFSFile file,String ip) {
		NameValuePair[] meta_list = new NameValuePair[3];
		meta_list[0] = new NameValuePair("width", "120");
		meta_list[1] = new NameValuePair("heigth", "120");
		meta_list[2] = new NameValuePair("author", "zhuzi");
		String[] uploadResults = null;
		try {
			uploadResults = storageClient.upload_file(file.getContent(), file.getExt(), meta_list);
		}  catch (Exception e) {
			e.printStackTrace();
		}
		if (uploadResults == null) {return null;}
		String groupName = uploadResults[0];
		String remoteFileName = uploadResults[1];
		return PROTOCOL + trackerServer.getInetSocketAddress().getHostName() + COLON + TRACKER_NGNIX_PORT + SEPARATOR + groupName + SEPARATOR + remoteFileName;
	}


	public static FileInfo getFile(String groupName, String remoteFileName) {
		try {
			return storageClient.get_file_info(groupName, remoteFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void deleteFile(String groupName, String remoteFileName) throws Exception {
		storageClient.delete_file(groupName, remoteFileName);
	}

	public static StorageServer[] getStoreStorages(String groupName) throws IOException {
		return trackerClient.getStoreStorages(trackerServer, groupName);
	}

	public static ServerInfo[] getFetchStorages(String groupName, String remoteFileName) throws IOException {
		return trackerClient.getFetchStorages(trackerServer, groupName, remoteFileName);
	}
}
