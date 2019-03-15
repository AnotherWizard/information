package cn.edu.csu.information.constants;

public interface AdminConstants {
    Boolean ADMIN = Boolean.TRUE;
    Boolean NOT_ADMIN = Boolean.FALSE;

    Integer NEWS_REVIEW_PASS = 0;
    Integer NEWS_REVIEW_NOT_PASS = -1;
    String REVIEW_ACCEPT = "accept";
    String REVIEW_REJECT = "reject";
    Integer DEFAULT_PAGE_SIZE = 10;

    Integer CATEGORY_OF_NEW = 1;

    String QINIU_DOMIN_PREFIX = "http://";

    String IMAGE_CODE_ID_PREFIX = "ImageCodeId_";

    String SMS_CODE_PREFIX = "SMS_";
    Integer IMAGE_CODE_REDIS_EXPIRES = 300;
    Integer SMS_CODE_REDIS_EXPIRES = 300;

}
