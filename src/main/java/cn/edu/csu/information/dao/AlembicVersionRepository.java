package cn.edu.csu.information.dao;

import cn.edu.csu.information.dataObject.AlembicVersion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface AlembicVersionRepository extends JpaRepository<AlembicVersion, String> {

//    @Query("select * from AlembicVersion ")
//    Page<AlembicVersion> findList(Pageable pageable);


    @Override
    Optional<AlembicVersion> findById(String id);

    AlembicVersion findByVersionNum(String versionNum);

    @Override
    void deleteById(String id);
}