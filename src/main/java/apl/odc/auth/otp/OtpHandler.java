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

    public GoogleAuthenticatorKey generateSeed() {
        return gAuth.createCredentials();
    }

    public String generateTotpAuthUrl(String issuer, String accountName, GoogleAuthenticatorKey seed) {
        return GoogleAuthenticatorQRGenerator.getOtpAuthURL(issuer, accountName, seed);
    }

    public int getTotp(String seed) {
        return gAuth.getTotpPassword(seed);
    }

    public boolean verifyTotp(String seed, int totp) {
        return gAuth.authorize(seed, totp);
    }

}
