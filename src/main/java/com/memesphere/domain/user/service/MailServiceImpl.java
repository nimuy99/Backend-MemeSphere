package com.memesphere.domain.user.service;

import com.memesphere.domain.user.dto.response.EmailResponse;
import com.memesphere.domain.user.entity.User;
import com.memesphere.domain.user.repository.UserRepository;
import com.memesphere.global.apipayload.code.status.ErrorStatus;
import com.memesphere.global.apipayload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    private final UserRepository userRepository;

    private static final String title = "MemeSphere 비밀번호 안내 이메일입니다.";
    private static final String message = "안녕하세요. MemeSphere 비밀번호 안내 메일입니다. "
            +"\n" + "회원님의 비밀번호는 아래와 같습니다."+"\n";
    private static final String fromAddress = "memesphere01@gmail.com";

    /** 이메일 생성 **/
    @Override
    public EmailResponse createMail(String memberEmail) {
        User existingUser = userRepository.findByEmail(memberEmail).orElse(null);
        /*
        임시 비밀번호 발급 로직 추가 예정
         */
        String password = existingUser.getPassword();

        if (existingUser == null) {
            throw new GeneralException(ErrorStatus.USER_NOT_FOUND);
        }

        if (password == null) {
            throw new GeneralException(ErrorStatus.SOCIAL_LOGIN_NOT_ALLOWED);
        }

        EmailResponse emailResponse = EmailResponse.builder()
                .toAddress(memberEmail)
                .title(title)
                .message(message + password)
                .fromAddress(fromAddress)
                .build();

        return emailResponse;
    }

    /** 이메일 전송 **/
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
