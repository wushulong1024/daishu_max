package com.daishu.daishu_order_service.mapper;

import com.daishu.pojo.TbGoodsDesc;
import com.daishu.pojo.TbGoodsDescExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TbGoodsDescMapper {
    long countByExample(TbGoodsDescExample example);

    int deleteByExample(TbGoodsDescExample example);

    int deleteByPrimaryKey(Long goodsId);

    int insert(TbGoodsDesc record);

    int insertSelective(TbGoodsDesc record);

    List<TbGoodsDesc> selectByExample(TbGoodsDescExample example);

    TbGoodsDesc selectByPrimaryKey(Long goodsId);

    int updateByExampleSelective(@Param("record") TbGoodsDesc record, @Param("example") TbGoodsDescExample example);

    int updateByExample(@Param("record") TbGoodsDesc record, @Param("example") TbGoodsDescExample example);

    int updateByPrimaryKeySelective(TbGoodsDesc record);

    int updateByPrimaryKey(TbGoodsDesc record);
}