package com.memesphere.domain.user.service;

import com.memesphere.domain.user.dto.response.EmailResponse;

public interface MailService {
    public EmailResponse createMail(String memberEmail);
    public void sendMail(EmailResponse email);
}
