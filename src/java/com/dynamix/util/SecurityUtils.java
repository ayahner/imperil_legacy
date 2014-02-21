package com.dynamix.util;


public class SecurityUtils {

	public static final Long getCurrentUserId() {
//		SecurityContext context = SecurityContextHolder.getContext();
//		if (context != null) {
//			Authentication authentication = context.getAuthentication();
//			if (authentication != null) {
//				Object principal = authentication.getPrincipal();
//				if (principal instanceof GrailsUser) {
//					return (Long) ((GrailsUser) principal).getId();
//				}
//			}
//		}
		return null;
	}
}
