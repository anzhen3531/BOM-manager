package ext.ziang.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

public class DownloadFileUtil {

	/**
	 * 下载文件
	 *
	 * @param fileName
	 *            文件名
	 * @param response
	 *            响应
	 * @throws IOException
	 *             io异常
	 */
	public static void downloadFile(String fileName, HttpServletResponse response) throws IOException {
		fileName = fileName.replaceAll("\\\\", "/");
		String displayName = fileName.substring(fileName.lastIndexOf("/") + 1);
		String filename = java.net.URLEncoder.encode(displayName, "UTF-8");
		OutputStream os = response.getOutputStream();
		response.setContentType("application/x-msdownload; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
		System.out.println("Test fileName = " + fileName);
		File temp = new File(fileName);
		InputStream input = new FileInputStream(temp);
		byte[] buff = new byte[512];
		int len = 0;
		while ((len = input.read(buff)) != -1) {
			os.write(buff, 0, len);
		}
		input.close();
		response.setStatus(HttpServletResponse.SC_OK);
		response.flushBuffer();
		os.flush();
	}

	/**
	 * 下载文件
	 *
	 * @param inputStream
	 *            文件流
	 * @param response
	 *            响应
	 * @throws IOException
	 *             io异常
	 */
	public static void downloadFile(InputStream inputStream, HttpServletResponse response, String displayName)
			throws IOException {
		String filename = java.net.URLEncoder.encode(displayName, "UTF-8");
		OutputStream os = response.getOutputStream();
		response.setContentType("application/x-msdownload; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
		byte[] buff = new byte[512];
		int len = 0;
		while ((len = inputStream.read(buff)) != -1) {
			os.write(buff, 0, len);
		}
		inputStream.close();
		response.setStatus(HttpServletResponse.SC_OK);
		response.flushBuffer();
		os.flush();
	}
}
