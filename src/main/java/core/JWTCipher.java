package core;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTCipher {
	private static long RELOAD_GAP = 60_000 * 5;
	private Algorithm algoritmHS = Algorithm.HMAC256("jawsubak");
	private JWTVerifier verifier = JWT.require(algoritmHS)
			.withIssuer("jawsubakvlog")
			.acceptExpiresAt(30 * 60)
			.build();
	
	public String generateToken() {
		String token = JWT.create()
				.withIssuer("jawsubakvlog")
				.withExpiresAt(new Date())
				.withClaim("isSignin", "true")
				.sign(algoritmHS);
		return token;
	}
	
	public int verifyToken(String token) {
		try {
			DecodedJWT decoded = verifier.verify(token);
			if(decoded == null) {
				return VerifyType.FAILED.getType();
			}
			if(checkReload(decoded)) {
				return VerifyType.RELOAD.getType();
			}
			return VerifyType.SUCCESS.getType();
		}catch(TokenExpiredException e) {
			return VerifyType.EXPIRED.getType();
		}catch(JWTVerificationException e) {
			return VerifyType.FAILED.getType();
		}
	}
	
	public boolean checkReload(DecodedJWT decoded) {
		long remain = System.currentTimeMillis() - decoded.getExpiresAt().getTime();
		if(remain > RELOAD_GAP) {
			return false;
		}else {
			return true;
		}
	}
	
}
