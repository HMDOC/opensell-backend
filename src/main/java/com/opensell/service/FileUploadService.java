package com.opensell.service;

import java.io.File;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {
	
	public static final String AD_IMAGE_PATH = "/ad-image";
	
	private static Random random = new Random();
	
	// POSTION FROM ASCI TABLE
	public static final byte FIRST_LOWER_LETTER = 97;
	public static final byte LAST_LOWER_LETTER = 122;
	public static final byte FIRST_UPPER_LETTER = 65;
	public static final byte LAST_UPPER_LETTER = 90;
	
	/**
	 * Function that return a random letter using the position of the ASCII table.
	 * For example, 'a' is at position 97 in the ASCI table.
	 * 
	 * @author Achraf
	 */
	public static char randAsciiLetter() {
		// Random to choose between upper(1) or lower(0).
		if(random.nextInt(0, 2) == 1) {
			return (char) random.nextInt(FIRST_UPPER_LETTER, LAST_UPPER_LETTER + 1);
		} else {
			return (char) random.nextInt(FIRST_LOWER_LETTER, LAST_LOWER_LETTER + 1);
		}
	}
	

	/**
	 * 
	 * @author Achraf
	 */
	public static String randFileName(String extension) {
		StringBuilder fileNameBuilder = new StringBuilder();

		for(int i = 0; i < 30; i++) {
			fileNameBuilder.append(randAsciiLetter());
		}
		fileNameBuilder.append(extension);
		
		return fileNameBuilder.toString();
	}
	
	
	public static boolean saveFile(List<MultipartFile> files, String path) throws Exception {
		files.forEach(file -> {
			File destFile = new File(path+"/"+randFileName(".png"));
			
			while(destFile.exists()) {
				destFile.renameTo(new File(path+"/"+randFileName(".png")));
			}
			
			try {
				destFile.createNewFile();
				file.transferTo(destFile);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		});

		return false;
	}
}
