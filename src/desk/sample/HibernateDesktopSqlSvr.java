package desk.sample;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class HibernateDesktopSqlSvr implements Runnable, ActionListener, ListSelectionListener {

	private Frame1 window;
	private HibernateDao<ShohinMap> hdao;
	public static void main(String[] args) {
		Thread thread = new Thread(new HibernateDesktopSqlSvr());
		thread.setUncaughtExceptionHandler(new OriginalUncaughtException());
		thread.start();
	}

	@Override
	public void run() {
		window = new Frame1();
		window.tableSetting();
		window.getButtonQuery().addActionListener(this);
		window.getButtonInsert().addActionListener(this);
		window.getButtonUpdate().addActionListener(this);
		window.getButtonDelete().addActionListener(this);
		window.getTable1().getSelectionModel().addListSelectionListener(this);
		hdao = new HibernateDao<ShohinMap>();
		
		//window.getFwindow().add(window.getFpanel());
		window.getFwindow().getContentPane().add(window.getFpanel(),BorderLayout.CENTER);
		window.getFwindow().setVisible(true);
		
		initialData();
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if (window.getButtonQuery().equals(event.getSource())) {
			Query();
		} else if (window.getButtonInsert().equals(event.getSource())) {
			Insert();
		} else if (window.getButtonUpdate().equals(event.getSource())) {
			Update();
		} else {
			Delete();
		}
	}
	
	@Override
	public void valueChanged(ListSelectionEvent listevent) {
		if(listevent.getValueIsAdjusting()) {
			return;
		}
		window.getTableRowSetTextField();
	}
	
	private void Query() {
		List<ShohinMap> list;
		String hqlQuery = "from desk.sample.ShohinMap";
		window.getDtableModel().setRowCount(0); //表示行クリア
        //hqlQuery += " as sd where sd.ShohinCode < :ShohinCode";
        list = hdao.GoQuery(hqlQuery);
        for (int i = 0; i < list.size(); i++) {
        	String ldate = ((ShohinMap)list.get(i)).getEditDate().toString();
        	ldate = ldate.substring(0,4) + "/" + ldate.substring(4,6) + "/" +ldate.substring(6,8);
        	String ltime = ((ShohinMap)list.get(i)).getEditTime().toString();
        	if (ltime.length() <= 5) {
        		ltime = "0" + ltime;
        	}
        	ltime = ltime.substring(0,2) + ":" + ltime.substring(2,4) + ":" + ltime.substring(4,6);
        	Object[] Objrs = {((ShohinMap)list.get(i)).getNumId(),
        			((ShohinMap)list.get(i)).getShohinCode(),
        			((ShohinMap)list.get(i)).getShohinName(),
        			ldate,
        			ltime,
        			((ShohinMap)list.get(i)).getNote()};
        	window.getDtableModel().addRow(Objrs);
        }
        window.tableSetting();
        window.textFieldClear();
        window.getLabelArea().append("全件表示しました。\n");
	}
	
	private void Insert() {
		if (window.getTextShohinNum().getText().matches("^[0-9]{1,4}$") == false) {
			JOptionPane.showMessageDialog(window.getFwindow(), "商品番号は半角数値の0～9999でなければなりません。","メッセージ", 2);
			return;
		}
		
		HibernateDao<ShohinMap> hibdao = new HibernateDao<ShohinMap>();
		ShohinMap data = new ShohinMap();
		data = setShohinMap(data);
		hibdao.insert(data);
		window.getLabelArea().append("商品を追加しました。\n");
	}
	
	private void Update() {
		int cnt = window.getDtableModel().getRowCount();
		if (cnt <= 0 || window.getLabelNumId().getText().equals("")) {
			JOptionPane.showMessageDialog(window.getFwindow(), "更新する商品行が選択できていません。","商品IDなし", 2);
			return;
		}
		if (window.getTextShohinNum().getText().matches("^[0-9]{1,4}$") == false) {
			JOptionPane.showMessageDialog(window.getFwindow(), "商品番号は半角数値の0～9999でなければなりません。","メッセージ", 2);
			return;
		}
		
		HibernateDao<ShohinMap> hidao = new HibernateDao<ShohinMap>();
		ShohinMap data = new ShohinMap();
		//data = hidao.search(Integer.valueOf(hidm.TextCode.getText()));
		data.setNumId(Integer.valueOf(window.getLabelNumId().getText()));
		data = setShohinMap(data);
		hidao.update(data);
		window.getLabelArea().append("商品を更新しました。\n");
	}
	
	private void Delete() {
		int cnt = window.getDtableModel().getRowCount();
		if (cnt <= 0 || window.getLabelNumId().getText().equals("")) {
			JOptionPane.showMessageDialog(window.getFwindow(), "更新する商品行が選択できていません。","商品IDなし", 2);
			return;
		}
		if (window.getTextShohinNum().getText().matches("^[0-9]{1,4}$") == false) {
			JOptionPane.showMessageDialog(window.getFwindow(), "商品番号は半角数値の0～9999でなければなりません。","メッセージ", 2);
			return;
		}
		
		HibernateDao<ShohinMap> hidao = new HibernateDao<ShohinMap>();
		ShohinMap data = new ShohinMap();
		data = hidao.search(Integer.valueOf(window.getLabelNumId().getText()));
		hidao.delete(data);
		window.getLabelArea().append("商品を削除しました。\n");
	}
	
	private ShohinMap setShohinMap(ShohinMap data) {
		data.setShohinCode(Short.valueOf(window.getTextShohinNum().getText()));
		data.setShohinName(window.getTextShohinName().getText());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String daytime = formatter.format(java.time.LocalDateTime.now());
		data.setEditDate(BigDecimal.valueOf(Integer.valueOf(daytime.substring(0,8))));
		data.setEditTime(BigDecimal.valueOf(Integer.valueOf(daytime.substring(8))));
		data.setNote(window.getTextNote().getText());
		return data;
	}
	
	private void initialData() {
		HibernateDao<ShohinMap> hibdao = new HibernateDao<ShohinMap>();
		if (hibdao.getCount("select count(*) from desk.sample.ShohinMap") > 0) {
			return;
		}
		ShohinMap syohindata = new ShohinMap();
		
		syohindata.setShohinCode((short)7500);
		syohindata.setShohinName("ｾﾄｳﾁﾚﾓﾝ");
		syohindata.setEditDate(BigDecimal.valueOf(20190417));
		syohindata.setEditTime(BigDecimal.valueOf(203145));
		syohindata.setNote("瀬戸内レモンです");
		hibdao.insert(syohindata);

		syohindata.setShohinCode((short)2840);
		syohindata.setShohinName("ﾘﾝｺﾞｼﾞｭｰｽ");
		syohindata.setEditDate(BigDecimal.valueOf(20050923));
		syohindata.setEditTime(BigDecimal.valueOf(102532));
		syohindata.setNote("果汁100%の炭酸飲料です");
		hibdao.insert(syohindata);

		syohindata.setShohinCode((short)1580);
		syohindata.setShohinName("ｶﾌｪｵﾚ");
		syohindata.setEditDate(BigDecimal.valueOf(20160716));
		syohindata.setEditTime(BigDecimal.valueOf(91103));
		syohindata.setNote("200ml増量です");
		hibdao.insert(syohindata);

		syohindata.setShohinCode((short)270);
		syohindata.setShohinName("ｳﾒｵﾆｷﾞﾘ");
		syohindata.setEditDate(BigDecimal.valueOf(20080825));
		syohindata.setEditTime(BigDecimal.valueOf(141520));
		syohindata.setNote("none");
		hibdao.insert(syohindata);
	}

}