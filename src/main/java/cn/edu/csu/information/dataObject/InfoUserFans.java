package cn.edu.csu.information.dataObject;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class InfoUserFans implements Serializable {

    @Id
    private Integer followerId;

    private Integer followedId;

    private static final long serialVersionUID = 1L;

    public Integer getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Integer followerId) {
        this.followerId = followerId;
    }

    public Integer getFollowedId() {
        return followedId;
    }

    public void setFollowedId(Integer followedId) {
        this.followedId = followedId;
    }
}