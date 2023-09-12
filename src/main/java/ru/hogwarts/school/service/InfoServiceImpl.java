package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class InfoServiceImpl implements InfoService {
    @Value("${server.port}")
    private String serverPort;
    @Override
    public String getPort() {
        return "Server port is:"+serverPort;
    }
}
