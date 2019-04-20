package org.sairaa.atmfinder;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.sairaa.atmfinder.Repository.AtmViewModel;
import org.sairaa.atmfinder.Utils.Constants;
import org.sairaa.atmfinder.database.AtmDetails;

public class AtmAdapter extends PagedListAdapter<AtmDetails,AtmAdapter.MyViewHolder> implements Constants {

    private Context context;
    private AtmViewModel viewModel;
    private int userAdmin = 0;
    private static DiffUtil.ItemCallback<AtmDetails> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<AtmDetails>() {
                @Override
                public boolean areItemsTheSame(@NonNull AtmDetails oldItem, @NonNull AtmDetails newItem) {
                    return oldItem.getAtmId() == newItem.getAtmId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull AtmDetails oldItem, @NonNull AtmDetails newItem) {
                    return oldItem.equals(newItem);
                }
            };

    public AtmAdapter(Context context, AtmViewModel viewModel, int userAdmin) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.viewModel = viewModel;
        this.userAdmin = userAdmin;
    }




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.saved_items, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        AtmDetails atmDetails = getItem(i);
        if (atmDetails != null) {
            holder.atmD.setText(atmDetails.getBankName().concat(String.valueOf(atmDetails.getAtmId()))
            .concat(String.valueOf(atmDetails.getLatitude()))
            .concat(String.valueOf(atmDetails.getLongitude()))
            .concat(String.valueOf(atmDetails.getWorkingStatus()))
            .concat(String.valueOf(atmDetails.getCashDeposite()))
            .concat(String.valueOf(atmDetails.getCashWithdraw()))
            .concat(atmDetails.getOthers()));
        }

        holder.bankName.setText("Bank Name: ".concat(atmDetails.getBankName()));
        holder.latLang.setText("Latitude: ".concat(String.valueOf(atmDetails.getLatitude())).concat(" ").concat(" Longitude: ").concat(String.valueOf(atmDetails.getLongitude())));
        if(atmDetails.getWorkingStatus()==1){
            holder.worlingStatus.setText("ATM Working Status: Working");
        }else {
            holder.worlingStatus.setText("ATM Working Status: Not Working");
        }
        String facility = "";
        if(atmDetails.getCashDeposite()==1){
            facility= facility.concat(" Cash Deposit");
        }
        if(atmDetails.getCashWithdraw() == 1){
            facility = facility.concat(" Cash Withdraw");
        }

        holder.facility.setText("Facilities Available: ".concat(facility));

        holder.location.setText(atmDetails.getOthers());



        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,EditActivity.class);
                intent.putExtra(adminUserT,userAdmin);
                intent.putExtra("position",i);
                context.startActivity(intent);
            }
        });




    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView atmD, bankName,latLang,worlingStatus,facility,location;
        private ConstraintLayout constraintLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            atmD = itemView.findViewById(R.id.atm_details_t);
            constraintLayout = itemView.findViewById(R.id.recycler_item_lay);
            bankName = itemView.findViewById(R.id.bank_nameR);
            latLang = itemView.findViewById(R.id.lat_lang);
            worlingStatus = itemView.findViewById(R.id.working_statusR);
            facility = itemView.findViewById(R.id.facilityR);
            location = itemView.findViewById(R.id.locationR);
        }
    }
}
