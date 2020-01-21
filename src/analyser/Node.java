package analyser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// �﷨���Ľڵ�
public class Node {
	
	private String name;		// ����
	private Node father;		// ���ڵ�
	private List<Node> sons;	// �ӽڵ�
	private Map<String, String> attributeMap;	// ���Ա�
	
	public Node(String name, Node father) {
		super();
		this.name = name;
		this.father = father;
		sons = new ArrayList<>();
		attributeMap = new HashMap<String, String>();
	}

	public void setSon (Node son) {
		sons.add(son);
	}
	
	public Node getSon(int i) {
		return sons.get(i);
	}
	
	public void removeSon(Node son) {
		sons.remove(son);
	}
	
	public Node getFather() {
		return father;
	}

	public List<Node> getSons() {
		return sons;
	}
	
	public String getName() {
		return name;
	}

	public void setAttribute (String key, String value) {
		attributeMap.put(key, value);
	}
	
	public String getAttribute (String key) {
		return attributeMap.get(key);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name + ":");
		for (String key : attributeMap.keySet()) {
			sb.append(key + "=" + attributeMap.get(key) + " ");
		}
		
		return sb.toString();
	}
}
