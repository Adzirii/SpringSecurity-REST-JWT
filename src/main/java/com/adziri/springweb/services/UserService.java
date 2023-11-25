package com.adziri.springweb.services;

import com.adziri.springweb.dto.RegistrationUserDTO;
import com.adziri.springweb.entities.User;
import com.adziri.springweb.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public Optional<User> findByUsername(String username){
        return userRepository.findUserByUsername(username);
    }
    public Optional<User> getUserById(Long userId) {
        return userRepository.findUserById(userId);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        org.springframework.security.core.userdetails.User.UserBuilder userBuilder = null;
        User user = userRepository.findUserByUsername(username).orElseThrow(()-> new UsernameNotFoundException(
                String.format("User '%s' not found", username)
        ));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList()
        );
    }

    public User createNewUser(RegistrationUserDTO userDTO){
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRoles(List.of(roleService.getUserRole()));
        return userRepository.save(user);
    }
}

