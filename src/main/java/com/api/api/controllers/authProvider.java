//package com.api.api.controllers;
//
//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.stereotype.Component;
//
//import javax.naming.AuthenticationException;
//import java.util.ArrayList;
//
//@Component
//public class authProvider implements AuthenticationProvider {
//
//    @Override
//    public Authentication authenticate(Authentication authentication)
//            throws AuthenticationException {
//
//        String name = authentication.getName();
//        String password = authentication.getCredentials().toString();
//
//        if (shouldAuthenticateAgainstThirdPartySystem()) {
//
//            // use the credentials
//            // and authenticate against the third-party system
//            return new UsernamePasswordAuthenticationToken(
//                    name, password, new ArrayList<>());
//        } else {
//            return null;
//        }
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return authentication.equals(UsernamePasswordAuthenticationToken.class);
//    }
//}