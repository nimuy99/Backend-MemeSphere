package com.memesphere.domain.user.service;

import com.memesphere.domain.user.converter.UserConverter;
import com.memesphere.domain.user.dto.response.EmailResponse;
import com.memesphere.domain.user.entity.User;
import com.memesphere.domain.user.repository.UserRepository;
import com.memesphere.global.apipayload.code.status.ErrorStatus;
import com.memesphere.global.apipayload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    private final UserRepository userRepository;

    private static final String title = "MemeSphere 임시 비밀번호 안내 이메일입니다.";
    private static final String message = "안녕하세요. MemeSphere 임시 비밀번호 안내 메일입니다. "
            +"\n" + "회원님의 임시 비밀번호는 아래와 같습니다. 로그인 후 반드시 비밀번호를 변경해주세요."+"\n";
    private static final String fromAddress = "memesphere01@gmail.com";

    @Override
    public EmailResponse createMail(String tmpPassword, String memberEmail) {
        User existingUser = userRepository.findByEmail(memberEmail).orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
        String password = existingUser.getPassword();

        if (password == null) {
            throw new GeneralException(ErrorStatus.SOCIAL_LOGIN_NOT_ALLOWED);
        }

        return UserConverter.toEmailResponse(tmpPassword, memberEmail, title, message, fromAddress);
    }

    @Override
    public void sendMail(EmailResponse email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email.getToAddress());
        mailMessage.setSubject(email.getTitle());
        mailMessage.setText(email.getMessage());
        mailMessage.setFrom(email.getFromAddress());
        mailMessage.setReplyTo(email.getFromAddress());

        mailSender.send(mailMessage);
    }
}
