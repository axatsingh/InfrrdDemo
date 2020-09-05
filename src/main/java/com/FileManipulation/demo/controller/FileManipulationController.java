package com.FileManipulation.demo.controller;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.FileManipulation.demo.model.FileInfo;

@RestController
@RequestMapping("/file")
public class FileManipulationController {
	
	Logger logger = LoggerFactory.getLogger(FileManipulationController.class);
	
	@Value("${sourceFilePath}")
	private String sourceFilePath;
	
	@RequestMapping(value = "/download", method = RequestMethod.GET) 
	public ResponseEntity<Object> downloadFile() {
	   logger.debug("Entering the download functionality");
	   try {
		   File file = new File(sourceFilePath);
		   InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		   HttpHeaders headers = new HttpHeaders();
		      
		   headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
		   headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		   headers.add("Pragma", "no-cache");
		   headers.add("Expires", "0");
		      
		   ResponseEntity<Object> 
		   responseEntity = ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(
		      MediaType.parseMediaType("application/txt")).body(resource);
		      
		   return responseEntity;
	   }catch (IOException e) {
		   logger.error(e.getMessage());
	   }catch (Exception e) {
		   logger.error(e.getMessage());
	   }
	   return null;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST) 
	public ResponseEntity<String> deleteFile(@RequestBody FileInfo fileInfo) {
		
	   logger.debug("Entering the delete functionality");
	   try {
		   boolean res= Files.deleteIfExists(Paths.get(fileInfo.getSourceDestination()));
		   	   
		   return new ResponseEntity<String>(res?"Deleted":"Some error occured",HttpStatus.OK);
	   }catch (IOException e) {
		   logger.error(e.getMessage());
	   }catch (Exception e) {
		   logger.error(e.getMessage());
	   }
	   return null;
	}
	
	@RequestMapping(value = "/copy", method = RequestMethod.POST) 
	public ResponseEntity<String> copyFile(@RequestBody FileInfo fileInfo)  {
		
	   logger.debug("Entering the copy functionality");
	   try {
		   Path sourceFile = Paths.get(fileInfo.getSourceDestination());
		   Path targetFile = Paths.get(fileInfo.getTragetDestination());
		   
		   Files.copy(sourceFile, targetFile,StandardCopyOption.REPLACE_EXISTING);
		   	   
		   return new ResponseEntity<String>("File Copied",HttpStatus.OK);
	   }catch (IOException e) {
		   logger.error(e.getMessage());
	   }catch (Exception e) {
		   logger.error(e.getMessage());
	   }
	   return null;
	}
	
	
}
