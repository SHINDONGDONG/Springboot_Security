package com.cos.security1.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.cos.security1.model.User;

import lombok.Data;

// 시큐리티가 /login 주소요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인이 진행이 완료가 되면 시큐리티 session을 만들어줍니다. (Security ContextHolder)
// 들어갈 수 있는 object가 정해져있음  => Authentication 타입 객체
// Authentication => 안에 User정보가 있어야됨.
// User Object의 타입 => UserDetails 타입 객체

// Security Session => Authentication => UserDetails(PrincipalDetails)

@Data
public class PrincipalDetails implements UserDetails,OAuth2User{

	private User user; //콤포지션
	
	public PrincipalDetails(User user) {
		this.user = user;
	}
	
	//해당 User의 권한을 return하는 메소드
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new ArrayList<>();
		collection.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return user.getRole();
			}
		});
		return collection;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
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
		//우리 사이트에서 1년동안 회원이 로그인을 안하면
		//휴면 계정으로 변환해야함..
		//현재 시간 - 로그인시간 = 1년을 초과하면 false로 전환
		return true;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return null;
	}

	@Override
	public String getName() {
		return null;
	}

	
}
