/**
 * 
 */
package com.kapadniskailas.restfulwebservices.restfulwebservices.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import com.kapadniskailas.restfulwebservices.restfulwebservices.beans.User;

/**
 * @author kailaskapadnis
 *
 */
@Component
public class UserDaoService {
	
	private static List<User> userList = new ArrayList<>();
	
	static {
		userList.add(new User(1, "Kailas", new Date()));
		userList.add(new User(2, "Pooja", new Date()));
		userList.add(new User(3, "Arush", new Date()));
	}
	
	public List<User> findAll(){

		return userList;
	}
	
	public User findOne(int id) {
		for (User user : userList) {
			if(user.getId().equals((Integer)id)){
				return user;
			}
		}
		return null;
	}
	
	class MyConsumer implements Consumer<Integer>{
		public void accept(Integer id) {
			
		}
	}
	
	public User save(User newUser) {
		for(User user: userList) {
			if(user.getId().equals(newUser.getId())) {
				return null;
			}
		}
		userList.add(newUser);
		return newUser;
	}
}
