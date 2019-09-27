package com.daishu.seckill.service;
import com.daishu.pojo.TbSeckillGoods;

import java.util.List;

/**
 * 秒杀服务层接口
 * @author Administrator
 *
 */
public interface SeckillGoodsService {

	/**
	 * 正在参与秒杀的商品
	 * @return
	 */
	public  List<TbSeckillGoods> findList();

	/**
	 *从缓存读取
 	 */
	public  TbSeckillGoods findOneFromRedis(Long id);


	/**
	 *从缓存读取
	 */
	public  TbSeckillGoods findOne(Long id);






}
