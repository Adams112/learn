package netty;

public enum IMP {
	SYSTEM("SYSTEM"),
	LOGIN("LOGIN"),
	LOGOUT("LOGOUT"),
	CHAT("CHAT"),
	FLOWER("FLOWER");
	
	private String name;
	
	IMP(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static boolean isIMP(String name) {
		return name.matches("^\\[(SYSTEM|LOGIN|LOGOUT|CHAT)\\]");
	}
	
	public String toString() {
		return name;
	}
}
