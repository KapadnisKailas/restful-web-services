/**
 * 
 */
package com.kapadniskailas.restfulwebservices.restfulwebservices.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.kapadniskailas.restfulwebservices.restfulwebservices.beans.User;
import com.kapadniskailas.restfulwebservices.restfulwebservices.dao.UserDaoService;
import com.kapadniskailas.restfulwebservices.restfulwebservices.exceptions.UserAlreadyExistException;
import com.kapadniskailas.restfulwebservices.restfulwebservices.exceptions.UserNotFoundException;

/**
 * @author kailaskapadnis
 *
 */

@RestController
public class UserResource {

	@Autowired
	private UserDaoService userDaoService;

	// without HATEOAS
	@GetMapping(value = "/users")
	public List<User> retrieveAllUsers() {
		return userDaoService.findAll();
	}

	// with HATEOAS - belowe code is not working
	/*
	 * @GetMapping(path = "/users") public Resources<User> retrieveAllUsers() {
	 * List<User> userList = userDaoService.findAll(); List<Link> links = new
	 * ArrayList<>(); for(User user: userList){ ControllerLinkBuilder linkTo =
	 * ControllerLinkBuilder.linkTo(UserResource.class, retrieveUser(user.getId()));
	 * links.add(linkTo.withRel("user")); }
	 * 
	 * Resources<User> resource = new Resources<User>(userList, links);
	 * 
	 * return resource; }
	 */

	// without HATEOAS
	/*
	 * @GetMapping("/users/{id}") public User retrieveUser(@PathVariable int id) {
	 * User user = userDaoService.findOne(id); if(user == null) throw new
	 * UserNotFoundException("id-"+id); return user; }
	 */

	// with HATEOAS
	@GetMapping("/users/{id}")
	public Resource<User> retrieveUser(@PathVariable int id) {
		User user = userDaoService.findOne(id);
		if (user == null)
			throw new UserNotFoundException("id-" + id);
		Resource<User> resource = new Resource<User>(user);
		ControllerLinkBuilder linkTo = ControllerLinkBuilder.linkTo(UserResource.class, retrieveAllUsers());
		resource.add(linkTo.withRel("all-users"));
		return resource;
	}

	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@RequestBody @Valid User user) {
		User savedUser = userDaoService.save(user);
		if (savedUser != null) {
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(savedUser.getId()).toUri();
			return ResponseEntity.created(location).build();
		}
		throw new UserAlreadyExistException("User with id-" + user.getId() + " already exist.");
	}
}
