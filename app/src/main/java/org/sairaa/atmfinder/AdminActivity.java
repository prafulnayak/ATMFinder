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
import android.widget.Toast;

import org.sairaa.atmfinder.Repository.AtmViewModel;
import org.sairaa.atmfinder.Utils.Constants;
import org.sairaa.atmfinder.Utils.RecyclerTouchListener;
import org.sairaa.atmfinder.database.AtmDetails;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener, Constants {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private Button addNewAtm;

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

        viewModel = ViewModelProviders.of(this).get(AtmViewModel.class);

        int userAdmin = getIntent().getIntExtra(adminUserT,0);
        switch (userAdmin){
            case adminT:
                addNewAtm.setVisibility(View.VISIBLE);
                break;

            case userT:
                addNewAtm.setVisibility(View.GONE);
                break;
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AtmAdapter(this,viewModel,userAdmin);

        viewModel.getSavedAtmList().observe(this, new Observer<PagedList<AtmDetails>>() {
            @Override
            public void onChanged(@Nullable PagedList<AtmDetails> hotelLists) {
                if(hotelLists != null){
                    adapter.submitList(hotelLists);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Toast.makeText(AdminActivity.this, "submit: "+query, Toast.LENGTH_SHORT).show();
                subScribeUI(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                Toast.makeText(AdminActivity.this, "Text Change: "+newText, Toast.LENGTH_SHORT).show();
                subScribeUI(newText);
                return true;
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                findItemAt(position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));




    }

    private void subScribeUI(String query) {
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
                startActivity(intent);

                break;

        }

    }
}
