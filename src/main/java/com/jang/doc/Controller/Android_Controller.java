package com.jang.doc.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.jang.doc.mapper.LoginMapper;
import com.jang.doc.model.AddressVO;
import com.jang.doc.model.DeliveryVO;
import com.jang.doc.model.DetailVO;
import com.jang.doc.model.ListVO;
import com.jang.doc.model.LoginVO;
import com.jang.doc.service.AdminService;
import com.jang.doc.service.LoginService;

@Controller
public class Android_Controller {

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private AdminService adminService;

	@RequestMapping("/android3")
	@ResponseBody
	public Map<String, String> androidTestWithRequest(HttpServletRequest request) {

		System.out.println(request.getParameter("title"));
		System.out.println(request.getParameter("memo"));

		Map<String, String> result = new HashMap<String, String>();
		result.put("data1", "�޸𿡿�");
		result.put("data2", "�ι�° �޸��Դϴ�.");

		return result;
	}

	/*
	 * @RequestMapping("/Login")
	 * 
	 * @ResponseBody public Map<String, String> android_Login(HttpServletRequest
	 * request) {
	 * 
	 * String userID = request.getParameter("ID"); String userPWD =
	 * request.getParameter("PWD");
	 * 
	 * System.out.println(userID); System.out.println(userPWD);
	 * 
	 * LoginVO loginVO = loginService.JoinDao(userID);
	 * 
	 * 
	 * 
	 * Map<String, String> result = new HashMap<String, String>();
	 * 
	 * 
	 * if(loginVO == null) { result.put("data1", "fail");
	 * System.out.println("����"); } else if(loginVO.getPWD().equals(userPWD)) {
	 * result.put("data1", "success"); System.out.println("����"); } else {
	 * result.put("data1", "fail"); System.out.println("���̵����"); }
	 * 
	 * return result; }
	 */
	
	//로그인
	@RequestMapping("/Login")
	@ResponseBody
	public Map<String, String> android_Login(HttpServletRequest request) {

		String userID = request.getParameter("User_id");
		String userPWD = request.getParameter("User_pass");
		String token = request.getParameter("token");

		System.out.println(userID);
		System.out.println(userPWD);
		System.out.println(token);

		LoginVO loginVO = loginService.JoinDao(userID,userPWD);
		
		
		
		Map<String, String> result = new HashMap<String, String>();
		
		ObjectMapper oMapper = new ObjectMapper();
		
		
	
		
		if(loginVO == null) {
			result.put("mno", "fail");
			System.out.println("로그인실패");
		} else {
			result = oMapper.convertValue(loginVO, Map.class);
			loginService.TokenUpdate(token, userID);
			System.out.println("로그인성공");
		}
		
		return result;
	}
	
	
//	@RequestMapping(value ="/Reservation" ,produces = "application/json; charset=utf8")
//	@ResponseBody
//	public String android_Reservation(@ModelAttribute("AddressVO") AddressVO addressVO, HttpServletRequest request) {
//		
//		System.out.println(addressVO.toString());
//		
//		loginService.ReservationDao(addressVO);
//		
//		List<AddressVO> test_List = new ArrayList<AddressVO>();
//		AddressVO testVO = new AddressVO();
//		testVO.setSend_name("�̸�");
//		testVO.setSend_addr1("�ּ�1");
//		testVO.setSend_addr2("�ּ�2");
//		testVO.setSend_addr3("�ּ�3");
//		
//		
//		test_List.add(addressVO);
//		test_List.add(testVO);
//		
//		Gson gson1 = new Gson();
//		String gson1String = gson1.toJson(test_List);
//		System.out.println(gson1String);
//
//
//		ObjectMapper oMapper = new ObjectMapper();
//		
//		
//		Map<String, String> result = oMapper.convertValue(addressVO, Map.class);
//		
//		return gson1String;
//		
//	}
	
	

