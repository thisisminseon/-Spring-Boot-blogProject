package com.blog.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blog.domain.SampleDto;
import com.blog.domain.SampleUser;

@Controller
@RequestMapping("/sample") // localhost:8089/sample
public class SampleController {

	SampleController(SecurityFilterChain fileterChain) {
	}

	@GetMapping("")
	public void basic() {
		System.out.println("basic method.....");
	}

	// localhost:8089/sample/ex01
	@GetMapping("/ex01")
	public void ex01(Model model) {
		// Model 속성에 담아서 forward 한 후, html로 출력하고 싶을 때
		System.out.println("ex01.....");
	}

	// localhost:8089/sample/ex02?name=hong&age=500
	// If want to save in SampleDto
	@GetMapping("/ex02")
	public void ex02(SampleDto dto, Model model) {
		System.out.println("age : " + dto.getAge());
		System.out.println("name : " + dto.getName());
		// dto 객체를 viewDto 속성에 담아서 /sample/ex02.html로 forward 한다
		model.addAttribute("viewDto", dto);
	}

	// localhost:8089/sample/ex03?name=hong&age=500
	@GetMapping("/ex03")
	public String ex03(@RequestParam("name") String name, @RequestParam("age") int age, Model model) {
		// url에서 name 변수에 저장된 값을 요청해서 String name 변수에 저장
		// url에서 age 변수에 저장된 값을 요청해서 int age 변수에 저장
		// controller 에서 메서드의 리턴 타입이 void이면, 요청한 주소의 html을 바로 찾아가라는 의미
		// 즉, /sample/ex03.html을 실행해라 이런 뜻이다.
		System.out.println("age : " + age);
		System.out.println("name : " + name);

		SampleDto dto = new SampleDto();
		dto.setAge(age);
		dto.setName(name);

		model.addAttribute("viewDto", dto);

		return "sample/ex02";
	}

	// List, Array processing
	// localhost:8089/sample/ex04?ids=111&ids=222&ids=333
	@GetMapping("/ex04")
	public String ex04(@RequestParam("ids") List<String> ids) {
		// ids 배열의 형태로 데이터가 넘어오면 String 타입의 가변배열에 저장한다.

		for (int i = 0; i < ids.size(); i++) {
			System.out.println("ids[" + i + "]" + ids.get(i));
		}
		return "sample/list";
	}

	// localhost:8089/sample/ex05?name=hong&age=500&regdate=2026/01/23
	@GetMapping("/ex05")
	public String ex05(SampleDto dto) {
		// 자동으로 SampleDto객체를 생성하고
		// url을 통해 매개변수 데이터를 SampleDto 변수에 자동으로 저장까지 된다
		// 반드시 파타미터의 이름과 Dto 변수의 이름이 같아야 한다
		System.out.println("dto : " + dto);
		return "sample/ex05";
	}

	// Error resolving template [sample/list] : sample/list.html이 없어서 생기는 오류

	@GetMapping("/write")
	public String memberWrite() {

		// RUN → sample/sampleForm.html
		return "sample/sampleForm";

		// Controller 에서 return 문장은 그 값을 돌려준다는 뜻이 아니라 문장 내용을 실행하라는 뜻이다.
	}

	// sample/writepro 요청을 post 방식으로 처리할때 실행
	@PostMapping("/write")
	public void memberInsert(SampleDto dto) {
		System.out.println("age : " + dto.getAge());
		System.out.println("name : " + dto.getName());
		// Access is blocked when processing with the POST method
	}

	// localhost:8089/sample/morning
	@GetMapping("/morning")
	// Model : When Use forwarding
	public String home(Model model) {

		model.addAttribute("message", "good morning");
		model.addAttribute("name", "홍길동");

		// sample/home.html 실행
		return "/sample/home";
	}

	@GetMapping("/messages")
	public String messages(Model model) {

		model.addAttribute("message", "Hello");
		model.addAttribute("price", 10000);
		model.addAttribute("quantity", 3);

		SampleUser user = new SampleUser(1L, "박장수", 50, "user", null, true);
		model.addAttribute("user", user);

		return "/sample/messages";
	}

