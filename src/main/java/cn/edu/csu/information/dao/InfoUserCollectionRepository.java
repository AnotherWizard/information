package cn.edu.csu.information.dao;

import cn.edu.csu.information.dataObject.InfoUserCollection;
import cn.edu.csu.information.dataObject.multiKeys.InfoUserCollectionMultiKey;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InfoUserCollectionRepository extends JpaRepository<InfoUserCollection, InfoUserCollectionMultiKey> {
    InfoUserCollection findByUserIdAndNewsId(Integer userId,Integer newsId);





}