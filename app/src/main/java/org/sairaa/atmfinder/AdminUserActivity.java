package org.sairaa.atmfinder;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Button;

import org.sairaa.atmfinder.Repository.AtmViewModel;
import org.sairaa.atmfinder.Utils.Constants;
import org.sairaa.atmfinder.Utils.RecyclerTouchListener;
import org.sairaa.atmfinder.database.AtmDetails;

public class AdminUserActivity extends AppCompatActivity implements View.OnClickListener, Constants {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private Button addNewAtm, openBankAcc;

    private AtmViewModel viewModel;

    private AtmAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        searchView = findViewById(R.id.search_material);
        recyclerView = findViewById(R.id.recyclerView_atm);

        addNewAtm = findViewById(R.id.add_new_atm);
        addNewAtm.setOnClickListener(this);

        openBankAcc = findViewById(R.id.open_bank_acc);
        openBankAcc.setOnClickListener(this);

        // View Model
        viewModel = ViewModelProviders.of(this).get(AtmViewModel.class);

        int userAdmin = getIntent().getIntExtra(adminUserT,0);
        switch (userAdmin){
            case adminT:
                //if it is admin show add new atm button to add new one
                addNewAtm.setVisibility(View.VISIBLE);
                openBankAcc.setVisibility(View.GONE);
                break;

            case userT:
                //if it is user make add new atm button invisible
                addNewAtm.setVisibility(View.GONE);
                openBankAcc.setVisibility(View.VISIBLE);
                break;
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AtmAdapter(this,viewModel,userAdmin);

        //Observe the data. Retrieve All ATM list from database
        viewModel.getSavedAtmList().observe(this, new Observer<PagedList<AtmDetails>>() {
            @Override
            public void onChanged(@Nullable PagedList<AtmDetails> atmLists) {
                if(atmLists != null){
                    adapter.submitList(atmLists);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        // Search String for Bank name and Location String
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Update the UI with queried Data
                UpdateUI(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Update the UI with queried Data
                UpdateUI(newText);
                return true;
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                // find the ATM details and show it in EDIT Activity for Edit Or may be for viewing only
                //Update for admin and viewing is for user only
                //Not Implemented Yet
                findItemAt(position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));




    }

    private void UpdateUI(String query) {
//        viewModel.getSearchAtmList(query);

        viewModel.getSearchAtmList(query).observe(this, new Observer<PagedList<AtmDetails>>() {
            @Override
            public void onChanged(@Nullable PagedList<AtmDetails> atmLists) {
                if(atmLists != null){
                    adapter.submitList(atmLists);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void findItemAt(int position) {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.add_new_atm:
                Intent intent = new Intent(this,EditActivity.class);
                intent.putExtra(adminUserT,adminT);
                startActivity(intent);

                break;

            case R.id.open_bank_acc:
                Intent bankIntent = new Intent(this,BankAccountActivity.class);
                startActivity(bankIntent);

        }

    }
}
