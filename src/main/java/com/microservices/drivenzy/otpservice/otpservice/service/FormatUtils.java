package com.microservices.drivenzy.otpservice.otpservice.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatUtils {

	//	^[-+]?[0-9]*\.?[0-9]+([eE][-+]?[0-9]+)?$
	public static boolean isValidFloatingNo(String s) {
		String regex = "[+-]?[0-9]+(\\.[0-9]+)?([Ee][+-]?[0-9]+)?";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(s);
		return (m.find() && m.group().equals(s));
	}
	public static boolean isAlphaNumeric(String s) 
	{ 
        return s != null && s.matches("^[a-zA-Z0-9]*$");
	}

	public static boolean isValidNo(String s) {
		Pattern p = Pattern.compile("[+-]?[0-9][0-9]*");
		Matcher m = p.matcher(s);
		return (m.find() && m.group().equals(s));
	}

	public static boolean isNull(String str) {
		if (str.equals("") || str == null || str.equalsIgnoreCase("null")) {
			return false;
		}
		return true;
	}

	public static boolean isValidMobileNo(String s) {
		Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");
		Matcher m = p.matcher(s);
		return (m.find() && m.group().equals(s));
	}

	public static boolean isNullOrEmpty(String str[]) {
		boolean result = false;
		if (str == null || str.length == 0) {
			result = true;
		}
		return result;
	}

	public static boolean isNullOrEmpty(String str) {
		boolean result = false;
		if (str == null || str.trim().isEmpty()) {
			result = true;
		}
		return result;
	}

	public static boolean isNullOrEmpty(Object str) {
		boolean result = false;
		if (str == null) {
			result = true;
		}
		return result;
	}

	public static boolean isNullOrEmpty(List<?> list) {
		boolean result = false;
		if (list == null || list.isEmpty()) {
			result = true;
		}
		return result;
	}

	public static boolean isNull(Object obj) {
		boolean result = false;
		if (obj == null) {
			result = true;
		}
		return result;
	}

	public static boolean isZero(int obj) {
		boolean result = false;
		if (obj == 0) {
			result = true;
		}
		return result;
	}

	public static boolean isZero(long obj) {
		boolean result = false;
		if (obj == 0) {
			result = true;
		}
		return result;
	}

	public static String formatDateToString(LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return date.format(formatter);
	}

	public static String formatDateTimeToString(LocalDateTime dateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return dateTime.format(formatter);
	}

	public static LocalDate stringToDate(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.parse(dateString, formatter);
	}

	public static LocalDateTime stringToDateTime(String dateTimeString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return LocalDateTime.parse(dateTimeString, formatter);
	}

	public static String generateQrCode(String data){
		try {
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200);

			// Convert QR code to image
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
			byte[] imageBytes = outputStream.toByteArray();

			// Convert image to Base64-encoded string
			String base64Image = Base64.encodeBase64String(imageBytes);

			return base64Image;
		} catch (WriterException | IOException e) {
			return null;
		}
	}

}