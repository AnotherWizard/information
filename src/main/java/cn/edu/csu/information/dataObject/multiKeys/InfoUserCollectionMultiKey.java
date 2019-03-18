package cn.edu.csu.information.dataObject.multiKeys;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liuchengsheng
 */
@Data
public class InfoUserCollectionMultiKey implements Serializable {
    private Integer userId;
    private Integer newsId;

    public InfoUserCollectionMultiKey(Integer userId, Integer newsId){
        this.userId = userId;
        this.newsId = newsId;
    }

    public InfoUserCollectionMultiKey(){

    }

}
