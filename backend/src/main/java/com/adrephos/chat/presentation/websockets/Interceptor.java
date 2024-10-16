package com.adrephos.chat.presentation.websockets;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.adrephos.chat.presentation.config.JwtUtil;

public class Interceptor extends HttpSessionHandshakeInterceptor {
  private JwtUtil jwtUtil = new JwtUtil();

  private String getQueryParamValue(String query, String param) {
    for (String pair : query.split("&")) {
      String[] keyValue = pair.split("=");
      if (keyValue.length == 2 && keyValue[0].equals(param)) {
        return keyValue[1];
      }
    }
    return null;
  }

  @Override
  public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
      Map<String, Object> attributes) throws Exception {
    String query = request.getURI().getQuery();

    if (query != null && query.contains("token")) {
      String token = getQueryParamValue(query, "token");

      if (token != null && jwtUtil.isValid(token)) {
        attributes.put("token", token);
        return true;
      }
    }
    return false;
  }
}
