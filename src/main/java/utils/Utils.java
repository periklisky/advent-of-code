package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class Utils {
	public static File getFileFromResource(String fileName) {
		return new File(Utils.class.getClassLoader().getResource(fileName).getFile());
	}
	
	public static InputStream getFileFromResourceAsStream(String fileName) {
		InputStream stream = Utils.class.getClassLoader().getResourceAsStream(fileName);
		
		if (stream == null) {
			throw new IllegalArgumentException("File '" + fileName + "' not found!");
		}
		return stream;
	}
	
	public static BufferedReader getBufferedReader(String filename) {
		InputStream stream = getFileFromResourceAsStream(filename);
		InputStreamReader streamReader = new InputStreamReader(stream);
		return new BufferedReader(streamReader);
	}

	public static String getMD5Hash(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] digest = md.digest();
		String hash = DatatypeConverter.printHexBinary(digest);

		return hash;
	}
}
