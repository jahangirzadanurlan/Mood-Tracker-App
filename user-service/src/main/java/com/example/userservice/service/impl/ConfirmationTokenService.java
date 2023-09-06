package com.example.userservice.service.impl;

import com.example.userservice.exception.ApplicationException;
import com.example.userservice.model.dto.response.ResponseDto;
import com.example.userservice.model.entity.ConfirmationToken;
import com.example.userservice.model.enums.Exceptions;
import com.example.userservice.repository.ConfirmationTokenRepository;
import com.example.userservice.service.IConfirmationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenService implements IConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public ResponseDto save(ConfirmationToken confirmationToken) {
        ConfirmationToken save = confirmationTokenRepository.save(confirmationToken);
        if (save != null){
            return new ResponseDto("Save is successfull");
        } else {
            throw new ApplicationException(Exceptions.TOKEN_IS_INVALID_EXCEPTION);
        }
    }

    @Override
    public ConfirmationToken getTokenByUUID(String uuid) {
        return confirmationTokenRepository.findConfirmationTokenByToken(uuid)
                .orElseThrow(() -> new ApplicationException(Exceptions.TOKEN_NOT_FOUND_EXCEPTION));
    }


}
