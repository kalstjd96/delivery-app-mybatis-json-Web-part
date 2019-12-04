package com.jang.doc.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.jang.doc.model.ALockerVO;
import com.jang.doc.model.AdminVO;
import com.jang.doc.model.DeliveryVO;
import com.jang.doc.service.AdminService;
import com.jang.doc.service.LoginService;
import com.jang.doc.util.FcmUtil;


@Controller
public class Admin_Controller {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private LoginService loginService;
	
	
	
	
	//바코드로 운송장번호 찍었을시 체크 및 정보 전달
	@RequestMapping("/Barcode")
	@ResponseBody
	public String android_Barcode(HttpServletRequest request) {
			
	
		String WAYBILL_NUMBER = request.getParameter("WAYBILL_NUMBER"); 
		System.out.println("바코드넘버 "+ WAYBILL_NUMBER);
		  
		String SNO = adminService.BarcodeRead(WAYBILL_NUMBER);
		String CHK = adminService.BarcodeChk(WAYBILL_NUMBER);
		System.out.println("컨트롤러 체크번호"+CHK);
		
		if(CHK.equals("3")){
			System.out.println("이미배송완료");
			return "overlep";
		} else if (CHK.equals("1")) {
			return "nostart";
		}else if(SNO == null) {
			System.out.println("사물함번호가 널값");
			return "none";
		} else {
			System.out.println("사물함번호가 잘넘어감");
			return SNO;
		}
			
	}
	
	//아파트 사물함 정렬
	@RequestMapping(value ="/LockerList" ,produces = "application/json; charset=utf8")
	@ResponseBody
	public String android_LockerList(HttpServletRequest request) {
		
		String WAYBILL_NUMBER = request.getParameter("WAYBILL_NUMBER");
		System.out.println(WAYBILL_NUMBER);
		
		List<ALockerVO> lockerVO =  adminService.LockerList(WAYBILL_NUMBER);
		
		Gson gson = new Gson();
		String gson1String = gson.toJson(lockerVO);
		System.out.println(gson1String);
			
			
		return  gson1String;
	}
	
	
	
	//사물함 상세번호 정렬
	@RequestMapping(value ="/LockerDetailList" ,produces = "application/json; charset=utf8")
	@ResponseBody
	public String android_LockerDetailList(HttpServletRequest request) {
		
		String LNO = request.getParameter("LNO");
		System.out.println(LNO);
		
		List<ALockerVO> lockerVO =  adminService.LockerDetailList(LNO);
		
		Gson gson = new Gson();
		String gson1String = gson.toJson(lockerVO);
		System.out.println(gson1String);
			
			
		return  gson1String;
	}
	
	//사물함 상세정보
	@RequestMapping(value ="/LockerDetail" ,produces = "application/json; charset=utf8")
	@ResponseBody
	public String android_LockerDetail(HttpServletRequest request) {
		
		String SNO = request.getParameter("SNO");
		System.out.println("SNO"+SNO);
		
		ALockerVO lockerVO =  adminService.LockerDetail(SNO);
		
		Gson gson = new Gson();
		String gson1String = gson.toJson(lockerVO);
		System.out.println(gson1String);
			
			
		return  gson1String;
	}
	
	
	
