package com.example.demo.service;

import com.example.demo.model.Role;
import com.example.demo.repository.RoleRepository;

public interface RoleService {

    Role findRoleByName(String Name);
}
