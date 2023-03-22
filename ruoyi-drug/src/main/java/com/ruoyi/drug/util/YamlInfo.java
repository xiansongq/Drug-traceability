package com.ruoyi.drug.util;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Configuration
@PropertySource(value = "classpath:application-dev.yml", factory = YamlConfigFactory.class)
@ConfigurationProperties(prefix = "fabric")
@Data
@Component
public class YamlInfo {

    String walletDirectory;

    String certificatePath;

    String username;

    String networkConfigPath;

    String privateKeyPath;

    String mspid;

    String channelName;

    String contractName;
    String cercontract;
    String tlsCertPath;
}
