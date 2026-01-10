package com.lms.dto;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {

	private int addressId;
	@Min(value=100)
	private int houseNumber;
	@NotBlank(message="area cant be blank")
	private String area;
	@NotNull
	private String city;
	private String state;
	private String country;
	@Digits(integer= 6, fraction=0)
	private long pincode;
}
