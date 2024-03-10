package com.enigma.wmbspring.service.impl;

import com.enigma.wmbspring.constant.UserRole;
import com.enigma.wmbspring.dto.request.AuthRequest;
import com.enigma.wmbspring.dto.response.LoginResponse;
import com.enigma.wmbspring.dto.response.RegisterResponse;
import com.enigma.wmbspring.entity.Customer;
import com.enigma.wmbspring.entity.Role;
import com.enigma.wmbspring.entity.UserAccount;
import com.enigma.wmbspring.repository.UserAccountRepository;
import com.enigma.wmbspring.service.AuthService;
import com.enigma.wmbspring.service.CustomerService;
import com.enigma.wmbspring.service.JwtService;
import com.enigma.wmbspring.service.RoleService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserAccountRepository userAccountRepository;
    private final CustomerService customerService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Value("${wmbspring.username.superadmin}")
    private String superAdminUsername;
    @Value("${wmbspring.password.superadmin}")
    private String superAdminPassword;

    @Transactional(rollbackFor = Exception.class)
    @PostConstruct
    public void initSuperAdmin() {
        Optional<UserAccount> currentUser = userAccountRepository.findByUsername("superadmin");
        if (currentUser.isPresent()) return;

        Role superAdmin = roleService.getOrSave(UserRole.ROLE_SUPER_ADMIN);
        Role admin = roleService.getOrSave(UserRole.ROLE_ADMIN);
        Role customer = roleService.getOrSave(UserRole.ROLE_CUSTOMER);

        UserAccount account = UserAccount.builder()
                .username(superAdminUsername)
                .password(passwordEncoder.encode(superAdminPassword))
                .role(List.of(superAdmin,admin,customer))
                .isEnable(true)
                .build();

        userAccountRepository.save(account);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse register(AuthRequest request) {
        try {
            Role role = roleService.getOrSave(UserRole.ROLE_CUSTOMER);
            String hashPassword = passwordEncoder.encode(request.getPassword());

            UserAccount userAccount = UserAccount.builder()
                    .username(request.getUsername())
                    .password(hashPassword)
                    .role(List.of(role))
                    .isEnable(true)
                    .build();

            userAccountRepository.saveAndFlush(userAccount);

            Customer customer = Customer.builder()
                    .status(true)
                    .userAccount(userAccount)
                    .build();
            customerService.create(customer);

            List<String> roles = userAccount.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority).toList();

            return RegisterResponse.builder()
                    .username(userAccount.getUsername())
                    .roles(roles)
                    .build();
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse registerAdmin(AuthRequest request) {
        Role roleAdmin = roleService.getOrSave(UserRole.ROLE_ADMIN);
        Role roleCustomer = roleService.getOrSave(UserRole.ROLE_CUSTOMER);

        String hashPassword = passwordEncoder.encode(request.getPassword());

        UserAccount userAccount = UserAccount.builder()
                .username(request.getUsername())
                .password(hashPassword)
                .role(List.of(roleAdmin, roleCustomer))
                .isEnable(true)
                .build();

        userAccountRepository.saveAndFlush(userAccount);

        Customer customer = Customer.builder()
                .status(true)
                .userAccount(userAccount)
                .build();
        customerService.create(customer);

        List<String> roles = userAccount.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        return RegisterResponse.builder()
                .username(userAccount.getUsername())
                .roles(roles)
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public LoginResponse login(AuthRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );

        Authentication authenticate = authenticationManager.authenticate(authentication);
        UserAccount userAccount = (UserAccount) authenticate.getPrincipal();
        String token = jwtService.generateToken(userAccount);
        return LoginResponse.builder()
                .username(userAccount.getUsername())
                .roles(userAccount.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .token(token)
                .build();
    }
}
