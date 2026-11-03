package com.becoder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.becoder.model.Userdtls;

public interface UserRepository extends JpaRepository<Userdtls,Integer>{

	Userdtls findByUsername(String username);

}
