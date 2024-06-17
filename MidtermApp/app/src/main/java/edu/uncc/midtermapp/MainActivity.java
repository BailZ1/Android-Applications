package edu.uncc.midtermapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements CreateBillFragment.CreateBillFragmentListener, SelectDiscountFragment.SelectDiscountFragmentListener, SelectCategoryFragment.SelectCategoryFragmentListener, SelectBillDateFragment.SelectBillDateFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load the CreateBillFragment as the initial fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main, new CreateBillFragment(), "CREATE_BILL")
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onSelectDiscount() {
        // Replace CreateBillFragment with SelectDiscountFragment
        replaceFragment(new SelectDiscountFragment(), "");
    }

    @Override
    public void onSelectCategory() {
        // Replace CreateBillFragment with SelectCategoryFragment
        replaceFragment(new SelectCategoryFragment(), "");
    }

    @Override
    public void onSelectBillDate() {
        // Replace CreateBillFragment with SelectBillDateFragment
        replaceFragment(new SelectBillDateFragment(), "");
    }

    @Override
    public void onCreateBill(Bill bill) {
        // Replace CreateBillFragment with BillSummaryFragment and pass the Bill object
        BillSummaryFragment fragment = BillSummaryFragment.newInstance(bill);
        replaceFragment(fragment, "");
    }

    private void replaceFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onDiscountSelected(String discount) {
        // Communicate the selected discount back to CreateBillFragment
        Log.d("Discount", "Selected: " + discount);
        getSupportFragmentManager().popBackStack();

        CreateBillFragment createBillFragment = (CreateBillFragment) getSupportFragmentManager().findFragmentByTag("CREATE_BILL");
        if (createBillFragment != null) {
            createBillFragment.updateDiscount(discount);
        }
    }

    @Override
    public void onCategorySelected(String category) {
        // Communicate the selected category back to CreateBillFragment
        getSupportFragmentManager().popBackStack();

        CreateBillFragment createBillFragment = (CreateBillFragment) getSupportFragmentManager().findFragmentByTag("CREATE_BILL");
        if (createBillFragment != null) {
            createBillFragment.updateCatergory(category);
        }
    }

    @Override
    public void onBillDateSelected(String billDate) {
        // Communicate the selected bill date back to CreateBillFragment
        getSupportFragmentManager().popBackStack();

        CreateBillFragment createBillFragment = (CreateBillFragment) getSupportFragmentManager().findFragmentByTag("CREATE_BILL");
        if (createBillFragment != null) {
            createBillFragment.updateBillDate(billDate);
        }
    }
}

