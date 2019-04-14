package com.oleg.restdemo.security;

import com.oleg.restdemo.exceptions.UsernameNotFoundException;
import com.oleg.restdemo.models.ApplicationUser;
import com.oleg.restdemo.repos.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository applicationUserRepository;

    public UserDetailsServiceImpl(UserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser applicationUser = applicationUserRepository
                .findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));


        return new User(applicationUser.getName(), applicationUser.getPassword(), emptyList());
    }
}
