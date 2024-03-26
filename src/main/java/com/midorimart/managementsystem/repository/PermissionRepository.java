package com.midorimart.managementsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.midorimart.managementsystem.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer>{

    public Optional<Permission> findByPathAndMethod(String path, String method);

}
