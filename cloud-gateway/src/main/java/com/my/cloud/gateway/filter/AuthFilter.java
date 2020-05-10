/**
 * 
 */
package com.my.cloud.gateway.filter;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.my.cloud.constant.StatusCodeConstants;

import cn.hutool.core.collection.CollectionUtil;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author YCKJ1148
 *
 */
@Component
@Order(0)
@PropertySource(value = "classpath:jwt.properties")
public class AuthFilter implements GlobalFilter {
	
	/**
	 * 不校验的url
	 */
	@Value("#{'${jwt.ignoreUrlList}'.split(',')}")
	public List<String> ignoreUrl;
	
    @Value("${jwt.secret.key}")
    private String secretKey;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		String requestUrl = exchange.getRequest().getPath().toString();
		boolean status = CollectionUtil.contains(ignoreUrl, requestUrl);
		if (!status) {
			String token = exchange.getRequest().getHeaders().getFirst("Authorization-X");
			//未携带token或token在黑名单内
	        if (StringUtils.isEmpty(token)) {
	            ServerHttpResponse originalResponse = exchange.getResponse();
	            originalResponse.setStatusCode(HttpStatus.OK);
	            originalResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
	            byte[] response = ("{\"code\": \""+ StatusCodeConstants.TOKEN_NONE +"\",\"msg\": \"401 Unauthorized.\"}")
	                    .getBytes(StandardCharsets.UTF_8);
	            DataBuffer buffer = originalResponse.bufferFactory().wrap(response);
	            return originalResponse.writeWith(Flux.just(buffer));
	        }
	        //取出token包含的身份
	        String userName = verifyJWT(token);
	        if(userName.isEmpty()){
	            ServerHttpResponse originalResponse = exchange.getResponse();
	            originalResponse.setStatusCode(HttpStatus.OK);
	            originalResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
	            byte[] response = ("{\"code\": \""+ StatusCodeConstants.TOKEN_ERROR +"\",\"msg\": \"invalid token.\"}")
	                    .getBytes(StandardCharsets.UTF_8);
	            DataBuffer buffer = originalResponse.bufferFactory().wrap(response);
	            return originalResponse.writeWith(Flux.just(buffer));
	        }
	        //将现在的request，添加当前身份
	        ServerHttpRequest mutableReq = exchange.getRequest().mutate().header("Authorization-UserName", userName).build();
	        ServerWebExchange mutableExchange = exchange.mutate().request(mutableReq).build();
	        return chain.filter(mutableExchange);
		}
		return chain.filter(exchange);
	}

    /**
     * JWT验证
     * @param token
     * @return userName
     */
    private String verifyJWT(String token){
        String userName = "";
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("MING")
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            userName = jwt.getClaim("userName").asString();
        } catch (JWTVerificationException e){
            e.printStackTrace();
            return "";
        }
        return userName;
    }

}
