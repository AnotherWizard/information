package cn.edu.csu.information.dao;

import cn.edu.csu.information.dataObject.InfoUserCollection;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InfoUserCollectionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table info_user_collection
     *
     * @mbg.generated Mon Mar 11 20:26:22 CST 2019
     */
    int insert(InfoUserCollection record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table info_user_collection
     *
     * @mbg.generated Mon Mar 11 20:26:22 CST 2019
     */
    int insertSelective(InfoUserCollection record);
}