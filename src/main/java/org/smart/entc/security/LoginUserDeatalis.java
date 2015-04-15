package org.smart.entc.security;

/**
 * Created by john on 7/2/14.
 */

import org.smart.entc.repo.DataLayer;
import org.smart.entc.repo.object.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import java.util.Collection;
import java.util.List;

public class LoginUserDeatalis implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {


        final User user= DataLayer.getUserByName(username);
        if(user==null){
            throw new UsernameNotFoundException(username + " not found");
        }

        //creating dummy user details, should do JDBC operations
        return new UserDetails() {

            private static final long serialVersionUID = 2059202961588104658L;

            @Override
            public boolean isEnabled() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public String getUsername() {
                return username;
            }

            @Override
            public String getPassword() {
                return user.getPassword();
            }

            //To DO for authentication.
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                List<SimpleGrantedAuthority> auths = new java.util.ArrayList<SimpleGrantedAuthority>();
                auths.add(new SimpleGrantedAuthority("Admin"));
                return auths;
            }
        };
    }

}
