package ext.ziang.histrory.history;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ext.ziang.common.util.IBAUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.hutool.core.util.StrUtil;
import ext.ziang.histrory.entity.ImportHistoryWTPartBean;
import wt.fc.ObjectReference;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.inf.library.WTLibrary;
import wt.pdmlink.PDMLinkProduct;
import wt.query.KeywordExpression;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionServerHelper;
import wt.util.WTException;

/**
 * 导入历史记录 WTPart 服务
 *
 * @author anzhen
 * @date 2024/03/20
 * windchill ext.ziang.histrory.history.ImportHistoryWTPartService
 */
public class ImportHistoryWTPartService {

	static HashMap<String, String> lifeCycle = new HashMap<>();
	static HashMap<String, String> classifyType = new HashMap<>();
	static HashMap<String, String> classifyTypePath = new HashMap<>();

	static {
		lifeCycle.put("设计阶段A", "编制中");
		lifeCycle.put("验证阶段B", "验证结束");
		lifeCycle.put("量产阶段C", "量产");
		classifyType.put("E", "电气件库");
		classifyType.put("M", "结构件库");
		classifyType.put("P", "包装件库");
		classifyType.put("C", "电子件库");
		classifyType.put("A", "辅材库");
		classifyTypePath.put("辅材库", "/Default/01 辅材");
		classifyTypePath.put("结构件库", "/Default/01 结构件");
		classifyTypePath.put("包装件库", "/Default/01 包装件");
		classifyTypePath.put("电子件库", "/Default/01 电子件");
		classifyTypePath.put("电气件库", "/Default/01 电气件");
	}

	public static void main(String[] args) throws Exception {
		// 读取excel
		createPartByExcelAllSheet(".\\历史物料属性导入1.xlsx", true);
	}

	/**
	 * 通过 Excel 创建零件 所有工作表
	 *
	 * @param path
	 *            路径
	 * @param flag
	 *            旗
	 * @throws IOException
	 *             io异常
	 */
	public static void createPartByExcelAllSheet(String path, boolean flag) throws Exception {
		// 读取excel'
		XSSFWorkbook workbook = new XSSFWorkbook(path);
		int numberOfSheets = workbook.getNumberOfSheets();
		for (int i = 0; i < numberOfSheets; i++) {
			XSSFSheet sheetAt = workbook.getSheetAt(i);
			if (flag) {
				// 读取到 Excel
				List<ImportHistoryWTPartBean> list = readTOExcelToSheet(sheetAt);
				System.out.println(list);
				// 批量创建部件-】
				for (ImportHistoryWTPartBean importHistoryWTPartBean : list) {
					createPart(importHistoryWTPartBean);
				}
				flag = false;
			}
		}
	}

	/**
	 * 读取 ToExcel 到工作表
	 *
	 * @param sheet
	 *            表
	 * @return {@link List}<{@link ImportHistoryWTPartBean}>
	 */
	public static List<ImportHistoryWTPartBean> readTOExcelToSheet(Sheet sheet) throws Exception {
		List<ImportHistoryWTPartBean> list = new ArrayList<>();
		// 获取标题
		Row row = sheet.getRow(0);
		HashMap<Integer, String> titleMap = new HashMap<>();
		int physicalNumberOfCells = row.getPhysicalNumberOfCells();
		for (int j = 7; j < physicalNumberOfCells; j++) {
			titleMap.put(j, row.getCell(j).getStringCellValue());
		}

		for (int rowIndex = 1; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++) {
			Row dataRow = sheet.getRow(rowIndex);
			// 获取数据
			ImportHistoryWTPartBean bean = new ImportHistoryWTPartBean();
			bean.setClassify(handlerCellValue(dataRow, 0).replace("-", ""));
			bean.setNumber(handlerCellValue(dataRow, 0) + handlerCellValue(dataRow, 1));
			bean.setName(handlerCellValue(dataRow, 2));
			bean.setVersion(handlerCellValue(dataRow, 3));
			bean.setUnit(handlerCellValue(dataRow, 4));
			// 判断是否是自制还是外购
			bean.setPartType(handlerCellValue(dataRow, 5));
			bean.setDescription(handlerCellValue(dataRow, 6));
			bean.setOidERPNumber(handlerCellValue(dataRow, 7));
			// TODO 定义类型
			bean.setType(handlerPartType(handlerCellValue(dataRow, 1)));
			HashMap<String, String> ibaMapping = new HashMap<>();
			for (int cellIndex = 8; cellIndex < physicalNumberOfCells; cellIndex++) {
				String value = handlerCellValue(dataRow, cellIndex);
				if (StrUtil.isNotBlank(value)) {
					String title = titleMap.get(cellIndex);
					String ibaName = title.substring(title.lastIndexOf("(") + 1, title.length() - 1);
					System.out.println("ibaName = " + ibaName);
					ibaMapping.put(ibaName, value);
				}
			}
			// TODO 定义容器
			handlerPartContainer(bean, ibaMapping);
			bean.setIbaMapping(ibaMapping);
		}
		return list;
	}

