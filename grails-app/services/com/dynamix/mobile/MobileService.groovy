package com.dynamix.mobile

import org.apache.commons.lang.StringUtils;

class MobileService {
	boolean detect(request) {
		def userAgent = request.getHeader('User-Agent')
		log.info('Current user agent:['+userAgent+']');
		def currentDevice = request.getAttribute('currentDevice');
		if(currentDevice){
			boolean detected = currentDevice.isMobile()
			log.info "device:"+currentDevice+", is mobile:"+detected
			if (detected) {
				if (userAgent?.contains('iPad')) {
					detected = false
				} else if (userAgent?.contains('Android') && !userAgent?.contains('Mobile')) {
					detected = false
				}
			}
							return detected
//			log.error "Detected:"+detected+", but returning true for testing purposes..."
//			return true
		}else{
			return false;
		}
	}
}
