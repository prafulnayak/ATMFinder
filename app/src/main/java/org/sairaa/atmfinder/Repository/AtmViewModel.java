package org.sairaa.atmfinder.Repository;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import org.sairaa.atmfinder.database.ATMdb;
import org.sairaa.atmfinder.database.AtmDetails;

public class AtmViewModel extends AndroidViewModel {

    private SavedAtmRepo mRepository;

    private DataSource.Factory<Integer, AtmDetails> savedH;

    private LiveData<PagedList<AtmDetails>> atmListLiveData;

    private LiveData<PagedList<AtmDetails>> atmListSearcheData;

    private Application application;

    public AtmViewModel (Application application) {
        super(application);
        this.application = application;
        mRepository = new SavedAtmRepo(application);
        savedH = mRepository.getSavedATM();
    }

    public LiveData<PagedList<AtmDetails>> getSavedAtmList() {

        atmListLiveData = null;
        DataSource.Factory<Integer,AtmDetails> factory = savedH;
        //config the pagedList
        //setPageSize(5) retrieves 5 sets of ATM object in single instance
        PagedList.Config pagConfig = new PagedList.Config.Builder().setPageSize(5).setEnablePlaceholders(false).build();
        LivePagedListBuilder<Integer, AtmDetails> pagedListBuilder = new LivePagedListBuilder(factory,pagConfig);
        atmListLiveData = pagedListBuilder.build();
        return atmListLiveData;
    }

    public LiveData<PagedList<AtmDetails>> getSearchAtmList(String queryString) {
        //set the query string to retrive like word from database
        String query = "%".concat(queryString).concat("%");

        atmListSearcheData = null;
        //a DataSource is the base class for loading snapshots of data into a PagedList
        //A DataSource.Factory is responsible for creating a DataSource.
        DataSource.Factory<Integer, AtmDetails> factory = mRepository.getSearchedAtmDetailsATM(query);//ATMdb.getsInstance(application).atmDao().allSearchedAtms(query);
        //a collection that loads data in pages, asynchronously. A PagedList can be used to load data from sources you define,
        // and present it easily in your UI with a RecyclerView.
        PagedList.Config pagConfig = new PagedList.Config.Builder().setPageSize(5).setEnablePlaceholders(false).build();
        // LivePagedListBuilder builds a LiveData<PagedList>, based on DataSource.Factory and a PagedList.Config.
        LivePagedListBuilder<Integer, AtmDetails> pagedListBuilder = new LivePagedListBuilder(factory, pagConfig);
        atmListSearcheData = pagedListBuilder.build();
        return atmListSearcheData;

    }



    public void insert(AtmDetails atmDetail) {
        mRepository.insert(atmDetail);
    }

    public void update(int dip,int wdraw, int wStatus, int id){
        mRepository.update(dip,wdraw,wStatus,id);
    }


    public AtmDetails getItemAtLoc(int position) {
        return mRepository.getItemAtLoc(position);

    }
}
