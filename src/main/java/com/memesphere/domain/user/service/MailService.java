package com.memesphere.domain.user.service;

import com.memesphere.domain.user.dto.response.EmailResponse;

public interface MailService {
    EmailResponse createMail(String tmpPassword, String memberEmail);
    void sendMail(EmailResponse email);
}
