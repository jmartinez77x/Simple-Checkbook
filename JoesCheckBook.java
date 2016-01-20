/*
Joe Martinez
Simple Checkbook Program
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.io.Serializable;
import java.util.*;

class transaction implements Serializable{ 
	public String date;
	public String transType;
	public int checkNum; 
	public String descrip;
	public double debitAmount; 
	public double creditAmount;
	public double balance; 
}

class JoesCheckBook implements ActionListener{
	public static JFrame frame; 
	public static Container pane;
	public static CardLayout cl = new CardLayout();
	public static ActionListener AL = new JoesCheckBook();
	public static JRadioButton byType, byDate; 
	public static JComboBox<String> newTransTypeList;
	public static String curAcctBal, curAcctName, initialAcctBal;
	public static ArrayList<transaction> account = new ArrayList<transaction>();
	public static String[][] viewDelTableData = new String[0][0];
	public static String[][] searchTransTableData = new String[0][0];
	public static String[] tableNames = {"Date","Trans. Type","Check No.",
					"Trans. Description","Payment/Debit(-)","Deposit/Credit(+)", "Balance"};
	public static JTable viewDelTable;
	public static JScrollPane viewDelTmpScroll, viewDelScroll, searchTransTmpScroll, searchTransScroll;
	public static JPanel topMenu, newAcct, loadTrans, newTrans, searchTrans, sortTrans, viewDelTrans;
	public static JTextField topMenuAcctNameText, topMenuBalanceText, newAcctInitBalText, newAcctNameText, 
		loadTransAcctNameText, newTransDepositText, newTransWithdwlText, newTransDescrText, newTransCheckNumText,
		newTransDateText, searchTransStringText;
	public static JButton newAcct_b, loadTrans_b, newTrans_b, searchTrans_b, sortTrans_b, viewDelTrans_b, 
		backupTrans_b, exit_b, loadTransCancel_b, newAcctCancel_b, newTransTopMenu_b, sortTransTopMenu_b, 
		sortTransSort_b, newAcctCreate_b, loadTransLoad_b, newTransSave_b, viewDelTransTopMenu_b, viewDelDeleteSel_b,
		searchTransSearch_b, searchTransTopMenu_b;
	
	public static void main(String[] args){
		frame = new JFrame("Joe's Check Book");
			frame.setSize(825,275);
			frame.setLocation(300,200);
			frame.setResizable(false);
			pane = frame.getContentPane();
			pane.setLayout(cl);
		
		//TOP MENU CARD
		topMenu = new JPanel();
			topMenu.setLayout(new BorderLayout());
			JLabel topMenuTitle = new JLabel("Use the buttons below to manage transactions", JLabel.CENTER);
				topMenuTitle.setFont(new Font("Dialog", 1, 25));
			JPanel topMenuMainPanel = new JPanel();
				JLabel topMenuAcctNameLabel = new JLabel("Account Name:", JLabel.LEFT);
					topMenuAcctNameLabel.setFont(new Font("Dialog", 1, 15));
				topMenuAcctNameText = new JTextField("",10);
				topMenuAcctNameText.setEditable(false);
				JLabel topMenuBalanceLabel = new JLabel("Balance:", JLabel.RIGHT);
					topMenuBalanceLabel.setFont(new Font("Dialog", 1, 15));
				topMenuBalanceText = new JTextField("", 10);
					topMenuBalanceText.setText("0.0");
					topMenuBalanceText.setHorizontalAlignment(JTextField.RIGHT);
					topMenuBalanceText.setEditable(false);
			JPanel topMenuButtons = new JPanel();
				topMenuButtons.setLayout(new GridLayout(2,4));
				newAcct_b = new JButton("Create a New Account"); newAcct_b.addActionListener(AL);
				loadTrans_b = new JButton("Load Trans from a file"); loadTrans_b.addActionListener(AL);
				newTrans_b = new JButton("Add new Transactions"); newTrans_b.addActionListener(AL);
				searchTrans_b = new JButton("Search Transactions"); searchTrans_b.addActionListener(AL);
				sortTrans_b = new JButton("Sort Transactions"); sortTrans_b.addActionListener(AL);
				viewDelTrans_b = new JButton("View/Delete Transactions"); viewDelTrans_b.addActionListener(AL);
				backupTrans_b = new JButton("Backup Transactions"); backupTrans_b.addActionListener(AL);
				exit_b = new JButton("Exit"); exit_b.addActionListener(AL); 
		
			topMenuMainPanel.add(topMenuAcctNameLabel);
			topMenuMainPanel.add(topMenuAcctNameText);
			topMenuMainPanel.add(topMenuBalanceLabel, BorderLayout.CENTER);
			topMenuMainPanel.add(topMenuBalanceText, BorderLayout.CENTER);
			topMenuButtons.add(newAcct_b); 
			topMenuButtons.add(loadTrans_b);
			topMenuButtons.add(newTrans_b);
			topMenuButtons.add(searchTrans_b);
			topMenuButtons.add(sortTrans_b);
			topMenuButtons.add(viewDelTrans_b);
			topMenuButtons.add(backupTrans_b);
			topMenuButtons.add(exit_b);
		topMenu.add(topMenuTitle, BorderLayout.NORTH);
		topMenu.add(topMenuMainPanel, BorderLayout.CENTER);
		topMenu.add(topMenuButtons, BorderLayout.SOUTH);
		//END TOP MENU CARD
		
		//CREATE NEW ACCOUNT CARD		
		newAcct = new JPanel();
			newAcct.setLayout(new BorderLayout());
			JPanel newAcctWindow = new JPanel();
				newAcctWindow.setLayout(new BorderLayout());
				JPanel newAcctTop = new JPanel();
					JLabel newAcctCreateAcctLabel = new JLabel("Create a New Account", JLabel.CENTER);
						newAcctCreateAcctLabel.setFont(new Font("Dialog", 1, 25));
				JPanel newAcctMiddle = new JPanel();
					newAcctMiddle.setLayout(new GridLayout(3,1));
					JPanel newAcctSpace = new JPanel();
					JPanel newAcctMiddleFirst = new JPanel();
						JLabel newAcctNameLabel = new JLabel("Account Name:");
							newAcctNameLabel.setFont(new Font("Dialog", 1, 15));
						newAcctNameText = new JTextField("", 10);
					JPanel newAcctMiddleSecond = new JPanel();
						JLabel newAcctInitBalLabel = new JLabel("Initial Balance:", JLabel.CENTER);
							newAcctInitBalLabel.setFont(new Font("Dialog", 1, 15));
						newAcctInitBalText = new JTextField("", 10);
				JPanel newAcctBottom = new JPanel();
					newAcctCreate_b = new JButton("Create"); 
						newAcctCreate_b.setPreferredSize(new Dimension(110,40));
						newAcctCreate_b.addActionListener(AL);
					newAcctCancel_b = new JButton("Cancel"); 
						newAcctCancel_b.setPreferredSize(new Dimension(110,40)); 
						newAcctCancel_b.addActionListener(AL);
			
					newAcctMiddleFirst.add(newAcctNameLabel);
					newAcctMiddleFirst.add(newAcctNameText);
					newAcctMiddleSecond.add(newAcctInitBalLabel);
					newAcctMiddleSecond.add(newAcctInitBalText);
				newAcctTop.add(newAcctCreateAcctLabel);
				newAcctMiddle.add(newAcctSpace);
				newAcctMiddle.add(newAcctMiddleFirst);
				newAcctMiddle.add(newAcctMiddleSecond);
				newAcctBottom.add(newAcctCreate_b);
				newAcctBottom.add(newAcctCancel_b); 
			newAcctWindow.add(newAcctTop, BorderLayout.NORTH);
			newAcctWindow.add(newAcctMiddle, BorderLayout.CENTER);
			newAcctWindow.add(newAcctBottom, BorderLayout.SOUTH);
		newAcct.add(newAcctWindow);
		//END CREATE NEW ACCOUNT CARD
		
		//LOAD TRANSACTION CARD
		loadTrans = new JPanel();
			loadTrans.setLayout(new BorderLayout());
			JPanel loadTransTop = new JPanel();
				JLabel loadTransLabel = new JLabel("Load Transaction From a File", JLabel.CENTER);
					loadTransLabel.setFont(new Font("Dialog", 1, 25));
			JPanel loadTransMiddle = new JPanel();
				loadTransMiddle.setLayout(new GridLayout(3,1));
				JPanel loadTransSpace = new JPanel();
				JPanel loadTransAcctNamePanel = new JPanel();
					JLabel loadTransAcctNameLabel = new JLabel("Account Name:");
						loadTransAcctNameLabel.setFont(new Font("Dialog", 1, 15));
					loadTransAcctNameText = new JTextField("", 10);
				JPanel loadTransBottom = new JPanel();
					loadTransLoad_b = new JButton("Load"); 
						loadTransLoad_b.setPreferredSize(new Dimension(110,40));  
						loadTransLoad_b.addActionListener(AL);
					loadTransCancel_b = new JButton("Cancel"); 
						loadTransCancel_b.setPreferredSize(new Dimension(110,40)); 
						loadTransCancel_b.addActionListener(AL); 
		
				loadTransBottom.add(loadTransLoad_b);
				loadTransBottom.add(loadTransCancel_b);
				loadTransAcctNamePanel.add(loadTransAcctNameLabel);
				loadTransAcctNamePanel.add(loadTransAcctNameText);
			loadTransTop.add(loadTransLabel);
			loadTransMiddle.add(loadTransSpace);
			loadTransMiddle.add(loadTransAcctNamePanel);
		loadTrans.add(loadTransTop, BorderLayout.NORTH);
		loadTrans.add(loadTransMiddle, BorderLayout.CENTER);
		loadTrans.add(loadTransBottom, BorderLayout.SOUTH);
		//END LOAD TRANSACTION CARD 
		
		//NEW TRANSACTION CARD
		newTrans = new JPanel();
			newTrans.setLayout(new BorderLayout());
			JPanel newTransMain = new JPanel();
				newTransMain.setLayout(new GridLayout(6,1));
				JLabel newTransDateLabel = new JLabel("Date:", JLabel.RIGHT);
				newTransDateText = new JTextField("", 7);
				JLabel newTransTypeLabel = new JLabel("Trans. Type:", JLabel.RIGHT);
					String[] transType = {"Deposit", "Automatic Deposit", "ATM Withdrawal", "Check", "Debit Card"};
					newTransTypeList = new JComboBox<String>(transType);
				JLabel newTransCheckNumLabel = new JLabel("Check No.:", JLabel.RIGHT);
				newTransCheckNumText = new JTextField("", 7);
				JLabel newTransDescrLabel = new JLabel("Trans. Description:", JLabel.RIGHT);
				newTransDescrText = new JTextField("", 7);
				JLabel newTransWithdwlLabel = new JLabel("Payment/Debit(-):", JLabel.RIGHT);
				newTransWithdwlText = new JTextField("", 7);
				JLabel newTransDepositLabel = new JLabel("Deposit/Credit(+):", JLabel.RIGHT);
				newTransDepositText = new JTextField("", 7);
				JPanel newTransBottom = new JPanel();
					newTransSave_b = new JButton("Save New Transaction"); 
						newTransSave_b.setPreferredSize(new Dimension(150,40));  
						newTransSave_b.addActionListener(AL);
					newTransTopMenu_b = new JButton("Top Menu"); 
						newTransTopMenu_b.setPreferredSize(new Dimension(150,40)); 
						newTransTopMenu_b.addActionListener(AL);
		
				newTransBottom.add(newTransSave_b);
				newTransBottom.add(newTransTopMenu_b); 
			newTransMain.add(newTransDateLabel);
			newTransMain.add(newTransDateText);
			newTransMain.add(newTransTypeLabel);
			newTransMain.add(newTransTypeList);
			newTransMain.add(newTransCheckNumLabel);
			newTransMain.add(newTransCheckNumText);
			newTransMain.add(newTransDescrLabel);
			newTransMain.add(newTransDescrText);
			newTransMain.add(newTransWithdwlLabel);
			newTransMain.add(newTransWithdwlText);
			newTransMain.add(newTransDepositLabel);
			newTransMain.add(newTransDepositText);
		newTrans.add(newTransMain);	
		newTrans.add(newTransBottom, BorderLayout.SOUTH);
		//END NEW TRANSACTION CARD
		
		//SEARCH TRANSACTION CARD
		searchTrans = new JPanel();
			searchTrans.setLayout(new BorderLayout());
			JLabel searchTransLabel = new JLabel("Search Transactions by Transaction Date/Type/Check No./Description", JLabel.CENTER);
				searchTransLabel.setFont(new Font("Dialog", 1, 15));
				searchTransScroll = new JScrollPane();
			JPanel searchTransBottom = new JPanel(); 
				searchTransBottom.setLayout(new BorderLayout());
				JPanel searchTransBottomNorth = new JPanel(); 
					JLabel searchTransStringLabel = new JLabel("Search String:", JLabel.CENTER);
						searchTransStringLabel.setFont(new Font("Dialog", 1, 10));
					searchTransStringText = new JTextField("", 10);
				JPanel searchTransBottomSouth = new JPanel();
					searchTransSearch_b = new JButton("Search");
						searchTransSearch_b.setPreferredSize(new Dimension(150,40));
						searchTransSearch_b.addActionListener(AL);
					searchTransTopMenu_b = new JButton("Top Menu");
						searchTransTopMenu_b.setPreferredSize(new Dimension(150,40));
						searchTransTopMenu_b.addActionListener(AL);
				
				searchTransBottomNorth.add(searchTransStringLabel, BorderLayout.CENTER);
				searchTransBottomNorth.add(searchTransStringText, BorderLayout.SOUTH);
				searchTransBottomSouth.add(searchTransSearch_b);
				searchTransBottomSouth.add(searchTransTopMenu_b);
			searchTransBottom.add(searchTransBottomNorth, BorderLayout.NORTH);
			searchTransBottom.add(searchTransBottomSouth, BorderLayout.SOUTH);
		searchTrans.add(searchTransLabel, BorderLayout.NORTH);
		searchTrans.add(searchTransScroll, BorderLayout.CENTER);
		searchTrans.add(searchTransBottom, BorderLayout.SOUTH);
		//END SEARCH TRANSACTION CARD
		
		//SORT TRANSACTION CARD
		sortTrans = new JPanel();
			sortTrans.setLayout(new BorderLayout());
			JLabel sortTransLabel = new JLabel("Sort Transactions", JLabel.CENTER);
				sortTransLabel.setFont(new Font("Dialog", 1, 25));
			JPanel sortTransRadioButtons = new JPanel();
				byType = new JRadioButton("By Type");
					byType.setFont(new Font("Dialog", 1, 20));
				byDate = new JRadioButton("By Date");
					byDate.setFont(new Font("Dialog", 1, 20));
			JPanel sortTransBottom = new JPanel();
			 	sortTransSort_b = new JButton("Sort");
					sortTransSort_b.setPreferredSize(new Dimension(125,40));
					sortTransSort_b.addActionListener(AL);
				sortTransTopMenu_b = new JButton("Top Menu"); 
					sortTransTopMenu_b.setPreferredSize(new Dimension(125,40));
					sortTransTopMenu_b.addActionListener(AL);
		
			sortTransRadioButtons.add(byType);
			sortTransRadioButtons.add(byDate);
			sortTransBottom.add(sortTransSort_b);
			sortTransBottom.add(sortTransTopMenu_b);
		sortTrans.add(sortTransRadioButtons, BorderLayout.CENTER);
		sortTrans.add(sortTransLabel, BorderLayout.NORTH);
		sortTrans.add(sortTransBottom, BorderLayout.SOUTH);
		//END SORT TRANSACTION CARD
		
		//VIEW/DELETE CARD
		viewDelTrans = new JPanel();
			viewDelTrans.setLayout(new BorderLayout());
			JLabel viewDelTransLabel = new JLabel("Transactions Currently in The CheckBook", JLabel.CENTER);
				viewDelTransLabel.setFont(new Font("Dialog", 1, 20));
			viewDelScroll = new JScrollPane();
			JPanel viewDelTransButtons = new JPanel();
				viewDelDeleteSel_b = new JButton("Delete Selected");
					viewDelDeleteSel_b.setPreferredSize(new Dimension(125,40));
					viewDelDeleteSel_b.addActionListener(AL);
			 	viewDelTransTopMenu_b = new JButton("Top Menu");
					viewDelTransTopMenu_b.setPreferredSize(new Dimension(125,40));
					viewDelTransTopMenu_b.addActionListener(AL);
			
			viewDelTransButtons.add(viewDelDeleteSel_b);
			viewDelTransButtons.add(viewDelTransTopMenu_b);
		viewDelTrans.add(viewDelTransLabel, BorderLayout.NORTH);
		viewDelTrans.add(viewDelScroll, BorderLayout.CENTER);
		viewDelTrans.add(viewDelTransButtons, BorderLayout.SOUTH);
		//END VIEW/DELETE CARD
		
		pane.add(topMenu, "topMenuCard");
		pane.add(newAcct, "newAcctCard");
		pane.add(loadTrans, "loadTransCard");
		pane.add(newTrans, "newTransCard");
		pane.add(searchTrans, "searchTransCard");
		pane.add(sortTrans, "sortTransCard");
		pane.add(viewDelTrans, "viewDeleteTransCard");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public static void showAcct(){ //adjusts current account name and balance 
		topMenuAcctNameText.setText(curAcctName);
		topMenuBalanceText.setText(curAcctBal);
	}
	
	public static void refreshDelTable(){
		viewDelTableData = new String[account.size()][7];
		for(int i = 0; i < account.size(); i++){
			viewDelTableData[i][0] = "" + ((account.get(i))).date;
			viewDelTableData[i][1] = "" + ((account.get(i))).transType;
			viewDelTableData[i][2] = "" + ((account.get(i))).checkNum;
			viewDelTableData[i][3] = "" + ((account.get(i))).descrip;
			viewDelTableData[i][4] = "" + ((account.get(i))).debitAmount;
			viewDelTableData[i][5] = "" + ((account.get(i))).creditAmount;
			viewDelTableData[i][6] = "" + ((account.get(i))).balance; 
		}
		viewDelTable = new JTable(viewDelTableData, tableNames);
		viewDelTmpScroll = new JScrollPane(viewDelTable);
		viewDelScroll.setViewport(viewDelTmpScroll.getViewport());
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == newAcct_b){ cl.show(pane, "newAcctCard");}
		if(e.getSource() == loadTrans_b){ cl.show(pane, "loadTransCard");}
		if(e.getSource() == newTrans_b){ cl.show(pane, "newTransCard");}
		if(e.getSource() == searchTrans_b){ cl.show(pane, "searchTransCard");
			searchTransTableData = new String[account.size()][7];                
			for(int i = 0; i < account.size(); i++){
				searchTransTableData[i][0] = "" + ((account.get(i))).date;
				searchTransTableData[i][1] = "" + ((account.get(i))).transType;
				searchTransTableData[i][2] = "" + ((account.get(i))).checkNum;
				searchTransTableData[i][3] = "" + ((account.get(i))).descrip;
				searchTransTableData[i][4] = "" + ((account.get(i))).debitAmount;
				searchTransTableData[i][5] = "" + ((account.get(i))).creditAmount;
				searchTransTableData[i][6] = "" + ((account.get(i))).balance;
			}
			JTable searchTransTable = new JTable(searchTransTableData, tableNames);
			searchTransTmpScroll = new JScrollPane(searchTransTable);
			searchTransScroll.setViewport(searchTransTmpScroll.getViewport());
		}
		if(e.getSource() == sortTrans_b){ cl.show(pane, "sortTransCard");}
		if(e.getSource() == viewDelTrans_b){ cl.show(pane, "viewDeleteTransCard"); 
			refreshDelTable();
		}
		if(e.getSource() == loadTransCancel_b){ cl.show(pane,"topMenuCard"); showAcct();}
		if(e.getSource() == newAcctCancel_b){ cl.show(pane,"topMenuCard"); showAcct();}
		if(e.getSource() == searchTransTopMenu_b){ cl.show(pane,"topMenuCard"); showAcct();}
		if(e.getSource() == viewDelTransTopMenu_b){ cl.show(pane,"topMenuCard"); showAcct();}
		if(e.getSource() == newTransTopMenu_b){ cl.show(pane,"topMenuCard"); showAcct();}
		if(e.getSource() == sortTransTopMenu_b){ cl.show(pane,"topMenuCard"); showAcct();} 
		if(e.getSource() == exit_b){ frame.dispose();}
		if(e.getSource() == viewDelDeleteSel_b){
			String a = "" + (viewDelTable.getValueAt(viewDelTable.getSelectedRow(), 4));
			String b = "" +(viewDelTable.getValueAt(viewDelTable.getSelectedRow(), 5));
			Double transDebit = Double.parseDouble(a);
			Double transCredit = Double.parseDouble(b);
			Double transTotal = -(transDebit + transCredit);
			Double curAcctTotal = Double.parseDouble(curAcctBal);
			Double newTotal = curAcctTotal + transTotal;
			curAcctBal = "" + newTotal;
			account.remove(viewDelTable.getSelectedRow());
			refreshDelTable();
		}
		if(e.getSource() == newTransSave_b){ 
			transaction t = new transaction();
			t.balance = Double.parseDouble(curAcctBal);
			t.transType = "" + newTransTypeList.getSelectedItem();
			if(!newTransDateText.getText().equals("")){
				t.date = newTransDateText.getText();
			}else{ t.date = "";}
			if(!newTransCheckNumText.getText().equals("")){
				t.checkNum = Integer.parseInt(newTransCheckNumText.getText());
			}else{ t.checkNum = 0;}
			if(!newTransDescrText.getText().equals("")){
				t.descrip = newTransDescrText.getText();
			}else{ t.descrip = "";}	
			if(!newTransWithdwlText.getText().equals("")){
				t.debitAmount = Double.parseDouble(newTransWithdwlText.getText());
				t.debitAmount = t.debitAmount-(t.debitAmount*2);
				t.balance = (Double.parseDouble(curAcctBal)) + t.debitAmount;
			}
			else if(!newTransDepositText.getText().equals("")){
				t.creditAmount = Double.parseDouble(newTransDepositText.getText());
				t.balance = (Double.parseDouble(curAcctBal)) + t.creditAmount;
			}
			else{
				t.debitAmount = 0;
				t.creditAmount = 0;
				t.balance = 0;
			}
			curAcctBal = "" + t.balance;
			account.add(t);
			
			newTransDateText.setText("");
			newTransCheckNumText.setText("");
			newTransDescrText.setText("");
			newTransWithdwlText.setText("");
			newTransDepositText.setText("");
		}
		if(e.getSource() == newAcctCreate_b){ 
			curAcctName = "";
			curAcctName = newAcctNameText.getText();
			initialAcctBal = "";
			initialAcctBal = newAcctInitBalText.getText();
			curAcctBal = initialAcctBal;
			
			newAcctNameText.setText("");
			newAcctInitBalText.setText("");
			showAcct();
			cl.show(pane,"topMenuCard");
		}
		if(e.getSource() == backupTrans_b){ 
			try {	
				FileOutputStream fos = new FileOutputStream (curAcctName, false);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				for(int j = 0; j < account.size(); j++){
					oos.writeObject(account.get(j));
				}
				oos.close();
			} 
			catch(FileNotFoundException x){ System.out.println(e.toString());}
			catch(IOException x){ x.printStackTrace();}
		}
		if(e.getSource() == loadTransLoad_b){ 
			transaction t = new transaction();
			try {
				FileInputStream fis = new FileInputStream (loadTransAcctNameText.getText());
				ObjectInputStream ois = new ObjectInputStream(fis);
				while (true) {
				 	t = (transaction) ois.readObject();
					account.add(t);
				}
			} 
			catch(EOFException y){} 
			catch(Exception y){ y.printStackTrace();}
			 
			curAcctName = loadTransAcctNameText.getText();
			curAcctBal = "" + t.balance;
			loadTransAcctNameText.setText("");
			showAcct();
			cl.show(pane,"topMenuCard");
		}
		//OPTIONAL 
		if(e.getSource() == searchTransSearch_b){ 
			//show selected transaction according to textfield 
		}
		//OPTIONAL
		if(e.getSource() == sortTransSort_b){ 
			//sort transactions based on radio buttons
		}
	}
}