package com.jang.doc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jang.doc.model.ALockerVO;
import com.jang.doc.model.AdminVO;
import com.jang.doc.model.DeliveryVO;

public interface AdminService {

	String BarcodeRead (String barcode); //바코드로 읽어온 운송장번호로 회원 보관함번호 조회
	String get_token(String WAYBILL_NUMBER); //사물함번호로 토큰값가져오기
	String BarcodeChk (String barcode); //바코드로 읽어온 운송장번호로 배송상태 조회해서 배송됐는지안됐는지 확인
	List<ALockerVO> LockerList (String WAYBILL_NUMBER); // 운송장번호의 우편번호로 해당사물함조회
	List<ALockerVO> LockerDetailList (String LNO); // 사물함일련번호로 상세 사물함 조회
	ALockerVO LockerDetail (String SNO); // 사물함상세일련번호로 상세 사물함 조회
	String Admin_LockerDao (String SNO, String NFCID); //사물함번호와 nfcid로 유효성검사
	void Locker_update (String LOCKER_CHK , String PRODUCT_CHK , String NFCID ); // 해당 보관함의 물품상태 및 잠금상태 확인
	void memberLocker_update (String SNO, String WAYBILL_NUMBER); // 배송완료후 대당회원 사물함번호 업데이트
	void delivery_Strat (String ADDR, String WAYBILL_NUMBER , String COURIER_NO); //택배기사사 배송시작해서 배송상태테이블에 인서트
	void delivery_end (String SNO, String ADDR, String WAYBILL_NUMBER , String COURIER_NO); //배송완료해서 배송상태 테이블이 인서트
	void delivery_Reservation (String SNO, String WAYBILL_NUMBER,String COURIER_NO,String ADDR);//예약상품 수거해서 배송상태테이블에 인서트
	AdminVO admin_JoinDao(@Param("userID")String userID, @Param("userPWD")String userPWD); // 관리자로그인
	List<DeliveryVO> DeliveryList (String AREA); //택배기사가 자기 지역으로 배송목록검색
	List<DeliveryVO> ReservationList (String AREA); //택배기사가 자기 지역으로 예약목록검색
	DeliveryVO DeliveryDetail (String WAYBILL_NUMBER); //배송상품 운송장번호로 상세 조회
	
}
