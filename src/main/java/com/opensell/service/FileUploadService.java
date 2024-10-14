package com.opensell.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.FileNotFoundException;
import com.opensell.config.AppConfig;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.net.MalformedURLException;

/**
 * Class to create randomized links and save file to the backend.
 * 
 * @author Achraf
 */
@Service
@RequiredArgsConstructor
public class FileUploadService {
	public final AppConfig appConfig;

	public static enum RandName {
		URL(12),
		FILE_NAME(30);

		@Getter
		private int length;

		private RandName(int length) {
			this.length = length;
		}
	}

	public enum FileType {
		AD_IMAGE("/ad-image/"),
		CUSTOMER_PROFIL("/customer-profile/");

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
		if (random.nextInt(0, 2) == 1) {
			return (char) random.nextInt(FIRST_UPPER_LETTER, LAST_UPPER_LETTER + 1);
		} else {
			return (char) random.nextInt(FIRST_LOWER_LETTER, LAST_LOWER_LETTER + 1);
		}
	}

	/**
	 * 
	 * @author Achraf
	 */
	public static String randFileName(String extension, RandName randName) {
		StringBuilder fileNameBuilder = new StringBuilder();

		for (int i = 0; i < randName.length; i++) {
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
	public List<String> saveFiles(List<MultipartFile> files, FileType fileType) throws Exception {
		try {
			if (files == null)
				throw new Exception("files cannot be null");
			if (fileType == null)
				throw new Exception("fileType cannot be null");

			List<String> filesPath = new ArrayList<>();
			var folderPath = appConfig.uploadPath() + fileType.folder;

			files.forEach(file -> {
				try {
					String randomFileName = randFileName(".png", RandName.URL);
					File destFile = new File(folderPath + randomFileName);

					while (destFile.exists()) {
						randomFileName = randFileName(".png", RandName.URL);
						destFile.renameTo(new File(folderPath + randomFileName));
					}

					destFile.createNewFile();
					file.transferTo(destFile);
					filesPath.add(randomFileName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});

			return filesPath;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ResponseEntity<?> readFileFromUri(String folder, String fileName) {
		try {
			var resource = new FileUrlResource(appConfig.uploadPath() + folder + "/" + fileName);
			if (!resource.exists()) {
				throw new FileNotFoundException(fileName);
			}

			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(resource);
		} catch (FileNotFoundException e) {
			return ResponseEntity.status(400).body("File does not exists.");
		} catch (MalformedURLException e) {
			return ResponseEntity.status(400).body("Malformed URL.");
		}
	}
}
