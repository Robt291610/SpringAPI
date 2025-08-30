package com.apishoppage.api.config.userconfig;

import com.apishoppage.api.entity.User;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Arrays;
import java.util.Collection;

//UserDetails is a class provided by Spring Framework
@RequiredArgsConstructor
public class UserConfig implements UserDetails {
    private final User user;


    /*All these methods define the user authority*/
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays
                .stream(user
                                .getRoles()
                                .split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
