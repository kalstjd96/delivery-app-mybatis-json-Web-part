package com.jang.doc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jang.doc.model.AddressVO;
import com.jang.doc.model.DeliveryVO;
import com.jang.doc.model.DetailVO;
import com.jang.doc.model.ListVO;
import com.jang.doc.model.LoginVO;

public interface LoginService {
	LoginVO JoinDao(String userID,String userPWD);
	void TokenUpdate (@Param("token")String token, @Param("id")String id); //토큰값 업데이트
	DeliveryVO ReservationDao (DeliveryVO deliveryVO);
	DeliveryVO ReservationNUM (); //택배예약시 해당 예약번호와 운송장번호 가져오기
	void Reservation_state (DeliveryVO deliveryVO); // 택배 예약하는 상태값 디비에 넣기
	DeliveryVO RefundDao (String WAYBILL_NUMBER);
	String LockerDao(String TagID); 
	String LockerOpenCheck (String LNO); // 사물함 열린상태 확인
	String LockerProductCheck (String NFCID); // 사물함 물건있는지 체크
	void User_Reservation (String WAYBILL_NUMBER, String NFCID );//고객이 물건에약하여 사물함선택해서 배송상태에 인설트
	void LockerDoor (String TagID, String state ); //사물함 열고닫기
	LoginVO MemberProfile (String MNO); //회원정보 검색
	List<DeliveryVO> LockerProfile (String MNO); //회원에게 할당덴 사물함검색
	void delivery_state_update(String MNO);//사용자가 택배수령하여 배송상태 테이블에 사물함값 지우기
	void member_sno_update(String MNO);//사용자가 택배수령하여 맴버테이블 사물함값 지우기
	
	List<DeliveryVO> ListDao(String MNO);
	List<DeliveryVO> SE_ListDao(String MNO); //받은 배송조회
	List<DeliveryVO> DetailDao(String WAYBILL_NUMBER);
	

}
