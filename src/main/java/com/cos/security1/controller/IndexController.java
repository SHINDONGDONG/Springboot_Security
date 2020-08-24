package com.cos.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

	
	@GetMapping({"/",""})
	public String index() {
		//머스태치 탬플렛 엔진 /기본폴더  src/main/resources/
		//뷰리졸버 설정 : templates(prefix), .mustache (suffix) //pom.xml입력되어있으면 생략가능
		return "index";
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
	@GetMapping("/login")
	public @ResponseBody String login() {
		return "login";
	}
	
	@GetMapping("/join")
	public @ResponseBody String join() {
		return "join";
	}
	
	@GetMapping("/joinProc")
	public @ResponseBody String joinProc() {
		return "회원가입 완료됨";
	}
}
