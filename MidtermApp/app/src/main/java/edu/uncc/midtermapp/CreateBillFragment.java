package edu.uncc.midtermapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CreateBillFragment extends Fragment {
    private EditText billNameEditText, billAmountEditText;
    private static TextView discountTextView, categoryTextView, billDateTextView;
    private String discount, category, billDate;

    // Interface to communicate with the Main Activity
    public interface CreateBillFragmentListener {
        void onSelectDiscount();
        void onSelectCategory();
        void onSelectBillDate();
        void onCreateBill(Bill bill);
    }

    private CreateBillFragmentListener listener;

    public void updateDiscount(String d){
        this.discount = d;
    }
    public void updateBillDate(String b){
        this.billDate = b;
    }
    public void updateCatergory(String c){
        this.category = c;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() instanceof CreateBillFragmentListener) {
            listener = (CreateBillFragmentListener) getActivity();
        } else {
            throw new RuntimeException(getActivity().toString()
                    + " must implement CreateBillFragmentListener");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        discountTextView.setText(this.discount);
        categoryTextView.setText(this.category);
        billDateTextView.setText(this.billDate);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_bill, container, false);

        // Initialize UI elements
        billNameEditText = view.findViewById(R.id.billNameEditText);
        billAmountEditText = view.findViewById(R.id.billAmountEditText);
        discountTextView = view.findViewById(R.id.discountTextView);
        categoryTextView = view.findViewById(R.id.categoryTextView);
        billDateTextView = view.findViewById(R.id.billDateTextView);



        // Set click listeners for buttons
        Button selectDiscountButton = view.findViewById(R.id.selectDiscountButton);
        selectDiscountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSelectDiscount();
            }
        });

        Button selectCategoryButton = view.findViewById(R.id.selectCategoryButton);
        selectCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSelectCategory();
            }
        });

        Button selectBillDateButton = view.findViewById(R.id.selectBillDateButton);
        selectBillDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSelectBillDate();
            }
        });

        Button createButton = view.findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate inputs
                if (billNameEditText.getText().toString().isEmpty() ||
                        billAmountEditText.getText().toString().isEmpty() ||
                        discount == null ||
                        category == null ||
                        billDate == null) {
                    Toast.makeText(getActivity(), "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    // Create Bill object
                    Bill bill = new Bill(billNameEditText.getText().toString(),
                            Double.parseDouble(billAmountEditText.getText().toString()),
                            discount,
                            category,
                            billDate);
                    listener.onCreateBill(bill);
                }
            }
        });

        return view;
    }
}
