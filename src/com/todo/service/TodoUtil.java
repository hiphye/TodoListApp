package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[�׸� �߰�]" + "\n ������ �Է��ϼ��� >");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("������ �ߺ��ɼ� �����ϴ�.");
			return;
		}
		sc.nextLine(); // �տ� ���ڸ� �����ֱ� ���� �ѹ� �Է� 
		System.out.print("������ �Է��ϼ��� >");
		desc = sc.nextLine().trim(); // �ؽ�Ʈ������ ����� ������ �ް�, Ȥ�ø� ������ ����
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
		System.out.println("�߰��Ǿ����ϴ�.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("[�׸� ����]" + "\n ������ �Է��ϼ��� >");
		String title = sc.next();
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				System.out.println("�����Ǿ����ϴ�.");
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[�׸� ����]" + "\n ������ ������ �Է��ϼ��� > ");
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("���� �����Դϴ�!");
			return;
		}

		System.out.print("�� ������ �Է� �ϼ��� > ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("������ �ߺ��˴ϴ�!");
			return;
		}
		sc.nextLine(); // ���� ����ִ°�
		System.out.print("���ο� ������ �Է��ϼ��� > ");
		String new_description = sc.next().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("�����Ǿ����ϴ�.");
			}
		}

	}

	public static void listAll(TodoList l) {
		for (TodoItem item : l.getList()) {
			System.out.println("[ " + item.getTitle() +" ] : "+ item.getDesc() +" - "+ item.getCurrent_date());
		}
	}

	public static void loadList(TodoList l, String filename) {
		// BufferedReader, FileReader, StringTokenizer ���
				try {
					BufferedReader br = new BufferedReader(new FileReader(filename));
				
					String oneline;
					while ((oneline = br.readLine()) != null) {
						StringTokenizer st = new StringTokenizer(oneline, "##");
						//title + "##" + desc + "##" + current_date
						String title = st.nextToken();
						String desc = st.nextToken();
						String current_date = st.nextToken();  
						
						TodoItem i = new TodoItem(title, desc, current_date );
						l.addItem(i); //�̷����ϸ� �ҷ��ü� �ִ°ǰ�?! 
					}
					br.close();
					System.out.println("���� �ε� �Ϸ� !!! ");
				} catch (FileNotFoundException e) {
					//e.printStackTrace();
					System.out.println("������ �����ϴ�!!");
				} catch (IOException e) {
					e.printStackTrace();
				} catch (NoSuchElementException e) {
					
				}
			}
		
	public static void saveList(TodoList l, String filename) {
		// FileWriter����ؼ� ����Ʈ�� �������� �����ϴ°� 
				try {
				Writer w = new FileWriter("todoList.txt");
				for (TodoItem item : l.getList())  {
					w.write(item.toSaveString());
				}
				w.close();
				System.out.println("���� ����!!! ");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}

