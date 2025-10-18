package com.ifs.twittersimplificado.entities;


import jakarta.persistence.*;
import org.springframework.security.access.prepost.PreAuthorize;

@Entity
@Table(name = "tb_role")
public class Role {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   
    public enum values{

        BASIC(1L),

        ADMIN(2L);


        long roleId;

        values(long roleId){
            this.roleId = roleId;
        }

        public long getRoleId() {
            return roleId;
        }
    }
}
