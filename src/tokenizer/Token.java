package tokenizer;

// ��¼�ִʵĽ��
public class Token {

	private TokenType type;	// ����
	private String value;	// ֵ
	
	public Token(TokenType type, String value) {
		super();
		this.type = type;
		this.value = value;
	}

	public TokenType getType() {
		return type;
	}
	
	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.format("%s:%s", value, type);
	}
}