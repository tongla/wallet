package wallet.wallet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import wallet.wallet.bean.RecordBean;
import wallet.wallet.bean.UserBean;

@Mapper
public  interface WalletDao {
	
	int isExistPhone(String phoneNum);
	int insertUserInfo(UserBean userBean);
	int lastLogin(UserBean userBean);
	List<RecordBean> queryRecordByName(String userName);
	void subOwnAccount(RecordBean record);
	void addTransAccount(RecordBean record); 
	void insertRecord(RecordBean record);
	int queryByuserName(String userName);
	void changeAccount(RecordBean record);
	UserBean queryUserByName(String userName);
	void updateUserInfo(UserBean userBean);
	void addOwnAccount(RecordBean recordBean);
	void tranferAccount(RecordBean recordBean);
	List<RecordBean> queryAllRecord();
	

}
