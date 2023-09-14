package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class InfoServiceImpl implements InfoService {
    Logger logger = LoggerFactory.getLogger(InfoServiceImpl.class);
    @Value("${server.port}")
    private String serverPort;
    @Override
    public String getPort() {
        return "Server port is:"+serverPort;
    }

    @Override
    public int[] sum() {
        logger.info("Was invoked method SUM");

        long startSum = System.currentTimeMillis();
        int sum=0;
        for (int i = 1; i <1_000_000 ; i++) {
            sum+=i;
        }
        int time = (int) (System.currentTimeMillis() - startSum);
        return new int[] {sum, time};
    }
}
