package com.example.shabbyshackinn.security;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface RoleRepo extends CrudRepository<Role , UUID> {
    @Query("SELECT r.name FROM Role r")
    List<String> findAllRoleNames();
    Role findByName(String name);
}
