package com.lemomcrab.TribunaldoZodiacoBackend.repository;

import com.lemomcrab.TribunaldoZodiacoBackend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
