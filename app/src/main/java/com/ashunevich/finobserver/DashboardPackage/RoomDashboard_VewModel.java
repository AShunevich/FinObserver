package com.ashunevich.finobserver.DashboardPackage;

import android.app.Application;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;



public class RoomDashboard_VewModel extends AndroidViewModel {
    private RoomDashboard_Repository mRepo;
    private LiveData<List<Dashboard_Account>> mAllAccounts;


    public RoomDashboard_VewModel(@NonNull Application application) {
        super(application);
        mRepo = new RoomDashboard_Repository(application);
        mAllAccounts = mRepo.getAllAcounts();
    }

    LiveData<List<Dashboard_Account>> getmAllAccounts() {
        return mAllAccounts;
    }

    public void insert(Dashboard_Account account) {
        mRepo.insert(account);
    }

    public void deleteAll() {mRepo.deleteAll();}

    public void deleteAccount(Dashboard_Account account) {mRepo.deleteAccount(account);}

    public void updateAccount(Dashboard_Account account) {
        mRepo.updateEntity(account);}

}