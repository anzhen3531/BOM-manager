package ext.ziang.test;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class SortByWeightedProximity {

    public static void main(String[] args) {

        // 查询参数
        List<JSONObject> params = new ArrayList<>();

        // 返回的数据
        List<List<JSONObject>> selectData = new ArrayList<>();

        // 配置查询的Key
        List<String> keyList = new ArrayList<>();
        keyList.add("LENGTH");
        keyList.add("WIDTH");
        keyList.add("C_THICKNESS");

        // 配置查询的权重
        Map<String, Double> weights = new HashMap<>();
        weights.put("LENGTH", 1.0);
        weights.put("WIDTH", 2.0);
        weights.put("C_THICKNESS", 3.0);

        Collections.sort(selectData, new Comparator<List<JSONObject>>() {
            @Override
            public int compare(List<JSONObject> jsonObjectLeft, List<JSONObject> jsonObjectRight) {
                double leftDistance = weightedDistance(params, weights, keyList, jsonObjectLeft);
                double rightDistance = weightedDistance(params, weights, keyList, jsonObjectRight);
                return Double.compare(leftDistance, rightDistance);
            }
        });
    }

    /**
     * 计算当前数据和输入数据的权重 算法效率为 O(n)
     * 
     * @param paramsObject 参数JSON对象
     * @param weights 权重
     * @param keyList 字段列表
     * @param jsonObjectLeft JSON 对象 left
     * @return 当前对象的排序值
     */
    public static double weightedDistance(List<JSONObject> paramsObject, Map<String, Double> weights,
        List<String> keyList, List<JSONObject> jsonObjectLeft) {
        // 构建查询的参数映射
        Map<String, Double> paramsMap = new HashMap<>();
        for (JSONObject jsonObject : paramsObject) {
            String attName = jsonObject.getString("attName");
            if (StringUtils.isNotBlank(attName)) {
                if (keyList.contains(attName)) {
                    paramsMap.put(attName, Double.valueOf(jsonObject.getString("attValue")));
                }
            }
        }
        double sumDistance = 0;
        // 逐个排除JSON对象 获取需要进行排序的字段 并计算
        for (JSONObject jsonObject : jsonObjectLeft) {
            String englishName = jsonObject.getString("englishName");
            if (StringUtils.isNotBlank(englishName)) {
                if (keyList.contains(englishName)) {
                    sumDistance += weights.get(englishName)
                        * Math.pow(Double.parseDouble(jsonObject.getString("value")) - paramsMap.get(englishName), 2);
                }
            }
        }
        return Math.sqrt(sumDistance);
    }
}