	// localhost:8089/sample/link
	@GetMapping("/link")
	public String link(Model model) {

		model.addAttribute("message", "Hello");
		model.addAttribute("price", 10000);
		model.addAttribute("quantity", 3);
		model.addAttribute("keyword", "자바");
		model.addAttribute("page", 3);

		SampleUser user = new SampleUser(1L, "박장수", 50, "user", null, true);
		model.addAttribute("user", user);

		return "/sample/link";
	}

	@GetMapping("/condition")
	public String condition(Model model) {

		SampleUser user1 = new SampleUser();

		user1.setId(1L);
		user1.setName("박장수");
		user1.setAge(22);
		user1.setRole("ADMIN");
		user1.setActive(true);

		model.addAttribute("user", user1);
		model.addAttribute("message", "Say i hate you");

		List<String> items = Arrays.asList("apple", "banana", "orange");
		model.addAttribute("items", items);

		List<SampleUser> users = Arrays.asList(new SampleUser(1L, "박장수", 20, "ADMIN", null, true),
				new SampleUser(2L, "김예은", 25, "USER", null, true), new SampleUser(3L, "박민선", 30, "USER", null, true));

		model.addAttribute("users", users);

		Map<String, Integer> map = new HashMap<>();
		map.put("apple", 1000);
		map.put("banana", 1500);
		map.put("orange", 3000);

		model.addAttribute("map", map);
		model.addAttribute("title", "자바야");
		model.addAttribute("canSubmit", false);
		model.addAttribute("active", true);
		model.addAttribute("error", true);
		model.addAttribute("agreed", true);

		return "/sample/condition"; // 속성들이 자동으로 forwarding
	}

	// List About Users
	@GetMapping("/users")
	public String List(Model model) {

		List<SampleUser> users = Arrays.asList(
				SampleUser.builder()
				.id(1L)
				.name("이유정")
				.age(32)
				.email("iamyou@gmail.com")
				.role("ADMIN")
				.active(true)
				.build(), // "생성하세요"의 의미
				SampleUser.builder()
				.id(2L)
				.name("장히애")
				.age(48)
				.email("anghi@gmail.com")
				.role("USER")
				.active(true)
				.build(),
				SampleUser.builder()
				.id(3L)
				.name("김주하")
				.age(27)
				.email("meenomom@gmail.com")
				.role("GUEST")
				.active(true)
				.build()
				);
		
		model.addAttribute("users", users);
		model.addAttribute("totalCount", users.size());
		model.addAttribute("menu", "users");

		return "sample/list";
	}
	
	
	// Detail For Users
	// localhost:8089/sample/2
	@GetMapping("/{id}")
	public String detail(@PathVariable("id") Long id, Model model) {
		SampleUser user = SampleUser.builder()
		.id(2L)
		.name("장히애")
		.age(30)
		.email("iamheeae@gmail.com")
		.role("USER")
		.active(true)
		.build();
		
		model.addAttribute("user", user);
		model.addAttribute("menu", "users");
		
		return "sample/detail";
	}
	
	// Registration Form Page (GET)
	// /sample/register
	 @GetMapping("/register")
	    public String form(Model model) {
	        model.addAttribute("user", new SampleUser());  // 빈 객체 생성
	        model.addAttribute("menu", "register");
	        return "sample/form";
	    }
	 
	 // Registration process (POST) 
	 // @ModelAttribute로 폼 데이터를 User객체에 자동 바인딩
     // /sample/register
	 @PostMapping("/register")
	    public String submit(@ModelAttribute SampleUser user, Model model) {
	        // Actually, save to DB
	        // userRepository.save(user);
	        
	        // Automatic ID Generation (실제로는 DB에서 자동 증가)
	        user.setId(System.currentTimeMillis());
	        
	        // 결과 페이지에 전달
	        model.addAttribute("user", user);
	        model.addAttribute("menu", "register");
	        
	        return "sample/result";
	    }
	 
}
