package shop.mtcoding.bank.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserRepository;
import shop.mtcoding.bank.dto.user.UserReqDto;
import shop.mtcoding.bank.dto.user.UserReqDto.JoinReqDto;
import shop.mtcoding.bank.dto.user.UserResDto;
import shop.mtcoding.bank.dto.user.UserResDto.JoinResDto;
import shop.mtcoding.bank.handler.exception.CustomApiException;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public JoinResDto join(JoinReqDto joinReqDto) {
        Optional<User> userOp = userRepository.findByUsername(joinReqDto.getUsername());
        if (userOp.isPresent()) {
            throw new CustomApiException("동일한 유저네임이 존재합니다.");
        }

        return new JoinResDto(userRepository.save(joinReqDto.toEntity(passwordEncoder)));
    }


}
