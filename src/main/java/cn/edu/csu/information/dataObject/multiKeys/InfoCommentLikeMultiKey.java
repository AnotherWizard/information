package cn.edu.csu.information.dataObject.multiKeys;


import lombok.Data;

import java.io.Serializable;

@Data
public class InfoCommentLikeMultiKey implements Serializable {

    private Integer commentId;

    private Integer userId;

    public InfoCommentLikeMultiKey(Integer userId, Integer commentId){
        this.commentId = commentId;
        this.userId = userId;
    }

    public InfoCommentLikeMultiKey(){

    }
}
