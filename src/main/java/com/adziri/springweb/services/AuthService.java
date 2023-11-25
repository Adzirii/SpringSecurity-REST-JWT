package com.adziri.springweb.services;

import com.adziri.springweb.dto.JwtRequest;
import com.adziri.springweb.dto.JwtResponse;
import com.adziri.springweb.dto.RegistrationUserDTO;
import com.adziri.springweb.dto.UserDTO;
import com.adziri.springweb.entities.User;
import com.adziri.springweb.exceptions.AppError;
import com.adziri.springweb.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;


    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        // TODO: обработка исключений через глобальный перехват исключений

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e){
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Invalid username or password"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDTO registrationUserDTO){
        if (!registrationUserDTO.getPassword().equals(registrationUserDTO.getConfirmPassword())){
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пароли не совпадают"), HttpStatus.BAD_REQUEST);
        }
        if (userService.findByUsername(registrationUserDTO.getUsername()).isPresent()) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пользовтель с таким именем уже существует"), HttpStatus.BAD_REQUEST);
        }

        User user = userService.createNewUser(registrationUserDTO);
        return ResponseEntity.ok(new UserDTO(user.getId(), user.getEmail(), user.getUsername()));
    }
}
