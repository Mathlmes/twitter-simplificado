package com.ifs.twittersimplificado.repository;


import com.ifs.twittersimplificado.entities.Role;
import com.ifs.twittersimplificado.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
