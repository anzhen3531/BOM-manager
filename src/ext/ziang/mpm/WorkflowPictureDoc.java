package ext.ziang.mpm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.hutool.core.util.StrUtil;
import ext.ziang.common.helper.WTPathHelper;

/**
 * 工作流图片文档
 *
 * @author anzhen
 * @date 2024/05/23
 */
public class WorkflowPictureDoc {
	private static final Logger logger = LoggerFactory.getLogger(WorkflowPictureDoc.class);


	public static final String SCRIPT_PATH = WTPathHelper.HOME + WTPathHelper.VBS_SCRIPT_PATH + "createChart.vbs";

	public static void main(String[] args) {
		try {
			// VBS脚本路径
			String scriptPath = "D:\\project\\BOM-manager\\src\\ext\\ziang\\mpm\\vbs\\createChart.vbs";
			// 要传递的Excel文件路径
			String excelFilePath = "C:\\Users\\anzhen\\Desktop\\WorkflowPicTepm.xlsx";
			Map<String, String> map = new HashMap<>();
			List<String> arrayList = new ArrayList<>();
			arrayList.add("Material");
			for (int i = 0; i < 30; i++) {
				String s = "OP" + i;
				if (i == 10 || i == 14) {
					s = s + "(R)$F";
				}
				if (i == 5) {
					s = s + "|'D12312312";
				}
				arrayList.add(s);
			}
			arrayList.add("End");
			String latestOp = "o";
			for (String s : arrayList) {
				if (StrUtil.isNotBlank(latestOp)) {
					map.put(latestOp, s);
				}
				latestOp = s;
			}
			// 转换List和Map为字符串并进行URL编码
			String listString = arrayList.stream()
					.map(WorkflowPictureDoc::encode)
					.collect(Collectors.joining(","));
			String mapString = map.entrySet().stream()
					.map(entry -> encode(entry.getKey()) + "=" + encode(entry.getValue()))
					.collect(Collectors.joining(","));

			logger.debug("{}", "mapString = " + mapString);
			logger.debug("{}", "listString = " + listString);
			String command = String.format("cscript //NoLogo \"%s\" \"%s\" \"%s\" \"%s\"",
					scriptPath, excelFilePath, listString, mapString);
			logger.debug("{}", "Command: " + command);
			Process process = Runtime.getRuntime().exec(command);
			Charset charset = Charset.forName("GBK");
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), charset));
			String line;
			while ((line = reader.readLine()) != null) {
				logger.debug("{}", "Handler Excel  Info" + line);
			}
			BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream(), charset));
			while ((line = errorReader.readLine()) != null) {
				System.err.println("Handler Excel  Error" + line);
			}
			// 等待脚本执行完毕
			int exitCode = process.waitFor();
			logger.debug("{}", "Process exited with code: " + exitCode);
			// 检查退出代码
			if (exitCode == 0) {
				logger.debug("{}", "脚本执行成功。");
			} else {
				System.err.println("脚本执行失败，退出代码：" + exitCode);
			}
			reader.close();
			errorReader.close();
		} catch (Exception e) {
			logger.error("Unexpected error", e);
		}
	}

	/**
	 * 处理程序 Excel 工作流 PIC
	 *
	 * @param dataMap
	 *            数据映射
	 */
	public static void handlerExcelWorkflowPic(Map<String, Object> dataMap) {
		try {
			// 要传递的Excel文件路径
			String excelFilePath = (String) dataMap.get("filePath");
			ArrayList<String> opLabelList = (ArrayList<String>) dataMap.get("opLabelList");
			Map<String, String> map = new HashMap<>();
			opLabelList.add(0, "Material");
			opLabelList.add("End");
			String latestOp = "";
			// 处理相关的链接线路
			for (String label : opLabelList) {
				if (StrUtil.isNotBlank(latestOp)) {
					map.put(latestOp, label);
				}
				latestOp = label;
			}
			// 转换List和Map为字符串并进行URL编码
			String listString = opLabelList.stream()
					.map(WorkflowPictureDoc::encode)
					.collect(Collectors.joining(","));
			String mapString = map.entrySet().stream()
					.map(entry -> encode(entry.getKey()) + "=" + encode(entry.getValue()))
					.collect(Collectors.joining(","));
			logger.debug("{}", "mapString = " + mapString);
			logger.debug("{}", "listString = " + listString);
			String command = String.format("cscript //NoLogo \"%s\" \"%s\" \"%s\" \"%s\"",
					SCRIPT_PATH, excelFilePath, listString, mapString);
			logger.debug("{}", "Command: " + command);
			Process process = Runtime.getRuntime().exec(command);
			Charset charset = Charset.forName("GBK");
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), charset));
			String line;
			while ((line = reader.readLine()) != null) {
				logger.debug("{}", "Handler Excel  Info" + line);
			}
			BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream(), charset));
			while ((line = errorReader.readLine()) != null) {
				System.err.println("Handler Excel  Error" + line);
			}
			// 等待脚本执行完毕
			int exitCode = process.waitFor();
			logger.debug("{}", "Process exited with code: " + exitCode);
			// 检查退出代码
			if (exitCode == 0) {
				logger.debug("{}", "脚本执行成功。");
			} else {
				System.err.println("脚本执行失败，退出代码：" + exitCode);
			}
			reader.close();
			errorReader.close();
		} catch (Exception e) {
			logger.error("Unexpected error", e);
		}
	}

	/**
	 * 编码
	 *
	 * @param value
	 *            价值
	 * @return {@code String }
	 *
	 */
	private static String encode(String value) {
		try {
			return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("UTF-8 encoding not supported", e);
		}
	}

}
