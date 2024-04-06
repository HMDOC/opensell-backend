package com.opensell.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;

/**
 * Class to create randomized links and save file to the backend.
 * 
 * @author Achraf
 */
@Service
public class FileUploadService {
	
	public enum FileType {
		AD_IMAGE("ad-image/"),
		CUSTOMER_PROFIL("customer-profil/");

		@Getter
		private String folder;

		private FileType(String folder) {
			this.folder = folder;
		}
	}

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
	
	
	/**
	 * Save multiple files to the backend and get the links.
	 * 
	 * @author Achraf
	 * @param files
	 * @param path
	 * @return
	 * @throws Exception
	*/
	public static List<String> saveFiles(List<MultipartFile> files, FileType fileType, String path) throws Exception {
		try {
			if(files == null) throw new Exception("files cannot be null");
			if(fileType == null) throw new Exception("fileType cannot be null");
			if(path == null) throw new Exception("path cannot be null");

			List<String> filesPath = new ArrayList<>();

			files.forEach(file -> {
				try {
					String randomFileName = fileType.folder+randFileName(".png");
					File destFile = new File(path+randomFileName);

					while(destFile.exists()) {
						randomFileName = fileType.folder+randFileName(".png");
						destFile.renameTo(new File(path+randomFileName));
					}

					destFile.createNewFile();
					file.transferTo(destFile);
					filesPath.add(randomFileName);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			});

			return filesPath;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
