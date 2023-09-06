package com.guiassignment;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class GuiApp {
	private JFrame frame;
	private JTable resultsTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiApp window = new GuiApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GuiApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1365, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lbl_databaseConnectionStatic = new JLabel("Database Connection:");
		lbl_databaseConnectionStatic.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbl_databaseConnectionStatic.setBounds(954, 13, 350, 13);
		frame.getContentPane().add(lbl_databaseConnectionStatic);
		
		JLabel lbl_DbConnectionDynamic = new JLabel("Disconnected");
		lbl_DbConnectionDynamic.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbl_DbConnectionDynamic.setBounds(1082, 13, 222, 13);
		frame.getContentPane().add(lbl_DbConnectionDynamic);
		
		JLabel lblNewLabel = new JLabel("Database Results");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(53, 301, 159, 42);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btn_ConnectToDB = new JButton("Connect");
		btn_ConnectToDB.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btn_ConnectToDB.setBounds(954, 37, 98, 30);
		frame.getContentPane().add(btn_ConnectToDB);
		btn_ConnectToDB.addActionListener(new ActionListener() {
	            @Override
				public void actionPerformed(ActionEvent e) {
	            	lbl_DbConnectionDynamic.setText("Connecting");
	            	connectToDB(lbl_DbConnectionDynamic);
					
				}
	        });
		
		connectToDB(lbl_DbConnectionDynamic);
		
		JButton btn_getRecords = new JButton("Get Records");
		btn_getRecords.setBounds(1082, 285, 200, 51);
		frame.getContentPane().add(btn_getRecords);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(63, 359, 1241, 264);
		frame.getContentPane().add(scrollPane);
		
		JLabel lblNewLabel_1 = new JLabel("Michael Smith 2023");
		lblNewLabel_1.setBounds(1243, 658, 98, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Database Connector");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblNewLabel_2.setBounds(10, 13, 296, 54);
		frame.getContentPane().add(lblNewLabel_2);
		
		
		btn_getRecords.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	getRecords(scrollPane);
				
			}
        });
		
	}
	
	private void getRecords(JScrollPane scrollPane) {

		resultsTable = new JTable();
		String sqlQuery = "SELECT * FROM Customers"; // Replace with your table name
		String connectionUrl = "jdbc:sqlserver://developer\\sqlexpress;databaseName=Northwind;integratedSecurity=true";

	    try (Connection conn = DriverManager.getConnection(connectionUrl);
	         Statement statement = conn.createStatement();
	         ResultSet resultSet = statement.executeQuery(sqlQuery)) {
	         ResultSetMetaData metaData = resultSet.getMetaData();

	        // Create a DefaultTableModel to hold the data
	        DefaultTableModel tableModel = new DefaultTableModel();

	        // Add column names to the table model
	        int columnCount = metaData.getColumnCount();
	        for (int i = 1; i <= columnCount; i++) {
	            tableModel.addColumn(metaData.getColumnName(i));
	        }

	        // Add rows to the table model
	        while (resultSet.next()) {
	            Object[] rowData = new Object[columnCount];
	            for (int i = 1; i <= columnCount; i++) {
	                rowData[i - 1] = resultSet.getObject(i);
	            }
	            tableModel.addRow(rowData);
	        }
	        resultsTable.setModel(tableModel);

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		scrollPane.setViewportView(resultsTable);
    }

	
	
	private void connectToDB(JLabel dbLabel) {
		
		String connectionUrl = "jdbc:sqlserver://developer\\sqlexpress;databaseName=Northwind;integratedSecurity=true";
	    
	    Connection conn;
		try {
			conn = DriverManager.getConnection(connectionUrl);
			if (conn != null) {
		        dbLabel.setText("Connected");
		    }
		} catch (SQLException e) {
			dbLabel.setText("Connection Failed...Try Again");
			e.printStackTrace();
		}
	}
}
