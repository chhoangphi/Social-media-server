package com.social.net.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "404 NOT FOUND")
public class NotFoundException extends RuntimeException {

}
