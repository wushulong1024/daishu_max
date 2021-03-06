package com.daishu.daishu_user_service.usermapper.mapper;

import com.daishu.pojo.TbCities;
import com.daishu.pojo.TbCitiesExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TbCitiesMapper {
    long countByExample(TbCitiesExample example);

    int deleteByExample(TbCitiesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbCities record);

    int insertSelective(TbCities record);

    List<TbCities> selectByExample(TbCitiesExample example);

    TbCities selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbCities record, @Param("example") TbCitiesExample example);

    int updateByExample(@Param("record") TbCities record, @Param("example") TbCitiesExample example);

    int updateByPrimaryKeySelective(TbCities record);

    int updateByPrimaryKey(TbCities record);
}