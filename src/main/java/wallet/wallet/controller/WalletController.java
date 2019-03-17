package wallet.wallet.controller;

import java.text.DateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import wallet.wallet.bean.RecordBean;
import wallet.wallet.bean.UserBean;
import wallet.wallet.service.WalletService;

@Controller
public class WalletController {
	@Autowired
	private WalletService walletService;
	
	@RequestMapping("/")
	public String login(HttpServletRequest request,Model model) {
		return "index";
	}
	@RequestMapping("/userMain")
	public String userMain(HttpServletRequest request,Model model) {
		HttpSession session=request.getSession();
		String userName =(String) session.getAttribute("userName");
		String page="userMain";
		if(userName==null) {
			page="login";
			model.addAttribute("message","登入超时，请重新登入");
		}else {
			
		}
		return page;
		
	}
	/**
	 * 登入界面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/LoginServlet")
	public String LoginServlet(HttpServletRequest request,Model model,UserBean userBean) {
		HttpSession session=request.getSession();
		String page="login";
		//从数据库中查询用户名和密码记录
		if(userBean.getUserName()!=null&&userBean.getPassword()!=null) {
			UserBean user=walletService.queryUserByName(userBean.getUserName());
			if(user==null) {
				model.addAttribute("message","用户名或密码错误");
				return page;
			}
			if(!user.getPassword().equals(userBean.getPassword())) {
				model.addAttribute("message","用户名或密码错误");
				
			}else if(user.getRole().equals("user")){
				session.setMaxInactiveInterval(30 * 60);
				//插入一条登入时间记录
				walletService.lastLogin(userBean);
				
				session.setAttribute("money", user.getMoney());
				session.setAttribute("userName", userBean.getUserName());
				//将最后一次登入时间放入session
				user=walletService.queryUserByName(userBean.getUserName());
				
				session.setAttribute("lastLogin", user.getLastLogin());
				page= "userMain";
			}else if(user.getRole().equals("system")) {
				page="record_system";
				//将所有用户的交易记录查询，并返回给页面
				List<RecordBean> recordList=walletService.queryAllRecord();
				model.addAttribute("recordList",recordList);
			}
		}
		
		return page;
		
	}
	/**
	 * 注册界面
	 * @param request
	 * @param userBean
	 * @return
	 */
	@RequestMapping("/register")
	public String register(UserBean userBean) {
		int isExistPhone=walletService.isExistPhone(userBean.getPhoneNum());
		String page="jmp2";
		if(isExistPhone==0) {
			walletService.insertUserInfo(userBean);
			page="jmp";
		}
		return page;
	}
	/**
	 * 交易记录
	 * @param model
	 * @return
	 */
	@RequestMapping("/transactionRecord")
	public String transactionRecord(HttpServletRequest request,Model model) {
		HttpSession session=request.getSession();
		String userName =(String) session.getAttribute("userName");
		String page="record";
		if(userName==null) {
			page="login";
			model.addAttribute("message","登入超时，请重新登入");
		}else {
			System.out.println(userName);
			List<RecordBean> recordList=walletService.queryRecordByName(userName);
			
			model.addAttribute("recordList",recordList);
		}
		return page;
	}
	/**
	 * 账户设置
	 * @param model
	 * @return
	 */
	@RequestMapping("/accountSetting")
	public String accountSetting(HttpServletRequest request,Model model) {
		HttpSession session=request.getSession();
		String userName =(String) session.getAttribute("userName");
		String page="userinfo";
		if(userName==null) {
			page="login";
			model.addAttribute("message","登入超时，请重新登入");
		}else {
			UserBean userBean=walletService.queryUserByName(userName);			
			model.addAttribute("userBean",userBean);
		}
		return page;
	}
	/**
	 * 转账
	 * @param model
	 * @return
	 */
	@RequestMapping("/transfer")
	public String transfer(HttpServletRequest request,Model model) {
		HttpSession session=request.getSession();
		String userName =(String) session.getAttribute("userName");
		String page="transfer";
		if(userName==null) {
			page="login";
			model.addAttribute("message","登入超时，请重新登入");
		}else {
			List<RecordBean> recordList=walletService.queryRecordByName(userName);
			
			model.addAttribute("recordList",recordList);
		}
		return page;
		
	}
	
