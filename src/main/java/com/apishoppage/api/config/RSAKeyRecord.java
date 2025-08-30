package com.apishoppage.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/*To work with this you need to enable configuration by adding a notation on the main class
*
* then you can set the public and private keys inside this record class
* as the .pem files located in resources/certs
* */
@ConfigurationProperties(prefix = "jwt")
public record RSAKeyRecord(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
}
