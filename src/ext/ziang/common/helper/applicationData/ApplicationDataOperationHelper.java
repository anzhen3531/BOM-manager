package ext.ziang.common.helper.applicationData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import com.ptc.core.query.common.QueryException;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import ext.ziang.common.helper.part.PartHelper;
import ext.ziang.common.util.ToolUtils;
import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.content.ContentServerHelper;
import wt.doc.WTDocument;
import wt.doc.WTDocumentMaster;
import wt.epm.EPMDocument;
import wt.epm.EPMDocumentMaster;
import wt.epm.structure.EPMMemberLink;
import wt.epm.structure.EPMReferenceLink;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.part.WTPart;
import wt.part.WTPartHelper;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.representation.Representation;
import wt.representation.RepresentationHelper;
import wt.util.WTException;
import wt.util.WTProperties;
import wt.vc.VersionControlHelper;
import wt.vc.config.OwnershipIndependentLatestConfigSpec;

public class ApplicationDataOperationHelper {

	private static String tempPath;
	private static WTProperties wtproperties;

	/**
	 * 标准路径
	 */
	static {
		try {
			wtproperties = WTProperties.getLocalProperties();
			tempPath = wtproperties.getProperty("wt.temp") + File.separator + "downloadOperation" + File.separator
					+ "download";
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 下载表示法PDF文件
	 *
	 * @param epmDoc
	 *            EPM文档
	 * @return
	 */
	public static String downloadRepresentationDoc(EPMDocument epmDoc, boolean is2d) {
		if (epmDoc == null) {
			return null;
		}
		String version = epmDoc.getVersionIdentifier().getValue() + "_"
				+ epmDoc.getIterationInfo().getIdentifier().getValue();
		String fileName;
		if (is2d) {
			fileName = epmDoc.getCADName().split("\\.")[0] + "_" + version + ".step";
		} else {
			fileName = epmDoc.getCADName().split("\\.")[0] + "_" + version + ".pdf";
		}
		String absoluteFileName = "";
		Representation representation;
		try {
			// 获取所有的表示法
			QueryResult representations = RepresentationHelper.service.getRepresentations(epmDoc);
			if (representations.size() < 1) {
				return null;
			}
			System.out.println("representations = " + representations);
			// 获取所有的表示法
			while (representations.hasMoreElements()) {
				representation = (Representation) representations.nextElement();
				ContentHolder holder = ContentHelper.service.getContents(representation);
				// 获取多个附件
				QueryResult qr = ContentHelper.service.getContentsByRole(holder, ContentRoleType.SECONDARY);
				if (qr == null) {
					return null;
				}
				System.out.println("qr.size() = " + qr.size());
				List<ApplicationData> applicationDataList = new ArrayList<>();
				while (qr.hasMoreElements()) {
					ApplicationData appData = (ApplicationData) qr.nextElement();
					applicationDataList.add(appData);
				}
				Optional<ApplicationData> applicationDataOptional = applicationDataList.stream()
						.sorted(Comparator.comparing(ApplicationData::getModifyTimestamp).reversed()).findFirst();
				System.out.println("applicationDataOptional = " + applicationDataOptional);
				if (applicationDataOptional.isPresent()) {
					String newPath = tempPath;
					File file = new File(newPath);
					if (!file.exists()) {
						file.mkdirs();
					}
					absoluteFileName = newPath + File.separator + fileName;
					ContentServerHelper.service.writeContentStream(applicationDataOptional.get(), absoluteFileName);
					return absoluteFileName;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return absoluteFileName;
	}

	/**
	 * 下载表示法PDF文件
	 *
	 * @param epmDoc
	 *            EPM文档
	 * @return
	 */
	public static String downloadRepresentationDoc(EPMDocument epmDoc, boolean is2d, String filepath) {
		String version = epmDoc.getVersionIdentifier().getValue() + "_"
				+ epmDoc.getIterationInfo().getIdentifier().getValue();
		String fileName;
		if (is2d) {
			fileName = epmDoc.getCADName().split("\\.")[0] + "_" + version + ".step";
		} else {
			fileName = epmDoc.getCADName().split("\\.")[0] + "_" + version + ".pdf";
		}
		String absoluteFileName = "";
		Representation representation;
		try {
			// 获取所有的表示法
			QueryResult representations = RepresentationHelper.service.getRepresentations(epmDoc);
			if (representations.size() < 1) {
				return null;
			}
			System.out.println("representations = " + representations);
			// 获取所有的表示法
			while (representations.hasMoreElements()) {
				representation = (Representation) representations.nextElement();
				ContentHolder holder = ContentHelper.service.getContents(representation);
				// 获取多个附件
				QueryResult qr = ContentHelper.service.getContentsByRole(holder, ContentRoleType.SECONDARY);
				if (qr == null) {
					return null;
				}
				System.out.println("qr.size() = " + qr.size());
				List<ApplicationData> applicationDataList = new ArrayList<>();
				while (qr.hasMoreElements()) {
					ApplicationData appData = (ApplicationData) qr.nextElement();
					applicationDataList.add(appData);
				}
				Optional<ApplicationData> applicationDataOptional = applicationDataList.stream()
						.sorted(Comparator.comparing(ApplicationData::getModifyTimestamp).reversed()).findFirst();
				System.out.println("applicationDataOptional = " + applicationDataOptional);
				if (applicationDataOptional.isPresent()) {
					File file = new File(filepath);
					if (!file.exists()) {
						file.mkdirs();
					}
					absoluteFileName = filepath + File.separator + fileName;
					ContentServerHelper.service.writeContentStream(applicationDataOptional.get(), absoluteFileName);
					return absoluteFileName;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return absoluteFileName;
	}

	/**
	 * 下载表示法PDF文件
	 *
	 * @param epmDoc
	 *            EPM文档
	 * @return
	 */
	public static String downloadSecondaryDoc(EPMDocument epmDoc) throws WTException {
		// 获取签名文件
		String version2d = epmDoc.getVersionIdentifier().getValue() + "_"
				+ epmDoc.getIterationInfo().getIdentifier().getValue();
		String fileName2d = epmDoc.getCADName().split("\\.")[0] + "_" + version2d + ".pdf";
		String absoluteFileName = "";
		try {
			ContentHolder holder = ContentHelper.service.getContents(epmDoc);
			QueryResult qr = ContentHelper.service.getContentsByRole(holder, ContentRoleType.SECONDARY);
			absoluteFileName = getFilePath(fileName2d, absoluteFileName, qr, tempPath);
		} catch (Exception e) {
			e.printStackTrace();
			throw new WTException(e);
		}
		return absoluteFileName;
	}

	/**
	 * 下载表示法PDF文件
	 *
	 * @param epmDoc
	 *            EPM文档
	 * @return
	 */
	public static String downloadSecondaryDoc(EPMDocument epmDoc, String filePath) throws WTException {
		// 获取签名文件
		String version2d = epmDoc.getVersionIdentifier().getValue() + "_"
				+ epmDoc.getIterationInfo().getIdentifier().getValue();
		String fileName2d = epmDoc.getCADName().split("\\.")[0] + "_" + version2d + ".pdf";
		String absoluteFileName = "";
		try {
			ContentHolder holder = ContentHelper.service.getContents(epmDoc);
			QueryResult qr = ContentHelper.service.getContentsByRole(holder, ContentRoleType.SECONDARY);
			absoluteFileName = getFilePath(fileName2d, absoluteFileName, qr, filePath);
		} catch (Exception e) {
			e.printStackTrace();
			throw new WTException(e);
		}
		return absoluteFileName;
	}

	/**
	 * 获取字符串
	 *
	 * @param fileName2d
	 *            文件名2D
	 * @param absoluteFileName
	 *            绝对文件名
	 * @param qr
	 *            二维码
	 * @param tempPath
	 *            临时路径
	 * @return {@link String }
	 * @throws WTException
	 *             WT异常
	 * @throws IOException
	 *             io异常
	 */
	public static String getFilePath(String fileName2d, String absoluteFileName, QueryResult qr, String tempPath)
			throws WTException, IOException {
		while (qr.hasMoreElements()) {
			Object objQr = qr.nextElement();
			if (objQr instanceof ApplicationData) {
				ApplicationData ad = (ApplicationData) objQr;
				String newPath = tempPath;
				File file = new File(newPath);
				if (!file.exists()) {
					file.mkdirs();
				}
				absoluteFileName = newPath + File.separator + fileName2d;
				ContentServerHelper.service.writeContentStream(ad, absoluteFileName);
			}
		}
		return absoluteFileName;
	}

	/**
	 * 下载表示法PDF文件
	 *
	 * @param document
	 *            公文
	 * @return
	 * @throws WTException
	 *             WT异常
	 */
	public static String downloadPrimaryDoc(WTDocument document) throws WTException {
		String absoluteFileName;
		try {
			ContentItem primaryContentItem = ContentHelper.service.getPrimary(document);
			String version2d = document.getVersionIdentifier().getValue() + "_"
					+ document.getIterationInfo().getIdentifier().getValue();
			ApplicationData primary = (ApplicationData) primaryContentItem;
			String fileName = primary.getFileName();
			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
			String finalFileName = document.getName();
			String sendU8FileName = finalFileName + "_" + version2d + "." + suffix;
			String newPath = tempPath;
			File file = new File(newPath);
			if (!file.exists()) {
				file.mkdirs();
			}
			absoluteFileName = newPath + File.separator + sendU8FileName;
			ContentServerHelper.service.writeContentStream(primary, absoluteFileName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new WTException(e);
		}
		return absoluteFileName;
	}

	/**
	 * 下载列表部分相关文档内容
	 *
	 * @param oidList
	 *            OID 列表
	 * @return {@link String }
	 */
	public static String downloadListPartRelateDocContent(ArrayList<String> oidList) throws WTException {
		List<File> partZipList = new ArrayList<>();
		for (String oid : oidList) {
			String content = downloadPartRelateDocContent(oid);
			partZipList.add(new File(content));
		}
		String path = tempPath + File.separator + generateUniqueRandomNumber(6) + File.separator;
		if (!new File(path).exists()) {
			new File(path).mkdirs();
		}
		String pathFile = path + "docMult" + ".zip";
		File file = new File(pathFile);
		handlerFileZip(partZipList, file, false);
		return file.getPath();
	}

	/**
	 * 下载零件相关文档内容
	 *
	 * @param oid
	 *            oid
	 * @return {@link String }
	 */
	public static String downloadPartRelateDocContent(String oid) throws WTException {
		List<File> fileList = new ArrayList<>();
		Persistable persistable = ToolUtils.getObjectByOid(oid);

		if (persistable instanceof WTPart) {
			WTPart part = (WTPart) persistable;
			// 获取参考文档
			List<WTDocument> partRefDocs = getPartRefDocs(part);
			for (WTDocument partRefDoc : partRefDocs) {
				String s = downloadPrimaryDoc(partRefDoc);
				fileList.add(new File(s));
			}
			EPMDocument epm3D = PartHelper.getPartRelatedActiveEPM3D(part);
			// 获取3d
			String path3dFile = downloadRepresentationDoc(epm3D, false);
			if (StrUtil.isNotBlank(path3dFile)) {
				// TODO
				fileList.add(new File(path3dFile));
				EPMDocument epm2d = getSldDRW(epm3D);
				// 获取2d
				String epm2dpath = downloadSecondaryDoc(epm2d);
				System.out.println("查询签名附件 epm2dpath = " + epm2dpath);
				if (epm2dpath == null || epm2dpath.isEmpty()) {
					epm2dpath = downloadRepresentationDoc(epm2d, true);
				}
				if (StrUtil.isNotBlank(epm2dpath)) {
					fileList.add(new File(epm2dpath));
					String path = tempPath + File.separator + generateUniqueRandomNumber(6) + File.separator;
					if (!new File(path).exists()) {
						new File(path).mkdirs();
					}
					String pathFile = path + part.getNumber() + part.getVersionIdentifier().getValue() + "_"
							+ part.getIterationInfo().getIdentifier().getValue() + ".zip";
					File file = new File(pathFile);
					handlerFileZip(fileList, file, false);
					return file.getPath();
				}
			}
		}
		return null;
	}

	/**
	 * 处理程序文件 zip
	 *
	 * @param fileList
	 *            文件列表
	 * @param zipFile
	 *            压缩文件路径
	 * @param isDeleteSourceFile
	 *            是否删除源文件
	 * @return boolean
	 */
	public static boolean handlerFileZip(List<File> fileList, File zipFile, boolean isDeleteSourceFile) {
		try {
			FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zipOut = new ZipOutputStream(fos);
			Set<String> addedFiles = new HashSet<>();
			for (File fileToZip : fileList) {
				if (!addedFiles.contains(fileToZip.getName())) {
					FileInputStream fis = new FileInputStream(fileToZip);
					ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
					zipOut.putNextEntry(zipEntry);
					byte[] bytes = new byte[1024];
					int length;
					while ((length = fis.read(bytes)) >= 0) {
						zipOut.write(bytes, 0, length);
					}
					fis.close();
					zipOut.closeEntry();
					// 将已添加的文件名添加到集合中
					addedFiles.add(fileToZip.getName());
				}
			}
			if (isDeleteSourceFile) {
				for (File file : fileList) {
					file.deleteOnExit();
				}
			}
			zipOut.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 通过部件得到其参考文档，返回参考文档列表
	 *
	 * @param part
	 *            条件部件
	 * @return 依附于条件部件的参考文档列表(WTDocument)
	 */
	public static List<WTDocument> getPartRefDocs(WTPart part) throws WTException {
		List<WTDocument> results = new ArrayList<WTDocument>();
		QueryResult qr = WTPartHelper.service.getReferencesWTDocumentMasters(part);
		while (qr.hasMoreElements()) {
			Object tempObj = qr.nextElement();
			if (tempObj instanceof WTDocumentMaster) {
				QueryResult result = VersionControlHelper.service.allVersionsOf((WTDocumentMaster) tempObj);
				if (result.hasMoreElements()) {
					results.add((WTDocument) result.nextElement());
				}
			}
		}
		return results;
	}

	// 生成指定位数的不重复随机数字
	public static String generateUniqueRandomNumber(int length) {
		// 创建一个包含0到9的数字列表
		List<Integer> digits = new ArrayList<>();
		for (int i = 0; i <= 9; i++) {
			digits.add(i);
		}
		// 使用洗牌算法打乱数字列表
		Collections.shuffle(digits);
		// 生成不重复的六位随机数字
		StringBuilder uniqueRandomNumber = new StringBuilder();
		for (int i = 0; i < Math.min(length, digits.size()); i++) {
			uniqueRandomNumber.append(digits.get(i));
		}
		return uniqueRandomNumber.toString();
	}

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
	 * 下载 EPM 文档列表
	 *
	 * @param strings
	 *            字符串
	 * @return {@link String }
	 * @throws WTException
	 *             WT异常
	 */
	public static String downloadEpmDocList(ArrayList<String> strings) throws WTException {
		List<File> fileList = new ArrayList<>();
		for (String string : strings) {
			String filePath = downloadEpmDoc(string);
			if (StrUtil.isNotBlank(filePath)) {
				fileList.add(new File(filePath));
			}
		}
		String path = tempPath + File.separator + generateUniqueRandomNumber(6) + File.separator;
		if (!new File(path).exists()) {
			new File(path).mkdirs();
		}
		String pathFile = path + "drawing" + ".zip";
		File file = new File(pathFile);
		handlerFileZip(fileList, file, false);
		return file.getPath();
	}

	/**
	 * 下载 EPM 文档
	 *
	 * @param oid
	 *            oid
	 * @return {@link String }
	 * @throws WTException
	 *             WT异常
	 */
	public static String downloadEpmDoc(String oid) throws WTException {
		EPMDocument epmDocument = (EPMDocument) ToolUtils.getObjectByOid(oid);
		String filePath;
		if (epmDocument.getCADName().contains(".SLDDRW")) {
			filePath = downloadSecondaryDoc(epmDocument);
			System.out.println("查询签名附件 epm2dpath = " + filePath);
			if (StrUtil.isBlank(filePath)) {
				filePath = downloadRepresentationDoc(epmDocument, true);
			}
		} else {
			filePath = downloadRepresentationDoc(epmDocument, false);
		}
		return filePath;
	}

	/**
	 * 下载文档列表
	 *
	 * @param strings
	 *            字符串
	 * @return {@link String }
	 * @throws WTException
	 *             WT异常
	 */
	public static String downloadDocList(ArrayList<String> strings) throws WTException {
		List<File> fileList = new ArrayList<>();
		for (String string : strings) {
			String filePath = downloadDoc(string);
			if (StrUtil.isNotBlank(filePath)) {
				fileList.add(new File(filePath));
			}
		}
		String path = tempPath + File.separator + generateUniqueRandomNumber(6) + File.separator;
		if (!new File(path).exists()) {
			new File(path).mkdirs();
		}
		String pathFile = path + "downloadDoc" + ".zip";
		File file = new File(pathFile);
		handlerFileZip(fileList, file, false);
		return file.getPath();
	}

	/**
	 * 下载文档
	 *
	 * @param oid
	 *            oid
	 * @return {@link String }
	 * @throws WTException
	 *             WT异常
	 */
	public static String downloadDoc(String oid) throws WTException {
		WTDocument document = (WTDocument) ToolUtils.getObjectByOid(oid);
		return downloadPrimaryDoc(document);
	}

	/**
	 * 由 ASM 下载
	 *
	 * @param epmDocument
	 *            EPM 文档
	 * @param selectPath
	 *            选择路径
	 * @return {@link String }
	 */
	public static String downloadByASM(EPMDocument epmDocument, String selectPath) {
		try {
			List<EPMDocument> epmDrwList;
			List<EPMDocument> epmThirdDocList = new ArrayList<>();
			List<File> filePathList = new ArrayList<>();
			// 查询子文档
			epmThirdDocList = getChildEPMByRoot(epmDocument, epmThirdDocList);
			System.out.println("epmThirdDocList = " + epmThirdDocList);
			epmDrwList = getDrwEPMByASM(epmThirdDocList);
			System.out.println("epmDrwList = " + epmDrwList);
			if (CollUtil.isNotEmpty(epmThirdDocList)) {
				for (EPMDocument document : epmThirdDocList) {
					String path = downloadRepresentationDoc(document, false, selectPath);
					filePathList.add(new File(path));
				}
			}
			if (CollUtil.isNotEmpty(epmDrwList)) {
				for (EPMDocument document : epmDrwList) {
					String filePath = downloadSecondaryDoc(document, selectPath);
					System.out.println("查询签名附件 epm2dpath = " + filePath);
					if (StrUtil.isBlank(filePath)) {
						filePath = downloadRepresentationDoc(document, true, selectPath);
					}
					filePathList.add(new File(filePath));
				}
			}
			System.out.println("filePathList = " + filePathList);
			String path = tempPath + File.separator + epmDocument.getCADName() + ".zip";
			handlerFileZip(filePathList, new File(path), true);
			return path;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 通过根模型获取全部的子模型 Widnchill内置API版本
	 *
	 * @return
	 */
	public static List<EPMDocument> getChildEPMByRoot(EPMDocument rootEPM, List<EPMDocument> epmList)
			throws WTException {
		QueryResult qr = PersistenceHelper.manager.navigate(rootEPM, EPMMemberLink.USES_ROLE, EPMMemberLink.class,
				true);
		while (qr.hasMoreElements()) {
			EPMDocumentMaster master = (EPMDocumentMaster) qr.nextElement();
			// 获取最新部件
			EPMDocument latestEPMDocument = findLatestEPMDocument(master);
			// 图纸类型只能为最新的图纸
			if (!epmList.contains(latestEPMDocument) && (latestEPMDocument.getCADName().contains(".SLDPRT")
					|| latestEPMDocument.getCADName().contains(".SLDASM"))) {
				epmList.add(latestEPMDocument);
			}
			epmList = getChildEPMByRoot(latestEPMDocument, epmList);
		}
		return epmList;
	}

	/**
	 * 根据EPM文档Master获取最新版EPM文档
	 *
	 * @param master
	 *            Master
	 * @return 最新EPM文档
	 */
	private static EPMDocument findLatestEPMDocument(EPMDocumentMaster master) {
		if (master != null) {
			try {
				QueryResult qr = VersionControlHelper.service.allVersionsOf(master);/* 通过版本控制获取最新版本 */
				if (qr.hasMoreElements()) {
					return (EPMDocument) qr.nextElement();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 获取 DRW EPM BY ASM
	 *
	 * @param epmThirdDocList
	 *            EPM3d文档
	 * @return {@link List}<{@link EPMDocument}>
	 * @throws WTException
	 *             WT异常
	 */
	private static List<EPMDocument> getDrwEPMByASM(List<EPMDocument> epmThirdDocList) throws WTException {
		List<EPMDocument> epmDrwList = new ArrayList<>();
		for (EPMDocument epmDocument : epmThirdDocList) {
			EPMDocument sldDRW = getSldDRW(epmDocument);
			if (sldDRW != null) {
				epmDrwList.add(sldDRW);
			}
		}
		return epmDrwList;
	}

	/**
	 * 获取 sld works 软件开发的 图纸对象
	 *
	 * @param epm
	 *            模型对象
	 * @return {@link EPMDocument}
	 * @throws WTException
	 *             WT异常
	 */
	public static EPMDocument getSldDRW(EPMDocument epm) throws WTException {
		String samName = epm.getCADName().toUpperCase();
		samName = samName.substring(0, samName.lastIndexOf(".")) + ".SLDDRW";
		// 直接获取参考
		EPMDocument drw = getEPMDocumentByNumber(samName);
		System.out.println("drw = " + drw);
		if (drw != null) {
			EPMReferenceLink refLink = queryEpmRefLink(drw, epm);
			System.out.println("refLink = " + refLink);
			if (refLink != null) {
				return drw;
			}
		}
		System.out.println("LocalDateTime.now() = " + LocalDateTime.now());
		return null;
	}

	/**
	 * 通过编号获取EPM文档
	 *
	 * @param number
	 * @return
	 */
	public static EPMDocument getEPMDocumentByNumber(String number) {
		String nrNumber = number.toLowerCase();
		EPMDocument epm = null;
		try {
			QuerySpec qs = new QuerySpec(EPMDocument.class);
			qs.appendWhere(new SearchCondition(EPMDocument.class, EPMDocument.CADNAME, SearchCondition.EQUAL, nrNumber,
					false));
			QueryResult qr = PersistenceHelper.manager.find(qs);
			qr = new OwnershipIndependentLatestConfigSpec().process(qr);
			if (qr.hasMoreElements()) {
				return epm = (EPMDocument) qr.nextElement();
			}
		} catch (QueryException e) {
			e.printStackTrace();
		} catch (WTException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查找二维图和三维模型的参考关系
	 *
	 * @param drw
	 *            二维图纸对象
	 * @param epm
	 *            三维模型对象
	 * @return
	 * @throws WTException
	 */
	public static EPMReferenceLink queryEpmRefLink(EPMDocument drw, EPMDocument epm) throws WTException {
		QuerySpec qs = new QuerySpec(EPMReferenceLink.class);
		SearchCondition sc1 = new SearchCondition(EPMReferenceLink.class, "roleAObjectRef.key.id",
				SearchCondition.EQUAL, PersistenceHelper.getObjectIdentifier(drw).getId());
		qs.appendWhere(sc1, new int[] { 0 });
		qs.appendAnd();
		SearchCondition sc2 = new SearchCondition(EPMReferenceLink.class, "roleBObjectRef.key.id",
				SearchCondition.EQUAL, PersistenceHelper.getObjectIdentifier(epm.getMaster()).getId());
		qs.appendWhere(sc2, new int[] { 0 });
		qs.setAdvancedQueryEnabled(true);
		QueryResult qr = PersistenceHelper.manager.find(qs);
		if (qr.size() > 0) {
			EPMReferenceLink refLink = (EPMReferenceLink) qr.nextElement();
			return refLink;
		}
		return null;
	}

	public static String escapePathForJavaScript(String filePath) {
		return filePath.replace("\\", "\\\\")
				.replace("'", "\\'")
				.replace("\"", "\\\"");
	}
}
