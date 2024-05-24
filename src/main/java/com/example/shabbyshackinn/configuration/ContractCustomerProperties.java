package com.example.shabbyshackinn.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContractCustomerProperties {
    private String url;
}
