package com.example.service;

import java.util.List;

import com.example.bindings.ActivateAccount;
import com.example.bindings.Login;
import com.example.bindings.User;

public interface UserMgmtService {
	
	public boolean saveUser(User user);
	
	public boolean activateUserAcc(ActivateAccount actAcc);
	
	public User getUserById(Integer userId);
	
	public List<User> getAllUsers();
	
	public boolean deleteUser(Integer userId);
	
	public boolean SoftDeleteAndChangeStatus(Integer userId, String accStatus);
	
	public String login(Login login);
	
	public String forgotPwd(String email);
	
	

}
