// Assignment 5
// EditUserFragment.java
// Bailey Bowling

package com.example.assignmen5;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements
        MainFragment.OnFragmentInteractionListener,
        CreateUserFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener,
        EditUserFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            loadFragment(new MainFragment(), false);
        }
    }

    @Override
    public void onStartButtonClicked() {
        loadFragment(new CreateUserFragment(), true);
    }

    @Override
    public void onNextButtonClicked(User user) {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new ProfileFragment().newInstance(user), "1")
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onUpdateButtonClicked(User user) {
        EditUserFragment editUserFragment = EditUserFragment.newInstance(user);
        loadFragment(editUserFragment, true);
    }

    @Override
    public void onSubmitButtonClicked(User user) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ProfileFragment profileFragment = (ProfileFragment) fragmentManager.findFragmentByTag("1");
        if (profileFragment != null) {
            profileFragment.updateUser(user);
            fragmentManager.popBackStack();
        }
    }


    @Override
    public void onCancelButtonClicked() {
        getSupportFragmentManager().popBackStack();
    }


    private void loadFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName());
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }
}
