package com.briup.demo.service.Impl;
/**
 * 栏目管理相关功能实现类
 */
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.briup.demo.bean.Article;
import com.briup.demo.bean.ArticleExample;
import com.briup.demo.bean.ArticleExample.Criteria;
import com.briup.demo.bean.Category;
import com.briup.demo.bean.CategoryExample;
import com.briup.demo.bean.ex.CategoryEx;
import com.briup.demo.mapper.ArticleMapper;
import com.briup.demo.mapper.CategoryMapper;
import com.briup.demo.mapper.ex.CategoryExMapper;
import com.briup.demo.service.ICategoryService;
import com.briup.demo.utils.CustomerException;
import com.briup.demo.utils.StatusCodeUtil;

@Service
public class CategoryServiceImpl implements ICategoryService{
	
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private ArticleMapper articleMapper;
	@Autowired
	private CategoryExMapper categoryExMapper;  //栏目的拓展dao

	@Override
	public List<Category> findAllCategories() throws CustomerException {
		return categoryMapper.selectByExample(new CategoryExample());
	}

	@Override
	public void saveOrUpdateCategory(Category category) throws CustomerException {
		if (category.getCode()==null || category.getName()==null) {
			throw new CustomerException(StatusCodeUtil.ERROR_CODE, "栏目名或栏目编号为空");
		}
		
		if (category.getId() == null) { //id为空,新增数据
			//保存前先查询传来的栏目编码和栏目名称是否已存在，以保证栏目编码和栏目名称都不能重复
			CategoryExample example = new CategoryExample();
			com.briup.demo.bean.CategoryExample.Criteria criteria = example.createCriteria();
			criteria.andCodeEqualTo(category.getCode());
			example.or().andNameEqualTo(category.getName());
			List<Category> list = categoryMapper.selectByExample(example);
			if (list.size()>0) {
				throw new CustomerException(StatusCodeUtil.ERROR_CODE, "栏目编码或栏目名称已存在");
			}
			categoryMapper.insert(category);
		}else { //id不为空,修改数据
			categoryMapper.updateByPrimaryKey(category);
		}
	}

	@Override
	public void deleteCategoryById(int id) throws CustomerException {
		//删除栏目的同时，需要先删除栏目中包含的文章信息
		ArticleExample example = new ArticleExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(id);
		articleMapper.deleteByExample(example);
		
		categoryMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Category findCategoryById(int id) throws CustomerException {
		return categoryMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<CategoryEx> findAllCategoryEx() throws CustomerException {
		return categoryExMapper.findAllCategoryExs();
	}

	@Override
	public List<Article> findArticlesByCategoryId(Integer categoryId) throws CustomerException {
		if (categoryId==null || categoryId<=0) {
			throw new CustomerException(StatusCodeUtil.ERROR_CODE, "栏目id不正确");
		}else {
			ArticleExample example = new ArticleExample();
			example.createCriteria().andCategoryIdEqualTo(categoryId);
			return articleMapper.selectByExample(example);
		}
	}

	@Override
	public CategoryEx findCategoryExById(Integer id) throws CustomerException {
		return categoryExMapper.findCategoryExById(id);
	}

}
