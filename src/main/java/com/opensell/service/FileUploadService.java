package com.opensell.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import com.opensell.config.AppConfig;
import com.opensell.enums.FileType;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import lombok.Getter;

/**
 * Class to create randomized links and save file to the backend.
 * 
 * @author Achraf
 */
@Service
@RequiredArgsConstructor
public class FileUploadService {
	private final AppConfig appConfig;

	@Getter
    public enum RandName {
		URL(12),
		FILE_NAME(30);

		private final int length;

		RandName(int length) {
			this.length = length;
		}
	}

	private static final Random random = new Random();
	
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
	public static String randFileName(String extension, RandName randName) {
		StringBuilder fileNameBuilder = new StringBuilder();

		for(int i = 0; i < randName.length; i++) {
			fileNameBuilder.append(randAsciiLetter());
		}
		fileNameBuilder.append(".").append(extension);
		
		return fileNameBuilder.toString();
	}

	public String getExtensionsFromMultipartFile(MultipartFile file) {
		String[] seperatedFileName = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
		return seperatedFileName[seperatedFileName.length - 1];
	}

	/**
	 * Save multiple files to the backend and get the links.
	 * 
	 * @author Achraf
	 * @param files The files you want the save.
	 * @param fileType The type of the files.
	 * @since 1.0
	*/
	public List<String> saveFiles(List<MultipartFile> files, FileType fileType) {
		try {
			if(files == null) throw new Exception("files cannot be null");
			if(fileType == null) throw new Exception("fileType cannot be null");
			List<String> filesPath = new ArrayList<>();
			var folderPath = appConfig.imageServerPath()+fileType.folder;

			files.forEach(file -> {
				try {
					String extension = getExtensionsFromMultipartFile(file);
					File destFile = new File(folderPath+randFileName(extension, RandName.FILE_NAME));

					while(destFile.exists()) {
						destFile.renameTo(new File(folderPath+randFileName(extension, RandName.FILE_NAME)));
					}

					destFile.createNewFile();
					file.transferTo(destFile);
					filesPath.add(destFile.getName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			});

			return filesPath;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ResponseEntity<?> readFileFromUri(String folder, String fileName) {
		try {
			var resource = new FileUrlResource(appConfig.imageServerPath() + folder + "/" + fileName);
			if (!resource.exists()) {
				throw new FileNotFoundException(fileName);
			}

			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
		} catch (FileNotFoundException e) {
			return ResponseEntity.status(400).body("File does not exists.");
		} catch (MalformedURLException e) {
			return ResponseEntity.status(400).body("Malformed URL.");
		}
	}
}
