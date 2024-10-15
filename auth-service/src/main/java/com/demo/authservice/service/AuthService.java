package com.demo.authservice.service;

import com.demo.authservice.model.dto.RegisterRequest;
import com.demo.authservice.model.entity.UserCredential;
import com.demo.authservice.producers.producer.KafkaProducer;
import com.demo.authservice.producers.properties.UserCreatedTopicProperties;
import com.demo.authservice.repository.UserCredentialRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserCredentialRepository userCredentialRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final KafkaProducer kafkaProducer;
    private final UserCreatedTopicProperties userCreatedTopicProperties;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredential> user= userCredentialRepository.findByUsername(username);
        return user.orElseThrow(EntityNotFoundException::new);
    }

    public String createUserCredential(RegisterRequest registerRequest) throws Exception {
        Optional<UserCredential> user= userCredentialRepository.findByUsername(registerRequest.username());
        if(user.isPresent()){
            throw new RuntimeException("user exists");
        }
        UserCredential userCredential= UserCredential.builder()
                .username(registerRequest.username())
                .email(registerRequest.email())
                .password(passwordEncoder.encode(registerRequest.password()))
                .accountNonExpired(true)
                .isEnabled(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();
        userCredentialRepository.save(userCredential);

        Map<String,Object> headers= new HashMap<>();
        headers.put(KafkaHeaders.TOPIC,userCreatedTopicProperties.getTopicName());
        headers.put(KafkaHeaders.KEY,userCredential.getUsername());

        kafkaProducer.sendMessage(new GenericMessage<>(userCredential.getEmail(),headers));
        return "register.success";

    }
    public Boolean validateToken(String token){
        String username= null;
        try {
            username= jwtService.extractValue(token,"username");
        }
        catch (ExpiredJwtException | SignatureException exception){
            return false;
        }

        if( username==null){
            return false;
        }
        UserDetails userCredential = loadUserByUsername(username);
        log.info("userCredential loaded " + userCredential);

        return jwtService.validateToken(token,userCredential);

    }
    public String generateToken(String username){

        return jwtService.generateToken(username);
    }
}
