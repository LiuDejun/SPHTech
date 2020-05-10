/**
 * 
 */
package com.my.cloud.test;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * @author YCKJ1148
 *
 */
public class Test {

	public static void main(String[] args) {
		String token = "dsadasfdsa";
		String userPhone = "";
		try {
			Algorithm algorithm = Algorithm.HMAC256("123456");
			JWTVerifier verifier = JWT.require(algorithm).withIssuer("userPhone").build();
			DecodedJWT jwt = verifier.verify(token);
			userPhone = jwt.getClaim("phone").asString();
		} catch (JWTVerificationException e) {
			e.printStackTrace();
		}
		System.out.println(userPhone);
	}
}
