package interpreter;

// ���¼
public class Activity {

	String func;	// ������
	int start;		// ����ջ�еı�����ʼλ��
	int now;		// ��ǰִ����Ԫʽ���±�
	boolean flag;	// �ж��ӳ����Ƿ�ִ��
	
	public Activity(String func, int start, int now) {
		this.func = func;
		this.start = start;
		this.now = now;
	}
}
