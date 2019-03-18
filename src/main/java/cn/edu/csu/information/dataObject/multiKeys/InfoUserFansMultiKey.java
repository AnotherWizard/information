package cn.edu.csu.information.dataObject.multiKeys;

import lombok.Data;

import java.io.Serializable;

@Data
public class InfoUserFansMultiKey implements Serializable {

    private Integer followerId;

    private Integer followedId;

    public InfoUserFansMultiKey(Integer followerId, Integer followedId){
        this.followerId = followerId;
        this.followedId = followedId;
    }

    public InfoUserFansMultiKey(){

    }
}
