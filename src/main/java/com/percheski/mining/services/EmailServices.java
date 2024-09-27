package com.percheski.mining.services;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface EmailServices {
    void sendSimpleMessage(String to, String subject, String text, String senderName) throws MessagingException, UnsupportedEncodingException;
}