	@RequestMapping(value ="/DeliveryStrat" ,produces = "application/json; charset=utf8")
	@ResponseBody
	public String android_DeliveryStrat( HttpServletRequest request) {
		String WAYBILL_NUMBER = request.getParameter("WAYBILL_NUMBER");
		String ADDR = request.getParameter("ADDR");
		String COURIER_NO = request.getParameter("COURIER_NO");
		
		adminService.delivery_Strat(ADDR, WAYBILL_NUMBER, COURIER_NO);
		String Toast = "Strat";
		
		return Toast;
		
	}
	
	
	//택배기사사 nfc로 열기 배송완료
		@RequestMapping(value ="/admin_nfc" ,produces = "application/json; charset=utf8")
		@ResponseBody
		public String android_nfc( HttpServletRequest request) {
			
			final String TagID = request.getParameter("TagID");
			String SNO = request.getParameter("SNO");
			String WAYBILL_NUMBER = request.getParameter("WAYBILL_NUMBER");
			String ADDR = request.getParameter("ADDR");
			String COURIER_NO = request.getParameter("COURIER_NO");
			String Toast = null;
			System.out.println(ADDR);
			
			Timer Door_timer = new Timer();
			TimerTask Door_task = new TimerTask() {
				@Override
				public void run() {
					loginService.LockerDoor(TagID, "C");
					System.out.println(TagID+"타이머성공!");
				}
			};
			
			String NFCcheck = adminService.Admin_LockerDao(SNO, TagID);
			
			
			if(NFCcheck != null) {
				Toast = "open";
				adminService.Locker_update("O", "I", TagID);
				System.out.println("성공!");
				Door_timer.schedule(Door_task, 10000);
				
				// 1.맴버테이블 SNO 업데이트  2.배송상태에 배송완료 인서트
				adminService.memberLocker_update(SNO, WAYBILL_NUMBER);
				adminService.delivery_end(SNO, ADDR, WAYBILL_NUMBER,COURIER_NO);
				
				String token = adminService.get_token(WAYBILL_NUMBER);
				System.out.println(token);
				FcmUtil FcmUtil = new FcmUtil();
				FcmUtil.send_FCM(token , "제목", "배송완료");
				
			} else {
				Toast = "close";
				
				System.out.println("실패!");
			}
			// tagid로 사물함정보 받아옴
			//if (사물함회원번호 = 안드로이드 회원번호
			//체크 업데이트 dao
			// update where on
			
			return Toast;
			
		}
		
		
		//택배기사가 예약된 상품 꺼낼때 nfc
		@RequestMapping(value ="/admin_Reservation_nfc" ,produces = "application/json; charset=utf8")
		@ResponseBody
		public String android_admin_Reservation_nfc( HttpServletRequest request) {
			
			final String TagID = request.getParameter("TagID");
			String SNO = request.getParameter("SNO");
			String ADDR = request.getParameter("ADDR");
			String WAYBILL_NUMBER = request.getParameter("WAYBILL_NUMBER");
			String COURIER_NO = request.getParameter("COURIER_NO");

			String Toast = null;

			
			Timer Door_timer = new Timer();
			TimerTask Door_task = new TimerTask() {
				@Override
				public void run() {
					loginService.LockerDoor(TagID, "C");
					System.out.println(TagID+"타이머성공!");
				}
			};
			
			String NFCcheck = adminService.Admin_LockerDao(SNO, TagID);
			
			
			if(NFCcheck != null) {
				Toast = "open";
				adminService.Locker_update("O", "O", TagID);
				System.out.println("성공!");
				Door_timer.schedule(Door_task, 10000);
				adminService.delivery_Reservation(SNO, WAYBILL_NUMBER, COURIER_NO,ADDR);

			} else {
				Toast = "close";
				
				System.out.println("실패!");
			}
			// tagid로 사물함정보 받아옴
			//if (사물함회원번호 = 안드로이드 회원번호
			//체크 업데이트 dao
			// update where on
			
			return Toast;
			
		}
		
		//관리자어드민
		@RequestMapping("/Admin_Login")
		@ResponseBody
		public Map<String, String> android_Login(HttpServletRequest request) {

			String userID = request.getParameter("User_id");
			String userPWD = request.getParameter("User_pass");

			System.out.println(userID);
			System.out.println(userPWD);

			AdminVO adminVO = adminService.admin_JoinDao(userID, userPWD);		
			
			
			Map<String, String> result = new HashMap<String, String>();
			
			ObjectMapper oMapper = new ObjectMapper();
			
			
			if(adminVO == null) {
				result.put("courier_no", "fail");
				System.out.println("로그인실패");
			} else {
				result = oMapper.convertValue(adminVO, Map.class);
				System.out.println("로그인성공");
			}
			
			return result;
		}
		
		
		
		//배송조회할목록
		@RequestMapping(value ="/DeliveryList" ,produces = "application/json; charset=utf8")
		@ResponseBody
		public String android_DeliveryList(HttpServletRequest request) {
			
			String AREA = request.getParameter("AREA");
			System.out.println(AREA);
			
			List<DeliveryVO> deliveryVO =  adminService.DeliveryList(AREA);
			
			Gson gson = new Gson();
			String gson1String = gson.toJson(deliveryVO);
			System.out.println(gson1String);
				
				
			return  gson1String;
		}
		
		//예약목록조회
		@RequestMapping(value ="/Reservation_List" ,produces = "application/json; charset=utf8")
		@ResponseBody
		public String android_Reservation_List(HttpServletRequest request) {
					
			String AREA = request.getParameter("AREA");
			System.out.println(AREA);
					
			List<DeliveryVO> deliveryVO =  adminService.ReservationList(AREA);
					
			Gson gson = new Gson();
			String gson1String = gson.toJson(deliveryVO);
			System.out.println(gson1String);
						
						
			return  gson1String;
		}		
		
		
		
		//배송상품 상세
		
		@RequestMapping("/DeliveryDetail")
		@ResponseBody
		public Map<String, String> DeliveryDetail(HttpServletRequest request) {

			String WAYBILL_NUMBER = request.getParameter("WAYBILL_NUMBER");

			System.out.println(WAYBILL_NUMBER);

			DeliveryVO deliveryVO = adminService.DeliveryDetail(WAYBILL_NUMBER);
			
			
			Map<String, String> result = new HashMap<String, String>();
			
			ObjectMapper oMapper = new ObjectMapper();
			
			

			result = oMapper.convertValue(deliveryVO, Map.class);

			
			return result;
		}
		 
	
	

}
