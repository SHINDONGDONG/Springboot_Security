package com.cos.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

@Controller
public class IndexController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("/test/login")
	public @ResponseBody String loginTest(Authentication authenticateAction,
			@AuthenticationPrincipal PrincipalDetails userDetails) {
		System.out.println("/test/login ==================");
		PrincipalDetails principalDetails = (PrincipalDetails) authenticateAction.getPrincipal();
		System.out.println("authentication : " + principalDetails.getUser());
		
		System.out.println("userdetails : " + userDetails.getUser());
		return "세션정보확인";
		
	}
	
	@GetMapping("/test/oauth/login")
	public @ResponseBody String loginTest(Authentication authenticateAction
			,@AuthenticationPrincipal OAuth2User oauth) {
		System.out.println("/test/oauth2/login ==================");
		OAuth2User oauth2User= (OAuth2User) authenticateAction.getPrincipal();
		System.out.println("authentication : " + oauth2User.getAttributes());
		System.out.println("oauth2 : " + oauth.getAttributes());
		
		return "oauth 세션정보확인";
		
	}
	
	//스프링 시큐리티는 자기만의 세션을 들고있다. (시큐리티세션)
	//userdetails,oauth2user 두개를 한번에 principaldetails 에 임플리먼트하여 두개다 찾을수잇게한다.
	
	
	@GetMapping({"/",""})
	public String index() {
		//머스태치 탬플렛 엔진 /기본폴더  src/main/resources/
		//뷰리졸버 설정 : templates(prefix), .mustache (suffix) //pom.xml입력되어있으면 생략가능
		return "index"; //src/main/resources/templates/index.mustach
	}
	
	@GetMapping("/user")
	public @ResponseBody String user() {
		return "user";
	}

	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "admin";
	}
	
	@GetMapping("/manager")
	public @ResponseBody String manager() {
		return "manager";
	}
	
	//스프링 시큐리티가 주소를 낚아챔 
	@GetMapping("/loginForm") //-SecurityConfig 파일을 생성후 작동안함
	public  String loginForm() {
		return "loginForm";
	}

	@GetMapping("/joinForm") //-SecurityConfig 파일을 생성후 작동안함
	public  String joinForm() {
		return "joinForm";
	}
		
		@PostMapping("/join")
		public String join(User user) {
			user.setRole("ROLE_USER");
			String rawPassword = user.getPassword();
			String encPassword = bCryptPasswordEncoder.encode(rawPassword);
			user.setPassword(encPassword);
			userRepository.save(user); //회원가입 비밀번호 1234 => 시큐리티로 로그인 불가능 	ㅍ패스워드 암호화가 안되었기 때문에 
		return "redirect:/loginForm";
	}
		
	@Secured("ROLE_ADMIN")  //특정메소드에 권한을 주고싶을때
	@GetMapping("/info")
	public @ResponseBody String info() {
		return "개인정보";
	}

	//@PostAuthorize
	@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
	@GetMapping("/data")
	public @ResponseBody String data() {
		return "데이터정보";
	}

}
