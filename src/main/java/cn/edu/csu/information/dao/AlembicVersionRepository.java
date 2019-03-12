package cn.edu.csu.information.dao;

import cn.edu.csu.information.dataObject.AlembicVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlembicVersionRepository extends JpaRepository<AlembicVersion, String> {

}