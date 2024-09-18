package com.github.supercoding.service;

import com.github.supercoding.repository.roles.Roles;
import com.github.supercoding.repository.roles.RolesRepository;
import com.github.supercoding.repository.userPrincipal.UserPrincipal;
import com.github.supercoding.repository.userPrincipal.UserPrincipalRepository;
import com.github.supercoding.repository.userPrincipal.UserPrincipalRoles;
import com.github.supercoding.repository.userPrincipal.UserPrincipalRolesRepository;
import com.github.supercoding.repository.users.UserEntity;
import com.github.supercoding.repository.users.UserRepository;
import com.github.supercoding.web.dto.auth.SignUp;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserPrincipalRepository userPrincipalRepository;
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final UserPrincipalRolesRepository userPrincipalRolesRepository;

    private final PasswordEncoder passwordEncoder;

    public boolean signUp(SignUp signUpRequest) {
        String email = signUpRequest.getEmail();
        String password = signUpRequest.getPassword();
        String username = signUpRequest.getName();

        // 1. id 동일 체크
        if(userPrincipalRepository.existsByEmail(email)){
            return false;
        }

        //2. 유저가 있으면 id만 등록 아니면 유저도 만들기
        UserEntity userFound = userRepository.findByUserName(username)
                .orElseGet(()->userRepository.save(UserEntity.builder()
                                .userName(username)
                                .build()));

        //3.username password 등록 , 기본 role,user
        Roles roles = rolesRepository.findByName("ROLE_USER")
                .orElseThrow(()->new NotFoundException("ROLE_USER를 찾을 수 없습니다."));
        UserPrincipal userPrincipal = UserPrincipal.builder()
                .email(email)
                .user(userFound)
                .password(passwordEncoder.encode(password))
                .build();
    userPrincipalRepository.save(userPrincipal);
    userPrincipalRolesRepository.save(
            UserPrincipalRoles.builder()
                    .roles(roles)
                    .userPrincipal(userPrincipal)
                    .build()
    );
    return true;
    }
}
