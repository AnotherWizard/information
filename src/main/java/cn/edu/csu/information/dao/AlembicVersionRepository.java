package cn.edu.csu.information.dao;

import cn.edu.csu.information.dataObject.AlembicVersion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlembicVersionRepository extends JpaRepository<AlembicVersion, String> {


}