package com.example.administrator.myprojectdemo.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 判断字符串是否符合格式的类
 */

public class StringUtil {

    //判断字符串是否为空
    public static boolean isEmpty(String str) {
        if (str == null|| str.equals("")) {
            return true;
        }
        return false;
    }

    //判断字符串是否为手机号
    public static boolean isMobileNum(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        System.out.println(m.matches() + "---");
        return m.matches();
    }
    /**
     * 判断手机号是否合法 长度等于11 并且输入有规律
     * */

    public static boolean isMobileNO(String mobiles) {
        Pattern p=Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
        Matcher m=p.matcher(mobiles);
        return m.matches();
    }
    /**
     * 判断邮箱是否合法
     * @param email
     * @return
     */
    public static boolean isEmail(String email){
        if (null==email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p =  Pattern.compile("\\w+([-_.]\\w+)*@[_a-zA-Z0-9\\-]+\\.\\w+([-_.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }
    //把字符串转为日期
    public static Date ConverToDate(String strDate) throws Exception
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.parse(strDate);
    }
    //判断字符串是不是数字
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    static public String trimAllWhitespace(String str) {
        if (str != null) {
            int len = str.length();
            if (len > 0) {
                char[] src = str.toCharArray();
                char[] dest = new char[src.length];

                int destPos = 0;
                for (int pos1 = 0, pos2 = 0; pos2 < src.length;) {
                    if (Character.isWhitespace(src[pos2])) {
                        if (pos1 == pos2) {
                            pos1++;
                            pos2++;
                        } else {
                            System.arraycopy(src, pos1, dest, destPos, pos2
                                    - pos1);
                            destPos += (pos2 - pos1);
                            pos2++;
                            pos1 = pos2;
                        }
                    } else {
                        pos2++;
                    }

                    if (pos2 == src.length) {
                        if (pos1 != pos2) {
                            System.arraycopy(src, pos1, dest, destPos, pos2
                                    - pos1);
                            destPos += (pos2 - pos1);
                        }
                        return new String(dest, 0, destPos);
                    }
                }
            }
        }
        return str;
    }


}
