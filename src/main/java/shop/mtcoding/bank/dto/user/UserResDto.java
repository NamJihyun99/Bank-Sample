package shop.mtcoding.bank.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import shop.mtcoding.bank.domain.user.User;

public class UserResDto {

    @ToString
    @Getter
    @Setter
    public static class JoinResDto {
        private Long id;
        private String username;
        private String fullName;

        public JoinResDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.fullName = user.getFullName();
        }
    }
}
