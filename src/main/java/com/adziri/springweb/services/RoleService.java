package com.adziri.springweb.services;

import com.adziri.springweb.entities.Role;
import com.adziri.springweb.entities.User;
import com.adziri.springweb.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole(){
        return roleRepository.findByName("ROLE_USER").get();
    }
}

