package com.briup.demo.jpadao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.briup.demo.bean.Customer;

public interface CustomerDao extends JpaRepository<Customer, Integer>{
	
	Customer findByUsername(String username); 
	
	@Modifying
	@Transactional
	@Query("update Customer c set c.username = ?1 , c.password = ?2 where c.id = ?3")
	void updateCustomer(String username,String password,Integer id); //更新用户信息
}
