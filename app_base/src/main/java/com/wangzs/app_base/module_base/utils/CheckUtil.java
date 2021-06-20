package com.wangzs.app_base.module_base.utils;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.EditText;


import com.wangzs.app_base.AppApplicationContext;
import com.wangzs.app_base.AppConfig;
import com.wangzs.app_base.R;
import com.wangzs.app_base.toast.ToastUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @Description:
 * @Author: wangzs
 * @Version:
 */
public class CheckUtil {

    public static final int TYPE_ALL = 0;
    public static final int TYPE_ADD = 1;
    public static final int TYPE_DEL = 2;
    public static final int TYPE_DYNAMIC = 3;

    public static final String[] PHONE_PREFIX = new String[]{"130", "131", "132", "133", "134", "135", "136", "137", "138", "139", "150", "151", "152", "153", "154", "155",
            "156", "157", "158", "159", "180", "181", "182", "183", "184", "185", "186", "187", "188", "189", "178"};

    public static boolean checkLocation(String mdn) {
        return checkMDN(mdn, false);
    }

    public static boolean checkMDN(String mdn) {
        return checkMDN(mdn, true);
    }

    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);

        return m.matches();
    }

    /**
     * 检查是否是11位数字
     *
     * @param mobiles
     * @return
     */
    public static boolean is11MobileNo(String mobiles) {
        Pattern p = Pattern.compile("^\\d{11}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static boolean isTraditionalPhone(String mobiles) {

        Pattern p = Pattern.compile("0\\d{2,3}\\d{5,9}");
        Matcher m = p.matcher(mobiles);

        return m.matches();
    }

    public static boolean checkPswLengthzero(String psw) {
        if (psw.length() == 0) {
            return false;
        }
        return true;
    }

    public static boolean checkPswLength(String psw) {
        if (psw.length() < 6 || psw.length() > 20) {
            return false;
        }
        return true;
    }

    /**
     * 禁止EditText输入特殊字符
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpeChat(EditText editText) {
        editText.setFilters(new InputFilter[]{checkSpecialCharacters()});
    }

    public static InputFilter checkSpecialCharacters() {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat = "[-`~!@#$%^&*()+=|{}':;',\\[\\].<>/\"?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.find() || source.equals(" ")) {
                    ToastUtils.error("不能输入空格和特殊字符");
                    return "";
                } else {
                    return null;
                }
            }
        };
        return filter;
    }

    public static boolean checkPswString(CharSequence psw) {
        /*
		 * boolean b =
		 * Pattern.compile("([\\u4e00-\\u9fa5]|[\\ufe30-\\uffa0])+").
		 * matcher(psw).find(); if(b){ return false; }
		 */
        //密码是数字或字母
//		Pattern p = Pattern.compile("^[A-Za-z0-9]+$");
        //密码是由数字和字母组成
        String regex = "(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[a-zA-Z0-9]{8,20}";
        Pattern p = Pattern.compile(regex);
        if (p.matcher(psw).matches()) {
            return true;
        }
        ;
        return false;
    }



    /**
     * 检查手机号码合法性
     *
     * @param mdn
     * @return
     */
    public static boolean checkMDN(String mdn, boolean checkLen) {
        if (mdn == null || mdn.equals("")) {
            return false;
        }
        // modify by zhangyp 去掉号码前边的+86
        if (mdn.startsWith("+86")) {
            mdn = mdn.substring(3);
        }
        if (checkLen && mdn.length() != 11) {
            return false;
        }
        boolean flag = false;
        String p = mdn.length() > 3 ? mdn.substring(0, 3) : mdn;
        for (int i = 0; i < PHONE_PREFIX.length; i++) {
            if (p.equals(PHONE_PREFIX[i])) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            return false;
        }
        return true;
    }

    public static final char[] INVALID_CH_CN = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9'};

	/*
	 * ,'.',',',';',':','!','@','/','(',')','[',']','{',
	 * '}','|','#','$','%','^','&','<','>','?','\'','+', '-','*','\\','\"'
	 */

    public static boolean checkCN(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        char[] cArray = str.toCharArray();
        for (int i = 0; i < cArray.length; i++) {
            for (int j = 0; j < INVALID_CH_CN.length; j++) {
                if (cArray[i] != INVALID_CH_CN[j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 是否为新版本, true 为有新版本， 否则；
     *
     * @return
     */
    public static boolean versionCompare(String oldversion, String newversion) {
        if (oldversion == null || newversion == null) {
            return false;
        }
        String[] oldstr = oldversion.split("\\.");
        String[] newstr = newversion.split("\\.");

        int[] oldint = new int[oldstr.length];
        int[] newint = new int[newstr.length];

        try {
            for (int i = 0; i < oldstr.length; i++) {
                oldint[i] = Integer.valueOf(oldstr[i]);
            }
            for (int i = 0; i < newstr.length; i++) {
                newint[i] = Integer.valueOf(newstr[i]);
            }
        } catch (Exception e) {
        }

        // 可以简化的逻辑
        int count = oldint.length > newint.length ? newint.length : oldint.length;
        for (int temp = 0; temp < count; temp++) {
            if (newint[temp] == oldint[temp]) {
                continue;
            } else if (newint[temp] > oldint[temp]) {
                return true;
            } else {
                return false;
            }
        }
        if (newint.length > oldint.length) {
            return true;
        }
        return false;
    }

    /**
     * 检测邮箱合法性
     *
     * @param email
     * @return
     */
    public static boolean checkEmailValid(String email) {
        if ((email == null) || (email.trim().length() == 0)) {
            return false;
        }
        String regEx = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(email.trim().toLowerCase());

        return m.find();
    }

    private static final Pattern IPV4_PATTERN = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");

    private static final Pattern IPV6_STD_PATTERN = Pattern.compile("^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");

    private static final Pattern IPV6_HEX_COMPRESSED_PATTERN = Pattern.compile("^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");

    static String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\." + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\:\\d{1,5}$";

    private static final Pattern IP_PORT = Pattern.compile(regex);

    public static boolean isIPv4Address(final String input) {
        return IPV4_PATTERN.matcher(input).matches();
    }

    public static boolean isIPv6StdAddress(final String input) {
        return IPV6_STD_PATTERN.matcher(input).matches();
    }

    public static boolean isIPv6HexCompressedAddress(final String input) {
        return IPV6_HEX_COMPRESSED_PATTERN.matcher(input).matches();
    }

    public static boolean isIPv6Address(final String input) {
        return isIPv6StdAddress(input) || isIPv6HexCompressedAddress(input);
    }

    public static boolean validateIpPort(final String input) {
        return IP_PORT.matcher(input).matches();
    }

    /**
     * @param mobile
     * @return
     */
    public static String formatMobileNumber(String mobile) {
        if (TextUtils.isEmpty(mobile)) {
            return "";
        }
        return mobile.replaceAll("[\\.\\-\\ ]", "").trim();
    }

//	public static String[] getMobiles(int type, String[] mobile_array, List<ECContacts> contactList) {
//		if (type == TYPE_ALL) {
//			ArrayList<String> phoneArrays = new ArrayList<String>();
//			try {
//				contactList = ContactsCache.getInstance().getContacts();
//				for (ECContacts c : contactList) {
//					List<Phone> phoneList = c.getPhoneList();
//					if (phoneList != null) {
//						int size = phoneList.size();
//						for (int i = 0; i < size; i++) {
//							Phone phone = phoneList.get(i);
//							if (phone != null) {
//								String phoneNumber = TextUtil.formatPhone(phone.getPhoneNum());
//								if (CheckUtil.checkMDN(phoneNumber)) {
//									// if(Global.clientInfo().getUserid().endsWith(phoneNumber))
//									// {
//									// continue;
//									// }
//									phoneArrays.add(phoneNumber);
//								}
//							}
//						}
//					}
//				}
//				String userId = AppMgr.getUserId();
//				if (!phoneArrays.contains(userId)) {
//					phoneArrays.add(userId);
//				}
//				return phoneArrays.toArray(new String[] {});
//			} catch (Exception e) {
//				e.printStackTrace();
//				return null;
//			} finally {
//				// if (contactList != null) {
//				// contactList.clear();
//				// }
//				// contactList = null;
//			}
//		} else if (type == TYPE_ADD) {
//			return mobile_array;
//		}
//		return null;
//	}

    // 过滤特殊字符
    public static String filterSpecialChars(String str) throws PatternSyntaxException {
        // 只允许字母和数字
        // String regEx = "[^a-zA-Z0-9]";
        // 清除掉所有特殊字符
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static InputFilter getNoEmoFilter() {
        InputFilter inputFilter = new InputFilter() {

            Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\u4E00-\\u9FA5_\\u0020]");

            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                Matcher matcher = pattern.matcher(charSequence);
                if (!matcher.find()) {
                    return null;
                } else {
                    ToastUtils.error("只能输入汉字,英文,数字");
                    return "";
                }

            }
        };

        return inputFilter;
    }

    public static InputFilter getNumAndEn() {
        InputFilter inputFilter = new InputFilter() {

            Pattern pattern = Pattern.compile("[^a-zA-Z0-9.]");

            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                Matcher matcher = pattern.matcher(charSequence);
                if (!matcher.find()) {
                    return null;
                } else {
                    ToastUtils.error("只能输入英文、数字和.");
                    return "";
                }

            }
        };

        return inputFilter;
    }


    public static InputFilter getNum() {
        InputFilter inputFilter = new InputFilter() {

            Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");

            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                Matcher matcher = pattern.matcher(charSequence);
                if (!matcher.find()) {
                    return null;
                } else {
                    ToastUtils.error("只能输入英文，数字");
                    return "";
                }

            }
        };

        return inputFilter;
    }

    public static String splitIp(String ip) {
        if (!TextUtils.isEmpty(ip)) {
            if (ip.contains("https://")) {
                String s = "https://";
                String[] split = ip.split(s);
                if (split.length > 1)
                    ip = split[1];
            } else if (ip.contains("http://")) {
                String s = "http://";
                String[] split = ip.split(s);
                if (split.length > 1)
                    ip = split[1];
            }
        }
        return ip;
    }

}
