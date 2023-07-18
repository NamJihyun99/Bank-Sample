package shop.mtcoding.bank.temp;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

// java.util.regex.Pattern 테스트
public class RegexTest {

    @Test
    public void 한글만_된다_test() throws Exception {
        String value = "ㄲ이건되나";
        boolean result = Pattern.matches("[ㄱ-ㅎ가-힣]+", value);
        System.out.println("#### 테스트: " + result);
    }

    @Test
    public void 한글은_안된다_test() throws Exception {
        String value = "ㅇㅇ";
        boolean result = Pattern.matches("^[^ㄱ-ㅎ가-힣]*$", value);
        System.out.println("#### 테스트: " + result);
    }

    @Test
    public void 영어는_안된다_test() throws Exception {
        String value = "Jennie";
        boolean result = Pattern.matches("^[^a-zA-Z]*$", value);
        System.out.println("#### 테스트: " + result);
    }

    @Test
    public void 영어와_숫자만_된다_test() throws Exception {
        String value = "pw1234";
        boolean result = Pattern.matches("[a-zA-Z\\d]+", value);
        System.out.println("#### 테스트: " + result);
    }

    @Test
    public void 영어만되고_길이는_최소2_최대4이다_test() throws Exception {
        String value = "pass";
        boolean result = Pattern.matches("[a-zA-Z]{2,4}+", value);
        System.out.println("#### 테스트: " + result);
    }

    @Test
    public void username_test() throws Exception {
        String username = "ssar";
        boolean result = Pattern.matches("[a-zA-Z\\d]{2,20}+", username);
        System.out.println("#### 테스트: " + result);

    }

    @Test
    public void fullName_test() throws Exception {
        String fullName = "보리쌀";
        boolean result = Pattern.matches("[가-힣a-zA-Z]{1,20}+", fullName);
        System.out.println("#### 테스트: " + result);

    }

    @Test
    public void email_test() throws Exception {
        String email = "ssar@nate.com";
        boolean result = Pattern.matches("[a-zA-Z\\d]{2,6}+@[a-zA-Z\\d]{2,6}+\\.[a-zA-Z]{3,5}+", email);
        System.out.println("#### 테스트: " + result);

    }

}
