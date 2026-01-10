package com.lms.service;

import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.lms.dto.UserDto;
import com.lms.entity.Address;
import com.lms.entity.User;
import com.lms.exception.AddressIdNotFoundException;
import com.lms.exception.UserIdNotFoundException;
import com.lms.repository.AddressRepository;
import com.lms.repository.UserRepository;
import com.lms.util.ResponseStructure;


@Service
public class UserService {

	@Autowired
	AddressRepository addressRepository;
	
	@Autowired
	UserRepository userRepository;
		
	@Autowired
	ModelMapper mapper;
	public ResponseEntity<ResponseStructure<User>> saveUser(UserDto userDto, int addressId){
		Optional<Address>dbAddress=addressRepository.findById(addressId);
		User user = mapper.map(userDto,User.class);
		if(dbAddress.isPresent()) {
			user.setAddress(dbAddress.get());
			userRepository.save(user);
			ResponseStructure<User> responseStructure = new ResponseStructure<>();
			responseStructure.setMessage("User saved successfully");
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setData(user);
			return new ResponseEntity<ResponseStructure<User>>(responseStructure,HttpStatus.OK);
		}
		else {
			throw new AddressIdNotFoundException("Address of this id is not available");
		}
	}

	
	public ResponseEntity<ResponseStructure<User>> findUserById(int userId) {
        Optional<User> optional = userRepository.findById(userId);

        if (optional.isPresent()) {
            User user = optional.get();

            ResponseStructure<User> responseStructure = new ResponseStructure<>();
            responseStructure.setMessage("User found successfully!");
            responseStructure.setStatusCode(HttpStatus.OK.value());
            responseStructure.setData(user);

            return new ResponseEntity<ResponseStructure<User>>(responseStructure, HttpStatus.OK);
        } else {
            throw new UserIdNotFoundException("User not found with id: " + userId);
        }
    }
	
	public ResponseEntity<ResponseStructure<List<User>>> fetchAllUsers() {
        List<User> users = userRepository.findAll();

        ResponseStructure<List<User>> responseStructure = new ResponseStructure<>();
        responseStructure.setMessage("All users fetched successfully!");
        responseStructure.setStatusCode(HttpStatus.OK.value());
        responseStructure.setData(users);

        return new ResponseEntity<ResponseStructure<List<User>>>(responseStructure, HttpStatus.OK);
    }
	
	
	public ResponseEntity<ResponseStructure<User>> updateUser( UserDto userDto,int userId) {
        Optional<User> optional = userRepository.findById(userId);

        if (optional.isPresent()) {
            User existingUser = optional.get();

            existingUser.setUserName(userDto.getUserName());
            existingUser.setEmail(userDto.getEmail());
            existingUser.setPhoneNumber(userDto.getPhoneNumber());

            User updatedUser = userRepository.save(existingUser);

            ResponseStructure<User> responseStructure = new ResponseStructure<>();
            responseStructure.setMessage("User updated successfully!");
            responseStructure.setStatusCode(HttpStatus.OK.value());
            responseStructure.setData(updatedUser);

            return new ResponseEntity<ResponseStructure<User>>(responseStructure, HttpStatus.OK);
        } else {
            throw new UserIdNotFoundException("User not found for update with id: " + userId);
        }
    }
	
	public ResponseEntity<ResponseStructure<User>> deleteUser(int userId) {
        Optional<User> optional = userRepository.findById(userId);

        if (optional.isPresent()) {
            User deletedUser = optional.get();
            userRepository.deleteById(userId);

            ResponseStructure<User> responseStructure = new ResponseStructure<>();
            responseStructure.setMessage("User deleted successfully!");
            responseStructure.setStatusCode(HttpStatus.OK.value());
            responseStructure.setData(deletedUser);

            return new ResponseEntity<ResponseStructure<User>>(responseStructure, HttpStatus.OK);
        } else {
            throw new UserIdNotFoundException("User not found for delete with id: " + userId);
        }
    }
	}
	
