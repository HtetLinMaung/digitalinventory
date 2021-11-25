package com.techhype.digitalinventory.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class Environment {
    @Value("${env.proxyhost}")
    private String proxyhost;
}
