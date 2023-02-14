package com.practice.bom.config;

import com.practice.bom.client.PracticeClient;
import com.practice.bom.server.PracticeServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author ljf
 * @description
 * @date 2023/2/13 5:33 PM
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class NettyInit implements CommandLineRunner {

    private final PracticeServer practiceServer;

    private final PracticeClient practiceClient;

    @Override
    public void run(String... args) throws Exception {
        try {
            practiceServer.start();
            practiceClient.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
