package cn.edu.csu.information.dao;

import cn.edu.csu.information.dataObject.InfoUserFans;
import cn.edu.csu.information.dataObject.multiKeys.InfoUserFansMultiKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface InfoUserFansRepository  extends JpaRepository<InfoUserFans, InfoUserFansMultiKey> {

    List<InfoUserFans> findByFollowerId(Integer followerId);

    List<InfoUserFans> findByFollowedId(Integer followedId);

    @Override
    <S extends InfoUserFans> S save(S s);

    @Override
    void deleteById(InfoUserFansMultiKey infoUserFansMultiKey);
}