	@RequestMapping("/money")
	public String money(HttpServletRequest request,Model model) {
		HttpSession session=request.getSession();
		String userName =(String) session.getAttribute("userName");
		UserBean user=walletService.queryUserByName(userName);
		session.setAttribute("money", user.getMoney());
		return "money";
	}
	@RequestMapping("/card")
	public String card(Model model) {
		return "card";
	}
	/**
	 * 转账页面输入后跳转
	 * @param model
	 * @return
	 */
	@RequestMapping("/transServlet")
	public String transServlet(HttpServletRequest request,Model model,RecordBean record) {
		//转账前校验：1.支付密码正确 2.本账户有钱，且大于转账金额。
		HttpSession session=request.getSession();
		UserBean userBean=walletService.queryUserByName(record.getUserName());
		if(record.getPayCode().equals(userBean.getPayCode())) {
			
		
			if(userBean.getMoney()<record.getPrem()) {
				model.addAttribute("errorMessage","账户余额不足！");
			}else {
				//账户金额减少和插入转账记录表需要同时成功
				walletService.tranferAccount(record);
				model.addAttribute("errorMessage","转账成功！");
			}
		}else {
			model.addAttribute("errorMessage","支付密码错误！");
		}
		return "success";
	}
	
	@RequestMapping("/ajaxQueryByuserName")
	@ResponseBody
	public String ajaxQueryByuserName(String userName) {
		int count=walletService.queryByuserName(userName);
		
		return Integer.toString(count);
	}
	/**
	 * 修改用户信息
	 * @param model
	 * @param userBean
	 * @return
	 */
	@RequestMapping("/uptUserServlet")
	public String uptUserServlet(Model model,UserBean userBean) {
		
		walletService.updateUserInfo(userBean);
		
		model.addAttribute("errorMessage","修改成功！");
		return "success";
	}
	/**
	 * 给自己账户充值
	 * @param model
	 * @param userBean
	 * @return
	 */
	@RequestMapping("/chargeServlet")
	public String chargeServlet(Model model,RecordBean record) {
		UserBean userBean=walletService.queryUserByName(record.getUserName());
		if(record.getPayCode().equals(userBean.getPayCode())) {
			
				walletService.chargeOwnAccount(record);
				model.addAttribute("errorMessage","充值成功！");
		
		}else {
			model.addAttribute("errorMessage","支付密码错误！");
		}
		return "success";
	}
	/**
	 * 提现
	 * @param model
	 * @param record
	 * @return
	 */
	@RequestMapping("/depositServlet")
	public String depositServlet(Model model,RecordBean record) {
		UserBean userBean=walletService.queryUserByName(record.getUserName());
		if(record.getPayCode().equals(userBean.getPayCode())) {
			if(userBean.getMoney()>=record.getPrem()) {
				walletService.subOwnAccount(record);
				model.addAttribute("errorMessage","提现成功！");
			}else {
				model.addAttribute("errorMessage","账户余额不足！");
			}
			
		}else {
			model.addAttribute("errorMessage","支付密码错误！");
		}
		return "success";
	}
	@RequestMapping("/appServlet")
	public String appServlet(Model model,RecordBean record) {
		UserBean userBean=walletService.queryUserByName(record.getUserName());
		if(record.getPayCode().equals(userBean.getPayCode())) {
			if(userBean.getMoney()>=record.getPrem()) {
				walletService.subOwnAccount(record);
				model.addAttribute("errorMessage","充值成功！");
			}else {
				model.addAttribute("errorMessage","账户余额不足！");
			}
		}else {
			model.addAttribute("errorMessage","支付密码错误！");
		}
		return "success";
	}
	@RequestMapping("/charge")
	public String charge(HttpServletRequest request,Model model,RecordBean record) {
		//转账前校验：1.支付密码正确 2.本账户有钱，且大于转账金额。
		HttpSession session=request.getSession();
		UserBean userBean=walletService.queryUserByName(record.getUserName());
		if(record.getPayCode().equals(userBean.getPayCode())) {
			if(userBean.getMoney()<record.getPrem()) {
				model.addAttribute("errorMessage","账户余额不足！");
			}else {
				//账户金额减少和插入转账记录表需要同时成功
				walletService.changeAccount(record);
				//walletService.insertRecord(record);
				model.addAttribute("errorMessage","转账成功！");
				UserBean user=walletService.queryUserByName(record.getUserName());
				session.setAttribute("money", user.getMoney());
			}
		}else {
			model.addAttribute("errorMessage","支付密码错误！");
		}
		return "success";
	}
	@RequestMapping("/exit")
	public String exit(Model model) {
		return "index";
	}
}
