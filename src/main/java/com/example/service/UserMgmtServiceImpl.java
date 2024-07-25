package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.example.bindings.ActivateAccount;
import com.example.bindings.Login;
import com.example.bindings.User;
import com.example.entity.UserMaster;
import com.example.repo.UserMasteRepo;

@Service
public class UserMgmtServiceImpl implements UserMgmtService {

	@Autowired
	private UserMasteRepo userMasteRepo;

	@Override
	public boolean saveUser(User user) {
		UserMaster entity = new UserMaster();
		BeanUtils.copyProperties(user, entity);

		entity.setPassword(generateRandomPwd());
		entity.setAccStatus("In-Active");

		UserMaster save = userMasteRepo.save(entity);
		
		return save.getUserID()!= null;
	}

	@Override
	public boolean activateUserAcc(ActivateAccount actAcc) {
		
		UserMaster entity = new UserMaster();
		entity.setEmail(actAcc.getEmail());
		entity.setPassword(actAcc.getTempPwd());
		
		//select * from User_master where email=? and pwd=?
		Example<UserMaster> of= Example.of(entity);
		
		List<UserMaster> findAll = userMasteRepo.findAll(of);
		
		if(findAll.isEmpty()) {
			return false;
		}else {
			UserMaster userMaster = findAll.get(0);
			userMaster.setPassword(actAcc.getNewPwd());
			userMaster.setAccStatus("Active");
			userMasteRepo.save(userMaster);
			return true;
		}	
	}

	@Override
	public User getUserById(Integer userId) {
		Optional<UserMaster> findById =  userMasteRepo.findById(userId);
		if(findById.isPresent()) {
			UserMaster userMaster = findById.get();
			User user = new User();
			BeanUtils.copyProperties(userMaster, user);
			return user;
		}
		return null;
	}

	@Override
	public List<User> getAllUsers() {
		List<UserMaster> findAll = userMasteRepo.findAll();
		
		List<User> users = new ArrayList<>();
		for(UserMaster entity : findAll) {
			User user = new User();
			BeanUtils.copyProperties(entity, user);
			users.add(user);
		}
		
		return users;
	}

	@Override
	public boolean deleteUser(Integer userId) {
		
		try {
			userMasteRepo.deleteById(userId);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean SoftDeleteAndChangeStatus(Integer userId, String accStatus) {
		
		Optional<UserMaster> findById =  userMasteRepo.findById(userId);
		
		if(findById.isPresent()) {
			UserMaster userMaster = findById.get();
			userMaster.setAccStatus(accStatus);
			userMasteRepo.save(userMaster);
			return true;
		}
		
		return false;
	}

	@Override
	public String login(Login login) {
		//Different approch
		
//		UserMaster entity = new UserMaster();
//		entity.setEmail(login.getEmail());
//		entity.setPassword(login.getPwd());
//		
//		//select * from User_master where email=? and pwd=?
//		Example<UserMaster> of = Example.of(entity);
//		
//		List<UserMaster> findAll = userMasteRepo.findAll(of);
		
		//or
		//Another Approch
		
		UserMaster entity = userMasteRepo.findByEmailAndPassword(login.getEmail(), login.getPwd());
				
		if(entity == null) {
			return "Invalid Credentials";
		}else {
			if(entity.getAccStatus().equals("Active")) {
				return "SUCCESS";
			}else {
				return "Account Not Activated";
			}
		}	
	}

	@Override
	public String forgotPwd(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	private String generateRandomPwd() {

		// create a string of uppercase and lowercase characters and numbers
		String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
		String numbers = "0123456789";

		// combine all strings
		String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;

		// create random string builder
		StringBuilder sb = new StringBuilder();

		// create an object of Random class
		Random random = new Random();

		// specify length of random string
		int length = 6;

		for (int i = 0; i < length; i++) {

			// generate random index number
			int index = random.nextInt(alphaNumeric.length());

			// get character specified by index
			// from the string
			char randomChar = alphaNumeric.charAt(index);

			// append the character to string builder
			sb.append(randomChar);
		}

		return sb.toString();

	}

}
