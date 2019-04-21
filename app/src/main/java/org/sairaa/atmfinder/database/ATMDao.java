package org.sairaa.atmfinder.database;


import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

@Dao
public interface ATMDao {

    @Query("SELECT * FROM AtmDetails")
    DataSource.Factory<Integer, AtmDetails> allSavedAtmDetails();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(AtmDetails atmDetails);

    @Query("SELECT * FROM atmdetails WHERE (bankName LIKE :queryString) OR (others LIKE " +
            ":queryString) ORDER BY atmId DESC")
    DataSource.Factory<Integer, AtmDetails> allSearchedAtms(String queryString);

    @Query("UPDATE AtmDetails SET cashDeposite = :dip, cashWithdraw = :wdraw, workingStatus = :wStatus WHERE atmId =:id ")
    void update(int dip,int wdraw, int wStatus, int id);
}
