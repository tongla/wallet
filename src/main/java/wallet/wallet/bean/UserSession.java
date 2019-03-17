package wallet.wallet.bean;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Scope(value = "session",proxyMode=ScopedProxyMode.TARGET_CLASS)
@Component
public class UserSession implements Serializable{
 
    private static final long serialVersionUID = 9120765714832970813L;
    //id
    private Integer phoneNum;
    //账号
    private String username;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
    //getter  setter 方法
	public Integer getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(Integer phoneNum) {
		this.phoneNum = phoneNum;
	}
}