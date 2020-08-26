package com.cos.security1.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{
	
	//후처리되는 함수 
	//구글로부터 받은 userRequest 데이터에 대한 후처리함수 
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("getClientRegistration : "+ userRequest.getClientRegistration());
		System.out.println("getAccessToken : "+ userRequest.getAccessToken().getTokenValue());
		//우리가 구글로그인 버튼 - 구글로그인창 - 로그인완료 - 코드리턴(Oauth-client라이브러리) - Access토큰요청 까지가 userrequest정보
		//userrequest정보 -> 회원프로필정보를 받아야함 - > loadUser()이다 -> 구글로부터 회원프로필 받아준다.
		System.out.println("getAttributes : "+ super.loadUser(userRequest).getAttributes());
		
		OAuth2User oauth2User = super.loadUser(userRequest);
		return super.loadUser(userRequest);
	}
	
}
