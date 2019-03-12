package cn.edu.csu.information.dao;

import cn.edu.csu.information.dataObject.InfoUserCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoUserCollectionRepository extends JpaRepository<InfoUserCollection,Integer> {

}