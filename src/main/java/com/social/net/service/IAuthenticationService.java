package com.social.net.service;

import com.social.net.payload.request.AuthenticationRequest;
import com.social.net.payload.request.RegisterRequest;
import com.social.net.payload.response.AuthenticationResponse;

public interface IAuthenticationService {

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse login(AuthenticationRequest request);
}
