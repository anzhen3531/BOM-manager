package ext.ziang.bom;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.LRUCache;
import cn.hutool.script.JavaScriptEngine;

/**
 * 快递实用程序
 *
 * @author chenzt
 * @date 2022/7/12
 */
public class ExpressUtils {
	static ScriptEngine scriptEngine;

	static {
		try {
			scriptEngine = scriptEngine();
		} catch (ScriptException e) {
			throw new RuntimeException(e);
		}
	}


	static final LRUCache<String, String> expressStrCache = CacheUtil.newLRUCache(512, 5 * 60 * 1000);
	static final String NUMBER_VALUE_REGEX = "^[\\d\\.]*";
	static CacheManager caffeineCacheManager;

	public static BigDecimal getRuleNumberValue(String attrValue) {
		// log.info("getRuleNumberValue: {}", attrValue);
		// 匹配值的时候：
		// 需要将其解析为BigDecimal，从第一个非数字字符截断，解析数值。
		if (StringUtils.isBlank(attrValue)) {
			return null;
		}
		Cache cache = caffeineCacheManager.getCache("ExpNumValCat");
		String result = cache.get(attrValue, String.class);
		if (result == null) {
			final Pattern pattern = Pattern.compile(NUMBER_VALUE_REGEX, Pattern.MULTILINE);
			final Matcher matcher = pattern.matcher(attrValue.trim());
			if (matcher.find()) {
				String val = matcher.group(0);
				result = val;
				cache.put(attrValue, result);
			}
		}
		if (StringUtils.isBlank(result)) {
			return null;
		} else {
			return new BigDecimal(result);
		}
	}

	/**
	 * 解析表达式
	 *
	 * @param express
	 * @param params
	 * @return
	 */
	public static String analysisExpress(String express, Map<String, String> params) {
		if (params == null) {
			return null;
		}
		// (((ain('{module_stack_id}','Dual-glass-Framed') ||
		// ain('{module_stack_id}','Dual-glass-Framed') ) && (
		// ain('{module_type}','Bi-facial') || ain('{module_type}','Bi-facial') ) && (
		// !ain('{item_attribute32}','SMBB3') && !ain('{item_attribute32}','SMBB3') ) )
		// )
		for (String pk : params.keySet()) {
			System.out.println("pk = " + pk);
			express = express.replaceAll("\\{" + pk + "\\}",
					Optional.ofNullable(params.get(pk)).orElse("").trim());
		}
		return express;
	}

