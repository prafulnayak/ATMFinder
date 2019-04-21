package org.sairaa.atmfinder.Utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import org.sairaa.atmfinder.Model.AccountDetail;
import org.sairaa.atmfinder.Model.UserAdmin;

import java.util.ArrayList;
import java.util.List;

public class ApiUtilsData implements Constants{

    private static List<UserAdmin> userAdminList = new ArrayList<>();

    private static List<AccountDetail> accountDetails = new ArrayList<>();

    public static void addUserAndAdmin(){
        userAdminList.add(new UserAdmin("praful","praful",userT));
        userAdminList.add(new UserAdmin("nayak","nayak",adminT));
        userAdminList.add(new UserAdmin("user","user",userT));
        userAdminList.add(new UserAdmin("admin","admin",adminT));
    }

    public static int checkUserOrAdmin(String userName, String password){

        int userAdminStatus = -1;
        for(UserAdmin userAdmin: userAdminList){
            if(userAdmin.getUserName().equals(userName) && userAdmin.getPassWord().equals(password)){
                userAdminStatus =  userAdmin.getUserType();
            }
        }


        return userAdminStatus;

    }

    public static void addAccount(AccountDetail account){
        accountDetails.add(account);
    }

    public static AccountDetail getSingleAccountDetails(String pan){
        AccountDetail singleAccount = new AccountDetail();

        for(AccountDetail detail: accountDetails){
            if(detail.getPan().equals(pan)){
                singleAccount = detail;
            }
        }

        return singleAccount;

    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

}
