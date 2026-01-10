package com.lms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lms.dto.UserDto;
import com.lms.entity.User;
import com.lms.service.UserService;
import com.lms.util.ResponseStructure;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/user")
@Tag(name="UserController",description="has rest end points of user")
public class UserController {

	@Autowired
	UserService userService;
	
	@Operation(operationId="createUser",summary="adding user",description="this"
			+ " rest end point is used to create a user and map with an address",
			parameters = { @Parameter(name="addressId",required=true,
			description="pass address id to which the user will be linked")})
	@ApiResponses(value= {
			@ApiResponse(responseCode="201",
					     description="creates and returns the saved user",
					     content= @Content(mediaType="application/json",
					     schema =@Schema(implementation=User.class))),
			@ApiResponse(responseCode = "404",
			description="user cannot be saved to this adressId")
	})
	@PostMapping("/{addressId}")
	public ResponseEntity<ResponseStructure<User>> saveUser(@RequestBody UserDto userDto,@PathVariable int addressId){
		return userService.saveUser(userDto,addressId);	
		}
	
	
	
	
	@Operation(operationId="getting user by id",summary="getting one user by id",description="this"
			+ " rest end point is used to return an user by id",
			parameters= {@Parameter(name="userId",required=true,
			description="pass user id")})
	@ApiResponses(value= {
			@ApiResponse(responseCode="200",
					     description="returns one user",
					     content= @Content(mediaType="application/json",
					     schema =@Schema(implementation=User.class))),
			@ApiResponse(responseCode = "404",
			description="user not found for this id")
	})
	@GetMapping("/{userId}")
	public ResponseEntity<ResponseStructure<User>> findUserById(@PathVariable int userId){
		return userService.findUserById(userId);
}
	
	
	
	@Operation(operationId="getting all users ",summary="getting all users",description="this"
			+ " rest end point is used to return all users")
	@ApiResponses(value= {
			@ApiResponse(responseCode="200",
					     description="returns all users",
					     content= @Content(mediaType="application/json",
					     schema =@Schema(implementation=User.class))),
	})
	@GetMapping
	public ResponseEntity<ResponseStructure<List<User>>> fetchAlLUsers(){
		return userService.fetchAllUsers();
	}
	
	
	
	
	@Operation(operationId="updating user by id",summary="updating one user by id",description="this"
			+ " rest end point is used to update a user by id",
			parameters= {@Parameter(name="userId",required=true,
			description="pass user id")})
	@ApiResponses(value= {
			@ApiResponse(responseCode="200",
					     description="updates one user",
					     content= @Content(mediaType="application/json",
					     schema =@Schema(implementation=User.class))),
			@ApiResponse(responseCode = "404",
			description="user not found for this id to update")
	})
	
	@PutMapping("/{userId}")
	public ResponseEntity<ResponseStructure<User>> updateUser(@RequestBody UserDto userDto, @PathVariable int userId){
		return userService.updateUser(userDto, userId);
	}
	
	
	
	@Operation(operationId="deleting user by id",summary="deleting one user by id",description="this"
			+ " rest end point is used to delete an user by id",
			parameters= {@Parameter(name="userId",required=true,
			description="pass user id")})
	@ApiResponses(value= {
			@ApiResponse(responseCode="200",
					     description="deletes one user",
					     content= @Content(mediaType="application/json",
					     schema =@Schema(implementation=User.class))),
			@ApiResponse(responseCode = "404",
			description="user not found for this id to delete")
	})
	@DeleteMapping("/{userId}")
	public ResponseEntity<ResponseStructure<User>> deleteUser(@PathVariable int userId){
		return userService.deleteUser(userId);
	}
}
