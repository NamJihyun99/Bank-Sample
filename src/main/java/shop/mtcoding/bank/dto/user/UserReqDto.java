package shop.mtcoding.bank.dto.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserEnum;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserReqDto {
    @Getter
    @Setter
    public static class JoinReqDto {

        @Pattern(regexp = "[a-zA-Z\\d]{2,20}+", message = "영문/숫자 2~20자 이내로 작성해주세요.")
        @NotEmpty
        private String username;

        @Size(min = 4, max = 20)
        @NotEmpty
        private String password;

        @Pattern(regexp = "[a-zA-Z\\d]{2,10}+@[a-zA-Z\\d]{2,6}+\\.[a-zA-Z]{3,5}+", message = "이메일 형식에 맞게 작성해주세요.")
        @NotEmpty
        private String email;

        @Pattern(regexp = "[가-힣a-zA-Z]{1,20}+", message = "영문/한글 1~20자 이내로 작성해주세요.")
        @NotEmpty
        private String fullName;

        public User toEntity(BCryptPasswordEncoder passwordEncoder) {
            return User.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .email(email)
                    .fullName(fullName)
                    .role(UserEnum.CUSTOMER)
                    .build();
        }
    }
}
