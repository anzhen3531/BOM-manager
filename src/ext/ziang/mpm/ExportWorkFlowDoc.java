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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
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
		}).filter(Objects::nonNull).sorted().collect(Collectors.toList());
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
			for (int i = 0; i < (size - tempSize - 1); i++) {
				if (sheet != null) {
					sheet.shiftRows(endWriteOpIndex, sheet.getLastRowNum(), 1);
					copyRowStylesAndMergedRegions(sheet, startWriteOpIndex, endWriteOpIndex);
				}
			}
		}

		if (sheet != null) {
			int index = startWriteOpIndex;
			for (String key : opLabelMapping.keySet()) {
				Row row = getRow(sheet, index);
				getCell(row, 0).setCellValue(key);
				getCell(row, 1).setCellValue(opLabelMapping.get(key));
				index += 1;
			}
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
	 * 复制行样式和合并区域
	 *
	 * @param sheet
	 *            表
	 * @param sourceRowIndex
	 *            源行索引
	 * @param targetRowIndex
	 *            目标行索引
	 */
	private static void copyRowStylesAndMergedRegions(XSSFSheet sheet, int sourceRowIndex, int targetRowIndex) {
		Row sourceRow = sheet.getRow(sourceRowIndex);
		Row targetRow = getRow(sheet, targetRowIndex);
		if (sourceRow == null) {
			return;
		}
		for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
			Cell oldCell = sourceRow.getCell(i);
			Cell newCell = targetRow.createCell(i);
			if (oldCell == null) {
				continue;
			}
			copyCellStyle(oldCell, newCell);
		}
		copyMergedRegions(sheet, sourceRowIndex, targetRowIndex);
	}

	/**
	 * 复制单元格样式
	 *
	 * @param oldCell
	 *            旧单元格
	 * @param newCell
	 *            新电池
	 */
	private static void copyCellStyle(Cell oldCell, Cell newCell) {
		Workbook workbook = oldCell.getSheet().getWorkbook();
		CellStyle newCellStyle = workbook.createCellStyle();
		newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
		newCell.setCellStyle(newCellStyle);
	}

	/**
	 * 复制合并区域
	 *
	 * @param sheet
	 *            表
	 * @param sourceRowIndex
	 *            源行索引
	 * @param targetRowIndex
	 *            目标行索引
	 */
	private static void copyMergedRegions(Sheet sheet, int sourceRowIndex, int targetRowIndex) {
		for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
			CellRangeAddress cellRangeAddress = sheet.getMergedRegion(i);
			if (cellRangeAddress.getFirstRow() == sourceRowIndex) {
				CellRangeAddress newCellRangeAddress = new CellRangeAddress(
						targetRowIndex,
						targetRowIndex + (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow()),
						cellRangeAddress.getFirstColumn(),
						cellRangeAddress.getLastColumn());
				sheet.addMergedRegion(newCellRangeAddress);
			}
		}
	}
}
