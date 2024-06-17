package com.example.assignment6;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements MainFragment.MainFragmentListener,
        CreateUserFragment.CreateUserFragmentListener, CountryFragment.CountryFragmentListener, DoBFragment.DoBFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            loadFragment(new MainFragment(), false, "MAIN_FRAGMENT");
        }
    }

    private void loadFragment(Fragment fragment, boolean addToBackStack, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.containerView, fragment, tag);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    @Override
    public void onStartButtonClicked() {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new CreateUserFragment(), "CREATE_USER_FRAGMENT")
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void gotocounty() {
    getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new CountryFragment(), "COUNTRY_FRAGMENT")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onCountrySelected(String country) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        CreateUserFragment createUserFragment = (CreateUserFragment) fragmentManager.findFragmentByTag("CREATE_USER_FRAGMENT");
        if (createUserFragment != null) {
            createUserFragment.updateCountry(country);
            fragmentManager.popBackStack();
        }

    }

    @Override
    public void onDateSelected(String date) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        CreateUserFragment createUserFragment = (CreateUserFragment) fragmentManager.findFragmentByTag("CREATE_USER_FRAGMENT");
        if (createUserFragment != null) {
            createUserFragment.updateDateOfBirth(date);

        }
        fragmentManager.popBackStack();
    }

    @Override
    public void onUserCreated(User user) {
        ProfileFragment profileFragment = ProfileFragment.newInstance(user);
        loadFragment(profileFragment, true, "PROFILE_FRAGMENT");
    }
}
