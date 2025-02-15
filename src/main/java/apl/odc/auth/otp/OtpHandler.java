package apl.odc.auth.otp;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OtpHandler {

    private final GoogleAuthenticator gAuth = new GoogleAuthenticator();

    public GoogleAuthenticatorKey generateSecretKey() {
        return gAuth.createCredentials();
    }

    public String generateTotpAuthUrl(String issuer, String accountName, GoogleAuthenticatorKey key) {
        return GoogleAuthenticatorQRGenerator.getOtpAuthURL(issuer, accountName, key);
    }

    public int getTotp(String secretKey) {
        return gAuth.getTotpPassword(secretKey);
    }

    public boolean verifyTotp(String secretKey, int totp) {
        return gAuth.authorize(secretKey, totp);
    }

}
