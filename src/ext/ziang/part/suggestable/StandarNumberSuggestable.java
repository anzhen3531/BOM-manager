//package ext.ziang.part.suggestable;
//
//import java.rmi.RemoteException;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Iterator;
//
//import org.apache.commons.lang3.StringUtils;
//
//import wt.fc.PersistenceHelper;
//import wt.fc.QueryResult;
//import wt.log4j.LogR;
//import wt.part.WTPart;
//import wt.query.QuerySpec;
//import wt.query.SearchCondition;
//import wt.type.ClientTypedUtility;
//import wt.type.TypeDefinitionForeignKey;
//import wt.type.TypeDefinitionReference;
//import wt.type.Typed;
//import wt.util.WTException;
//import wt.vc.Iterated;
//import wt.vc.config.LatestConfigSpec;
//import wt.vc.views.View;
//import wt.vc.views.ViewHelper;
//import wt.vc.views.ViewManageable;
//
//import com.google.common.collect.Lists;
//import com.ptc.core.components.suggest.SuggestParms;
//import com.ptc.core.components.suggest.SuggestResult;
//import com.ptc.core.components.suggest.Suggestable;
//
//import ext.shac.core.Global;
//import ext.shac.part.util.PartNumberGenerationUtil;
//
///**
// *
// * 类名:StandarNameConfigSuggestable 描述:通过关键字搜索物料的编码
// *
// * @author wq.Ran
// * @Date 2019年8月15日 下午3:51:22
// * @version 1.0.0
// */
//public class StandarNumberSuggestable implements Suggestable {
//
//	/**
//	 *
//	 * 描述:重写Suggestable中的getSuggestions方法
//	 * 通过关键字和类型动态查询系统中相关零件编码
//	 * @param parms SuggestTextBox控件参数
//	 * @return
//	 * @author wq.Ran
//	 * @Date 2019年8月15日 下午3:51:22
//	 * @version 1.0.0
//	 */
//	@Override
//	public Collection<SuggestResult> getSuggestions(SuggestParms parms) {
//		ArrayList<SuggestResult> results = Lists.newArrayList();
//		String keyword = parms.getSearchTerm();
//		String mapKey = parms.getParm("number");
//		if(StringUtils.isNotBlank(mapKey)){
//			HashMap<String, String> cacheMaps = new HashMap<String, String>();
////			try {
////				cacheMaps = WTPartCommand.initPartsNameAndEnglishNameCache(mapKey);
////			} catch (WTException e) {
////				e.printStackTrace();
////			}
//			/**
//			 * 通过零件的中文名称关键字查询配置文件中的零件名称和英文名称对应关系
//			 */
//			Iterator<String> iterator = cacheMaps.keySet().iterator();
//			while (iterator.hasNext()) {
//				String key = iterator.next();
//				/**
//				 * 如果查询的配置表文件中中文名称包含关键字则进行添加
//				 */
//				if (key.contains(keyword)) {
//					results.add(SuggestResult.valueOf(key));
//				}
//			}
//			/**
//			 * 如果查询的结果为空，则添加当前输入的值
//			 */
//			if (results.isEmpty()) {
//				results.add(SuggestResult.valueOf(keyword.trim()));
//			}
//		}else{
//			mapKey = parms.getParm("wtPartTypeName");
//			mapKey = mapKey.split("\\|")[2];
//			/**
//			 * add by wq.Ran 判断输入的部件编号和部件类型是否相符合 20220328  start
//			 */
//			if((mapKey.equals(Global.TYPE_PARTK) && keyword.startsWith("B"))||
//					(mapKey.equals(Global.TYPE_PARTB) && keyword.startsWith("K"))||
//							(mapKey.equals(Global.TYPE_PARTZ) && keyword.startsWith("Z"))){
//				//add by wangdong 20220929 类型与编号匹配增加部件Z
//				results.add(SuggestResult.valueOf("--部件类型和部件编号不符，请检查--"));
//				return results;
//			}
//			/**
//			 * add by wq.Ran 判断输入的部件编号和部件类型是否相符合 20220328  end
//			 */
//			try {
//				/**
//				 * 通过零件类型、名称关键字、类型查询系统中对应的零件对象
//				 */
//				QueryResult qr = getVersionedByClassAndNumberAndNameAndTypeInnerName(WTPart.class, keyword+"*", "", mapKey, "", false);
//				while(qr.hasMoreElements()){
//					WTPart wtPart = (WTPart) qr.nextElement();
//					results.add(SuggestResult.valueOf(wtPart.getNumber()));
//				}
//				if(results.size() == 0){
//					if(keyword.length() == 13){
//	                	 String checkResult  = PartNumberGenerationUtil.checkPartNumberRule(keyword);
//	                	 if("SUCCESS".equals(checkResult)){
//	 						results.add(SuggestResult.valueOf("--无查询结果--"));
//	 						results.add(SuggestResult.valueOf(keyword));
//	                	 }else{
//	                		results.add(SuggestResult.valueOf(checkResult));
//	                	 }
//
//					}else{
//						results.add(SuggestResult.valueOf("--编码必须为13位数字或字母--"));
//					}
//				}
//			} catch (WTException e) {
//				LogR.getLogger(StandarNumberSuggestable.class.getName()).error(e.getMessage());
//				e.printStackTrace();
//			}
//		}
//		return results;
//	}
//
//	public QueryResult getVersionedByClassAndNumberAndNameAndTypeInnerName(Class klass, String number, String name,
//			String typeInnerName, String viewName, boolean flag) throws WTException {
//		/**
//		 * 定义对象类型查询
//		 */
//		QuerySpec querySpec = new QuerySpec(klass);
//		/**
//		 * 判断编号参数是否为空
//		 */
//		if (StringUtils.isNotBlank(number)) {
//			/**
//			 * 如果编号包含*，则进行*号转化，进行模糊查询，否则进行全等查询
//			 */
//			if (number.contains("*")) {
//				number = number.replace("*", "%");
//				querySpec.appendWhere(new SearchCondition(klass, "master>number", SearchCondition.LIKE, number, flag), new int[1]);
//			} else {
//				querySpec.appendWhere(new SearchCondition(klass, "master>number", SearchCondition.EQUAL, number, flag), new int[1]);
//			}
//			querySpec.appendAnd();
//		}
//		/**
//		 * 判断名称参数是否为空
//		 */
//		if (StringUtils.isNotBlank(name)) {
//			/**
//			 * 如果名称包含*，则进行*号转化，进行模糊查询，否则进行全等查询
//			 */
//			if (name.contains("*")) {
//				name = name.replace("*", "%");
//				querySpec.appendWhere(new SearchCondition(klass, "master>name", SearchCondition.LIKE, name, flag), new int[1]);
//			} else {
//				querySpec.appendWhere(new SearchCondition(klass, "master>name", SearchCondition.EQUAL, name, flag), new int[1]);
//			}
//			querySpec.appendAnd();
//		}
//		/**
//		 * 判断定义的类型名称参数是否为空，不为空则增加类型条件查询
//		 */
//		if (StringUtils.isNotBlank(typeInnerName)) {
//			try {
//				Long subTypeId = new Long(ClientTypedUtility.getTypeDefinitionReference(typeInnerName).getKey().getBranchId());
//				querySpec.appendWhere(new SearchCondition(klass,Typed.TYPE_DEFINITION_REFERENCE + "." + TypeDefinitionReference.KEY + "."
//										+ TypeDefinitionForeignKey.BRANCH_ID,SearchCondition.EQUAL, subTypeId),new int[] { 0 });
//				querySpec.appendAnd();
//			} catch (RemoteException e) {
//				e.printStackTrace();
//			}
//		}
//		/**
//		 * 判断定义的视图名称是否为空，不为空则增加视图名称查询
//		 */
//		if (StringUtils.isNotBlank(viewName)) {
//			viewName = viewName.trim();
//			View viewObj = ViewHelper.service.getView(viewName);
//			if (viewObj == null) {
//				return null;
//			}
//			querySpec.appendAnd();
//			querySpec.appendWhere(
//					new SearchCondition(klass, ViewManageable.VIEW+"."+TypeDefinitionReference.KEY, SearchCondition.EQUAL, PersistenceHelper.getObjectIdentifier(viewObj)),
//					new int[1]);
//		}
//		/**
//		 * 增加最新版本参数查询
//		 */
//		querySpec.appendWhere(new SearchCondition(klass, Iterated.LATEST_ITERATION, SearchCondition.IS_TRUE), new int[1]);
//		LatestConfigSpec configSpec = new LatestConfigSpec();
//		configSpec.appendSearchCriteria(querySpec);
//		/**
//		 * 返回查询结果集
//		 */
//		QueryResult qr = PersistenceHelper.manager.find(querySpec);
//		qr = configSpec.process(qr);
//		return qr;
//	}
//}