	//회원정보
	@RequestMapping("/MyMenu")
	@ResponseBody
	public Map<String, String> android_MyMenu(HttpServletRequest request) {
		
		String MNO = request.getParameter("MNO");
		System.out.println("회원번호: "+ MNO);
		
		LoginVO loginVO = loginService.MemberProfile(MNO);

		ObjectMapper oMapper = new ObjectMapper();
		
		
		Map<String, String> result = oMapper.convertValue(loginVO, Map.class);
		
		
		
		return result;
		
	}
	
	//사물함 정보 검색
	
		@RequestMapping(value ="/Locker" ,produces = "application/json; charset=utf8")
		@ResponseBody
		public String android_Locker(HttpServletRequest request) {
			
			String MNO = request.getParameter("MNO");
			System.out.println("회원번호: "+ MNO);
			
			
			//select * from AA_LOCKER_CHART where MNO = #{MNO}
			//TODO 회원 번호로 사물함디비에서 검색해 널이냐 아니냐인데

			//운송장번호 주소 상품이름 나타냄  아마 리스트vo로 처리해서 해야됄듯함
			List<DeliveryVO> deliveryVOlist = loginService.LockerProfile(MNO);

			ObjectMapper oMapper = new ObjectMapper();
			
			
			
			
			Gson gson = new Gson();
			
			String gson1String;

			
			if(deliveryVOlist.isEmpty()) {
				
				gson1String= "fail";
				System.out.println("사물함없음");
			} else {
				String datetest = deliveryVOlist.get(0).getProcess_date();
				System.out.println(datetest);
				gson1String = gson.toJson(deliveryVOlist);
				System.out.println("사물함있움");
			}
			
			
			return gson1String;
			
		}
	
	//택배 예약
	@RequestMapping("/Reservation")
	@ResponseBody
	public Map<String, String> android_Reservation(@ModelAttribute("DeliveryVO") DeliveryVO deliveryVO, HttpServletRequest request) {
		
		System.out.println(deliveryVO.toString());
		
		DeliveryVO numVO =loginService.ReservationDao(deliveryVO);
		System.out.println(numVO.getWaybill_number()+","+numVO.getDno());

		ObjectMapper oMapper = new ObjectMapper();
		
		
		Map<String, String> result = oMapper.convertValue(numVO, Map.class);
		
		return result;
		
	}
	
	//환불신청
	@RequestMapping("/Refund")
	@ResponseBody
	public Map<String, String> android_Refund( HttpServletRequest request) {
		
		String WAYBILL_NUMBER = request.getParameter("WAYBILL_NUMBER");
		System.out.println(WAYBILL_NUMBER);
		
		
		DeliveryVO deliveryVO = loginService.RefundDao(WAYBILL_NUMBER);
		System.out.println(deliveryVO.toString());
		
		ObjectMapper oMapper = new ObjectMapper();
		
		Map<String, String> result = oMapper.convertValue(deliveryVO, Map.class);
		
		return result;
		
	}
	
