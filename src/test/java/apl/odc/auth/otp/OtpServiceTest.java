package apl.odc.auth.otp;

import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OtpServiceTest {

    OtpHandler otpHandler = new OtpHandler();

    @Test
    @DisplayName("OTP 검증")
    void otpTest() {
        GoogleAuthenticatorKey seed = otpHandler.generateSeed();
        System.out.println(seed.getKey());

        String url = otpHandler.generateTotpAuthUrl("odc1", "udc1", seed);
        System.out.println(url);

        int totp = otpHandler.getTotp(seed.getKey());

        boolean result = otpHandler.verifyTotp(seed.getKey(), totp);
        Assertions.assertTrue(result);
    }

}