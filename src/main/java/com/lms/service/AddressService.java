package com.lms.service;


import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.lms.dto.AddressDto;
import com.lms.entity.Address;
import com.lms.exception.AddressIdNotFoundException;
import com.lms.repository.AddressRepository;
import com.lms.util.ResponseStructure;


@Service
public class AddressService {

   
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	AddressRepository addressRepository;

    
	public ResponseEntity<ResponseStructure<Address>> saveAddress(AddressDto addressDto){
//		Address address= new Address();
//		address.setAddressId(addressDto.getAddressId());
//		address.setArea(addressDto.getArea());
//		address.setCity(addressDto.getCity());
//		address.setCountry(addressDto.getCountry());
//		address.setHouseNumber(addressDto.getHouseNumber());
//		address.setPincode(addressDto.getPincode());
//    	address.setState(addressDto.getState());
//		return response;
		Address address = mapper.map(addressDto, Address.class);
		addressRepository.save(address);
		ResponseStructure<Address> responseStructure = new ResponseStructure<>();
		responseStructure.setMessage("Address Saved Successuflly!");
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setData(address);
		return new ResponseEntity<ResponseStructure<Address>>(responseStructure,HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseStructure<Address>> findAddressById(int addressId){
		Optional<Address> optional =addressRepository.findById(addressId);
		ResponseStructure<Address> responseStructure = new ResponseStructure<>();
		if(optional.isPresent()) {
			Address address=optional.get();
			AddressDto addressDto=mapper.map(address, AddressDto.class);
			responseStructure.setMessage("Address found successfully for this id!");
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setData(address);
			return new ResponseEntity<ResponseStructure<Address>>(responseStructure,HttpStatus.OK);
		}
		else {
//			responseStructure.setMessage("Address not found for this id!");
//			responseStructure.setStatusCode(HttpStatus.BAD_REQUEST.value());
//			responseStructure.setData(null);
//			return new ResponseEntity<ResponseStructure<AddressDto>>(responseStructure,HttpStatus.BAD_REQUEST);
			 throw new AddressIdNotFoundException("Address of this id is not available");
		}
	}
	
	
	public ResponseEntity<ResponseStructure<List<Address>>> findAll() {
	    List<Address> addresses = addressRepository.findAll();

	    ResponseStructure<List<Address>> responseStructure = new ResponseStructure<>();
	    responseStructure.setMessage("All addresses fetched successfully!");
	    responseStructure.setStatusCode(HttpStatus.OK.value());
	    responseStructure.setData(addresses);

	    return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseStructure<Address>> updateAddress(int addressId,AddressDto addressDto){
		 Optional<Address> optional = addressRepository.findById(addressId);
	        ResponseStructure<Address> responseStructure = new ResponseStructure<>();
            
	        if (optional.isPresent()) {
	            Address existingAddress = optional.get();
	             mapper.map(addressDto, existingAddress);
	            
	          existingAddress.setAddressId(addressId);
	          existingAddress.setHouseNumber(addressDto.getHouseNumber());
	          existingAddress.setArea(addressDto.getArea());
	          existingAddress.setCity(addressDto.getCity());
	          existingAddress.setPincode(addressDto.getPincode());
	          existingAddress.setState(addressDto.getState());

	            Address updatedAddress = addressRepository.save(existingAddress);

	            responseStructure.setMessage("Address updated successfully!");
	            responseStructure.setStatusCode(HttpStatus.OK.value());
	            responseStructure.setData(updatedAddress);

	            return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	        } else {
//	            responseStructure.setMessage("Address not found for update!");
//	            responseStructure.setStatusCode(HttpStatus.BAD_REQUEST.value());
//	            responseStructure.setData(null);

	           throw new AddressIdNotFoundException("Address not updated");
	        
	}
	}
	
	public ResponseEntity<ResponseStructure<String>> deleteAddress(int addressId) {
        Optional<Address> optional = addressRepository.findById(addressId);
        ResponseStructure<String> responseStructure = new ResponseStructure<>();

        if (optional.isPresent()) {
            addressRepository.deleteById(addressId);
            responseStructure.setMessage("Address deleted successfully!");
            responseStructure.setStatusCode(HttpStatus.OK.value());
            responseStructure.setData("Deleted Id: " + addressId);

            return new ResponseEntity<>(responseStructure, HttpStatus.OK);
        } else {
//            responseStructure.setMessage("Address not found for delete!");
//            responseStructure.setStatusCode(HttpStatus.NOT_FOUND.value());
//            responseStructure.setData(null);
        	 throw new AddressIdNotFoundException("Address not deleted");
           
        }
    }
}