	//nfc로 해당사물함 열기 도착한 물건꺼내기
	@RequestMapping(value ="/nfc" ,produces = "application/json; charset=utf8")
	@ResponseBody
	public String android_nfc( HttpServletRequest request) {
		
		final String TagID = request.getParameter("TagID");
		String UserID = request.getParameter("UserID");
		String Toast = null;
		System.out.println("TageID:"+TagID+",UserID:"+UserID);
		
		Timer Door_timer = new Timer();
		TimerTask Door_task = new TimerTask() {
			@Override
			public void run() {
				loginService.LockerDoor(TagID, "C");
				System.out.println(TagID+"타이머성공!");
			}
		};
		//TODO select MNO from AA_LOCKER_CHART where NFCID = #{TagID} 
		//태그값으로 회원번호 비교하는건데 
		//select MNO from aaa_member a join aaa_locker_chart b on a.SNO = b.SNO where NFCID = '042E2C92DF4981'; 이걸로 변경
		String DB_UserID = loginService.LockerDao(TagID);
		
		System.out.println("디비유저값:"+DB_UserID);
		if(DB_UserID != null && DB_UserID.equals(UserID)) {
			Toast = "열었습니다!";
			adminService.Locker_update("O", "O", TagID);
			//TODO 1. 배송상태에서 사물함번호 다날리기,  2.맴버에서 사물함값날리기
			//1. update aaa_delivery_state set sno = '' where sno = (select sno from AAA_MEMBER where MNO = '1');
			//2. update AAA_member set sno = '' where mno = '1';
			loginService.delivery_state_update(UserID);
			loginService.member_sno_update(UserID);
			
			
			System.out.println("성공!");
			Door_timer.schedule(Door_task, 10000);
		} else {
			Toast = "회원정보와 일치하지않은 사물함입니다.";
			
			System.out.println("실패!");
		}
		// tagid로 사물함정보 받아옴
		//if (사물함회원번호 = 안드로이드 회원번호
		//체크 업데이트 dao
		// update where on	
		return Toast;
		
	}
	
	
	//사용자가 택배보낼대 사물함 열고 상품넣기
	@RequestMapping(value ="/se_nfc" ,produces = "application/json; charset=utf8")
	@ResponseBody
	public String android_se_nfc( HttpServletRequest request) {
		
		final String TagID= request.getParameter("TagID");
		String WAYBILL_NUMBER= request.getParameter("WAYBILL_NUMBER");
		String result = null;
		
		Timer Door_timer = new Timer();
		TimerTask Door_task = new TimerTask() {
			@Override
			public void run() {
				loginService.LockerDoor(TagID, "C");
				System.out.println(TagID+"타이머성공!");
			}
		};
		
		String CHK = loginService.LockerProductCheck(TagID);
		
		if(CHK.equals("I")) {
			result = "fail";
		} else {
			adminService.Locker_update("O", "I", TagID);
			loginService.User_Reservation(WAYBILL_NUMBER, TagID);
			Door_timer.schedule(Door_task, 10000);
		}
		
		
		
		return result;
	}
	
	

	//받은 배송조회화면
	@RequestMapping(value ="/List" ,produces = "application/json; charset=utf8")
	@ResponseBody
	public String android_List(HttpServletRequest request) {
		
		String MNO= request.getParameter("MNO");
		System.out.println(MNO);
		List<DeliveryVO> listVO =  loginService.ListDao(MNO); 
		Gson gson = new Gson();
		String gson1String = gson.toJson(listVO);
		System.out.println(gson1String);
			
			
		return  gson1String;
	}
	
	// 보낸 배송조회화면
		@RequestMapping(value ="/SE_List" ,produces = "application/json; charset=utf8")
		@ResponseBody
		public String android_SE_List(HttpServletRequest request) {
			
			String MNO= request.getParameter("MNO");
			System.out.println(MNO);
			List<DeliveryVO> listVO =  loginService.SE_ListDao(MNO); 
			Gson gson = new Gson();
			String gson1String = gson.toJson(listVO);
			System.out.println(gson1String);
				
				
			return  gson1String;
		}

	//배송상세
	@RequestMapping(value ="/Detail" ,produces = "application/json; charset=utf8")
	@ResponseBody
	public String android_Detail(HttpServletRequest request) {
		
		String WAYBILL_NUMBER= request.getParameter("WAYBILL_NUMBER");
		System.out.println(WAYBILL_NUMBER);
		
		
		List<DeliveryVO> listVO =  loginService.DetailDao(WAYBILL_NUMBER); 

		String gson1String;
		
		if(listVO.isEmpty()) {
			gson1String = "fail";
		} else {
			Gson gson = new Gson();
			gson1String = gson.toJson(listVO);
		}
		
		
		
		System.out.println(gson1String);
			
			
		return  gson1String;


	}
	
	//아두이노 와이파이모듈로 문열림 감지
	@RequestMapping(value = "/WIFItest", produces = "application/json; charset=utf8")
	@ResponseBody
	public String android_WIFItest( HttpServletRequest request) {
		
		String LNO = request.getParameter("LNO");
		String doorCheck = loginService.LockerOpenCheck(LNO);
		
		
		
		return doorCheck;
		
	}
	
	
	@RequestMapping(value = "/test", produces = "application/json; charset=utf8")
	@ResponseBody
	public String android_test() {
		
		String test = "끄앙";
		System.out.println("테스트으악!");
		
		
		return test;
		
	}
}
