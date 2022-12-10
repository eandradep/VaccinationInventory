package com.kruger.vaccination.inventory.models.services.user;

import com.google.common.base.CaseFormat;
import com.kruger.vaccination.inventory.models.dao.IUserDao;
import com.kruger.vaccination.inventory.models.entity.Employee;
import com.kruger.vaccination.inventory.models.entity.Role;
import com.kruger.vaccination.inventory.models.entity.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements IUsuarioService, UserDetailsService {

    @Autowired
    private IUserDao iUserDao;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount usuario = iUserDao.findByUsername(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Error en el login: no existe el usuario '" + username + "' en el sistema!");
        }
        List<GrantedAuthority> authorities = usuario.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getNombre()))
                .collect(Collectors.toList());
        return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
    }

    @Override
    @Transactional(readOnly = true)
    public UserAccount findByUsername(String username) {
        return iUserDao.findByUsername(username);
    }

    @Override
    public UserAccount saveUserAccount(Employee employee) {
        UserAccount userAccount = new UserAccount();
        userAccount.setUsername(getUserName(employee));
        userAccount.setPassword(passwordEncoder.encode(employee.getEmployeeIdentification()));
        userAccount.setIdentification(employee.getEmployeeIdentification());
        userAccount.setEnabled(true);
        userAccount.setRoles(Collections.singletonList(new Role(2L)));
        return iUserDao.save(userAccount);
    }

    private String getUserName(Employee employee){
        String username =
                employee.getEmployeeName().concat(" ").concat(employee.getEmployeeLastName())
                        .toUpperCase()
                        .replace(" ", "_");
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,username);
    }

}
