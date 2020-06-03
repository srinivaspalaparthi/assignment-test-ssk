package com.dipl.assignment;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dipl.assignment.controllers.FileStructureController;
import com.dipl.assignment.utility.ResponseBean;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class AssigmentTestCases {

	@Autowired
	FileStructureController fileStructureController;

	@Test
	public void getStructuresTest() {
		// file path
		String request = "{\r\n"
				+ "	\"absolutePath\":\"C:\\\\Users\\\\Pictures\\\\Screenshots\\\\Screenshot (4).png\"\r\n"
				+ "}";

		ResponseEntity<ResponseBean> responseEntity = fileStructureController.getFileStructue(request);
		ResponseBean responseBean = responseEntity.getBody();
		assertEquals(responseBean.getStatus(), HttpStatus.OK);

	}

	@Test
	public void getStructuresTest1() {
		// folder path
		String request = "{\r\n" + 
				"	\"absolutePath\":\"C:\\\\Users\\\\Pictures\\\\Screenshots\\\\\"\r\n" + 
				"}";

		ResponseEntity<ResponseBean> responseEntity = fileStructureController.getFileStructue(request);
		ResponseBean responseBean = responseEntity.getBody();
		assertEquals(responseBean.getStatus(), HttpStatus.OK);
	}

	@Test
	public void getStructuresTest2() {
		// incorrect path
		String request = "{\r\n" + "	\"absolutePath\":\"\\\\\Pictures\\\\Screenshots\\\\\"\r\n"
				+ "}";

		ResponseEntity<ResponseBean> responseEntity = fileStructureController.getFileStructue(request);
		ResponseBean responseBean = responseEntity.getBody();
		assertEquals(responseBean.getStatus(), HttpStatus.EXPECTATION_FAILED);
	}

}
