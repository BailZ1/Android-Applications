package edu.uncc.midtermapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.Calendar;

public class SelectBillDateFragment extends Fragment {

    private DatePicker billDatePicker;

    public interface SelectBillDateFragmentListener {
        void onBillDateSelected(String billDate);
    }

    private SelectBillDateFragmentListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() instanceof SelectBillDateFragmentListener) {
            listener = (SelectBillDateFragmentListener) getActivity();
        } else {
            throw new RuntimeException(getActivity().toString()
                    + " must implement SelectBillDateFragmentListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_bill_date, container, false);

        billDatePicker = view.findViewById(R.id.billDatePicker);

        Calendar calendar = Calendar.getInstance();
        billDatePicker.setMaxDate(calendar.getTimeInMillis());

        Button submitButton = view.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = billDatePicker.getDayOfMonth();
                int month = billDatePicker.getMonth();
                int year = billDatePicker.getYear();

                String billDate = day + "/" + (month + 1) + "/" + year;

                listener.onBillDateSelected(billDate);
            }
        });

        Button cancelButton = view.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }
}
