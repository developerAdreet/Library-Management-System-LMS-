package com.lms.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

	private int userId;
	private String userName;
	@Min(value= 700000000)
	@Max(value= 99999999)
	private long phoneNumber;
	private String email;
}
