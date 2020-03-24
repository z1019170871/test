package com.briup.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.briup.demo.bean.Article;
import com.briup.demo.bean.Category;
import com.briup.demo.bean.ex.CategoryEx;
import com.briup.demo.service.ICategoryService;
import com.briup.demo.utils.CustomerException;
import com.briup.demo.utils.Message;
import com.briup.demo.utils.MessageUtil;
import com.briup.demo.utils.StatusCodeUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "栏目相关接口")
public class CategoryController {
	
	@Autowired
	private ICategoryService categoryService;
	
	@GetMapping("/selectCategory")
	@ApiOperation("查询所有栏目")
	public Message<List<Category>> selectCategory() {
		return MessageUtil.success(categoryService.findAllCategories());
	}
	
	@PostMapping("/addCategory")
	@ApiOperation("添加栏目")
	public Message<String> addCategory(Category category) {
		try {
			categoryService.saveOrUpdateCategory(category);
			return MessageUtil.success();
		} catch (CustomerException e) {
			return MessageUtil.error(StatusCodeUtil.ERROR_CODE, "系统错误:"+e.getMessage());
		}
	}
	
	@PostMapping("/updateCategory")
	@ApiOperation("修改栏目")
	public Message<String> updateCategory(Category category) {
		try {
			categoryService.saveOrUpdateCategory(category);
			return MessageUtil.success();
		} catch (CustomerException e) {
			return MessageUtil.error(StatusCodeUtil.ERROR_CODE, "系统错误:"+e.getMessage());
		}
	}
	
	@GetMapping("/deleteCategoryById")
	@ApiOperation("通过id删除栏目")
	public Message<String> deleteCategoryById(int id) {
		categoryService.deleteCategoryById(id);
		return MessageUtil.success();
	}
	
	@GetMapping("/selectCategoryById")
	@ApiOperation("通过id查询指定栏目信息")
	public Message<Category> selectCategoryById(int id) {
		return MessageUtil.success(categoryService.findCategoryById(id));
	}
	
	@GetMapping("/selectArticleByCategoryId")
	@ApiOperation("通过栏目id查询其包含的文章")
	public Message<List<Article>> selectArticleByCategoryId(Integer categoryId) {
		System.out.println(categoryId);
		try {
			return MessageUtil.success(categoryService.findArticlesByCategoryId(categoryId));
		} catch (CustomerException e) {
			return MessageUtil.error(StatusCodeUtil.ERROR_CODE, "系统错误:"+e.getMessage());
		}
	}
	
	/**
	 * 根据id查找栏目及其包含的所有文章信息
	 */
	@GetMapping("/findCategoryExById")
	@ApiOperation("根据栏目id查找栏目及其包含的所有文章信息")
	public Message<CategoryEx> findCategoryExById(Integer id) {
		return MessageUtil.success(categoryService.findCategoryExById(id));
	}
}
