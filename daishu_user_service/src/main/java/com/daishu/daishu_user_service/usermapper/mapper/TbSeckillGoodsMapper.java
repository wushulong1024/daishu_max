package com.daishu.daishu_user_service.usermapper.mapper;

import com.daishu.pojo.TbSeckillGoods;
import com.daishu.pojo.TbSeckillGoodsExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TbSeckillGoodsMapper {
    long countByExample(TbSeckillGoodsExample example);

    int deleteByExample(TbSeckillGoodsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbSeckillGoods record);

    int insertSelective(TbSeckillGoods record);

    List<TbSeckillGoods> selectByExample(TbSeckillGoodsExample example);

    TbSeckillGoods selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbSeckillGoods record, @Param("example") TbSeckillGoodsExample example);

    int updateByExample(@Param("record") TbSeckillGoods record, @Param("example") TbSeckillGoodsExample example);

    int updateByPrimaryKeySelective(TbSeckillGoods record);

    int updateByPrimaryKey(TbSeckillGoods record);
}