package com.nuzhd.springemailverification.service;

import com.nuzhd.springemailverification.model.ConfirmationToken;
import com.nuzhd.springemailverification.repo.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository tokenRepository;

    public void saveToken(ConfirmationToken token) {
        tokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public Optional<ConfirmationToken> findByUserId(Long userId) {
        return tokenRepository.findByUserId(userId);
    }
}
