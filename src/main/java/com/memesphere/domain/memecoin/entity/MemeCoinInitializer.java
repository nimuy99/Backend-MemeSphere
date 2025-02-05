package com.memesphere.domain.memecoin.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.memesphere.domain.memecoin.repository.MemeCoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MemeCoinInitializer implements CommandLineRunner {
    private final MemeCoinRepository memeCoinRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {
        if (memeCoinRepository.count() == 0) {
            // JAR 내부 classpath에서 JSON 파일 로드
            try (InputStream inputStream = getClass().getClassLoader()
                    .getResourceAsStream("memecoin-storage/memecoin.json")) {

                if (inputStream == null) {
                    throw new IOException("'memecoin-storage/memecoin.json' 파일을 찾을 수 없습니다.");
                }

                // JSON 파일을 Java 객체(List<MemeCoin>)로 변환
                List<MemeCoin> memeCoins = objectMapper.readValue(
                        inputStream,
                        new TypeReference<List<MemeCoin>>() {}
                );

                // DB에 저장
                memeCoinRepository.saveAll(memeCoins);

            } catch (IOException e) {
                throw new RuntimeException("JSON 파일 로드 중 오류 발생: " + e.getMessage(), e);
            }
        }
    }
}