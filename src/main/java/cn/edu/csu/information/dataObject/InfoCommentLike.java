package cn.edu.csu.information.dataObject;

import cn.edu.csu.information.dataObject.multiKeys.InfoCommentLikeMultiKey;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Date;

@Entity
@DynamicUpdate
@IdClass(InfoCommentLikeMultiKey.class)
@Data
public class InfoCommentLike implements Serializable {

    private Date createTime;

    private Date updateTime = new Date();

    @Id
    private Integer commentId;
    @Id
    private Integer userId;

    private static final long serialVersionUID = 1L;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}