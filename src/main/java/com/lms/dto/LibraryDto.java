package com.lms.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibraryDto {

	private int libraryId;
	private String libraryName;
	@Min(value= 700000000)
	@Max(value= 99999999)
	private long phoneNumber;
}
