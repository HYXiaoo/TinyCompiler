package analyser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// ������Ϣ
public class FuncInfo {

	public int position;		// ��ʼλ��
	public String returnType;	// ��������
	public List<String> vars;	// �����б�
	public List<String> arglist;// �����б�
	public Map<String, VarInfo> args;	// ������
	public Map<String, VarInfo> locals;// ���ر�����
	public Map<String, VarInfo> tmps;	// ��ʱ������
	
	public FuncInfo (String returnType) {
		this.returnType = returnType;
		vars = new ArrayList<String>();
		arglist = new ArrayList<String>();
		args = new HashMap<>();
		locals = new HashMap<>();
		tmps = new HashMap<>();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String var : vars)
			sb.append(var + ":" + getVarInfo(var) + " ");
		return "position=" + position + " vars=" + sb;
	}
	
	public VarInfo getVarInfo (String id) {
		if (args.containsKey(id))
			return args.get(id);
		if (locals.containsKey(id))
			return locals.get(id);
		if (tmps.containsKey(id))
			return tmps.get(id);
		return null;
	}
}
