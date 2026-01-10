package com.lms.service;

import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.lms.dto.LibraryDto;
import com.lms.entity.Address;
import com.lms.entity.Library;
import com.lms.exception.AddressIdNotFoundException;
import com.lms.exception.LibraryIdNotFoundException;
import com.lms.repository.AddressRepository;
import com.lms.repository.LibraryRepository;
import com.lms.util.ResponseStructure;

@Service
public class LibraryService {

	@Autowired
	AddressRepository addressRepository;
	
	@Autowired
	LibraryRepository libraryRepository;
	
	@Autowired
	ModelMapper mapper;

   
	
	public ResponseEntity<ResponseStructure<Library>> saveLibrary(LibraryDto libraryDto, int addressId){
		Optional<Address>dbAddress=addressRepository.findById(addressId);
		Library library = mapper.map(libraryDto,Library.class);
		if(dbAddress.isPresent()) {
			library.setAddress(dbAddress.get());
			libraryRepository.save(library);
			ResponseStructure<Library> responseStructure = new ResponseStructure<>();
			responseStructure.setMessage("Library saved successfully");
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setData(library);
			return new ResponseEntity<ResponseStructure<Library>>(responseStructure,HttpStatus.OK);
		}
		else {
			throw new AddressIdNotFoundException("Address of this id is not available");
		}
	}
	
	public ResponseEntity<ResponseStructure<Library>> findLibraryById(int libraryId) {
        Optional<Library> optional = libraryRepository.findById(libraryId);

        if (optional.isPresent()) {
            Library library = optional.get();

            ResponseStructure<Library> responseStructure = new ResponseStructure<>();
            responseStructure.setMessage("Library found successfully!");
            responseStructure.setStatusCode(HttpStatus.OK.value());
            responseStructure.setData(library);

            return new ResponseEntity<ResponseStructure<Library>>(responseStructure, HttpStatus.OK);
        } else {
            throw new LibraryIdNotFoundException("Library not found with this id");
        }
    }
	
	public ResponseEntity<ResponseStructure<List<Library>>> findAllLibrary(){
		 List<Library> library = libraryRepository.findAll();

	        ResponseStructure<List<Library>> responseStructure = new ResponseStructure<>();
	        responseStructure.setMessage("All libraries fetched successfully!");
	        responseStructure.setStatusCode(HttpStatus.OK.value());
	        responseStructure.setData(library);
			return new ResponseEntity<ResponseStructure<List<Library>>>(responseStructure, HttpStatus.OK);

	        
	}
	
	public ResponseEntity<ResponseStructure<Library>> updateLibrary( LibraryDto libraryDto,int libraryId) {
        Optional<Library> optional = libraryRepository.findById(libraryId);

        if (optional.isPresent()) {
            Library existingLibrary = optional.get();

            existingLibrary.setLibraryName(libraryDto.getLibraryName());
            existingLibrary.setPhoneNumber(libraryDto.getPhoneNumber());

            Library updatedLibrary = libraryRepository.save(existingLibrary);

            ResponseStructure<Library> responseStructure = new ResponseStructure<>();
            responseStructure.setMessage("Library updated successfully!");
            responseStructure.setStatusCode(HttpStatus.OK.value());
            responseStructure.setData(updatedLibrary);

            return new ResponseEntity<ResponseStructure<Library>>(responseStructure, HttpStatus.OK);
        } else {
            throw new LibraryIdNotFoundException("Library not found for updation with this id");
        }
    }
	
	public ResponseEntity<ResponseStructure<String>> deleteLibrary(int libraryId) {
        Optional<Library> optional = libraryRepository.findById(libraryId);

        if (optional.isPresent()) {
           
            libraryRepository.deleteById(libraryId);

            ResponseStructure<String> responseStructure = new ResponseStructure<>();
            responseStructure.setMessage("Library deleted successfully!");
            responseStructure.setStatusCode(HttpStatus.OK.value());
            responseStructure.setData("Deleted library");

            return new ResponseEntity<>(responseStructure, HttpStatus.OK);
        } else {
            throw new LibraryIdNotFoundException("Library not found for deletion with this id");
        }
    }
}
