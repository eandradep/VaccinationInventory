package com.kruger.vaccination.inventory.models.dao;

import com.kruger.vaccination.inventory.models.entity.UserAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IUserDao extends CrudRepository<UserAccount, Long>{
	
	UserAccount findByUsername(String username);

	List<UserAccount> findUserAccountsByUsernameStartingWith(String username);

}
