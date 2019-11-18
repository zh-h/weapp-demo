package me.zonghua.weapp.weappdemo.controller;

import me.zonghua.weapp.weappdemo.exception.WeAppException;
import me.zonghua.weapp.weappdemo.request.WeAppLoginRequest;
import me.zonghua.weapp.weappdemo.response.Response;
import me.zonghua.weapp.weappdemo.response.WeAppLoginResponse;
import me.zonghua.weapp.weappdemo.service.WeAppAuthenticationService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/api/weapp")
public class WeAppAuthenticationController {

    private final WeAppAuthenticationService weAppAuthenticationService;

    public WeAppAuthenticationController(WeAppAuthenticationService weAppAuthenticationService) {
        this.weAppAuthenticationService = weAppAuthenticationService;
    }

    @PostMapping("/login")
    public Response<WeAppLoginResponse> login(@RequestBody @Valid WeAppLoginRequest weAppLoginRequest) {
        return Response.success(weAppAuthenticationService.login(weAppLoginRequest));
    }

    @DeleteMapping("/logout")
    public Response logout(@RequestBody @Valid @NotEmpty String token) {
        weAppAuthenticationService.logout(token);
        return Response.success(null);
    }

    @ExceptionHandler(WeAppException.class)
    public Response handleWeAppException(WeAppException e) {
        return Response.fail(e.getErrorCode(), e.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    public Response handleException(Exception e) {
        return Response.fail(500, e.getMessage(), null);
    }
}
