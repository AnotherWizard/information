package cn.edu.csu.information.constants;

public interface CommonConstants {
    //点击排行展示的最多新闻数据
    Integer CLICK_RANK_MAX_NEWS = 6;
    //最新的新闻的状态码
    Integer NEWEST_STATUS_NEWS = 0;

    Integer DEFAULT_PAGE_SIZE = 10;

    Boolean IS_COLLECTED = true;

    Boolean NOT_COLLECTED = false;

    Boolean IS_FOLLOWED = true;

    Boolean NOT_FOLLOWED = false;

    String COOKIE_TOKEN = "token";
    String TOKEN_PREFIX = "token_";
    Integer TOKEN_EXPIRE = 7200;

    String DEFAULT_SOURCE = "个人发布";

    Integer NEWS_NOT_REVIEW = 1;

    Integer DEFAULT_PAGE = 0;

}
