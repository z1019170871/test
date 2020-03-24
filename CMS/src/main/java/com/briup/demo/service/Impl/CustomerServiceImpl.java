package com.briup.demo.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.briup.demo.bean.Customer;
import com.briup.demo.jpadao.CustomerDao;
import com.briup.demo.service.ICustomerService;
import com.briup.demo.utils.CustomerException;
import com.briup.demo.utils.StatusCodeUtil;

@Service
public class CustomerServiceImpl implements ICustomerService{
	
	@Autowired
	private CustomerDao customerDao;

	@Override
	public Customer findCustomerByName(String username) throws CustomerException{
		Customer customer = customerDao.findByUsername(username);
		return customer;
	}

	@Override
	public void saveCustomer(Customer customer) throws CustomerException{
		if (customer != null) {
			Customer oldCustomer = customerDao.findByUsername(customer.getUsername());
			if (oldCustomer == null) {
				customerDao.save(customer);
			}else {
				throw new CustomerException(StatusCodeUtil.ERROR_CODE, "用户名已存在");
			}
		}else {
			throw new CustomerException(StatusCodeUtil.ERROR_CODE, "注册的用户名或密码不能为空");
		}
	}

	@Override
	public void updateCustomer(Customer customer) throws CustomerException {
		if (customer != null) {
			if (customer.getUsername()!=null && customer.getPassword()!=null) {
				customerDao.updateCustomer(customer.getUsername(), customer.getPassword(),customer.getId());
			}else {
				throw new CustomerException(StatusCodeUtil.ERROR_CODE, "用户名或密码不能为空");
			}
		}else {
			throw new CustomerException(StatusCodeUtil.ERROR_CODE, "用户信息不能为空");
		}
	}

	@Override
	public Boolean checkCustomer(String username, String password) throws CustomerException {
		Customer customer = customerDao.findByUsername(username);
		if (customer == null) {
			throw new CustomerException(StatusCodeUtil.ERROR_CODE, "用户不存在");
		}else if(!customer.getPassword().equals(password)) {
			throw new CustomerException(StatusCodeUtil.ERROR_CODE, "密码错误");
		}else {
			return true;
		}
	}
}
