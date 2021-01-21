package com.ashunevich.finobserver.DashboardAccountPackage;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.ashunevich.finobserver.TransactionsPackage.TransactionNewItem;
import com.ashunevich.finobserver.TransactionsPackage.TransactionSetNew;
import com.ashunevich.finobserver.databinding.DashboardFragmentBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import static android.app.Activity.RESULT_CANCELED;


public class DashboardFragment extends Fragment {
    EventBus bus;
    private DashboardFragmentBinding binding;
    DialogFragment newAccountDialogFragment;
    private final ArrayList<AccountItem> listContentArr = new ArrayList<>();
    private final ArrayList<String> arrayList = new ArrayList<>();
    AccountItem newItem = new AccountItem() ;
    DashboardAccRecViewAdapter adapter;
    private Double incomeValue;
    private Double expValue;
    private Double balanceValue;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        assert inflater != null;


        binding = DashboardFragmentBinding.inflate(inflater, container, false);
        binding.accountView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new DashboardAccRecViewAdapter(listContentArr);
        adapter.setListContent(listContentArr);
        binding.accountView.setAdapter(adapter);

        binding.newAccount.setOnClickListener(view -> {
            assert getFragmentManager() != null;
            newAccountDialogFragment = new AccountNewDialogFragment();
            newAccountDialogFragment.show(getFragmentManager(), "newAccountDialogFragment");
        });
        binding.newTransactionDialog.setOnClickListener(view -> newTransaction());

        if(incomeValue != null && expValue != null && balanceValue !=null ){
            binding.incomeView.setText(String.valueOf(incomeValue));
            binding.expendView.setText(String.valueOf(expValue));
            binding.balanceView.setText(String.valueOf(balanceValue));
        }
        else{
            binding.incomeView.setText(String.valueOf(0.0));
            binding.expendView.setText(String.valueOf(0.0));
            binding.balanceView.setText(String.valueOf(0.0));
        }


        bus = EventBus.getDefault();
        return binding.getRoot();

    }


    public void newTransaction(){
        Intent intent = new Intent(getContext(), TransactionSetNew.class);
              intent.putStringArrayListExtra("AccountTypes",arrayList);
        startActivityForResult(intent,1000);
    }



    @Override
    public void onDetach() {
     EventBus.getDefault().unregister(this);
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }



    private Double stringToDouble(TextView view){
        return Double.parseDouble(view.getText().toString());
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setRecyclerView(AccountNewtItem receivedItem){
        newItem.setImage(receivedItem.getImage());
        newItem.setAccountType(receivedItem.getAccountType());
        newItem.setAccountValue(receivedItem.getAccountValue());
        newItem.setAccountCurrency(receivedItem.getAccountCurrency());
        arrayList.add(receivedItem.getAccountType());
        listContentArr.add(newItem);
        adapter.notifyItemInserted(adapter.getItemCount());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode != RESULT_CANCELED) {
            String typeTransaction = data.getStringExtra("Type");
            String accountTransaction = data.getStringExtra("Account");
            String categoryAccount = data.getStringExtra("Category");
            Double getDouble = data.getDoubleExtra("Value", 0);
            incomeValue = stringToDouble(binding.incomeView);
            expValue = stringToDouble(binding.expendView);
            balanceValue = stringToDouble(binding.balanceView);

            Bundle result = new Bundle();
            result.putString("bundleType",typeTransaction );
            result.putString("accountTransaction",accountTransaction );
            result.putString("categoryAccount",categoryAccount );
            result.putDouble("bundleType",getDouble );

            if (typeTransaction.matches("Income")) {
                binding.incomeView.setText(String.valueOf(getDouble + incomeValue));
                binding.balanceView.setText(String.valueOf(getDouble + incomeValue));
            } else {
                binding.expendView.setText(String.valueOf(getDouble + expValue));
                binding.balanceView.setText(String.valueOf(balanceValue - getDouble));
            }

        }
        else{
            Snackbar.make(binding.DashboardLayout,"Transaction canceled", BaseTransientBottomBar.LENGTH_SHORT).show();
        }


        super.onActivityResult(requestCode, resultCode, data);
    }
/*
    public void postValue(String transactionValue, String transactionAccount, String transactionCategory,String transactionType ) {
        //   (TODO) send values to parent activity.
        bus.post(new TransactionNewItem(transactionValue,transactionAccount,transactionCategory,transactionType));
    }

 */


    // POSSIBILITIES (1.2) Count all accounts balance
    // POSSIBILITIES (1.2.1) Count accounts balance when account removed
    /*
    private void sumBalance(Double Value){
        Double balance = Double.parseDouble(binding.balanceSum.getText().toString())+Value;
        binding.balanceSum.setText(String.valueOf(balance));
    }


    @Override
    public void passData(Double value) {
        binding.balanceSum.setText(String.valueOf(value));
    }

     */

    // TODO (1) Implement account mechanism :
    // DONE  (1.1) Add/Remove account --> RecyclerView, DialogFragment, Implement EventBus
    // TODO  (1.3) Permanent account holder



}