	/**
	 * 处理程序部件容器
	 *
	 * @param bean
	 *            豆
	 * @param ibaMapping
	 *            IBA 映射
	 */
	private static void handlerPartContainer(ImportHistoryWTPartBean bean, HashMap<String, String> ibaMapping)
			throws Exception {
		// 存在项目编码则创建在产品库（通过项目编码查询产品库），
		// 放置07ProductBOM中，生命周期阶段根据产品库中阶段属性来进行判断 状态根据产品库获取 lifeCycle直接从map中获取
		// 不存在 根据 放入存储库 并且找到对应的位置 状态为量产
		if (ibaMapping.containsKey("XMBH")) {
			// 需要转换Oid
			String projectCode = ibaMapping.get("XMBH");
			PDMLinkProduct pdmlinkProduct = findProductByName(projectCode);
			String locationPath = "/Default/07 Product BOM";
			String ibaValue = IBAUtils.getIBAValue(pdmlinkProduct, "");
			String lifeCycleState = lifeCycle.get(ibaValue);
			bean.setLifeCycleState(lifeCycleState);
			bean.setLocationPath(locationPath);
			bean.setContainer(getOid(pdmlinkProduct));
		} else {
			// 通过分类查找对应的产品库
			String classify = classifyType.get(bean.getClassify().substring(0, 1));
			WTLibrary library = findLibraryByName(classify);
			String locationPath = classifyTypePath.get(classify);
			bean.setLocationPath(locationPath);
			bean.setContainer(getOid(library));
			bean.setLifeCycleState("量产");
		}
	}

	/**
	 * 处理程序部件类型
	 *
	 * @param classify
	 *            分类
	 * @return {@link String}
	 */
	private static String handlerPartType(String classify) {
		// 需要设置为部件类型
		if (classify.contains("A")) {
			return "辅料";
		} else if (classify.contains("C")) {
			return "电子件";
		} else if (classify.contains("E")) {
			return "电气件";
		} else if (classify.contains("M")) {
			return "结构件";
		} else if (classify.contains("P")) {
			return "包装类";
		} else if (classify.contains("S")) {
			return "软件";
		} else if (classify.contains("F")) {
			return "成品";
		}
		return null;
	}

	/**
	 * 创建零件
	 *
	 * @param bean
	 *            豆
	 */
	public static void createPart(ImportHistoryWTPartBean bean) {

	}

	private static String handlerCellValue(Row dataRow, int i) {
		if (dataRow.getCell(i) != null) {
			return dataRow.getCell(i).getStringCellValue();
		} else {
			return "";
		}
	}

	// 查询产品库接口

	/**
	 * 根据容器得到容器的文件夹
	 *
	 * @return 产品库
	 */
	public static PDMLinkProduct findProductByName(String cProductFamily) {
		PDMLinkProduct product = null;
		boolean access = SessionServerHelper.manager.setAccessEnforced(false);
		try {
			QuerySpec qs = new QuerySpec(PDMLinkProduct.class);
			SearchCondition namecontainerinfo = new SearchCondition(new KeywordExpression("A0.NAMECONTAINERINFO"),
					SearchCondition.LIKE, new KeywordExpression("'" + cProductFamily + "'"));
			qs.appendWhere(namecontainerinfo, new int[] { 0 });
			System.out.println("查询产品库 = " + qs);
			QueryResult qr = PersistenceHelper.manager.find(qs);
			if (qr.hasMoreElements()) {
				product = (PDMLinkProduct) qr.nextElement();
			}
		} catch (WTException e) {
			e.printStackTrace();
		} finally {
			SessionServerHelper.manager.setAccessEnforced(access);
		}
		return product;
	}

	// 查询存储库接口

	/**
	 * 根据容器得到容器的文件夹
	 *
	 * @return 产品库
	 */
	public static WTLibrary findLibraryByName(String cProductFamily) {
		WTLibrary library = null;
		boolean access = SessionServerHelper.manager.setAccessEnforced(false);
		try {
			QuerySpec qs = new QuerySpec(WTLibrary.class);
			SearchCondition namecontainerinfo = new SearchCondition(new KeywordExpression("A0.NAMECONTAINERINFO"),
					SearchCondition.LIKE, new KeywordExpression("'" + cProductFamily + "'"));
			qs.appendWhere(namecontainerinfo, new int[] { 0 });
			System.out.println("查询产品库 = " + qs);
			QueryResult qr = PersistenceHelper.manager.find(qs);
			if (qr.hasMoreElements()) {
				library = (WTLibrary) qr.nextElement();
			}
		} catch (WTException e) {
			e.printStackTrace();
		} finally {
			SessionServerHelper.manager.setAccessEnforced(access);
		}
		return library;
	}

	public static String getOid(Persistable persistable) throws WTException {
		ReferenceFactory refFactory = new ReferenceFactory();
		return refFactory.getReferenceString(
				ObjectReference.newObjectReference((persistable.getPersistInfo().getObjectIdentifier())));
	}
}
