package ext.ziang.mpm;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ptc.windchill.mpml.processplan.MPMProcessPlan;
import com.ptc.windchill.mpml.processplan.operation.MPMOperationUsageLink;

import ext.ziang.common.constants.PathConstants;
import ext.ziang.common.helper.WTPathHelper;
import ext.ziang.common.helper.mpm.MPMCustomHelper;
import ext.ziang.common.util.ToolUtils;
import wt.util.WTException;

/**
 * 导出工作流程文档
 *
 * @author ander
 * @date 2024/05/23
 */
public class ExportWorkFlowDoc {

	public static final Integer startWriteOpIndex = 8;
	public static final Integer endWriteOpIndex = 25;

	/**
	 * 收集数据
	 *
	 * @param mpmProcessPlan
	 *            MPM工艺计划
	 * @return {@link Map }<{@link String }, {@link Object }>
	 */
	public static Map<String, Object> collectData(MPMProcessPlan mpmProcessPlan) throws WTException {
		// 获取所有的信息
		List<MPMOperationUsageLink> operationLinkList = MPMCustomHelper.findChildOperationList(mpmProcessPlan);
		// 控制工艺计划的操作
		Map<String, Object> dataMap = new HashMap<>();
		HashMap<String, String> labelAndOpMapping = new HashMap<>();
		List<String> opLabelList = operationLinkList.stream().map(link -> {
			if (link != null) {
				String operationLabel = link.getOperationLabel();
				String operationName = link.getOperationName();
				labelAndOpMapping.put(operationLabel, operationName);
				return link.getOperationLabel();
			}
			return null;
		}).filter(Objects::nonNull).collect(Collectors.toList());
		dataMap.put("opLabelList", opLabelList);
		dataMap.put("opLabelMap", labelAndOpMapping);
		return dataMap;
	}

