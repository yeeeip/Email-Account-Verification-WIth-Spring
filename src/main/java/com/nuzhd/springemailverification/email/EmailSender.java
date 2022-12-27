package com.nuzhd.springemailverification.email;

public interface EmailSender {
    void send(String to, String email);
}
