package cn.itrip.common;

/**
 * 系统错误编码，根据业务定义如下
 * <br/>
 * 酒店主业务biz：1开头（10000）<br/>
 * 评论：10001 ——10100<br/>
 * 酒店详情：10101 ——10200<br/>
 * 订单：10201 ——10400<br/>
 * 搜索search：2开头（20000）<br/>
 * 认证auth：3开头（30000）<br/>
 * 支付trade：4开头（40000）<br/>
 *
 * @author hduser
 */
public class ErrorCode {

    /*认证模块错误码-start*/
    public final static String AUTH_UNKNOWN = "30000";
    public final static String AUTH_USER_ALREADY_EXISTS = "30001";//用户已存在
    public final static String AUTH_AUTHENTICATION_FAILED = "30002";//认证失败
    public final static String AUTH_PARAMETER_ERROR = "30003";//用户名密码参数错误，为空
    public final static String AUTH_ACTIVATE_FAILED = "30004";//邮件注册，激活失败
    public final static String AUTH_REPLACEMENT_FAILED = "30005";//置换token失败
    public final static String AUTH_TOKEN_INVALID = "30006";//token无效
    public static final String AUTH_ILLEGAL_USERCODE = "30007";//非法的用户名
    /*认证模块错误码-end*/
    public static final String SEARCH_CITYID_NOTFOUND = "20004";
    public static final String SEARCH_NUKNOW = "20003";
    public static final String SEARCH_DESTINATION_NOTNULL = "20002";


    public static final String BIZ_CITYID_NOTNULL = "10203";
    public static final String BIZ_UNKNOWN = "10204";

    public static final String BIZ_TARGETID_NOTNULL = "100213";
    public static final String BIZ_GETIMAGEFAIL = "100212";

    public static final String BIZ_HOTLEID_NOTNULL = "100215";
    public static final String BIZ_GETFEATUREFAIL = "100215";


    public static final String BIZ_ID_NOTNULL = "10206";
    public static final String BIZ_GETFACILITIESFAIL = "10207";

    public static final String BIZ_GETLABELfAIL = "10205";

    public static final String BIZ_TYPE_NOTNULL = "10201";
    public static final String BIZ_GETTYPEfAIL = "10202";

    public static final String BIZ_HOTELID_NOTNULL = "10210";
    public static final String BIZ_GETDETAILSFAIL = "10202";

    public static final String BIZ_HOTELORDATE_ERROR = "100303";
    public static final String BIZ_SYSTEMEXCEPTION = "100304";

    public static final String BIZ_GETHOTELID_NOTNULL = "10208";
    public static final String BIZ_ASYSYTEMERROR = "10209";

    public static final String BIZ_GETIMG_FAIL = "100301 ";
    public static final String BIZ_ROOMID_NOTNULL = "100302";

    public static final String BIZ_GETROOMBEDTYP_FAIL = "100305";

    public static final String COMMENT_GETTRAVELTYP_FAIL = "100019";


    public static final String COMMENT_GETIMG_FAIL = "100012";
    public static final String COMMENT_TARGETID_NOTNULL = "100013";


}