	/**
	 * 创建文档
	 *
	 * @param oid
	 *            oid
	 * @return {@link String } 文件路径
	 */
	public static String createExcel(String oid) throws WTException {
		FileOutputStream os = null;
		FileInputStream input = null;
		String newPath = "";
		try {
			// 获取工艺计划
			MPMProcessPlan plan = (MPMProcessPlan) ToolUtils.getObjectByOid(oid);
			// 获取工艺计划需要导出的装配物料清单数据
			Map<String, Object> dataMap = collectData(plan);
			String excelPath = handlerDataWriteExcel(plan, dataMap);
			// 通过类型获取默认这个图文档是否存在
			System.out.println("excelPath = " + excelPath);
			// WTDocument describeDoc = MPMCustomHelper.getDocDescribeOfProcessPlan(plan,
			// "");
			// if (describeDoc == null) {
			// // 创建文档并设置内容
			// } else {
			// // 导出文档更新内容即可
			// }
		} catch (Exception e) {
			e.printStackTrace();
			throw new WTException(e.getMessage());
		} finally {
			try {
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (input != null) {
					input.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return newPath;
	}

	/**
	 * 处理程序数据写入 Excel
	 *
	 * @param plan
	 *            计划
	 * @param map
	 *            地图
	 * @return {@code String }
	 */
	public static String handlerDataWriteExcel(MPMProcessPlan plan, Map<String, Object> map) {
		// 获取导出的文件信息
		String filePath = WTPathHelper.TEMP + File.separator + plan.getNumber() + "GKT";
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		String docName = plan.getName() + "_工序卡";
		String newPath = filePath + File.separator + docName + ".xlsx";
		System.out.println("newPath = " + newPath);
		File oldfile = new File(newPath);
		if (oldfile != null) {
			oldfile.delete();
		}
		try {
			OutputStream os = new FileOutputStream(newPath);
			InputStream input = new FileInputStream(PathConstants.WORKFLOW_TEMPLATE_PATH);
			XSSFWorkbook workBook = new XSSFWorkbook(new BufferedInputStream(input));
			handlerExcelData(workBook, map);
			workBook.write(os);
			os.flush();
			map.put("filePath", newPath);
			handlerWorkflowPictureExcel(map);
			return newPath;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 处理程序 Excel 数据
	 *
	 * @param workBook
	 *            工作簿
	 * @param dataMap
	 *            数据映射
	 */
	private static void handlerExcelData(XSSFWorkbook workBook, Map<String, Object> dataMap) {
		HashMap<String, String> opLabelMapping = (HashMap<String, String>) dataMap.get("opLabelMap");
		// 获取起始位置之后进行处理
		XSSFSheet sheet = workBook.getSheetAt(0);
		// 确定数据总行数
		Set<String> opLabelList = opLabelMapping.keySet();
		int size = opLabelList.size();
		// 判断是否需要进行扩容
		int tempSize = endWriteOpIndex - startWriteOpIndex;
		System.out.println("tempSize = " + tempSize);
		if (size > tempSize) {
			for (int i = 1; i <= size - tempSize; i++) {
				if (sheet != null) {
					Row row = getRow(sheet, startWriteOpIndex);
					copyRow(sheet, row, sheet.createRow(startWriteOpIndex + i));
				}
			}
		}
		//
		if (sheet != null) {
			int index = startWriteOpIndex;
			opLabelMapping.forEach((key, value) -> {
				Row row = getRow(sheet, index);
				getCell(row, 0).setCellValue(key);
				getCell(row, 1).setCellValue(value);
			});
		}
	}

	/**
	 * 处理程序工作流图片 Excel
	 *
	 * @param map
	 *            地图
	 */
	private static void handlerWorkflowPictureExcel(Map<String, Object> map) {
		WorkflowPictureDoc.handlerExcelWorkflowPic(map);
	}

	/**
	 * 获取行
	 *
	 * @param sheet
	 *            表
	 * @param rowIndex
	 *            行索引
	 * @return {@link Row }
	 */
	public static Row getRow(Sheet sheet, Integer rowIndex) {
		Row row = sheet.getRow(rowIndex);
		if (row == null) {
			// 创建一行
			return sheet.createRow(rowIndex);
		}
		return row;
	}

	/**
	 * 获取行
	 *
	 * @param row
	 *            行
	 * @param cellIndex
	 *            单元格索引
	 * @return {@link Row }
	 */
	public static Cell getCell(Row row, Integer cellIndex) {
		Cell rowCell = row.getCell(cellIndex);
		if (rowCell == null) {
			// 创建一行
			return row.createCell(cellIndex);
		}
		return rowCell;
	}

	/**
	 * 复制行
	 *
	 * @param sheet
	 *            表
	 * @param sourceRow
	 *            源行
	 * @param targetRow
	 *            目标行
	 */
	private static void copyRow(Sheet sheet, Row sourceRow, Row targetRow) {
		for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
			Cell sourceCell = sourceRow.getCell(i);
			Cell targetCell = targetRow.createCell(i);
			if (sourceCell != null) {
				copyCell(sourceCell, targetCell);
			}
		}
		// 复制行高
		targetRow.setHeight(sourceRow.getHeight());
	}

	/**
	 * 复制单元格
	 *
	 * @param sourceCell
	 *            源单元格
	 * @param targetCell
	 *            目标单元格
	 */
	private static void copyCell(Cell sourceCell, Cell targetCell) {
		// 复制单元格样式
		CellStyle newCellStyle = targetCell.getSheet().getWorkbook().createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		targetCell.setCellStyle(newCellStyle);
		// 复制单元格内容
		switch (sourceCell.getCellType()) {
			case STRING:
				targetCell.setCellValue(sourceCell.getStringCellValue());
				break;
			case NUMERIC:
				if (DateUtil.isCellDateFormatted(sourceCell)) {
					targetCell.setCellValue(sourceCell.getDateCellValue());
				} else {
					targetCell.setCellValue(sourceCell.getNumericCellValue());
				}
				break;
			case BOOLEAN:
				targetCell.setCellValue(sourceCell.getBooleanCellValue());
				break;
			case FORMULA:
				targetCell.setCellFormula(sourceCell.getCellFormula());
				break;
			case BLANK:
				targetCell.setBlank();
				break;
			default:
				break;
		}
	}
}
