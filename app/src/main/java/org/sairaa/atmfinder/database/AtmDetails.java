package org.sairaa.atmfinder.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class AtmDetails {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int atmId;
    private String bankName;
    private double latitude;
    private double longitude;
    private int cashWithdraw;
    private int cashDeposite;
    private int checqeDeposite;
    private int workingStatus;
    private String others;

    @Ignore
    public AtmDetails(String bankName, double latitude, double longitude, int cashWithdraw, int cashDeposite, int checqeDeposite, int workingStatus, String others) {
        this.bankName = bankName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cashWithdraw = cashWithdraw;
        this.cashDeposite = cashDeposite;
        this.checqeDeposite = checqeDeposite;
        this.workingStatus = workingStatus;
        this.others = others;
    }

    public AtmDetails(int atmId, String bankName, double latitude, double longitude, int cashWithdraw, int cashDeposite, int checqeDeposite, int workingStatus, String others) {
        this.atmId = atmId;
        this.bankName = bankName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cashWithdraw = cashWithdraw;
        this.cashDeposite = cashDeposite;
        this.checqeDeposite = checqeDeposite;
        this.workingStatus = workingStatus;
        this.others = others;
    }

    public int getAtmId() {
        return atmId;
    }

    public void setAtmId(int atmId) {
        this.atmId = atmId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getCashWithdraw() {
        return cashWithdraw;
    }

    public void setCashWithdraw(int cashWithdraw) {
        this.cashWithdraw = cashWithdraw;
    }

    public int getCashDeposite() {
        return cashDeposite;
    }

    public void setCashDeposite(int cashDeposite) {
        this.cashDeposite = cashDeposite;
    }

    public int getChecqeDeposite() {
        return checqeDeposite;
    }

    public void setChecqeDeposite(int checqeDeposite) {
        this.checqeDeposite = checqeDeposite;
    }

    public int getWorkingStatus() {
        return workingStatus;
    }

    public void setWorkingStatus(int workingStatus) {
        this.workingStatus = workingStatus;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }
}
