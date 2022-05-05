package com.prgrammers.clone.config;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LogConfig {

	@Around("within(com.prgrammers.clone..*)")
	public Object logging(ProceedingJoinPoint pjp) throws Throwable {

		String params = getRequestParams();

		long startAt = System.currentTimeMillis();

		log.info("-----------> REQUEST : {}({}) = {}", pjp.getSignature().getDeclaringTypeName(),
				pjp.getSignature().getName(), params);

		Object result = pjp.proceed();

		long endAt = System.currentTimeMillis();

		log.info("-----------> RESPONSE : {}({}) = {} ({}ms)", pjp.getSignature().getDeclaringTypeName(),
				pjp.getSignature().getName(), result, endAt - startAt);
		return result;
	}

	private String paramMapToString(Map<String, String[]> paramMap) {
		return paramMap.entrySet().stream()
				.map(entry -> String.format("%s -> (%s)",
						entry.getKey(), (Arrays.toString(entry.getValue()))))
				.collect(Collectors.joining(", "));
	}

	// Get request values
	private String getRequestParams() {

		String params = "없음";

		RequestAttributes requestAttributes = RequestContextHolder
				.getRequestAttributes(); // 3

		if (requestAttributes != null) {
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder
					.getRequestAttributes()).getRequest();

			Map<String, String[]> paramMap = request.getParameterMap();
			if (!paramMap.isEmpty()) {
				params = " [" + paramMapToString(paramMap) + "]";
			}
		}

		return params;

	}
}
