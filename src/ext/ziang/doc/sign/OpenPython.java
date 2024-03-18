package ext.ziang.doc.sign;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * 打开 Python 调用签字服务
 *
 * @author anzhen
 * @date 2024/03/17
 */
public class OpenPython {

	private static final String EVN = "LOCAL";
	/**
	 * python exe 路径
	 */
	private static String PYTHON_EXE_PATH = "D:\\python\\pytho3.11.8\\python.exe";
	/**
	 * Python Word 签名脚本路径
	 */
	private static String PYTHON_WORD_SIGN_SCRIPT_PATH = "D:\\pythonProject\\handlerWordToDocx\\main.py";

	static {
		if ("LOCAL".equals(EVN)) {
			PYTHON_EXE_PATH = "C:\\python\\python.exe";
			PYTHON_WORD_SIGN_SCRIPT_PATH = "C:\\ptc\\Windchill_11.0\\Windchill\\src\\ext\\ziang\\doc\\sign\\main.py";
		} else if ("PRD".equals(EVN)) {
			PYTHON_EXE_PATH = "C:\\python\\python.exe";
			PYTHON_WORD_SIGN_SCRIPT_PATH = "D:\\pythonProject\\handlerWordToDocx\\main.py";
		}
	}

	public static void main(String args[]) {
		// 浮动参数
		String path = "D:\\project\\BOM-manager\\src\\ext\\ziang\\doc\\sign\\main.py";
		List<String> argg = new ArrayList<>();
		// 浮动参数
		Hashtable hashTable = new Hashtable<>();
		hashTable.put("审核", "admin 2023-02-01");
		hashTable.put("编制", "admin 2023-02-01");
		hashTable.put("批准", "admin 2023-02-01");
		hashTable.put("制作", "admin 2023-02-01");
		String signKey = hashTable.toString();
		System.out.println("signKey = " + signKey);
		argg.add("D:\\pythonProject\\handlerWordToDocx\\temp\\testDoc.docx");
		argg.add("D:\\pythonProject\\handlerWordToDocx\\temp\\newTestDoc.docx");
		argg.add(signKey);
		// 增加签名参数
		OpenPython open = new OpenPython(path, argg);
		Integer run = open.run();
		System.out.println("run = " + run);
		if (run == -1) {
			System.out.println("run py script failed code = " + run);
		}
		List<String> result = open.getResult();
		System.out.println("result = " + result);
	}

	/**
	 * 处理Word签名通过 Python 脚本
	 *
	 * @param selfFilePath
	 *            self 文件路径
	 * @param table
	 *            signInfo
	 * @param targetFilePath
	 *            目标文件路径
	 * @return {@link String}
	 */
	public static String handlerWordSignToPython(String selfFilePath, Hashtable table, String targetFilePath) {
		List<String> argg = new ArrayList<>();
		Hashtable hashTable = new Hashtable<>();
		String signKey = hashTable.toString();
		System.out.println("signKey = " + signKey);
		argg.add(selfFilePath);
		argg.add(targetFilePath);
		argg.add(table.toString());
		OpenPython open = new OpenPython(PYTHON_WORD_SIGN_SCRIPT_PATH, argg);
		Integer run = open.run();
		System.out.println("run = " + run);
		if (run == -1) {
			System.out.println("run py script failed code = " + run);
			return null;
		}
		List<String> result = open.getResult();
		if (result.size() == 1) {
			return result.get(0);
		}
		return null;
	}

	/**
	 * 路径
	 */
	private String path;
	/**
	 * 参数
	 */
	private List<String> args = new ArrayList<>();
	/**
	 * 结果
	 */
	private List<String> result = new ArrayList<>();

	/**
	 * 打开 Python
	 *
	 * @param path
	 *            路径
	 * @param args
	 *            参数
	 */
	OpenPython(String path, List<String> args) {
		this.path = path;
		this.args = args;
	}

	/**
	 * 参数组装
	 * 
	 * @return {@link String[]}
	 */
	private String[] parameterCombine() {
		String[] argg = new String[2 + args.size()];
		argg[0] = PYTHON_EXE_PATH;
		argg[1] = path;
		for (int i = 0; i < args.size(); i++) {
			argg[2 + i] = args.get(i);
		}
		return argg;
	}

	/**
	 * 运行文件
	 */
	public Integer run() {
		try {
			Process pr = Runtime.getRuntime().exec(parameterCombine());
			pr.waitFor();
			BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result.add(line);
			}
			return pr.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 获取返回值
	 * 
	 * @return {@link List}<{@link String}>
	 */
	public List<String> getResult() {
		return this.result;
	}

}
