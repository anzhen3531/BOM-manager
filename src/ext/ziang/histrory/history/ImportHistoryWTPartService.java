package ext.ziang.histrory.history;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import ext.ziang.common.util.IBAUtils;
import ext.ziang.histrory.entity.ImportHistoryWTPartBean;
import wt.fc.ObjectIdentifier;
import wt.fc.ObjectReference;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.fc.collections.WTValuedHashMap;
import wt.folder.Folder;
import wt.folder.FolderHelper;
import wt.inf.container.WTContainerRef;
import wt.inf.library.WTLibrary;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleTemplate;
import wt.part.PartType;
import wt.part.QuantityUnit;
import wt.part.Source;
import wt.part.WTPart;
import wt.pdmlink.PDMLinkProduct;
import wt.pom.Transaction;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.rule.init.InitRuleHelper;
import wt.session.SessionServerHelper;
import wt.type.TypeDefinitionReference;
import wt.type.TypedUtility;
import wt.util.WTException;
import wt.vc.VersionControlHelper;
import wt.vc.Versioned;
import wt.vc.views.ViewHelper;

/**
 * 导入历史记录 WTPart 服务
 *
 * @author anzhen
 * @date 2024/03/20
 *       windchill ext.ziang.histrory.history.ImportHistoryWTPartService
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
		createPartByExcelAllSheet(
				"C:\\ptc\\Windchill_11.0\\Windchill\\src\\ext\\ziang\\histrory\\history\\历史物料属性导入1.xlsx");
	}

	/**
	 * 通过 Excel 创建零件 所有工作表
	 *
	 * @param path
	 *            路径
	 * @throws IOException
	 *             io异常
	 */
	public static void createPartByExcelAllSheet(String path) throws Exception {
		// 读取excel'
		XSSFWorkbook workbook = new XSSFWorkbook(path);
		List<String> errorList = new ArrayList<>();
		int numberOfSheets = workbook.getNumberOfSheets();
		for (int i = 0; i < numberOfSheets; i++) {
			XSSFSheet sheetAt = workbook.getSheetAt(i);
			// 读取到 Excel
			List<ImportHistoryWTPartBean> list = readTOExcelToSheet(sheetAt);
			System.out.println(list);
			// 批量创建部件-】
			for (ImportHistoryWTPartBean importHistoryWTPartBean : list) {
				boolean createFlag = createPart(importHistoryWTPartBean);
				if (createFlag) {
					System.out.println("创建当前对象成功! -> " + importHistoryWTPartBean);
				} else {
					errorList.add("创建当前对象失败! -> " + importHistoryWTPartBean);
				}
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
		if (row == null) {
			return list;
		}
		int physicalNumberOfCells = row.getPhysicalNumberOfCells();
		for (int j = 7; j < physicalNumberOfCells; j++) {
			Cell cell = row.getCell(j);
			if (cell == null) {
				continue;
			}
			titleMap.put(j, cell.getStringCellValue());
		}

		for (int rowIndex = 1; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++) {
			Row dataRow = sheet.getRow(rowIndex);
			if (dataRow == null) {
				continue;
			} else if (dataRow.getCell(0) == null) {
				continue;
			}
			// 获取数据
			ImportHistoryWTPartBean bean = new ImportHistoryWTPartBean();
			bean.setClassify(handlerCellValue(dataRow, 0).replace("-", ""));
			bean.setNumber(handlerCellValue(dataRow, 0) + handlerCellValue(dataRow, 1));
			bean.setName(handlerCellValue(dataRow, 2));
			bean.setVersion(handlerCellValue(dataRow, 3));
			bean.setUnit(handlerCellValue(dataRow, 4));
			bean.setPartType(handlerCellValue(dataRow, 5));
			bean.setDescription(handlerCellValue(dataRow, 6));
			bean.setOidERPNumber(handlerCellValue(dataRow, 7));
			bean.setType(handlerPartType(handlerCellValue(dataRow, 0)));
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
			handlerPartContainer(bean, ibaMapping);
			bean.setIbaMapping(ibaMapping);
			list.add(bean);
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
		if (ibaMapping.containsKey("SWXMH")) {
			// 需要转换Oid
			String projectCode = ibaMapping.get("SWXMH");
			PDMLinkProduct pdmlinkProduct = findProductByName(projectCode);
			if (pdmlinkProduct != null) {
				String ibaValue = IBAUtils.getIBAValue(pdmlinkProduct, "ProductPhase");
				String lifeCycleState = lifeCycle.get(ibaValue);
				bean.setLifeCycleState(lifeCycleState);
				bean.setContainer(getOid(pdmlinkProduct));
				return;
			}
			String locationPath = "/Default/07 Product BOM";
			bean.setLocationPath(locationPath);
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
		// 需要设置为部件类型 真实物料类型
		if (classify.contains("A")) {
			return "com.soarwhale.AuxiliaryMaterials";
		} else if (classify.contains("C")) {
			return "com.ptc.ElectricalPart";
		} else if (classify.contains("E")) {
			return "com.soarwhale.ElectricalComponents";
		} else if (classify.contains("M")) {
			return "com.soarwhale.Structure";
		} else if (classify.contains("P")) {
			return "com.soarwhale.Packaging";
		} else if (classify.contains("S")) {
			return "com.soarwhale.SoftWare";
		} else if (classify.contains("F")) {
			return "com.soarwhale.FinishProuct";
		}
		return null;
	}

	private static String handlerCellValue(Row dataRow, int i) {
		if (dataRow == null) {
			return "";
		}
		if (dataRow.getCell(i) != null) {
			return getCellValue(dataRow.getCell(i));
		} else {
			return "";
		}
	}

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
			SearchCondition namecontainerinfo = new SearchCondition(
					PDMLinkProduct.class, PDMLinkProduct.DESCRIPTION,
					SearchCondition.LIKE, cProductFamily);
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
			SearchCondition namecontainerinfo = new SearchCondition(
					WTLibrary.class, WTLibrary.NAME,
					SearchCondition.LIKE, cProductFamily);
			qs.appendWhere(namecontainerinfo, new int[] { 0 });
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

	public static String getCellValue(Cell cell) {
		if (cell == null) {
			return "";
		}
		if (cell.getCellType().equals(CellType.STRING)) {// 字符串
			return cell.getStringCellValue();
		} else if (cell.getCellType().equals(CellType.NUMERIC)) {// 数值
			if (DateUtil.isCellDateFormatted(cell)) {// 日期类型
				return cell.getDateCellValue().toString();
			} else {// 数值类型
				return String.valueOf(cell.getNumericCellValue());
			}
		} else if (cell.getCellType().equals(CellType.BOOLEAN)) {// 布尔类型
			return cell.getBooleanCellValue() ? "TRUE" : "FALSE";
		} else {
			return "";
		}
	}

	/**
	 * 创建部分
	 *
	 * @param bean
	 *            创建bean
	 */
	public static synchronized boolean createPart(ImportHistoryWTPartBean bean) {
		Transaction tx = new Transaction();// 事务
		try {
			tx.start();
			WTPart part = WTPart.newWTPart(bean.getNumber(), bean.getName(), getQuantityUnit(bean.getUnit()));
			// 结构标准件等等
			if (bean.getType() != null) {
				TypeDefinitionReference tdRef = TypedUtility.getTypeDefinitionReference(bean.getType());
				part.setTypeDefinitionReference(tdRef);
			}
			// 设置容器 为部件库
			ObjectIdentifier oid = ObjectIdentifier.newObjectIdentifier(bean.getContainer());
			WTContainerRef containerRef = WTContainerRef.newWTContainerRef(oid);
			Folder folder = FolderHelper.service.getFolder(bean.getLocationPath(), containerRef);
			part.setContainerReference(containerRef);
			WTValuedHashMap map = new WTValuedHashMap();
			map.put(part, folder);
			FolderHelper.assignLocations(map);
			part.setPartType(PartType.SEPARABLE);
			part.setSource(Source.MAKE);
			// 设置为D视图
			ViewHelper.assignToView(part, ViewHelper.service.getView("Design"));
			part.setEndItem(false);
			LifeCycleTemplate lifecycleTemplate = (LifeCycleTemplate) InitRuleHelper.evaluator.getValue("lifeCycle.id",
					part, containerRef);
			if (lifecycleTemplate != null) {
				LifeCycleHelper.setLifeCycle(part, lifecycleTemplate);
			}
			PersistenceHelper.manager.save(part);
			// 设置基本属性
			IBAUtils.setIBAStringValue(part, "CLASSIFY", bean.getClassify());
			// 品牌
			IBAUtils.setIBAStringValue(part, "SpecificationModels", bean.getDescription());
			// 型号
			IBAUtils.setIBAStringValue(part, "ERP_OLD_NUMBER", bean.getOidERPNumber());
			// 材料
			IBAUtils.setIBAStringValue(part, "productType", bean.getPartType());
			HashMap<String, String> ibaMapping = bean.getIbaMapping();
			if (CollUtil.isNotEmpty(ibaMapping)) {
				ibaMapping.forEach((key, value) -> IBAUtils.setIBAStringValue(part, key, value));
			}
			tx.commit();
			// 逐步修订
			if (!bean.getVersion().equals("00")) {
				// 修订大版本
				Versioned versioned = VersionControlHelper.service.newVersion(part);
				PersistenceHelper.manager.save(versioned);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			return false;
		}
	}

	/**
	 * 获取单位
	 *
	 * @param unit
	 *            单位字符
	 * @return
	 */
	public static QuantityUnit getQuantityUnit(String unit) {
		QuantityUnit qu = null;
		if (!StringUtils.isEmpty(unit)) {
			QuantityUnit[] quantityUnitArray = QuantityUnit.getQuantityUnitSet();
			for (QuantityUnit quantityUnit : quantityUnitArray) {
				qu = quantityUnit;
				System.out.println("qu = " + qu);
				if (unit.equalsIgnoreCase(qu.toString())) {
					break;
				}
			}
		}
		return qu;
	}
}
