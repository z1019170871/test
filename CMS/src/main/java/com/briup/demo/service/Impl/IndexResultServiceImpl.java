package com.briup.demo.service.Impl;
/**
 * 查询首页所有数据的实现类
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.briup.demo.bean.ex.IndexResult;
import com.briup.demo.service.ICategoryService;
import com.briup.demo.service.IIndexResultService;
import com.briup.demo.service.ILinkService;
import com.briup.demo.utils.CustomerException;

@Service
public class IndexResultServiceImpl implements IIndexResultService{
	
	//关联service层接口，而不是关联mapper接口
	@Autowired
	private ILinkService linkService;
	@Autowired
	private ICategoryService categoryService;

	@Override
	public IndexResult findIndexAllResult() throws CustomerException {
		IndexResult indexResult = new IndexResult();
		indexResult.setLinks(linkService.findAllLinks());
		indexResult.setCategoryExs(categoryService.findAllCategoryEx());
		return indexResult;
	}

}
