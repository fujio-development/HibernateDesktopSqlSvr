package desk.sample;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class Frame1 {
	private JFrame Fwindow = new JFrame();
	private JPanel Fpanel = new JPanel();
	private JTable Table1;
	private JScrollPane Scroll1;
	private DefaultTableModel DtableModel;
	private String[] headers = {"商品ID", "商品番号", "商品名", "編集日付", "編集時刻", "備考"};
	private JTextArea LabelArea = new JTextArea();
	private JButton ButtonQuery = new JButton();
	private JButton ButtonInsert = new JButton();
	private JButton ButtonUpdate = new JButton();
	private JButton ButtonDelete = new JButton();
	private JLabel Label1 = new JLabel();
	private JLabel Label2 = new JLabel();
	private JLabel Label3 = new JLabel();
	private JLabel Label4 = new JLabel();
	private JLabel LabelNumId = new JLabel();
	private JTextField TextShohinNum = new JTextField();
	private JTextField TextShohinName = new JTextField();
	private JTextField TextNote = new JTextField();
	
	public Frame1() {
		createWindow();
	}
	
	private JComponent controlSetting(JComponent ctl, String txt, int x, int y, int w, int h) {
		ctl.setBounds(x,y,w,y);
		return ctl;
	}
	
	private void createWindow() {
		Fwindow.setTitle("Hibernate + SQLServer デスクトップアプリ");
		Fwindow.setLocation(500,200);
		Fwindow.setSize(800, 600);
		Fwindow.setUndecorated(false); // タイトルバー表示・非表示
		Fwindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		DtableModel = new DefaultTableModel(headers, 0);
		Table1 = new JTable(DtableModel);
		Scroll1 = new JScrollPane(Table1);
		Scroll1.setBounds(25,25,700,200);
		Fpanel.setPreferredSize(new Dimension(700, 100));
		Fpanel.add(Scroll1);
		LabelArea.setText("表示エリア\n");
		LabelArea.setBounds(25,235,350,100);
		LabelArea.setFocusable(false);
		Fpanel.add(LabelArea);

		ButtonQuery.setText("抽出");
		ButtonQuery.setBounds(50,470,150,50);
		//controlSetting(ButtonQuery,50,470,150,50);
		
		Fpanel.add(ButtonQuery);
		ButtonInsert.setText("追加");
		ButtonInsert.setBounds(230, 470, 150, 50);
		Fpanel.add(ButtonInsert);
		ButtonUpdate.setText("更新");
		ButtonUpdate.setBounds(410, 470, 150, 50);
		Fpanel.add(ButtonUpdate);
		ButtonDelete.setText("削除");
		ButtonDelete.setBounds(590, 470, 150, 50);
		Fpanel.add(ButtonDelete);

		Label1.setText("商品ID：");
		Label1.setBounds(400,250,100,25);
		Fpanel.add(Label1);
		Label2.setText("商品番号：");
		Label2.setBounds(400,300,100,25);
		Fpanel.add(Label2);
		Label3.setText("商品名：");
		Label3.setBounds(400,350,100,25);
		Fpanel.add(Label3);
		Label4.setText("備考：");
		Label4.setBounds(400,400,100,25);
		Fpanel.add(Label4);
		
		LabelNumId.setBounds(670,250,60,25);
		LabelNumId.setHorizontalAlignment(JLabel.RIGHT);
		Fpanel.add(LabelNumId);
		TextShohinNum.setBounds(600,300, 150,25);
		Fpanel.add(TextShohinNum);
		TextShohinName.setBounds(550,350,200,25);
		Fpanel.add(TextShohinName);
		TextNote.setBounds(450,400,300,25);
		Fpanel.add(TextNote);
		Fpanel.setLayout(null);
	}
	
	public void getTableRowSetTextField() {
		LabelNumId.setText(((Integer) Table1.getValueAt(Table1.getSelectedRow(), 0)).toString());
		TextShohinNum.setText(((Short) Table1.getValueAt(Table1.getSelectedRow(), 1)).toString());
		TextShohinName.setText(Table1.getValueAt(Table1.getSelectedRow(), 2).toString());
		TextNote.setText(Table1.getValueAt(Table1.getSelectedRow(), 5).toString());
	}
	
	public void textFieldClear() {
		LabelNumId.setText("");
		TextShohinNum.setText("");
		TextShohinName.setText("");
		TextNote.setText("");
	}
	
	public void tableSetting() {
		Table1.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		TableColumn col = Table1.getColumnModel().getColumn(0);
		col.setPreferredWidth(5);
		col = Table1.getColumnModel().getColumn(1);
		col.setPreferredWidth(5);
		col = Table1.getColumnModel().getColumn(2);
		col.setPreferredWidth(20);
		col = Table1.getColumnModel().getColumn(3);
		col.setPreferredWidth(5);
		col = Table1.getColumnModel().getColumn(4);
		col.setPreferredWidth(5);
		/**col = Table1.getColumnModel().getColumn(5);
		col.setPreferredWidth(35);**/
	}
	
	public JFrame getFwindow() {
		return Fwindow;
	}
	
	public JPanel getFpanel() {
		return Fpanel;
	}
	
	public JTable getTable1() {
		return Table1;
	}
	public DefaultTableModel getDtableModel() {
		return DtableModel;
	}
	public JTextArea getLabelArea() {
		return LabelArea;
	}
	public JLabel getLabelNumId() {
		return LabelNumId;
	}
	public JTextField getTextShohinNum() {
		return TextShohinNum;
	}
	public JTextField getTextShohinName() {
		return TextShohinName;
	}
	public JTextField getTextNote() {
		return TextNote;
	}
	public JButton getButtonQuery() {
		return ButtonQuery;
	}
	public JButton getButtonInsert() {
		return ButtonInsert;
	}
	public JButton getButtonUpdate() {
		return ButtonUpdate;
	}
	public JButton getButtonDelete() {
		return ButtonDelete;
	}
}