package com.parsla.PeopleHub.service.authconfig;


import com.parsla.PeopleHub.entity.UserInfo;
import com.parsla.PeopleHub.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service(value = "userService")
public class UserInfoService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserInfo> userDetail = repository.findByEmail(email);

        if (userDetail.isEmpty()) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        // Converting userDetail to UserDetails
        return new org.springframework.security.core.userdetails.User(userDetail.get().getEmail(), userDetail.get().getPassword(),
                getAuthority(userDetail.get()));


//        // Converting userDetail to UserDetails
//        return userDetail.map(UserInfoDetails::new)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found " + email));
    }

    public String addUser(UserInfo userInfo) {
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "User Added Successfully";
    }

    public UserInfo getUser(String email) {
        try {
            Optional<UserInfo> userDetail = repository.findByEmail(email);
            if (userDetail.isEmpty()) {
                throw new UsernameNotFoundException("Invalid username or password.");
            }
            userDetail.get().setPassword(null);
            return userDetail.get();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new UsernameNotFoundException("User Email not found");
        }
    }

    public String updateUser(String email, UserInfo userInfo) {
        Optional<UserInfo> userDetail = repository.findByEmail(email);
        if (userDetail.isEmpty()) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        userDetail.get().setEmail(userInfo.getEmail());
        repository.save(userInfo);
        return "User Updated Successfully";
    }

    private Set<SimpleGrantedAuthority> getAuthority(UserInfo user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        Arrays.stream(user.getRoles().split(",")).forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role));
        });
        //TODO PARTEEK
//        user.getRoles().forEach(role -> {
//            // authorities.add(new SimpleGrantedAuthority(role.getName()));
//            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
//        });
        return authorities;
        // return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

}