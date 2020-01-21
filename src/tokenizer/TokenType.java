package tokenizer;

// ����
public enum TokenType {
	
	END,
	KEYWORD,
	TYPE,
	SYMBOL,
	INTEGER,
	FLOAT,
	CHAR,
	STRING,
	IDENTIFIER;
	
	public String print() {
		switch(this) {
		case END : return "��ֹ��";
		case KEYWORD : return "�ؼ���";
		case TYPE : return "������";
		case SYMBOL : return "����";
		case INTEGER : return "����";
		case FLOAT : return "������";
		case CHAR : return "�ַ�";
		case STRING : return "�ַ���";
		case IDENTIFIER : return "��ʶ��";
		default : return "";
		}
	}
}
