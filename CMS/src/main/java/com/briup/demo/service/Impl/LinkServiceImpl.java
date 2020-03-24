package com.briup.demo.service.Impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 操作链接的service功能类
 */
import com.briup.demo.bean.Link;
import com.briup.demo.bean.LinkExample;
import com.briup.demo.bean.LinkExample.Criteria;
import com.briup.demo.mapper.LinkMapper;
import com.briup.demo.service.ILinkService;
import com.briup.demo.utils.CustomerException;
import com.briup.demo.utils.StatusCodeUtil;

@Service
public class LinkServiceImpl implements ILinkService {
	@Autowired
	private LinkMapper linkMapper;

	@Override
	public void saveOrUpdateLink(Link link) throws CustomerException {
		//参数为引用类型，要做判空处理
		if (link.getName()==null || link.getUrl()==null) {
			throw new CustomerException(StatusCodeUtil.ERROR_CODE,"链接名或链接地址为空");
		}
		//判断link对象的id是否为空，如果为空则新增，如果不为空则修改
		if (link.getId() == null) {
			linkMapper.insert(link);
		}else {
			linkMapper.updateByPrimaryKey(link);
		}
	}

	@Override
	public List<Link> findAllLinks() throws CustomerException {
		return linkMapper.selectByExample(new LinkExample());
	}

	@Override
	public void deleteLinkById(int id) throws CustomerException {
		linkMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<Link> findLinksByName(String name) throws CustomerException {
		name = name==null?"":name.trim();
		LinkExample example = new LinkExample();
		if ("".equals(name)) {
			//如果搜索条件没写，则返回所有数据
			return linkMapper.selectByExample(example);
		}else {
			//如果搜索条件不为空，则返回满足条件的数据
			Criteria criteria = example.createCriteria();
			criteria.andNameLike("%"+name+"%");
			return linkMapper.selectByExample(example);
		}
	}

}
