package com.kruger.vaccination.inventory.models.services.user;

import com.kruger.vaccination.inventory.models.entity.Employee;
import com.kruger.vaccination.inventory.models.entity.UserAccount;
import org.springframework.transaction.annotation.Transactional;

public interface IUsuarioService {

	@Transactional(readOnly=true)
	UserAccount findByUsername(String username);

	@Transactional()
	UserAccount saveUserAccount(Employee employee);
}
