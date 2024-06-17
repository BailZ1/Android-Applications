package edu.uncc.assignment10;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import edu.uncc.assignment10.databinding.BillRowItemBinding;
import edu.uncc.assignment10.databinding.FragmentBillsBinding;

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
    BillsRecyclerViewAdapter adapter;

    private ArrayList<Bill> mBills = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBillsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBills.clear();
        mBills.addAll(mListener.getAllBills());

        binding.recyclerViewBills.setHasFixedSize(true);
        binding.recyclerViewBills.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new BillsRecyclerViewAdapter();
        binding.recyclerViewBills.setAdapter(adapter);

        binding.textViewSortedBy.setText("Sorted By " + sortAttribute + " (" + sortOrder + ")");

        binding.buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.clearAllBills();
                mBills.clear();
                adapter.notifyDataSetChanged();
            }
        });

        binding.buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoCreateBill();
            }
        });

        binding.buttonSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSortSelection();
            }
        });

        Collections.sort(mBills, new Comparator<Bill>() {
            @Override
            public int compare(Bill o1, Bill o2) {
                if (sortAttribute.matches("Date") && sortOrder.matches("ASC")) {
                    return o1.getBillDate().compareTo(o2.getBillDate());
                } else if (sortAttribute.matches("Date") && sortOrder.matches("DESC")) {
                    return -1 * (o1.getBillDate().compareTo(o2.getBillDate()));
                } else if (sortAttribute.matches("Category") && sortOrder.matches("ASC")) {
                    return o1.getCategory().compareTo(o2.getCategory());
                } else if (sortAttribute.matches("Category") && sortOrder.matches("DESC")) {
                    return -1 * (o1.getCategory().compareTo(o2.getCategory()));
                } else if (sortAttribute.matches("Discount") && sortOrder.matches("ASC")) {
                    return Double.compare(o1.getDiscount(), o2.getDiscount());
                } else if (sortAttribute.matches("Discount") && sortOrder.matches("DESC")) {
                    return -1 * (Double.compare(o1.getDiscount(), o2.getDiscount()));
                } else {
                    return 0;
                }
            }
        });
        adapter.notifyDataSetChanged();


    }

    BillsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BillsListener) {
            mListener = (BillsListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement BillsListener");
        }
    }

    interface BillsListener {
        void goToBillSummary(Bill bill);
        void goToEditBill(Bill bill);
        ArrayList<Bill> getAllBills();
        void gotoCreateBill();
        void gotoSortSelection();
        void clearAllBills();
        void removeBill(Bill bill);
    }

    class BillsRecyclerViewAdapter extends RecyclerView.Adapter<BillsRecyclerViewAdapter.BillsViewHolder> {

        DecimalFormat df = new DecimalFormat("#");
        DecimalFormat df2 = new DecimalFormat("#.00");
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        public BillsRecyclerViewAdapter() {
        }

        @NonNull
        @Override
        public BillsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            BillRowItemBinding mBinding = BillRowItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            BillsViewHolder holder = new BillsViewHolder(mBinding);

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull BillsViewHolder holder, int position) {
            Bill bill = mBills.get(position);
            holder.position = holder.getAdapterPosition();
            holder.bill = bill;

            holder.mBinding.textViewBillNameTemplate.setText(bill.getName());
            holder.mBinding.textViewBillAmountTemplate.setText("Bill Amount: $" + df2.format(bill.getAmount()));
            holder.mBinding.textViewDiscountPercentTemplate.setText("Discount: %" + df.format(bill.getDiscount()) + " ($" + df2.format(bill.getAmount() * (bill.getDiscount() / 100)) + ")");
            holder.mBinding.textViewTotalBillTemplate.setText("Total Bill: $" + df2.format(bill.getAmount() - (bill.getAmount() * (bill.getDiscount() / 100))));
            holder.mBinding.textViewDateTemplate.setText(sdf.format(bill.getBillDate()));
            holder.mBinding.textViewCategoryTemplate.setText(bill.getCategory());
        }

        @Override
        public int getItemCount() {
            return mBills.size();
        }

        public class BillsViewHolder extends RecyclerView.ViewHolder {

            BillRowItemBinding mBinding;
            int position;
            Bill bill;
            public BillsViewHolder(BillRowItemBinding binding) {
                super(binding.getRoot());
                mBinding = binding;

                mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.goToBillSummary(bill);
                    }
                });

                mBinding.imageViewEditTemplate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.goToEditBill(bill);
                    }
                });

                mBinding.imageViewDeleteTemplate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.removeBill(bill);
                        mBills.remove(bill);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }

    }

}