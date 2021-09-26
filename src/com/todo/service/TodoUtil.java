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
		
		String category,title, desc, due_date ;
		Scanner sc = new Scanner(System.in);
		System.out.print("[�׸� �߰�]" + "\nī�װ��� �Է��ϼ��� >");
		category = sc.next();
		System.out.print("������ �Է��ϼ��� >");
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("������ �ߺ��ɼ� �����ϴ�.");
			return;
		}
		sc.nextLine(); // �տ� ���ڸ� �����ֱ� ���� �ѹ� �Է� 
		System.out.print("������ �Է��ϼ��� >");
		desc = sc.nextLine().trim(); // �ؽ�Ʈ������ ����� ������ �ް�, Ȥ�ø� ������ ����
		System.out.print("�������ڸ� �Է��ϼ��� >");
		due_date = sc.next();
		
		TodoItem t = new TodoItem(category, title, desc, due_date);
		list.addItem(t); //add()
		System.out.println("�߰��Ǿ����ϴ�.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("[�׸� ����]" + "\n ������ �׸��� ��ȣ�� �Է��Ͻÿ� >");
		int num = sc.nextInt();
		
		
		
		for (TodoItem item : l.getList()) {
			if (num ==(l.indexOf(item)+1)) {
				System.out.println((l.indexOf(item)+1)+". "+"[ " + item.getCategory() +" ] : "+ item.getTitle()+" - "+ item.getDesc() +" - "+ item.getCurrent_date());
				System.out.print("�� �׸��� �����Ͻðڽ��ϱ�? >");
				String answer = sc.next();
				if(answer.equals("y")) {
				l.deleteItem(num-1); //remove
				System.out.println("�����Ǿ����ϴ�.");
				} 
				break;
			}
		}
	} //��ȣ�� �޾Ƽ� ���� �ϵ���

	public static void findItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Ű���带 �Է��ϼ��� >");
		String key = sc.next();
		int i =0;
		for (TodoItem item : l.getList()) {
			if (key.contains(item.getTitle())) {
				System.out.println((l.indexOf(item)+1)+". "+"[ " + item.getCategory() +" ] : "+ item.getTitle()+" - "+ item.getDesc() +" - "+ item.getCurrent_date());
				i++;
				} 
			if (key.contains(item.getDesc())) {
				System.out.println((l.indexOf(item)+1)+". "+"[ " + item.getCategory() +" ] : "+ item.getTitle()+" - "+ item.getDesc() +" - "+ item.getCurrent_date());
				i++;
				} 
			}
		System.out.println("��" +i + "���� �׸��� ã�ҽ��ϴ�.");
		}
	

	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[�׸� ����]" + "\n ������ �׸��� ��ȣ�� �Է��ϼ��� > ");
		int num = sc.nextInt();
		for (TodoItem item : l.getList()) {
			if (num ==(l.indexOf(item)+1)) {
				System.out.println((l.indexOf(item)+1)+". "+"[ " + item.getCategory() +" ] : "+ item.getTitle()+" - "+ item.getDesc() +" - "+ item.getCurrent_date());
				System.out.print("�� ī�װ��� �Է� �ϼ��� > ");
				String new_category = sc.next().trim();
				System.out.print("�� ������ �Է� �ϼ��� > ");
				String new_title = sc.next().trim();
				if (l.isDuplicate(new_title)) {
					System.out.println("������ �ߺ��˴ϴ�!");
					return;
				}
				System.out.print("���ο� ������ �Է��ϼ��� > ");
				String new_description = sc.next().trim();
				System.out.print("���ο� �������ڸ� �Է��ϼ��� > ");
				String new_duedate = sc.next().trim();
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_category , new_title, new_description, new_duedate);
				l.addItem(t);
				System.out.println("�����Ǿ����ϴ�.");
				break;
			}
		}
		}
		// ��ȣ�� �޾Ƽ� �����ϵ��� 

	public static void listAll(TodoList l) {
		int i = l.countAll();
		System.out.println("[��ü ���, �� " + i + "��]");
		for (TodoItem item : l.getList()) {
			System.out.println((l.indexOf(item)+1)+". "+"[ " + item.getCategory() +" ] : "+ item.getTitle()+" - "+ item.getDesc() +" - "+ item.getdue_date()+" - "+item.getCurrent_date());
		} //index�� 0 ���� �����ϴϱ� +1 ���ذ��� . ���
	}

	public static void loadList(TodoList l, String filename) {
		// BufferedReader, FileReader, StringTokenizer ���
				try {
					BufferedReader br = new BufferedReader(new FileReader(filename));
				
					String oneline;
					while ((oneline = br.readLine()) != null) {
						StringTokenizer st = new StringTokenizer(oneline, "##");
						//title + "##" + desc + "##" + current_date
						String category = st.nextToken();
						String title = st.nextToken();
						String desc = st.nextToken();
						String due_date =st.nextToken();
						String current_date = st.nextToken();  
						
						TodoItem i = new TodoItem(category, title, desc, due_date, current_date );
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
				Writer w = new FileWriter(filename);
				for (TodoItem item : l.getList())  {
					w.write(item.toSaveString());
				}
				w.close();
				System.out.println("���� ����!!! ");
			}  catch (IOException e) {
				e.printStackTrace();
			}
	}

}

