// Bailey Bowling
// Assignment 08

package edu.uncc.assignment08;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import edu.uncc.assignment08.databinding.FragmentBillsBinding;

public class BillsFragment extends Fragment {

    public BillsFragment() {
        // Required empty public constructor
    }

    String sortAttribute = "Date", sortOrder = "ASC";

    public void setSortItems(String sortAttribute, String sortOrder) {
        this.sortAttribute = sortAttribute;
        this.sortOrder = sortOrder;
    }

    FragmentBillsBinding binding;

    private final ArrayList<Bill> mBills = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBillsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBills.clear();
        mBills.addAll(mListener.getAllBills());

        if (isAdded() && getContext() != null) {
            BillsAdapter billsAdapter = new BillsAdapter(getContext(), mBills);
            ListView listView = binding.listView;
            listView.setAdapter(billsAdapter);

            listView.setOnItemClickListener((parent, view1, position, id) -> {
                Bill bill = mBills.get(position);
                mListener.goToBillSummary(bill);
            });
        } else {
            Log.d("BillsFragment", "Fragment is not added or ListView is null");
        }

        binding.textViewSortedBy.setText("Sorted By " + sortAttribute + " (" + sortOrder + ")");

        binding.buttonClear.setOnClickListener(v -> mListener.clearAllBills());

        binding.buttonNew.setOnClickListener(v -> mListener.gotoCreateBill());

        binding.buttonSort.setOnClickListener(v -> mListener.gotoSortSelection());
    }

    BillsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BillsListener) {
            mListener = (BillsListener) context;
        } else {
            throw new RuntimeException(context + " must implement BillsListener");
        }
    }

    interface BillsListener {
        void goToBillSummary(Bill bill);
        ArrayList<Bill> getAllBills();
        void gotoCreateBill();
        void gotoSortSelection();
        void clearAllBills();
    }

    private static class BillsAdapter extends ArrayAdapter<Bill> {
        @SuppressLint("ConstantLocale")
        private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());

        public BillsAdapter(Context context, ArrayList<Bill> bills) {
            super(context, 0, bills);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Bill bill = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_bill, parent, false);
            }

            TextView billName = convertView.findViewById(R.id.billName);
            TextView billAmount = convertView.findViewById(R.id.billAmount);
            TextView billDiscount = convertView.findViewById(R.id.billDiscount);
            TextView billDiscountAmount = convertView.findViewById(R.id.billDiscountAmount);
            TextView billTotal = convertView.findViewById(R.id.billTotal);
            TextView billDate = convertView.findViewById(R.id.billDate);
            TextView billCategory = convertView.findViewById(R.id.billCategory);

            if (bill != null) {
                billName.setText(bill.getName());
                billAmount.setText(String.format(Locale.getDefault(), "%.2f", bill.getAmount()));
                double discountAmount = bill.getAmount() * bill.getDiscount() / 100;
                // Show discount percent and amount saved
                billDiscount.setText(String.format(Locale.getDefault(), "%.2f%%", bill.getDiscount()));
                billDiscountAmount.setText(String.format(Locale.getDefault(), "%.2f", discountAmount));
                billTotal.setText(String.format(Locale.getDefault(), "%.2f", bill.getAmount() - discountAmount));
                billDate.setText(DATE_FORMAT.format(bill.getBillDate()));
                billCategory.setText(bill.getCategory());
            }

            return convertView;
        }
    }
}
