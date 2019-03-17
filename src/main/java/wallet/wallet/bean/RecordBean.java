package wallet.wallet.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RecordBean {
	
	
    private String idrecord;
    private String userName;
    private String transName;
    private String payCode;
    private String premFlag;
    private double prem;
    private String createDate;
	public String getIdrecord() {
		return idrecord;
	}
	public void setIdrecord(String idrecord) {
		this.idrecord = idrecord;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getCreateDate() {
		
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
	    String dateString = formatter.format(createDate);
		this.createDate = dateString;
	}
	public String getTransName() {
		return transName;
	}
	public void setTransName(String transName) {
		this.transName = transName;
	}
	public String getPremFlag() {
		return premFlag;
	}
	public void setPremFlag(String premFlag) {
		this.premFlag = premFlag;
	}
	public double getPrem() {
		return prem;
	}
	public void setPrem(double prem) {
		this.prem = prem;
	}


	public String getPayCode() {
		return payCode;
	}
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}
    
}
