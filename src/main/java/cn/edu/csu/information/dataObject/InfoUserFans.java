package cn.edu.csu.information.dataObject;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@DynamicUpdate
public class InfoUserFans implements Serializable {

    @Id
    private Integer followerId;

    private Integer followedId;

    private static final long serialVersionUID = 1L;

    public InfoUserFans(Integer followerId, Integer followedId){
        this.followerId = followerId;
        this.followedId = followedId;
    }

    public InfoUserFans(){

    }

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