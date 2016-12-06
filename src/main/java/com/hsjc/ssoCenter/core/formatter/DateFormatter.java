package com.hsjc.ssoCenter.core.formatter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author : zga
 * @date : 2015-11-24
 */
public class DateFormatter implements Formatter<Date> {
	@Override
	public Date parse(String s, Locale locale) throws ParseException {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MMM/dd");
		return dateFormat.parse(s);
	}

	@Override
	public String print(Date date, Locale locale) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MMM/dd");
		return dateFormat.format(date);
	}
}
