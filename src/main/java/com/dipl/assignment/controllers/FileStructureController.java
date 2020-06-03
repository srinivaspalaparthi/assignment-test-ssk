package com.dipl.assignment.controllers;

import java.io.File;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dipl.assignment.utility.FileStructureUtility;
import com.dipl.assignment.utility.ResponseBean;
import com.google.gson.JsonParser;

/**
 * @author 
 *
 */
@RestController
@RequestMapping("/files")
public class FileStructureController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private FileStructureUtility fileStructureUtility;

	public FileStructureController(FileStructureUtility fileStructureUtility) {
		this.fileStructureUtility = fileStructureUtility;
	}

	/**
	 * @param path
	 * @return
	 */
	@PostMapping(value = "/path")
	public ResponseEntity<ResponseBean> getFileStructue(@RequestBody String path) {
		logger.debug("#getFileStructue" + path);

		ResponseBean responseBean = new ResponseBean();
		responseBean.setStatus(HttpStatus.OK);
		responseBean.setMessage("success");

		Map<String, Object> structureDetails = null;

		try {
			String absolutePath = new JsonParser().parse(path).getAsJsonObject().get("absolutePath").getAsString();
			File file = new File(absolutePath);
			if (file.exists() && file.isDirectory()) {
				structureDetails = fileStructureUtility.getFolderStructureDetails(file);
			} else if (file.exists()) {
				structureDetails = fileStructureUtility.getFileStructureDetails(file);
			} else {
				responseBean.setStatus(HttpStatus.EXPECTATION_FAILED);
				responseBean.setMessage("specified path was not absolute");
			}
			responseBean.setData(structureDetails);
		} catch (Exception e) {
			e.printStackTrace();
			responseBean.setStatus(HttpStatus.EXPECTATION_FAILED);
			responseBean.setMessage("something gone wrong");
			responseBean.setData(null);
			logger.error("#getFileStructue" + e.getMessage());
			return ResponseEntity.status(responseBean.getStatus()).body(responseBean);
		}
		return ResponseEntity.status(responseBean.getStatus()).body(responseBean);
	}

}
