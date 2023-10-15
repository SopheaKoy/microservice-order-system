package dev.sophea.authorizationservice.pkce;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Slf4j
@Component
public class PKCEUtil {

    public String generateCodeVerifier() throws UnsupportedEncodingException {

        SecureRandom secureRandom = new SecureRandom();

        byte [] codeVerifier = new byte[54];

        secureRandom.nextBytes(codeVerifier);

        return Base64.getUrlEncoder().withoutPadding().encodeToString(codeVerifier);

    }

    // code Challenge
    public String generatedCodeChallenge(String codeVerifier)throws UnsupportedEncodingException , NoSuchAlgorithmException {

        byte [] bytes = codeVerifier.getBytes(StandardCharsets.US_ASCII);

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

        messageDigest.update(bytes , 0 , bytes.length);

        byte [] digest = messageDigest.digest();


        return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);


    }
}
