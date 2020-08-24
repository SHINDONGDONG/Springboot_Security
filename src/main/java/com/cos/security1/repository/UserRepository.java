package com.cos.security1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.security1.model.User;

//CRUD 함수를 JAPRepository가 들고있음
//@Repository라는 어노테이션이 없어도 IoC가 된다. 이유는 japRepository를 상속했기 때문에
public interface UserRepository extends JpaRepository<User, Integer>{

	//findBy규칙- >Username 문법 
	//select * from user where username =1?;
	public User findByUsername(String username); //JPA네이밍 쿼리메소드
}
