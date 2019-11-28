package com.yichun.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yichun.domain.User;
import com.yichun.repository.UserRepository;
import com.yichun.util.CheckUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
public class UserController {
	
	
	private final UserRepository repository;
	
	public UserController(UserRepository repository) {
		this.repository = repository;
	}
	
	/**
	 * 以數駔形式一次性返回數據
	 * 
	 */
	@GetMapping("/")
	public Flux<User> getAll(){
		return repository.findAll();
	}
	
	/**
	 * 以SSE刑式多次返回數據
	 */
	@GetMapping(value = "/stream/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<User> streamGetAll(){
		return repository.findAll();
	}
	
	@PostMapping("/")
	public Mono <User> createUser(@Valid @RequestBody User user){
		//spring data jpa 裡面，新增和修改都是save 有id是修改, id 為空是新增
		//根據實際情況是否置空id
		user.setId(null);
		CheckUtil.checkName(user.getName());
		return this.repository.save(user);
	}
	
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> deleteUser(
		@PathVariable("id") String id){
		
		//deleteID 沒有返回值，不能判斷數據是否存在
		//this.repository.deleteById(id)
		return this.repository.findById(id)
				//當你要操作數據，並返回一個Mono這個時候使用flatMap
				//如果不操作數據，只是轉換數據，使用map
				.flatMap(user-> this.repository.delete(user)
						.then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
				.defaultIfEmpty(new ResponseEntity<> (HttpStatus.NOT_FOUND));
		
		
	}
	
	@PutMapping("/{id}")
	public Mono<ResponseEntity<User>> updateUser(
			@PathVariable("id") String id,
			@Valid @RequestBody User user
		){
		
		CheckUtil.checkName(user.getName());
		return this.repository.findById(id)
		.flatMap(u -> {
			u.setAge(user.getAge());
			u.setName(user.getName());
			return this.repository.save(u);
		}).map(u -> new ResponseEntity <User> (u,HttpStatus.OK))
		.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<User>> findUserById(@PathVariable("id") String id){
		
		return this.repository.findById(id)
				.map(u -> new ResponseEntity<User> (u, HttpStatus.OK))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	
	/*
	 * 根據年齡查詢
	 * 
	 */
	
	@GetMapping("/age/{start}/{end}")
	public Flux <User> findByAge(@PathVariable("start") int start, @PathVariable("end") int end ){
		return this.repository.findByAgeBetween(start, end);
	}
	
	
	@GetMapping(value = "/stream/age/{start}/{end}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux <User> streamFindByAge(@PathVariable("start") int start, @PathVariable("end") int end ){
		return this.repository.findByAgeBetween(start, end);
	}
	
	/*
	 * 
	 * 得到20-30歲的用戶
	 * 
	 */
	@GetMapping("/old")
	public Flux <User> oldUser(){
		return this.repository.oldUser();
	}
	
	@GetMapping(value = "/stream/old", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux <User> streamOldUser(){
		return this.repository.oldUser();
	}
	
	
	
}
 