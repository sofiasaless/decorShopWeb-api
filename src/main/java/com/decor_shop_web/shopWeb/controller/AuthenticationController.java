package com.decor_shop_web.shopWeb.controller;

import com.decor_shop_web.shopWeb.dto.MyUserDTO;
import com.decor_shop_web.shopWeb.dto.MyUserMapper;
import com.decor_shop_web.shopWeb.dto.TokenResponse;
import com.decor_shop_web.shopWeb.infra.TokenService;
import com.decor_shop_web.shopWeb.model.MyUser;
import com.decor_shop_web.shopWeb.repository.MyUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final MyUserRepository myUserRepository;

    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid MyUserDTO myUserDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(myUserDTO.getUsername(), myUserDTO.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        // returning token
        var token = tokenService.generateToken((MyUser) auth.getPrincipal());

        return ResponseEntity.ok(new TokenResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid MyUserDTO myUserDTO) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(myUserDTO.getPassword());
        myUserDTO.setPassword(encryptedPassword);
        myUserRepository.save(MyUserMapper.INSTANCE.toMyUser(myUserDTO));

        return ResponseEntity.ok().build();
    }

}
