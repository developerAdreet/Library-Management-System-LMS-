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

import com.lms.dto.AddressDto;
import com.lms.entity.Address;
import com.lms.service.AddressService;
import com.lms.util.ResponseStructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/address")
@Tag(name="addressController",description="has rest end points of address")
public class AddressController {

	@Autowired
	AddressService addressService;
	@Operation(operationId="createAddress",summary="adding address",description="this"
			+ " rest end point is used to create an address")
	@ApiResponses(value= {
			@ApiResponse(responseCode="201",
					     description="creates and returns the saved adderss",
					     content= @Content(mediaType="application/json",
					     schema =@Schema(implementation=Address.class)))
	})
	@PostMapping
	public ResponseEntity<ResponseStructure<Address>> saveAddress(@RequestBody @Valid AddressDto addressDto){
	return addressService.saveAddress(addressDto);	
	}
	
	
	@Operation(operationId="getting address by id",summary="getting one address by id",description="this"
			+ " rest end point is used to return an address by id",
			parameters= {@Parameter(name="addressId",required=true,
			description="pass address id")})
	@ApiResponses(value= {
			@ApiResponse(responseCode="200",
					     description="returns one address",
					     content= @Content(mediaType="application/json",
					     schema =@Schema(implementation=Address.class))),
			@ApiResponse(responseCode = "404",
			description="address not found for this id")
	})
	@GetMapping("/{addressId}")
	public ResponseEntity<ResponseStructure<Address>> findByAddressId(@PathVariable int addressId){
		return addressService.findAddressById(addressId);
	}
	
	
	@Operation(operationId="getting all address ",summary="getting all address",description="this"
			+ " rest end point is used to return all address")
	@ApiResponses(value= {
			@ApiResponse(responseCode="200",
					     description="returns all addresses",
					     content= @Content(mediaType="application/json",
					     schema =@Schema(implementation=Address.class))),
	})
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Address>>> findAll(){
		return addressService.findAll();
	}
	
	@Operation(operationId="updating address by id",summary="updating one address by id",description="this"
			+ " rest end point is used to update an address by id",
			parameters= {@Parameter(name="addressId",required=true,
			description="pass address id")})
	@ApiResponses(value= {
			@ApiResponse(responseCode="200",
					     description="updates one address",
					     content= @Content(mediaType="application/json",
					     schema =@Schema(implementation=Address.class))),
			@ApiResponse(responseCode = "404",
			description="address not found for this id to update")
	})
	
	
	@PutMapping("/{addressId}")
    public ResponseEntity<ResponseStructure<Address>> updateAddress(@PathVariable int addressId, @RequestBody @Valid AddressDto addressDto) {
        return addressService.updateAddress(addressId, addressDto);
    }
	
	@Operation(operationId="deleting address by id",summary="deleting one address by id",description="this"
			+ " rest end point is used to delete an address by id",
			parameters= {@Parameter(name="addressId",required=true,
			description="pass address id")})
	@ApiResponses(value= {
			@ApiResponse(responseCode="200",
					     description="deletes one address",
					     content= @Content(mediaType="application/json",
					     schema =@Schema(implementation=Address.class))),
			@ApiResponse(responseCode = "404",
			description="address not found for this id to delete")
	})
	@DeleteMapping("/{addressId}")
    public ResponseEntity<ResponseStructure<String>> deleteAddress(@PathVariable int addressId) {
        return addressService.deleteAddress(addressId);
    }
}
