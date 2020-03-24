package com.briup.demo.service.Impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.briup.demo.bean.Article;
import com.briup.demo.bean.ArticleExample;
import com.briup.demo.bean.Category;
import com.briup.demo.bean.CategoryExample;
import com.briup.demo.mapper.ArticleMapper;
import com.briup.demo.mapper.CategoryMapper;
import com.briup.demo.service.IArticleService;
import com.briup.demo.utils.CustomerException;
import com.briup.demo.utils.StatusCodeUtil;

/**
 * 实现文章管理相关的逻辑类
 * @author asus
 *
 */
@Service
public class ArticleServiceImpl implements IArticleService{
	
	@Autowired
	private ArticleMapper articleMapper;
	@Autowired
	private CategoryMapper categoryMapper;

	@Override
	public void saveOrUpdateArticle(Article article) throws CustomerException {
		if (article == null) {
			throw new CustomerException(StatusCodeUtil.ERROR_CODE, "文章参数为空");
		}
		if (article.getId() == null) {
			//需要添加日期和点击个数
			article.setPublishdate(new Date());
			article.setClicktimes(0);
			articleMapper.insert(article);
		}else {
			article.setPublishdate(new Date());
			Integer clicktimes = articleMapper.selectByPrimaryKey(article.getId()).getClicktimes();
			article.setClicktimes(clicktimes);
			articleMapper.updateByPrimaryKey(article);
		}
	}

	@Override
	public void deleteArticleById(int id) throws CustomerException {
		articleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<Article> findArticleByCondition(String keyStr, String condition) 
			throws CustomerException {
		/*
		 * 1.没有添加任何条件，则查询所有文章
		 * 2.没有指定栏目，但指定了关键字，则根据关键字查询
		 * 3.没有指定关键字，但指定了栏目，则根据栏目查询
		 * 4.指定栏目同时指定查询的关键字，则根据栏目和关键字查询满足条件的文章
		 */
		keyStr = keyStr==null?"":keyStr.trim();
		condition = condition==null?"":condition.trim();

		ArticleExample articleExample = new ArticleExample();
		
		if ("".equals(keyStr) && "".equals(condition)) {
			//情况1
			return articleMapper.selectByExample(articleExample);
		}else if (!"".equals(condition) && "".equals(condition)) {
			//情况2
			articleExample.createCriteria().andTitleLike("%"+keyStr+"%");
			return articleMapper.selectByExample(articleExample);
		}else if (!"".equals(condition) && "".equals(keyStr)) {
			//情况3 先根据栏目名查到栏目id 再通过栏目id查找属于该id的文章
			CategoryExample categoryExample = new CategoryExample();
			categoryExample.createCriteria().andNameEqualTo(condition);
			List<Category> list = categoryMapper.selectByExample(categoryExample);
			if (list.size() > 0) {
				Integer id = list.get(0).getId();
				articleExample.createCriteria().andCategoryIdEqualTo(id);
				return articleMapper.selectByExample(articleExample);
			}else {
				throw new CustomerException(StatusCodeUtil.ERROR_CODE, "没有指定的搜索栏目");
			}
		}else {
			//情况4 先根据栏目名查到栏目id 再通过栏目id查找属于该id的文章 并且还要满足关键字
			CategoryExample categoryExample = new CategoryExample();
			categoryExample.createCriteria().andNameEqualTo(condition);
			List<Category> list = categoryMapper.selectByExample(categoryExample);
			if (list.size() > 0) {
				Integer id = list.get(0).getId();
				articleExample.createCriteria().andCategoryIdEqualTo(id).andTitleLike("%"+keyStr+"%");
				return articleMapper.selectByExample(articleExample);
			}else {
				throw new CustomerException(StatusCodeUtil.ERROR_CODE, "没有指定的搜索栏目");
			}
		}
	}

	@Override
	public Article findArticleById(Integer id) throws CustomerException {
		if (id==null || id<=0) {
			throw new CustomerException(StatusCodeUtil.ERROR_CODE, "文章id有误");
		}else {
			Article article = articleMapper.selectByPrimaryKey(id);
			article.setClicktimes(article.getClicktimes()+1);
			articleMapper.updateByPrimaryKey(article);
			return article;
		}
	}

}
