package com.briup.demo.service;

import com.briup.demo.bean.Customer;
import com.briup.demo.utils.CustomerException;

public interface ICustomerService {
	
	/**
	 * 根据名字查询用户信息
	 * @param name
	 * @return
	 */
	Customer findCustomerByName(String username) throws CustomerException;
	
	/**
	 * 插入用户信息
	 * @param customer
	 */
	void saveCustomer(Customer customer) throws CustomerException;
	
	/**
	 * 更新用户信息
	 * @param customer
	 * @throws CustomerException
	 */
	void updateCustomer(Customer customer) throws CustomerException;
	
	/**
	 * 登录验证
	 * @param username
	 * @param password
	 * @return true为验证成功 否则抛出异常
	 * @throws CustomerException
	 */
	Boolean checkCustomer(String username,String password) throws CustomerException;
}
