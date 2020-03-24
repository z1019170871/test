package com.briup.demo.service;
/**
 * 栏目相关的Service层
 * @author asus
 *
 */

import java.util.List;

import com.briup.demo.bean.Article;
import com.briup.demo.bean.Category;
import com.briup.demo.bean.ex.CategoryEx;
import com.briup.demo.utils.CustomerException;

public interface ICategoryService {

	/**
	 * 查询所有栏目
	 */
	public List<Category> findAllCategories() throws CustomerException;
	
	/**
	 * 添加或修改栏目信息
	 */
	public void saveOrUpdateCategory(Category category) throws CustomerException;
	
	/**
	 * 根据id删除栏目信息
	 */
	public void deleteCategoryById(int id) throws CustomerException;
	
	/**
	 * 根据id查找指定的栏目信息
	 */
	public Category findCategoryById(int id) throws CustomerException;
	
	/**
	 * 查询栏目信息并且级联查询包含的文章信息
	 */
	List<CategoryEx> findAllCategoryEx() throws CustomerException;
	
	/**
	 * 根据栏目id查询其包含的文章
	 * @param categoryId
	 * @return
	 * @throws CustomerException
	 */
	List<Article> findArticlesByCategoryId(Integer categoryId) throws CustomerException;
	
	/**
	 * 查询栏目及其包含的文章信息
	 */
	CategoryEx findCategoryExById(Integer id) throws CustomerException;
}
