package com.jang.doc.model;

public class AddressVO {

	private String send_name;
    private String send_phone;
    private String send_addr1;
    private String send_addr2;
    private String send_addr3;
    private String product;
    private String join_date;
    private String company;
    private String recv_name;
    private String recv_phone;
    private String recv_addr1;
    private String recv_addr2;
    private String recv_addr3;
    
    
	public String getSend_name() {
		return send_name;
	}
	public void setSend_name(String send_name) {
		this.send_name = send_name;
	}
	
	public String getSend_phone() {
		return send_phone;
	}
	public void setSend_phone(String send_phone) {
		this.send_phone = send_phone;
	}
	public String getSend_addr1() {
		return send_addr1;
	}
	public void setSend_addr1(String send_addr1) {
		this.send_addr1 = send_addr1;
	}
	public String getSend_addr2() {
		return send_addr2;
	}
	public void setSend_addr2(String send_addr2) {
		this.send_addr2 = send_addr2;
	}
	public String getSend_addr3() {
		return send_addr3;
	}
	public void setSend_addr3(String send_addr3) {
		this.send_addr3 = send_addr3;
	}
	
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getJoin_date() {
		return join_date;
	}
	public void setJoin_date(String join_date) {
		this.join_date = join_date;
	}
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getRecv_name() {
		return recv_name;
	}
	public void setRecv_name(String recv_name) {
		this.recv_name = recv_name;
	}
	
	public String getRecv_phone() {
		return recv_phone;
	}
	public void setRecv_phone(String recv_phone) {
		this.recv_phone = recv_phone;
	}
	public String getRecv_addr1() {
		return recv_addr1;
	}
	public void setRecv_addr1(String recv_addr1) {
		this.recv_addr1 = recv_addr1;
	}
	public String getRecv_addr2() {
		return recv_addr2;
	}
	public void setRecv_addr2(String recv_addr2) {
		this.recv_addr2 = recv_addr2;
	}
	public String getRecv_addr3() {
		return recv_addr3;
	}
	public void setRecv_addr3(String recv_addr3) {
		this.recv_addr3 = recv_addr3;
	}
	
	@Override
	public String toString() {
		return "AddressVO [send_name=" + send_name + ", send_phone=" + send_phone + ", send_addr1=" + send_addr1
				+ ", send_addr2=" + send_addr2 + ", send_addr3=" + send_addr3 + ", product=" + product + ", join_date="
				+ join_date + ", company=" + company + ", recv_name=" + recv_name + ", recv_phone=" + recv_phone
				+ ", recv_addr1=" + recv_addr1 + ", recv_addr2=" + recv_addr2 + ", recv_addr3=" + recv_addr3 + "]";
	}
	
	
	
    
}
