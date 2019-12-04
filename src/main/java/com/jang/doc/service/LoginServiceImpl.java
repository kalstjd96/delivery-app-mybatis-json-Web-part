package com.jang.doc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jang.doc.mapper.LoginMapper;
import com.jang.doc.model.AddressVO;
import com.jang.doc.model.DeliveryVO;
import com.jang.doc.model.DetailVO;
import com.jang.doc.model.ListVO;
import com.jang.doc.model.LoginVO;

@Service
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	private LoginMapper loginMapper;
	

	@Override
	public LoginVO JoinDao(String userID,String userPWD) {
		
		return loginMapper.JoinDao(userID,userPWD);
	}

	@Override
	public DeliveryVO ReservationDao(DeliveryVO deliveryVO) {

		loginMapper.ReservationDao(deliveryVO);
		DeliveryVO numVO = loginMapper.ReservationNUM();
		loginMapper.Reservation_state(numVO);
		return numVO;
		
		
	}

	@Override
	public DeliveryVO RefundDao(String WAYBILL_NUMBER) {
		return loginMapper.RefundDao(WAYBILL_NUMBER);
	}

	@Override
	public String LockerDao(String TagID) {
		return loginMapper.LockerDao(TagID);
	}

	@Override
	public String LockerOpenCheck(String LNO) {
		return loginMapper.LockerOpenCheck(LNO);
	}

	@Override
	public void LockerDoor(String TagID, String state) {
		loginMapper.LockerDoor(TagID, state);
	}

	@Override
	public LoginVO MemberProfile(String MNO) {
		return loginMapper.MemberProfile(MNO);
	}

	@Override
	public List<DeliveryVO> LockerProfile(String MNO) {
		return loginMapper.LockerProfile(MNO);
	}

	@Override
	public List<DeliveryVO> ListDao(String MNO) {
		return loginMapper.ListDao(MNO);
	}

	@Override
	public List<DeliveryVO> DetailDao(String WAYBILL_NUMBER) {
		return loginMapper.DetailDao(WAYBILL_NUMBER);
	}

	@Override
	public void delivery_state_update(String MNO) {
		loginMapper.delivery_state_update(MNO);
		
	}

	@Override
	public void member_sno_update(String MNO) {
		loginMapper.member_sno_update(MNO);
		
	}

	@Override
	public DeliveryVO ReservationNUM() {
		return loginMapper.ReservationNUM();
	}

	@Override
	public void Reservation_state(DeliveryVO deliveryVO) {
		loginMapper.Reservation_state(deliveryVO);
		
	}

	@Override
	public List<DeliveryVO> SE_ListDao(String MNO) {
		return loginMapper.SE_ListDao(MNO);
	}

	@Override
	public String LockerProductCheck(String NFCID) {
		return loginMapper.LockerProductCheck(NFCID);
	}

	@Override
	public void User_Reservation(String WAYBILL_NUMBER, String NFCID) {
		loginMapper.User_Reservation(WAYBILL_NUMBER, NFCID);
	}

	@Override
	public void TokenUpdate(String token, String id) {
		loginMapper.TokenUpdate(token, id);
		
	}

}
