package org.sairaa.atmfinder.Repository;

import android.app.Application;
import android.arch.paging.DataSource;
import android.os.AsyncTask;

import org.sairaa.atmfinder.database.ATMDao;
import org.sairaa.atmfinder.database.ATMdb;
import org.sairaa.atmfinder.database.AtmDetails;

public class SavedAtmRepo {

    private ATMDao atmDao;
    private DataSource.Factory<Integer, AtmDetails> savedAtmDetails;
    private DataSource.Factory<Integer, AtmDetails> searchedAtmDetails;


    public SavedAtmRepo(Application application) {
        ATMdb db = ATMdb.getsInstance(application);
        atmDao = db.atmDao();
        savedAtmDetails =atmDao.allSavedAtmDetails();

    }

    public DataSource.Factory<Integer, AtmDetails> getSearchedAtmDetailsATM(String query) {
        return atmDao.allSearchedAtms(query);
    }


    public DataSource.Factory<Integer, AtmDetails> getSavedATM() {
        return savedAtmDetails;
    }

    public void insert(AtmDetails atm) {
        new insertAsyncTask(atmDao).execute(atm);
    }

    public AtmDetails getItemAtLoc(int position) {

        return null;
    }

    private static class insertAsyncTask extends AsyncTask<AtmDetails, Void, Void> {

        private ATMDao mAsyncTaskDao;

        insertAsyncTask(ATMDao atmDao) {
            mAsyncTaskDao = atmDao;
        }

        @Override
        protected Void doInBackground(AtmDetails... atm) {
            mAsyncTaskDao.insert(atm[0]);
            return null;
        }

    }


}
