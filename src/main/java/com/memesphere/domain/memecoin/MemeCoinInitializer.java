package com.memesphere.domain.memecoin;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.memesphere.domain.memecoin.domain.MemeCoin;
import com.memesphere.domain.memecoin.repository.MemeCoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MemeCoinInitializer implements CommandLineRunner {
    private final MemeCoinRepository memeCoinRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {
        if (memeCoinRepository.count() == 0) {
            String filePath = "src/main/resources/memecoin-storage/memecoin.json";

            List<MemeCoin> memeCoins = objectMapper.readValue(
                    new File(filePath),
                    new TypeReference<List<MemeCoin>>() {}
            );

            memeCoinRepository.saveAll(memeCoins);
        }
    }
}