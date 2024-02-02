package ext.ziang.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HandlerExcelName {

    public static void main(String[] args) {
        System.out.println(handlerVersionByName("208058 V1Z-L28-056 8L28046-02 A00"));
    }


    /**
     * 按名称划分处理程序版本
     *
     * @param name 名字
     * @return {@link String}
     */
    private static String handlerVersionByName(String name) {
        System.out.println("name = " + name);

        Pattern pattern = Pattern.compile("\\b[A-Z]\\d{2}\\b");
        Matcher matcher = pattern.matcher(name);
        if (matcher.find()) {
            String group = matcher.group();
            System.out.println("group = " + group);
            return group;
        }
        return null;
    }

}
