package Editor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Trie
{
	private Node root;
	
	private int size;
	
	public Trie()
	{
		root = new Node();
		size = 0;
	}
	
	public void add(String str)
	{
		add(str, root, 0);
	}
	
	public void remove(String str)
	{
		remove(str, root, 0);
	}
	
	public boolean contains(String str)
	{
		return contains(str, root, 0);
	}
	
	public int size() {
		return size;
	}
	
	public List<String> startsWith(String str)
	{
		List<String> strs = new ArrayList<String>();
	
		startsWith(str, strs, root, 0);
	
		return strs;
	}
	
	private void startsWith(
			String str, List<String> strs,
			Node node, int depth)
	{
		if(depth < str.length())
		{
			char c = str.charAt(depth);
			
			Node child = node.children.get(c);
		
			if(child != null)
				startsWith(str, strs, child, depth + 1);
		}
		else
		{
			if(node.str != null)
				strs.add(node.str);
			
			for(Node child : node.children.values())
				startsWith(str, strs, child, depth + 1);
		}
	}

	private void add(String str, Node parent, int depth)
	{
		if(depth < str.length())
		{
			char c = str.charAt(depth);

			if(!parent.children.containsKey(c))
			{
				Node child = new Node();
						
				parent.children.put(c, child);
			}
		
			Node child = parent.children.get(c);
			
			add(str, child, depth + 1);
		}
		else
		{
			if(parent.str == null)
			{
				parent.str = str;
				size++;
			}
		}
	}

	private void remove(String str, Node node, int depth)
	{
		if(depth < str.length())
		{
			char c = str.charAt(depth);
		
			Node child = node.children.get(c);
			
			if(child != null)
			{
				remove(str, child, depth + 1);
				
				if(child.children.size() == 0)
					node.children.remove(child);
			}
		}
		else
		{
			if(node.str != null)
			{
				node.str = null;
				size--;
			}
		}
	}
	
	private boolean contains(String str, Node node, int depth)
	{
		if(depth < str.length())
		{
			char c = str.charAt(depth);
			
			Node child = node.children.get(c);
			
			if(child == null)
				return false;
			else
				return contains(str, child, depth + 1);
		}
		else
			return node.str != null;	
	}
	
	public String toString()
	{
		Stack<Node> stack = new Stack<Node>();
		
		String str = "{";
		
		stack.add(root);
		
		while(!stack.isEmpty())
		{	
			Node node = stack.pop();
			
			if(node.str != null)
				str += node.str + ",";

			stack.addAll(node.children.values());
		}
		
		str += "}";
		
		return str;
	}
	
	class Node
	{		
		Map<Character, Node> children 
			= new HashMap<Character, Node>();
		
		String str;
	}
}
