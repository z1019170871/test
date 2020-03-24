package com.briup.demo.service;

import com.briup.demo.bean.ex.IndexResult;
import com.briup.demo.utils.CustomerException;

/**
 * 首页数据管理的service接口
 * @author asus
 *
 */
public interface IIndexResultService {

	/**
	 * 查询首页需要的所有数据
	 * @return
	 * @throws CustomerException
	 */
	IndexResult findIndexAllResult() throws CustomerException;
	
}
