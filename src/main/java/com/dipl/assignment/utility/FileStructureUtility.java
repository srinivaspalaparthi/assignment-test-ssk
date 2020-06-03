package com.dipl.assignment.utility;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author 
 *
 */
@Component
public class FileStructureUtility {

	private Logger logger = LoggerFactory.getLogger(getClass());

//	public static void main(String[] args) {
//		File file = new File("C:\\Users\\Pictures\\Screenshots/");
//		Map<String, Object> folderStructureDetails = getFolderStructureDetails(file);
//
//		File filetype = new File("C:\\Users\\Pictures\\Screenshots\\Screenshot (4).png");
//		Map<String, Object> fileStructureDetails = getFileStructureDetails(filetype);
//
//		System.out.println("folder details" + folderStructureDetails);
//		System.out.println("file details" + fileStructureDetails);
//	}

	/**
	 * @param path
	 * @return
	 */
	public Map<String, Object> getFolderStructureDetails(File path) {
		logger.debug("request with param file" + path.getAbsolutePath());
		try {
			// list the files in path
			File[] content = path.listFiles();
			List<Map<String, Object>> files = new LinkedList<>();
			// loop list files
			for (File f : content) {
				if (f.isDirectory()) {
					// recursive loop if folder
					logger.debug("recursive start" + f.getParent());
					getFolderStructureDetails(f);
				} else {
					Map<String, Object> fileDetails = new HashMap<>();
					String name = f.getName();
					String parent = f.getParent();
					fileDetails.put("foldername", parent.substring(parent.lastIndexOf("\\") + 1));
					fileDetails.put("name", name);
					fileDetails.put("path", f.getAbsolutePath());
					Map<String, Object> fileAttributes = new HashMap<>();
					fileAttributes.put("canRead", f.canRead());
					fileAttributes.put("canWrite", f.canWrite());
					fileAttributes.put("canExecute", f.canExecute());
					fileDetails.put("attributes", fileAttributes);
					files.add(fileDetails);
				}
			}
			// construct response
			Map<String, Object> result = new HashMap<>();
			result.put("files", files);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * @param path
	 * @return
	 */
	public Map<String, Object> getFileStructureDetails(File path) {
		logger.debug("request with param file" + path.getAbsolutePath());
		Map<String, Object> fileDetails = new HashMap<>();
		try {
			if (path.isFile()) {
				String name = path.getName();
				String parent = path.getParent();
				fileDetails.put("foldername", parent.substring(parent.lastIndexOf("\\") + 1));
				fileDetails.put("name", name);
				fileDetails.put("path", path.getAbsolutePath());
				Map<String, Object> fileAttributes = new HashMap<>();
				fileAttributes.put("canRead", path.canRead());
				fileAttributes.put("canWrite", path.canWrite());
				fileAttributes.put("canExecute", path.canExecute());
				fileDetails.put("attributes", fileAttributes);
			} else {
				fileDetails.put("error", "this logic ment for file only");
				logger.debug("error", "this logic ment for file only");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return fileDetails;
	}

}
