package cn.edu.csu.information.dataObject;

import cn.edu.csu.information.dataObject.multiKeys.InfoUserCollectionMultiKey;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Date;

@Entity
@IdClass(InfoUserCollectionMultiKey.class)
public class InfoUserCollection implements Serializable {

    @Id
    private Integer userId;

    @Id
    private Integer newsId;

    private Date createTime;

    public InfoUserCollection(Integer userId, Integer newsId, Date createTime){
        this.userId = userId;
        this.newsId = newsId;
        this.createTime = createTime;
    }

    public InfoUserCollection(){

    }

    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}