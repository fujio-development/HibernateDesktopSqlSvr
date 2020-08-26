package desk.sample;

import java.math.BigDecimal;

public class ShohinMap {
	//オブジェクトと列名を別にしたい場合はmap.hbm.xmlのcolumnの方で行う
	private Integer _NumId;
	private Short _ShohinCode;
	private String _ShohinName;
	private BigDecimal _EditDate;
	private BigDecimal _EditTime;
	private String _Note;

	public ShohinMap() {
		super();
	}

	public ShohinMap(Short Code, String Name, BigDecimal Date, BigDecimal Time, String Note) {
		super();
		this._ShohinCode = Code;
		this._ShohinName = Name;
		this._EditDate = Date;
		this._EditTime = Time;
		this._Note = Note;
	}

	//hbm.xmlのidであるNumIdはgetterとsetterが必ず必要。
	//hbm.xmlのidやpropertyのnameはgetterやsetterの名前からget、setを除いたものと一致させる事。
	public Integer getNumId() {
		return _NumId;
	}
	public void setNumId(Integer value) {
		this._NumId = value;
	}

	public Short getShohinCode() {
		return _ShohinCode;
	}
	public void setShohinCode(Short value) {
		this._ShohinCode = value;
	}

	public String getShohinName() {
		return _ShohinName;
	}
	public void setShohinName(String value) {
		this._ShohinName = value;
	}

	public BigDecimal getEditDate() {
		return _EditDate;
	}
	public void setEditDate(BigDecimal value) {
		this._EditDate = value;
	}

	public BigDecimal getEditTime() {
		return _EditTime;
	}
	public void setEditTime(BigDecimal value) {
		this._EditTime = value;
	}

	public String getNote() {
		return _Note;
	}
	public void setNote(String value) {
		this._Note = value;
	}
}