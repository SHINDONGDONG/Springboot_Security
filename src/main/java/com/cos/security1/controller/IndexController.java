package com.cos.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

@Controller
public class IndexController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
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

}
