package com.cos.security1.config;

import org.aspectj.weaver.ast.And;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.web.servlet.SecurityMarker;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration.ProviderDetails.UserInfoEndpoint;

import com.cos.security1.config.oauth.PrincipalOauth2UserService;

@Configuration
@EnableWebSecurity//스프링 시큐리티 필터가 스프링 필터체인에 등록된다.
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) //secured어노테이션 활성화, preAuthorize,postAuthrize 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean //해당 메서드의 리턴되는 오브젝ㅌ느를 Ioc로 등록해준다.
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	private PrincipalOauth2UserService principalOauth2UserService;
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/user/**").authenticated() //이런 주소로 들어오면 인증이 필요해 //인증만되면 들어갈 수 있는 주손
			.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')") //인증이필요하고 admin or manager권한이 있는사람만 허용
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
			.anyRequest().permitAll() //위 3개주소아닌 이상 다 권한이 허용됨.
			.and()
			.formLogin()
			.loginPage("/loginForm")
			.loginProcessingUrl("/login") // /login이라는 주소가 호출이되면 시큐리티가 낚아채서 대신 로그인을 진행해준다.
			.defaultSuccessUrl("/") //성공하면 이 주소로 이동한다.
			.and()
			.oauth2Login()
			.loginPage("/loginForm") //구글로그인이 완료된 뒤 후처리가 필요 Tip. 코드X, (엑세스토큰 + 사용자 프로필정보를 한번에 취득)
			//1.코드받기(인증),2.엑세스토큰받기(권한),3.사용자 프로필정보 취득,4.정보를 토대로 자동회원가입 or 취득정보가 부족할 시  
			.userInfoEndpoint()
			.userService(principalOauth2UserService);//oauth2 userservice
	}
	
}
