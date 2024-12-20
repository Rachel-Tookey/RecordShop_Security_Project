package com.example.group.project.security.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GetCookies {

    public static String GetCookie(String jwtToken) {
        return String.format("token=%s; Secure; HttpOnly; Path=/; SameSite=Secure", jwtToken);
    }

}
