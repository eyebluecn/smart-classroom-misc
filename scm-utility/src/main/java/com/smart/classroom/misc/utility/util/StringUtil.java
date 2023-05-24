package com.smart.classroom.misc.utility.util;

import com.google.common.base.CaseFormat;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.helpers.MessageFormatter;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * 关于字符串处理的通用方法。
 */
@Slf4j
public class StringUtil extends StringUtils {

    public static String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private static final Pattern NUMBER_PATTERN = Pattern.compile("^-?[1-9]\\d*\\.?\\d*|^-?0\\.\\d*[1-9]\\d*$");

    /**
     * 获取长度为n的字符串
     *
     * @param n 字符串长度
     * @return 一个长度为n的随机字符串
     */
    public static String randomString(int n) {
        String rawString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int rawLength = rawString.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int pos = new Random().nextInt(rawLength);
            sb.append(rawString.charAt(pos));
        }
        return sb.toString();
    }

    //将阿拉伯数字转成中文。
    public static String numberToChinese(int num) {

        String[] chnNumChar = new String[]{"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String[] chnUnitSection = new String[]{"", "万", "亿", "万亿", "亿亿"};
        String[] chnUnitChar = new String[]{"", "十", "百", "千"};

        String strIns = "";
        String chnStr = "";
        int unitPos = 0;
        boolean zero = true;
        while (num > 0) {
            int v = num % 10;
            if (v == 0) {
                if (!zero) {
                    zero = true;
                    chnStr = chnNumChar[v] + chnStr;
                }
            } else {
                zero = false;
                strIns = chnNumChar[v];
                strIns += chnUnitChar[unitPos];
                chnStr = strIns + chnStr;
            }
            unitPos++;
            num = num / 10;
        }
        return chnStr;

    }

    /**
     * 名词变复数归纳总结
     * 1.一般情况下，在名词后加“s”或“es”.
     * 2.以s,sh,ch,x结尾的名字，在名词后直接加“es”.
     * 3.以o结尾的名字，有两种情况：
     * 1）有生命的名词，在名词后加“es”.
     * 如：tomato-tomatoes potato-potatoes
     * 2)无生命的名字，在名字后加“s”.
     * 如：photo-photos radio-radios
     * 注意：使用java一律采用加“s”的策略
     * 4.以辅音字母+y结尾的名词,将y改变为i,再加-es.
     * 元音字母+y结尾的名词则直接加s
     */
    public static String toPlural(String singular) {

        if (singular == null || "".equals(singular)) {
            return singular;
        }
        int length = singular.length();
        //一个字母的直接加个s.
        if (length == 1) {
            return singular + "s";
        }

        char lastChar = singular.charAt(length - 1);
        char lastSecondChar = singular.charAt(length - 2);
        if (lastChar == 's' || lastChar == 'x' || (lastChar == 'h' && (lastSecondChar == 's' || lastSecondChar == 'c'))) {
            return singular + "es";
        } else if (lastChar == 'y' && (lastSecondChar != 'a' && lastSecondChar != 'e' && lastSecondChar != 'i' && lastSecondChar != 'o' && lastSecondChar != 'u')) {
            return singular.substring(0, length - 1) + "ies";
        } else {
            return singular + "s";
        }

    }

    //把一个字符串转成下划线的大写格式
    public static String upperCamelToUpperUnderscore(String name) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, name);
    }

    //把一个大写驼峰转成小写驼峰
    public static String upperCamelToLowerCamel(String name) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, name);
    }

    //把一个大写驼峰转成小写驼峰
    public static String lowerCamelToUpperCamel(String name) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, name);
    }

    //把一个下划线风格的字符串转成首字母大写驼峰法字符串
    public static String underscoreToUpperCamel(String name) {
        if (isBlank(name)) {
            return name;
        }

        name = name.toUpperCase();

        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name);
    }

    //把一个下划线风格的字符串转成首字母小写驼峰法字符串
    public static String underscoreToLowerCamel(String name) {
        if (isBlank(name)) {
            return name;
        }

        name = name.toUpperCase();

        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, name);
    }

    //类似于sl4j的字符串格式化.使用 {} 做占位符。
    public static String format(String messagePattern, Object... arguments) {
        return MessageFormatter.arrayFormat(messagePattern, arguments).getMessage();
    }

    /**
     * 将10进制转换成N进制
     *
     * @param tenRadixNumber 十进制数
     * @param radix          进制编码支持10+26=36进制
     * @return
     */
    public static String radixTenToN(long tenRadixNumber, int radix) {

        if (tenRadixNumber == 0) {
            return CHARS.charAt(0) + "";
        }

        StringBuilder buf = new StringBuilder();
        int remainder = 0;
        while (tenRadixNumber != 0) {
            remainder = (int) (tenRadixNumber % radix);// 求余数
            tenRadixNumber = tenRadixNumber / radix;// 除以基数
            buf.append(CHARS.charAt(remainder));// 保存余数，记得要倒叙排列
        }
        buf.reverse();// 倒叙排列
        return buf.toString();
    }

    /**
     * 生成一个数据服务ukey.
     * 生成策略：
     * 1.为节省长度，时间戳转换成62进制(0-9a-zA-Z)
     * <p>
     * 优点：
     * 1.不重复，
     * 2.短小
     * 3.可以反解析判断创建时间
     */
    public static String generateProteinUniqueCode() {

        long num = System.currentTimeMillis();

        return "Tp" + generateUniqueCode(num);
    }

    //生成质量巡检的code
    public static String generateVitaminUniqueCode() {

        long num = System.currentTimeMillis();

        return "Tv" + generateUniqueCode(num);
    }

    //生成报表的code
    public static String generateCarbonUniqueCode() {

        long num = System.currentTimeMillis();

        return "Tc" + generateUniqueCode(num);
    }

    public static String generateApplicationUniqueCode() {

        long num = System.currentTimeMillis();

        return "Ta" + generateUniqueCode(num);
    }

    private static String generateUniqueCode(long num) {

        return StringUtil.radixTenToN(num, CHARS.length());
    }

    /**
     * @param keyword 需要进行模糊搜索的词
     */
    public static String safeLike(String keyword) {

        if (keyword == null) {
            return null;
        }

        keyword = keyword.replaceAll("_", "\\\\_");

        keyword = keyword.replaceAll("%", "\\\\%");
        return keyword;

    }

    /**
     * 将所有的回车符替换成空格，方便打印。
     */
    public static String replaceReturnToSpace(String content) {

        if (content == null) {
            return null;
        }

        content = content.replaceAll("\r\n", " ");
        content = content.replaceAll("\n", " ");

        return content;

    }

    /**
     * 获取到md5加密后的字符串
     */
    public static String md5(String str) {
        return DigestUtils.md5Hex(str);
    }

    /**
     * 从一个dingdingWebhook中读取出access_token
     * eg: https://oapi.dingtalk.com/robot/send?access_token=2a6402421725e2033db9c33be6c6c5e56f366edee64aa4c859831dff68d89738
     */
    public static String parseWebhookAccessToken(String url) {
        String urlHead = "https://oapi.dingtalk.com/robot/send?access_token=";
        if (url == null) {
            return null;
        } else {
            url = url.trim();
            if (url.startsWith(urlHead)) {
                return url.substring(urlHead.length());
            } else {
                return url;
            }
        }
    }

    /**
     * 核心日志采用\u0001 进行分割
     * 因此对于要落日志的字段，要求不能包含该字符，同时不能包含换行符。
     * <p>
     * \u0001转换成空格
     * 回车转换成|n|
     */
    public static String safeLog(String content) {
        if (StringUtil.isBlank(content)) {
            return "-";
        }
        String str1 = StringUtil.replace(content, "\u0001", " ");
        return StringUtil.replace(str1, "\n", "|n|");
    }

    /**
     * 对于太长的文字，取前面和后面的部分，中间用...表示.
     *
     * @param text      需要处理的文本
     * @param maxLength 最多保留多少个字符
     * @param tail      后面保留多少个字符
     * @return 经过处理的字符串
     */
    public static String digest(String text, int maxLength, int tail) {

        if (StringUtil.isEmpty(text)) {
            return text;
        }

        String dotString = "...";
        int totalLength = text.length();
        int dotLength = dotString.length();
        int head = maxLength - tail - dotLength;

        if (maxLength < tail || tail < 0 || head < 0) {
            throw new RuntimeException("大小指定不符合逻辑");
        }

        if (StringUtil.isBlank(text)) {
            return text;
        } else {
            if (totalLength <= maxLength) {
                return text;
            } else {
                return StringUtil.format("{}{}{}", text.substring(0, head), dotString, text.substring(totalLength - tail));
            }
        }
    }

    /**
     * 对于太长的文字，取前面和后面的部分，中间用...表示.
     * 默认后面保留6个自
     */
    public static String digest(String text, int maxLength) {

        return digest(text, maxLength, 6);
    }

    /**
     * 生成一个uuid
     *
     * @return 一个唯一的uui
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 对于一个url，我们只要其host部分
     * http(s):// pathname 全部去掉
     * 如果以*开头，那么去掉*
     */
    public static String getBareWithPortUrl(String url) {

        if (isBlank(url)) {
            return "";
        }

        url = url.trim();

        if (url.startsWith("http://")) {
            url = url.substring("http://".length());
        }

        if (url.startsWith("https://")) {
            url = url.substring("https://".length());
        }
        //去除pathname
        url = url.split("/")[0];

        //去除*
        if (url.startsWith("*.")) {
            url = url.substring("*.".length());
        }

        //去除**
        if (url.startsWith("**.")) {
            url = url.substring("**.".length());
        }

        return url;
    }

    /**
     * 对于一个url，我们只要其host部分
     * http(s):// 端口 pathname 全部去掉
     * 如果以*开头，那么去掉*
     */
    public static String getBareUrl(String url) {

        url = getBareWithPortUrl(url);

        //去除后面的端口。
        url = url.split(":")[0];

        return url;
    }

    /**
     * 判断是否是数据，包括小数
     */
    public static boolean isNumber(Object input) {
        if (input == null) {
            return false;
        }
        if (input instanceof Number) {
            return true;
        }
        String str = input.toString();
        //先用double来转，如果有异常再用int来转
        try {
            Double valueAfterChange = Double.parseDouble(str);
            return true;
        } catch (Exception e) {
            return false;
        }
        //if(StringUtil.equals(str,"0.00")||StringUtil.equals(str,"0.0") || StringUtil.equals(str,"0"))
        //{
        //    return true;
        //}
        //String strToJudge = str.replaceAll("E","");//防止科学计数法
        //if (isEmpty(strToJudge)) {
        //    return false;
        //}
        //return NUMBER_PATTERN.matcher(strToJudge).matches();
    }

    /**
     * 将字符串转成整数
     *
     * @param value 数字字符串
     * @return 整数
     */
    public static Integer parseInteger(String value) {
        try {
            return Integer.valueOf(value);
        } catch (Throwable throwable) {
            log.error("字符串转整数异常", throwable);
            return null;
        }
    }


    public static int findAll(String str, String strTofind) {
        int count = 0;
        int beginIndex = 0;
        while (str.indexOf(strTofind, beginIndex) >= 0) {
            count++;
            beginIndex = str.indexOf(strTofind, beginIndex) + 1;
        }
        return count;
    }


    /**
     * 将一个对象安全地转换成字符串
     * 尤其是对于0的转换 |obj| < 0.00001 被认为是0 因为转换时会出现浮点精度问题
     */
    public static String convertToString(Object obj) {

        if (obj == null) {
            return null;
        }

        double zeroDoubleFlag = 0.00001;

        if (obj instanceof BigDecimal) {
            BigDecimal bd = (BigDecimal) obj;
            if (NumberUtil.isInteger(bd)) {
                return "" + bd.intValue();
            } else {
                double d = bd.doubleValue();
                if (Math.abs(d) < zeroDoubleFlag) {
                    return "0";
                } else {
                    return "" + d;
                }
            }
        } else if (obj instanceof Double) {
            double d = (Double) obj;
            if (Math.abs(d) < zeroDoubleFlag) {
                return "0";
            } else {
                return "" + d;
            }
        } else if (obj instanceof Float) {
            float d = (Float) obj;
            if (Math.abs(d) < zeroDoubleFlag) {
                return "0";
            } else {
                return "" + d;
            }
        } else {
            return obj.toString();
        }
    }

    //  删除前缀
    public static String removePrefix(String preFix, String str) {
        if (isBlank(preFix)) {
            return str;
        }
        if (isBlank(str)) {
            return "";
        }
        int preFixLen = preFix.length();
        if (str.startsWith(preFix)) {
            return str.substring(preFixLen);
        }
        return str;
    }

    /**
     * 获取补齐的6位工号
     * <p>
     * see StringUtil.sixNo()
     */
    @Deprecated
    public static String getPadWorkNo(String workNo) {
        //外包工号不做补齐
        if (workNo.contains("WB")) {
            return workNo;
        } else {
            String padWorkNo = "000000" + workNo;
            return padWorkNo.substring(padWorkNo.length() - 6, padWorkNo.length());
        }
    }

    /**
     * 获取省略后工号
     * <p>
     * see StringUtil.sixNo()
     */
    public static String getSimpleWorkNo(String workNo) {
        //外包工号不做补齐
        if (workNo.contains("WB")) {
            return workNo;
        } else {
            return workNo.replaceAll("^(0+)", "");
        }
    }

    /**
     * 将工号补齐为6位。 WB工号不补齐，超过6位的不操作。
     *
     * @param no 原来的工号
     * @return 补齐操作后的工号
     */
    public static String sixNo(String no) {

        if (StringUtil.isBlank(no)) {
            return "000000";
        }

        if (no.length() >= 6) {
            return no;
        }

        if (no.contains("WB")) {
            return no;
        }

        StringBuilder zeros = new StringBuilder();
        for (int i = 0; i < 6 - no.length(); i++) {
            zeros.append("0");
        }

        return zeros + no;
    }

    /**
     * 判断两个用户的工号是否相等
     */
    public static boolean isSameUser(String userNo, String anotherUserNo) {
        return equals(sixNo(userNo), sixNo(anotherUserNo));
    }
}
