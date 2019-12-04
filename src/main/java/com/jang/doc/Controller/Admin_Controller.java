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
	
	
	
	
	//���ڵ�� ������ȣ ������� üũ �� ���� ����
	@RequestMapping("/Barcode")
	@ResponseBody
	public String android_Barcode(HttpServletRequest request) {
			
	
		String WAYBILL_NUMBER = request.getParameter("WAYBILL_NUMBER"); 
		System.out.println("���ڵ�ѹ� "+ WAYBILL_NUMBER);
		  
		String SNO = adminService.BarcodeRead(WAYBILL_NUMBER);
		String CHK = adminService.BarcodeChk(WAYBILL_NUMBER);
		System.out.println("��Ʈ�ѷ� üũ��ȣ"+CHK);
		
		if(CHK.equals("3")){
			System.out.println("�̹̹�ۿϷ�");
			return "overlep";
		} else if (CHK.equals("1")) {
			return "nostart";
		}else if(SNO == null) {
			System.out.println("�繰�Թ�ȣ�� �ΰ�");
			return "none";
		} else {
			System.out.println("�繰�Թ�ȣ�� �߳Ѿ");
			return SNO;
		}
			
	}
	
	//����Ʈ �繰�� ����
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
	
	
	
	//�繰�� �󼼹�ȣ ����
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
	
	//�繰�� ������
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
	
	
	//�ù���� nfc�� ���� ��ۿϷ�
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
					System.out.println(TagID+"Ÿ�̸Ӽ���!");
				}
			};
			
			String NFCcheck = adminService.Admin_LockerDao(SNO, TagID);
			
			
			if(NFCcheck != null) {
				Toast = "open";
				adminService.Locker_update("O", "I", TagID);
				System.out.println("����!");
				Door_timer.schedule(Door_task, 10000);
				
				// 1.�ɹ����̺� SNO ������Ʈ  2.��ۻ��¿� ��ۿϷ� �μ�Ʈ
				adminService.memberLocker_update(SNO, WAYBILL_NUMBER);
				adminService.delivery_end(SNO, ADDR, WAYBILL_NUMBER,COURIER_NO);
				
				String token = adminService.get_token(WAYBILL_NUMBER);
				System.out.println(token);
				FcmUtil FcmUtil = new FcmUtil();
				FcmUtil.send_FCM(token , "����", "��ۿϷ�");
				
			} else {
				Toast = "close";
				
				System.out.println("����!");
			}
			// tagid�� �繰������ �޾ƿ�
			//if (�繰��ȸ����ȣ = �ȵ���̵� ȸ����ȣ
			//üũ ������Ʈ dao
			// update where on
			
			return Toast;
			
		}
		
		
		//�ù��簡 ����� ��ǰ ������ nfc
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
					System.out.println(TagID+"Ÿ�̸Ӽ���!");
				}
			};
			
			String NFCcheck = adminService.Admin_LockerDao(SNO, TagID);
			
			
			if(NFCcheck != null) {
				Toast = "open";
				adminService.Locker_update("O", "O", TagID);
				System.out.println("����!");
				Door_timer.schedule(Door_task, 10000);
				adminService.delivery_Reservation(SNO, WAYBILL_NUMBER, COURIER_NO,ADDR);

			} else {
				Toast = "close";
				
				System.out.println("����!");
			}
			// tagid�� �繰������ �޾ƿ�
			//if (�繰��ȸ����ȣ = �ȵ���̵� ȸ����ȣ
			//üũ ������Ʈ dao
			// update where on
			
			return Toast;
			
		}
		
		//�����ھ���
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
				System.out.println("�α��ν���");
			} else {
				result = oMapper.convertValue(adminVO, Map.class);
				System.out.println("�α��μ���");
			}
			
			return result;
		}
		
		
		
		//�����ȸ�Ҹ��
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
		
		//��������ȸ
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
		
		
		
		//��ۻ�ǰ ��
		
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
