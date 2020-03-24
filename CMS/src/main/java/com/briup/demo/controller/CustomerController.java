package com.briup.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.briup.demo.bean.Customer;
import com.briup.demo.service.ICustomerService;
import com.briup.demo.utils.CustomerException;
import com.briup.demo.utils.Message;
import com.briup.demo.utils.MessageUtil;
import com.briup.demo.utils.StatusCodeUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "用户相关操作接口")
public class CustomerController {
	
	@Autowired
	private ICustomerService customerService;
	
	@GetMapping("/findCustomerByName")
	@ApiOperation("通过用户名查找用户")
	public Message<Customer> findCustomerByName(String username) {
		return MessageUtil.success(customerService.findCustomerByName(username));
	}
	
	@PostMapping("/saveCustomer")
	@ApiOperation("保存新用户")
	public Message<String> saveCustomer(Customer customer) {
		try {
			customerService.saveCustomer(customer);
			return MessageUtil.success();
		} catch (CustomerException e) {
			return MessageUtil.error(StatusCodeUtil.ERROR_CODE, "系统错误:"+e.getMessage());
		}
	}
	
	@PostMapping("/updateCustomer")
	@ApiOperation("更新用户信息")
	public Message<String> updateCustomer(Customer customer) {
		try {
			customerService.updateCustomer(customer);
			return MessageUtil.success();
		} catch (CustomerException e) {
			return MessageUtil.error(StatusCodeUtil.ERROR_CODE, "系统错误:"+e.getMessage());
		}
	}
	
	@GetMapping("/checkCustomer")
	@ApiOperation("登录验证")
	public Message<Boolean> checkCustomer(String username, String password) {
		try {
			return MessageUtil.success(customerService.checkCustomer(username, password));
		} catch (CustomerException e) {
			return MessageUtil.error(StatusCodeUtil.ERROR_CODE, "系统错误:"+e.getMessage());
		}
	}
}
