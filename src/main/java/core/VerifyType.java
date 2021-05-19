package core;

public enum VerifyType {
	SUCCESS(0),
	EXPIRED(1),
	RELOAD(2),
	FAILED(3);
	
	private int type;
	
	private VerifyType() {
		
	}
	
	private VerifyType(int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
}
