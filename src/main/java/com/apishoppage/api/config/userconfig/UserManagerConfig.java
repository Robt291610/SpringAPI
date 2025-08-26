package com.apishoppage.api.config.userconfig;

import com.apishoppage.api.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserManagerConfig implements UserDetailsService {
    private final UserRepository userRepository;

    public UserManagerConfig(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(email)
                .map(UserConfig::new)
                .orElseThrow(()-> new UsernameNotFoundException("UserEmail: "+email+" does not exist"));
    }
}
