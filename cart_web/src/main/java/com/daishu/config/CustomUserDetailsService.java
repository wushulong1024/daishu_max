package com.daishu.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于加载用户信息 实现UserDetailsService接口，或者实现AuthenticationUserDetailsService接口
 */
public class CustomUserDetailsService
  //实现UserDetailsService接口，实现loadUserByUsername方法
  implements UserDetailsService {
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    System.out.println("当前的用户名是："+username);
      List<GrantedAuthority> authorities=new ArrayList();
      authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

      return new User(username,"",authorities);
  }

}
