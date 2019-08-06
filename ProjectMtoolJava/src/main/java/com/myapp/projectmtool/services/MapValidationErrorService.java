package com.myapp.projectmtool.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
public class MapValidationErrorService {
    public ResponseEntity<?> mapValidationService(BindingResult result){

        //checks for errors while doing post
        //if it has error
        if(result.hasErrors()){
            Map<String,String > errorMap = new HashMap<>();

            //adding to a proper map so that we can use in UI in future
            for (FieldError error : result.getFieldErrors()){
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            return  new ResponseEntity<Map<String,String >>(errorMap, HttpStatus.BAD_REQUEST);

        }
        return  null;
    }
}
