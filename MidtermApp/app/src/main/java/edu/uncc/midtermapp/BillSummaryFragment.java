package edu.uncc.midtermapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BillSummaryFragment extends Fragment {
    private static final String ARG_BILL = "bill";

    private Bill bill;

    public static BillSummaryFragment newInstance(Bill bill) {
        BillSummaryFragment fragment = new BillSummaryFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_BILL, bill);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bill = (Bill) getArguments().getSerializable(ARG_BILL);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill_summary, container, false);

        // Initialize UI elements
        TextView billNameTextView = view.findViewById(R.id.billNameTextView);
        TextView billAmountTextView = view.findViewById(R.id.billAmountTextView);
        TextView discountTextView = view.findViewById(R.id.discountTextView);
        TextView discountAmountTextView = view.findViewById(R.id.discountAmountTextView);
        TextView totalBillTextView = view.findViewById(R.id.totalBillTextView);
        TextView categoryTextView = view.findViewById(R.id.categoryTextView);
        TextView billDateTextView = view.findViewById(R.id.billDateTextView);
        Button backButton = view.findViewById(R.id.backButton);

        // Set values from the Bill object
        if (bill != null) {
            billNameTextView.setText(bill.getBillName());
            billAmountTextView.setText(String.format("$%.2f", bill.getBillAmount()));

            double discount = Double.parseDouble(bill.getDiscount().replace("%", ""));
            double discountAmount = bill.getBillAmount() * (discount / 100);
            double totalBill = bill.getBillAmount() - discountAmount;

            discountTextView.setText(bill.getDiscount());
            discountAmountTextView.setText(String.format("$%.2f", discountAmount));
            totalBillTextView.setText(String.format("$%.2f", totalBill));
            categoryTextView.setText(bill.getCategory());
            billDateTextView.setText(bill.getBillDate());
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }
}
