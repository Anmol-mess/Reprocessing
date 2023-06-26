package com.springrest.servicerest.Core;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CoreUtil {
	
	public static String getQuotedString(String item) {
		if (item == null)
			return item;

		item = JSONValue.escape(item);
		item = "\""+item+"\"";
		return item;
	}
	public static String getDateAndTimeForMessageGenarationField(String originalDateTime) {
		String originalDateTimeString = originalDateTime;
		String dateTimeString = originalDateTimeString.trim();
		String withoutTimeZone;
		if (dateTimeString.lastIndexOf("-") + 4 + 1 == dateTimeString.length()) {
			withoutTimeZone = dateTimeString.replaceAll("(-\\d\\d\\d\\d)", "");
		} else {
			withoutTimeZone = dateTimeString;
		}

		String allSpecialsRemoved = withoutTimeZone.replaceAll("[^0-9]", "");
		int finalDateTimeLength = allSpecialsRemoved.length();

		String finalFormattedDateTime = null;
		// yyyyMMdd - format for date
		if (finalDateTimeLength == 8) {
			finalFormattedDateTime = String.valueOf(formattingForDateForMessageGenarationField(allSpecialsRemoved));
		} else { // format for datetime
			finalFormattedDateTime = formattingForDateTimeForMessageGenarationField(allSpecialsRemoved,
					finalDateTimeLength);
		}

		return finalFormattedDateTime;
	}

	public static String formattingForDateTimeForMessageGenarationField(String dateTimeString,
			int finalDateTimeLength) {
		if (finalDateTimeLength < 8) { // invalid dateTime

		} else if (finalDateTimeLength > 8 && finalDateTimeLength < 15) { // convert to yyyyMMddHHmmss
			dateTimeString = String.format("%-14s", dateTimeString).replace(' ', '0');
		} else if (finalDateTimeLength > 14) { // truncate to 14
			dateTimeString = dateTimeString.substring(0, 14);
		}

		// parse datetime
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String finalDateTimeFormat = null;
		try {	
			LocalDateTime dateTimeObject = LocalDateTime.parse(dateTimeString, formatter);
			dateTimeObject.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			finalDateTimeFormat = dateTimeObject.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		} catch (Exception e) {
			//log.error(MessageFormat.format(">>>>> Failed to parse {0} dateString", dateTimeString));

		}

		return finalDateTimeFormat;
	}

	public static String formattingForDateForMessageGenarationField(String dateString) {
		// parse date
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		String finalDateTimeFormat = null;
		try {
			LocalDate dateTimeObject = LocalDate.parse(dateString, formatter);
			finalDateTimeFormat = dateTimeObject.format(DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00"));
		} catch (Exception e) {
			//log.error(MessageFormat.format(">>>>> Failed to parse {0} dateString", dateString));
		}

		return finalDateTimeFormat;
	}


}
