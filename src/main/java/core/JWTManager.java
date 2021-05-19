package core;

public class JWTManager {

	private static ThreadLocal<JWTCipher> THREAD_LOCALS = new ThreadLocal<JWTCipher>() {
		@Override
		protected JWTCipher initialValue() {
			return new JWTCipher();
		}
	};
	
	private JWTManager() {
		
	}
	
	public static JWTCipher getJWTCipher() {
		return THREAD_LOCALS.get();
	}
	
	public static void remove() {
		THREAD_LOCALS.remove();
	}
}
