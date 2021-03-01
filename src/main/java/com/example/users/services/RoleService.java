package com.example.users.services;


import com.example.users.entities.Role;
import com.example.users.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }


    public List<Role> getRoles(){
        return roleRepository.findAll();
    }

    public Role createRole(Role role) {
        return roleRepository.save(role); // generates the necessary keys
    }

    public Role updateRole(Integer roleId, Role role) {
        Optional<Role> result = roleRepository.findById(roleId);

        if(result.isPresent()){
            return roleRepository.save(role);
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Role id: %d does not exist", roleId));
        }

    }

    public void deleteRole(Integer roleId) {
        Optional<Role> result = roleRepository.findById(roleId);

        if(result.isPresent()){
            roleRepository.delete(result.get());
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Role id: %d does not exist", roleId));
        }
    }
}
