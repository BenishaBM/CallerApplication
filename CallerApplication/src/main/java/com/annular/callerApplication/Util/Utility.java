package com.annular.callerApplication.Util;

import java.util.List;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Utility {
	
	private static final Logger logger = LoggerFactory.getLogger(Utility.class);

    public static String getSiteUrl(HttpServletRequest httpServletRequest) {
        String siteUrl = httpServletRequest.getRequestURL().toString();
     
        return siteUrl.replace(httpServletRequest.getServletPath(), "");
    }

    public static boolean isNullOrBlankWithTrim(String value) {
        return value == null || value.trim().equals("null") || value.trim().isEmpty();
    }

    public static boolean isNullOrEmptyList(List<?> value) {
        return value == null || value.isEmpty();
    }

    public static boolean isNullOrEmptySet(Set<?> value) {
        return value == null || value.isEmpty();
    }

    public static boolean isNullOrEmptyMap(Map<?, ?> value) {
        return value == null || value.isEmpty();
    }

    public static boolean isNullOrZero(Integer value) {
        return value == null || value == 0;
    }

    public static boolean isNullObject(Object obj) {
        return obj == null;
    }

    public static double parseDouble(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new NumberFormatException("Empty or null string");
        }
        if (value.equalsIgnoreCase("NaN") || value.equalsIgnoreCase("Infinity")) {
            throw new NumberFormatException("Invalid double value: " + value);
        }
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            // Log the value causing the exception for debugging
            logger.error("Invalid double value: {}", value);
            throw new NumberFormatException("Invalid double value: " + value);
        }
    }

}
