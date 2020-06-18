package com.server;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/users")
public class AppController {
	
	
	@Autowired(required=true)
	private UserRepository dao;
	
	@RequestMapping(value="/update",method=RequestMethod.GET)
	public String getResponse() {
		return "<b>Welcome to REST Users!</b>";
	}
	
	@RequestMapping(value="/getUser/{id}")
	public User readUser(@PathVariable int id) {
		System.out.println("Finding the user with  "+dao.getClass().getName());
	
		 Optional<User> obj = dao.findById(id);
		 User data = obj.get();
		 return data;
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public User addNewUser(@RequestBody User userObj) {
		User current= null;
		
		if(userObj!=null)
		{
			current = dao.save(userObj);
			System.out.println("The new user is saved with "+current.getId());
		}
		
		return current;
	}

}
