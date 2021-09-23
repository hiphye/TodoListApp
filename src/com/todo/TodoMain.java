package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		boolean isList = false;
		boolean quit = false;
		TodoUtil.loadList(l, "todoList.txt");
		Menu.displaymenu(); //�̰� ��������
		do {
			Menu.prompt(); // �� �޼ҵ忡 Ŀ�ǵ��Է¶� ǥ��
			isList = false; // �ٽ� �����Ѵٴ°� ������ true�� �Ǵ� �������ִ�.
			String choice = sc.next(); //scanner�� ���ڿ� �Է� �޴´�. �� ���� ���� ����� ����
			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;

			case "ls_name_asc":
				l.sortByName();
				System.out.println("��������� �����Ͽ����ϴ�.");
				isList = true; //���⼭ �÷��� ������ ��������.
				break;

			case "ls_name_desc":
				l.sortByName();
				l.reverseList();
				System.out.println("���񿪼����� �����Ͽ����ϴ�.");
				isList = true;
				break;
				
			case "ls_date":
				l.sortByDate();
				System.out.println("��¥������ �����Ͽ����ϴ�.");
				isList = true;
				break;

			case "exit":
				quit = true;
				break;
			case "help":
				Menu.displaymenu();
				break;

			default:
				System.out.println("��Ȯ�� ��ɾ �Է��ϼ���. (���� - help)");
				break;
			}
			
			if(isList) l.listAll(); // true�� ������ ������ Ʈ���϶� ����Ʈ���� �ϰԵȴ�. ���ĵ��� ��ü ����Ʈ�� �ѹ� ������! ������ ������ �ִ°� ��� �ѹ��� �ذ�.
		} while (!quit);
		TodoUtil.saveList(l,"todoList.txt");
	}
}
