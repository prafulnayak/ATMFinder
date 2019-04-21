package org.sairaa.atmfinder.Model;

public class AccountDetail {

    private String name;
    private String pan;
    private String phoneNo;
    private String address;

    public AccountDetail() {
    }

    public AccountDetail(String name, String pan, String phoneNo, String address) {
        this.name = name;
        this.pan = pan;
        this.phoneNo = phoneNo;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
