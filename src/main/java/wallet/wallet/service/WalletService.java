package wallet.wallet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wallet.wallet.bean.RecordBean;
import wallet.wallet.bean.UserBean;
import wallet.wallet.dao.WalletDao;

@Service("WalletService")
public class WalletService {
	@Autowired
	WalletDao walletDao;
	public int lastLogin(UserBean userBean) {
		return walletDao.lastLogin(userBean);
	}
	public int isExistPhone(String phoneNum) {
		// TODO Auto-generated method stub
		return walletDao.isExistPhone(phoneNum);
	}
	public void insertUserInfo(UserBean userBean) {
		
		walletDao.insertUserInfo(userBean);
	}
	public List<RecordBean> queryRecordByName(String userName) {
		
		return walletDao.queryRecordByName(userName);
	}
	public void changeAccount(RecordBean record) {
		walletDao.subOwnAccount(record);
		walletDao.addTransAccount(record);
	}
	public void insertRecord(RecordBean record) {
		walletDao.insertRecord(record);
		
	}
	public int queryByuserName(String userName) {
		// TODO Auto-generated method stub
		return walletDao.queryByuserName(userName);
	}
	public UserBean queryUserByName(String userName) {
		// TODO Auto-generated method stub
		return walletDao.queryUserByName(userName);
	}
	public void updateUserInfo(UserBean userBean) {
		walletDao.updateUserInfo(userBean);
	}
	public void chargeOwnAccount(RecordBean recordBean) {
		walletDao.addOwnAccount(recordBean);
	}
	public void subOwnAccount(RecordBean record) {
		walletDao.subOwnAccount(record);
		
	}
	public void tranferAccount(RecordBean record) {
		walletDao.tranferAccount(record);
		
	}
	public List<RecordBean> queryAllRecord() {
		
		return walletDao.queryAllRecord();
	}
}
