package com.ruoyi.drug.util;

import com.ruoyi.drug.util.ChaincodeEventListener;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.client.CallOption;
import org.hyperledger.fabric.client.Contract;
import org.hyperledger.fabric.client.Gateway;
import org.hyperledger.fabric.client.Network;
import org.hyperledger.fabric.client.identity.Identities;
import org.hyperledger.fabric.client.identity.Signers;
import org.hyperledger.fabric.client.identity.X509Identity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;


@Slf4j
@Configuration
@AllArgsConstructor
public class FabricGateway {

    final YamlInfo yamlInfo;

    /**
     * 连接网关
     */
    @Bean
    public Gateway connectGateway() throws IOException, InvalidKeyException, CertificateException {
        Reader  certificateReader = Files.newBufferedReader(Paths.get("D:\\xiansong\\project\\Drug_traceability\\RuoYi-Vue\\ruoyi-drug\\src\\main\\resources\\crypto-config\\peerOrganizations\\org1.example.com\\users\\User1@org1.example.com\\msp\\signcerts\\User1@org1.example.com-cert.pem"), StandardCharsets.UTF_8);

        X509Certificate certificate = Identities.readX509Certificate(certificateReader);

        Reader  privateKeyReader = Files.newBufferedReader(Paths.get("D:\\xiansong\\project\\Drug_traceability\\RuoYi-Vue\\ruoyi-drug\\src\\main\\resources\\crypto-config\\peerOrganizations\\org1.example.com\\users\\User1@org1.example.com\\msp\\keystore\\priv_sk"), StandardCharsets.UTF_8);

        PrivateKey privateKey = Identities.readPrivateKey(privateKeyReader);
//
//        ManagedChannel grpcChannel = ManagedChannelBuilder.forAddress("192.168.40.129", 7051)
//                .usePlaintext()
//                .build();
//
//        Gateway.Builder builder = Gateway.newInstance()
//                .identity( new X509Identity(yamlInfo.getMspid(), certificate))
//                .signer( Signers.newPrivateKeySigner(privateKey))
//                .connection(grpcChannel);
//        return builder.connect();
        Gateway gateway = Gateway.newInstance()
                .identity(new X509Identity(yamlInfo.getMspid() , certificate))
                .signer(Signers.newPrivateKeySigner(privateKey))
                .connection(newGrpcConnection())
                .evaluateOptions(CallOption.deadlineAfter(5, TimeUnit.SECONDS))
                .endorseOptions(CallOption.deadlineAfter(15, TimeUnit.SECONDS))
                .submitOptions(CallOption.deadlineAfter(5, TimeUnit.SECONDS))
                .commitStatusOptions(CallOption.deadlineAfter(1, TimeUnit.MINUTES))
                .connect();

        log.info("=========================================== connected fabric gateway {} " , gateway);

        return gateway;
    }


    private ManagedChannel newGrpcConnection() throws IOException, CertificateException {
        Reader tlsCertReader = Files.newBufferedReader(Paths.get("D:\\xiansong\\project\\Drug_traceability\\RuoYi-Vue\\ruoyi-drug\\src\\main\\resources\\crypto-config\\peerOrganizations\\org1.example.com\\peers\\peer0.org1.example.com\\tls\\ca.crt"));
        X509Certificate tlsCert = Identities.readX509Certificate(tlsCertReader);

        return NettyChannelBuilder.forTarget("192.168.40.129:7051")
                .sslContext(GrpcSslContexts.forClient().trustManager(tlsCert).build())
                .overrideAuthority("peer0.org1.example.com")
                .build();
    }

    @Bean
    public Network network(Gateway gateway) {
        return gateway.getNetwork(yamlInfo.getChannelName());
    }

    @Bean
    public Contract catContract(Network network) {
        return network.getContract(yamlInfo.getContractName());
    }

    @Bean
    public ChaincodeEventListener chaincodeEventListener(Network network) {
        return new ChaincodeEventListener(network);
    }

}