	/**
	 * Exec Express价值
	 *
	 * @param express
	 *            表达
	 * @param params
	 *            参数
	 * @return {@link String }
	 */
	public static String execExpressForValue(String express, Map<String, String> params) {
		if (StringUtils.isBlank(express)) {
			return null;
		}
		// log.info("执行表达式: {} ,params: {}", express, params);
		String currentExpress = ExpressUtils.analysisExpress(express, params);
		try {
			return (String) scriptEngine.eval(currentExpress);
		} catch (ScriptException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 执行表达式
	 *
	 * @param express
	 * @param params
	 * @return
	 */
	public static boolean execExpress(String express, Map<String, String> params) {
		if (StringUtils.isBlank(express)) {
			return false;
		}
		System.out.println("express = " + express + ", params = " + params);
		// log.info("执行表达式: {} ,params: {}", express, params);
		String currentExpress = ExpressUtils.analysisExpress(express, params);
		System.out.println("currentExpress = " + currentExpress);
		try {
			return (boolean) scriptEngine.eval(currentExpress);
		} catch (ScriptException e) {
			e.printStackTrace();
			return false;
		}
	}

	// /**
	// * 获取规则
	// *
	// * @param ruleLines
	// * 规则线
	// * @return {@link String }
	// */
	// public static String getExpress(List<ExpressRuleLineDTO> ruleLines) {
	// System.out.println("ExpressUtils.getExpress");
	// System.out.println("ruleLines = " + ruleLines);
	// ruleLines = ruleLines.stream().filter(item ->
	// CollectionUtils.isNotEmpty(item.getDetails()))
	// .collect(Collectors.toList());
	// if (ruleLines.isEmpty()) {
	// return "";
	// }
	// // 规则组id
	// HashMap<Long, List<ExpressRuleLineDTO>> groupedRules = new HashMap<>();
	// for (ExpressRuleLineDTO ruleLine : ruleLines) {
	// Long groupId = ruleLine.getRuleControlLine().getRuleLineId();
	// groupedRules.computeIfAbsent(groupId, (k) -> new ArrayList<>());
	// groupedRules.get(groupId).add(ruleLine);
	// }
	// String expressStr = "";
	// StringBuilder express = new StringBuilder();
	// boolean first = true;
	// for (Long headerKey : groupedRules.keySet()) {
	// List<ExpressRuleLineDTO> expressRuleLineDTOS = groupedRules.get(headerKey);
	// String controlObjectExpress =
	// expressRuleLineDTOS.stream().map(ExpressUtils::getControlObjectExpress)
	// .filter(StringUtils::isNotBlank).collect(Collectors.joining(" || "));
	// if (StringUtils.isBlank(controlObjectExpress)) {
	// continue;
	// }
	// if (first) {
	// express.append(" ( ");
	// first = false;
	// } else {
	// express.append(" && ( ");
	// }
	// express.append(controlObjectExpress);
	// express.append(")");
	// }
	// expressStr = express.toString();
	// return expressStr;
	// }
	//
	// private static String getExpressRuleLineDTOKey(HashMap<Long,
	// List<ExpressRuleLineDTO>> ruleLines) {
	// StringBuilder key = new StringBuilder();
	// for (Map.Entry<Long, List<ExpressRuleLineDTO>> entry : ruleLines.entrySet())
	// {
	// key.append(entry.getKey());
	// key.append(":");
	// for (ExpressRuleLineDTO expressRuleLineDTO : entry.getValue()) {
	// expressRuleLineDTO.getDetails().forEach(detail -> {
	// key.append(detail.getSourceColumn());
	// key.append("+");
	// key.append(detail.getOperation());
	// key.append("+");
	// detail.getValues().forEach(value -> {
	// key.append(value.getAttrValue()).append("_").append(value.getAttrValueTo());
	// });
	// });
	// }
	// }
	// return key.toString();
	// }
	//
	// /**
	// * 获取控制对象快递
	// *
	// * @param ruleLine
	// * 规则线
	// * @return {@link String }
	// */
	// private static String getControlObjectExpress(ExpressRuleLineDTO ruleLine) {
	// if (ruleLine.getDetails().isEmpty()) {
	// return "";
	// }
	// StringBuilder express = new StringBuilder();
	// express.append(" ( ");
	// boolean first = true;
	// for (ExpressRuleDetailDTO detail : ruleLine.getDetails()) {
	// String sourceColumn = detail.getSourceColumn();
	// if (StringUtils.isBlank(sourceColumn)) {
	// System.out.println("数据有误 sourceColumn is Null");
	// return "";
	// }
	// /* 如果是包含 则是 A == A1 || A==A2 || A==A3 */
	// String delimiter = " || ";
	// // 查看操作规则是不是排除
	// if (Objects.equals(detail.getOperation(), "排除")) {
	// /* 如果是排除 则是 A!= A1 && A!=A2 && A!=A3 */
	// delimiter = " && ";
	// }
	// StringBuilder detailValueExpress = new StringBuilder(" ( ");
	// detailValueExpress.append(detail.getValues().stream().map(value ->
	// getValueExpressStr(value, detail))
	// .collect(Collectors.joining(delimiter)));
	// detailValueExpress.append(" ) ");
	// if (first) {
	// express.append(detailValueExpress);
	// first = false;
	// } else {
	// express.append(" && ").append(detailValueExpress);
	// }
	// }
	// express.append(" ) ");
	// return express.toString();
	// }
	//
	// /**
	// * 获取 Value Express STR
	// *
	// * @param expressRuleValueDTO
	// * 表示规则值 DTO
	// * @param detail
	// * 细节
	// * @return {@link String }
	// */
	// private static String getValueExpressStr(ExpressRuleValueDTO
	// expressRuleValueDTO, ExpressRuleDetailDTO detail) {
	// StringBuilder express = new StringBuilder();
	// String valPrefix = " ";
	// if (Objects.equals(detail.getOperation(), "排除")) {
	// // 如果为排除,则对结果取反
	// valPrefix = " !";
	// }
	// // 分别处理数字和文本的情况
	// // if ("数字".equals(expressRuleValueDTO.getValueType())
	// // || StringUtils.isNotBlank(expressRuleValueDTO.getAttrValueTo())) {
	// //
	// express.append(valPrefix).append("numRange({").append(detail.getSourceColumn()).append("},")
	// // .append(expressRuleValueDTO.getAttrValue().trim()).append(",")
	// // .append(expressRuleValueDTO.getAttrValueTo().trim()).append(") ");
	// // } else {
	// // 等于 和 包含,现在将等于包含看做一致
	// express.append(valPrefix).append("ain('{").append(detail.getSourceColumn()).append("}','")
	// .append(expressRuleValueDTO.getAttrValue().trim()).append("') ");
	// // }
	//
	// return express.toString();
	// }

	public static BigDecimal getItemNumberValue(String attrValue) {
		// log.info("getItemNumberValue: {}", attrValue);
		if (StringUtils.isBlank(attrValue)) {
			return null;
		}
		Cache cache = caffeineCacheManager.getCache("ExpNumVal");
		String result = cache.get(attrValue, String.class);
		if (result == null) {
			try {
				// 匹配值的时候：
				// （基础（物料表）表解析）
				// 特例：1 范围值：≥610G/m2 解析为 610
				// 2 误差值：610±20G/m2 解析成 610
				if (attrValue.contains("±")) {
					attrValue = attrValue.split("±")[0];
				}
				attrValue = attrValue.replace("≥", "");
				attrValue = attrValue.replace("≤", "");

				final Pattern pattern = Pattern.compile(NUMBER_VALUE_REGEX, Pattern.MULTILINE);
				final Matcher matcher = pattern.matcher(attrValue.trim());
				if (matcher.find()) {
					String val = matcher.group(0);
					result = val;
					cache.put(attrValue, result);
				}
			} catch (Exception e) {
				// 跳过
			}
		}
		if (StringUtils.isBlank(result)) {
			return null;
		} else {
			return new BigDecimal(result);
		}
	}

	public static void main(String[] args) throws ScriptException {
		try {
			// 执行JavaScript代码
			Object eval = scriptEngine.eval(
					"(((" +
							"ain('{item_attribute18}','H(N)') || " +
							"ain('{item_attribute18}','H') || " +
							"ain('{item_attribute18}','H(N)') || " +
							"ain('{item_attribute18}','H'))))");
			System.out.println("eval = " + eval);
		} catch (ScriptException e) {
			e.printStackTrace();
		}

		System.out.println("validateIsNotBlankAttr(\"123123\",\"123\",\"12312\",null) = " + validateIsNotBlankAttr("123123", "123", "12312", null));

	}

	public static ScriptEngine scriptEngine() throws ScriptException {
		StringBuffer exp = new StringBuffer();
		exp.append("      function ain(fieldValue, value) {");
		exp.append(
				"          return value != null && fieldValue != null && (';'+value+';').indexOf(';' + fieldValue +';') >= 0;");
		exp.append("      };");

		exp.append("      function eq(fieldValue, value) {");
		exp.append("          return value != null && fieldValue != null && ''+fieldValue == ''+value;");
		exp.append("      };");

		exp.append("      function notin(fieldValue, value) {");
		exp.append(
				"          return value != null && fieldValue != null && (';'+value+';').indexOf(';' + fieldValue +';') < 0;");
		exp.append("      };");

		exp.append("      function numRange(fieldValue, valueFrom,valueTo) {");
		exp.append("          return valueFrom != null && valueTo != null && fieldValue != null && " +
				" fieldValue >= valueFrom && fieldValue<=valueTo ;");
		exp.append("      };");
		// 结果
		ScriptEngineManager manager = new ScriptEngineManager(null);
		ScriptEngine engine = manager.getEngineByName("nashorn");
		engine.eval(exp.toString());
		return engine;
	}

	/**
	 * 验证不是空白属性
	 *
	 * @param isNotBlankAttr 不是空白 attr
	 *
	 * @return boolean
	 */
	public static boolean validateIsNotBlankAttr(String... isNotBlankAttr) {
		for (String stringValue : isNotBlankAttr) {
			if (StringUtils.isBlank(stringValue)) {
				return false;
			}
		}
		return true;
	}
}
