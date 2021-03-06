package com.mpdamal.cloudental.external.reports;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class ReportsGeneratorToolUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel jpActions;
	private JButton jbSingle;
	private JButton jbDefault;
	private JButton jbDoAll;
	private JTable _dentistTable;

	public ReportsGeneratorToolUI() {
		setTitle("Daily Report Generator Tool");
		setSize(500,500);
		setLocation(150, 150);
		initGrcs();
		populateTable();
	}
	
	private void initGrcs() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout thisLayout = new GridBagLayout();
		thisLayout.rowWeights = new double[] {0.1, 0.0};
		thisLayout.rowHeights = new int[] {7, 7};
		thisLayout.columnWeights = new double[] {0.1, 0.1, 0.1};
		thisLayout.columnWidths = new int[] {20, 7, 7};
		getContentPane().setLayout(thisLayout);

		_dentistTable = new JTable();
		getContentPane().add(new JScrollPane(_dentistTable), new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));		

		jpActions = new JPanel();
		{
			jbDoAll = new JButton();
			jbDoAll.setText("Create All Reports");
			jbDoAll.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					for (int i = 0; i < _dentistTable.getRowCount(); i++) {
						int dentistid = Integer.parseInt(_dentistTable.getValueAt(i, 0).toString());
						DailyReportsGeneratorTool.printReport(dentistid);
					}
				}
			});
			jpActions.add(jbDoAll);
			
			jbSingle = new JButton();
			jbSingle.setText("Create For this Dentist");
			jbSingle.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int dentistid = Integer.parseInt(_dentistTable.getValueAt(_dentistTable.getSelectedRow(), 0).toString());
					DailyReportsGeneratorTool.printReport(dentistid);
				}
			});
			jpActions.add(jbSingle);
			
			jbDefault = new JButton();
			jbDefault.setText("Create Default Report");
			jbDefault.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					DailyReportsGeneratorTool.printDefaultReport();
				}
			});
			jpActions.add(jbDefault);
		}
		getContentPane().add(jpActions, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
	}
	
	private void populateTable() {
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		Vector<String> row = new Vector<String>();
		
		try {
			ResultSet rs = DailyReportsGeneratorTool.getDentistsInfo();
			while ( rs.next() )	{
				if (rs.getBoolean(5)) {
	 				row = new Vector<String>(4);
					row.add(rs.getString(1));
					row.add(rs.getString(2));
					row.add(rs.getString(3));
					row.add(rs.getString(4));
					data.add(row);
				}
				
			}
			rs.getStatement().close();
			rs.close();
		} catch (SQLException se) {
			System.err.println("Threw a SQLException creating the list of Dentist.");
			System.err.println(se.getMessage());
		}		
		row = new Vector<String>(4);
		row.add("ID");
		row.add("Username");
		row.add("Surname");
		row.add("Name");
		TableModel patientsTableModel = new DefaultTableModel(data, row);
		_dentistTable.setModel(patientsTableModel);
	}
}
