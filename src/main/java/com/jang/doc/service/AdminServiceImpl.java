package com.jang.doc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jang.doc.mapper.AdminMapper;
import com.jang.doc.model.ALockerVO;
import com.jang.doc.model.AdminVO;
import com.jang.doc.model.DeliveryVO;


@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminMapper adminMapper;
	
	@Override
	public String BarcodeRead(String barcode) {
		return adminMapper.BarcodeRead(barcode);
	}

	@Override
	public List<ALockerVO> LockerList(String WAYBILL_NUMBER) {
		return adminMapper.LockerList(WAYBILL_NUMBER);
	}

	@Override
	public List<ALockerVO> LockerDetailList(String LNO) {
		return adminMapper.LockerDetailList(LNO);
	}

	@Override
	public ALockerVO LockerDetail(String SNO) {
		return adminMapper.LockerDetail(SNO);
	}

	@Override
	public String BarcodeChk(String barcode) {
		return adminMapper.BarcodeChk(barcode);
	}

	@Override
	public String Admin_LockerDao(String SNO, String NFCID) {
		return adminMapper.Admin_LockerDao(SNO, NFCID);
	}

	@Override
	public void Locker_update(String LOCKER_CHK, String PRODUCT_CHK, String NFCID) {
		adminMapper.Locker_update(LOCKER_CHK, PRODUCT_CHK, NFCID);
		
	}

	@Override
	public void memberLocker_update(String SNO, String WAYBILL_NUMBER) {
		adminMapper.memberLocker_update(SNO, WAYBILL_NUMBER);
		
	}

	@Override
	public void delivery_end(String SNO, String ADDR, String WAYBILL_NUMBER, String COURIER_NO) {
		adminMapper.delivery_end(SNO, ADDR, WAYBILL_NUMBER,COURIER_NO);
		
	}

	@Override
	public AdminVO admin_JoinDao(String userID, String userPWD) {
		return adminMapper.admin_JoinDao(userID, userPWD);
	}

	@Override
	public List<DeliveryVO> DeliveryList(String AREA) {
		return adminMapper.DeliveryList(AREA);
	}

	@Override
	public DeliveryVO DeliveryDetail(String WAYBILL_NUMBER) {
		return adminMapper.DeliveryDetail(WAYBILL_NUMBER);
	}

	@Override
	public List<DeliveryVO> ReservationList(String AREA) {

		return adminMapper.ReservationList(AREA);
	}

	@Override
	public void delivery_Reservation(String SNO, String WAYBILL_NUMBER, String COURIER_NO,String ADDR) {
		adminMapper.delivery_Reservation(SNO, WAYBILL_NUMBER, COURIER_NO,ADDR);
		
	}

	@Override
	public void delivery_Strat(String ADDR, String WAYBILL_NUMBER, String COURIER_NO) {
		adminMapper.delivery_Strat(ADDR, WAYBILL_NUMBER, COURIER_NO);
		
	}

	@Override
	public String get_token(String WAYBILL_NUMBER) {
		return adminMapper.get_token(WAYBILL_NUMBER);
	}

	
}
