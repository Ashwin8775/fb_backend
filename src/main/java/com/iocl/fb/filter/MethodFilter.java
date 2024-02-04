package com.iocl.fb.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author t_Salian
 *
 */
@Component
public class MethodFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (!(request.getMethod().equals("GET") || request.getMethod().equals("POST"))) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		} else {
			try {
				String host = "";
				Enumeration<String> hosts = request.getHeaders("host");
				Enumeration<String> refer = request.getHeaders("Referer");
				String referer = "";

				while (refer.hasMoreElements()) {
					referer = (String) refer.nextElement();
					referer = referer.replace("https://", "").replace("http://", "").split(":")[0].split("/")[0];
					// Intention is to disallow cross Referers
				}
				while (hosts.hasMoreElements()) {
					host = hosts.nextElement().split(":")[0];// Splitting for dealing with localhost
				}

				if (host.equals("associates.indianoil.co.in") || host.equals("spandan.indianoil.co.in")
						|| host.equals("webapp.indianoil.co.in") || host.equals("localhost")
						|| host.equals("10.146.66.122") || host.equals("10.146.76.124")
						|| host.equals("uat.indianoil.co.in")) {
					if (!host.equals(referer) && (!referer.equals("localhost"))) {
						// response.sendError(HttpServletResponse.SC_BAD_REQUEST);
						// return;
					}
					response.setHeader("Access-Control-Allow-Methods", "GET,POST");

					response.addHeader("Strict-Transport-Security", "max-age=31556926; includeSubDomains");
					response.addHeader("X-XSS-Protection", "1; mode=block");
					response.addHeader("X-Frame-Options", "SAMEORIGIN");
					response.addHeader("X-Content-Type-Options", "nosniff");
					response.addHeader("Public-Key-Pins",
							"pin-sha256=\"jjaSQ+A6AolUfqvyK3s0gI907nVYJ/uyZpoKT6iZCvE=\"; " + "  max-age=5184000;");

					filterChain.doFilter(request, response);
				} else {
					System.out.println("Error in filter : Not a valid Host");
					response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				}

			} catch (Exception ex) {
				System.out.println("Error in filter : " + ex.getMessage());
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				ex.printStackTrace();
			}
		}
	}
}
