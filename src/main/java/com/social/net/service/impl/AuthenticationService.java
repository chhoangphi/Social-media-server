package com.social.net.service.impl;

import com.social.net.common.exception.EmailExistedException;
import com.social.net.domain.Profile;
import com.social.net.domain.User;
import com.social.net.domain.enums.Role;
import com.social.net.payload.request.AuthenticationRequest;
import com.social.net.payload.request.RegisterRequest;
import com.social.net.payload.response.AuthenticationResponse;
import com.social.net.repository.ProfileRepository;
import com.social.net.repository.UserRepository;
import com.social.net.service.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final UserRepository repository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest request) throws EmailExistedException {
        User userEntity = repository.findByEmail(request.getEmail()).orElse(null);
        if (userEntity != null) {
            throw new EmailExistedException();
        }
        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        user = repository.save(user);
        Profile profileEntity = new Profile(user);
        profileRepository.save(profileEntity);
        user.setProfile(profileEntity);
        repository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest request)
            throws NoSuchElementException, AuthenticationException {
        User user = repository.findByEmail(request.getEmail()).orElseThrow();
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),
                        request.getPassword()));
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }

}
