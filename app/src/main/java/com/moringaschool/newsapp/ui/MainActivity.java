package com.moringaschool.newsapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moringaschool.newsapp.Constants;
import com.moringaschool.newsapp.R;
import com.moringaschool.newsapp.adapters.PagerAdapter;
import com.moringaschool.newsapp.models.ArticleNews;
import com.moringaschool.newsapp.ui.CreateAccountActivity;
import com.moringaschool.newsapp.ui.LoginActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        View.OnTouchListener,
        GestureDetector.OnGestureListener,
        View.OnDragListener,
        GestureDetector.OnDoubleTapListener{

    private static final String TAG = "MainActivity";
    private GestureDetector mGestureDetector;

    private EditText mName, mEmail;
    TabLayout tabLayout;
    TabItem mHome,mSports,mScience,mTechnology,mEntertainment,mHealth;
    PagerAdapter pagerAdapter;
    Toolbar mToolbar;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public List<ArticleNews> news;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String news = intent.getStringExtra("business");




        //user info
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    getSupportActionBar().setTitle("Welcome, " + user.getDisplayName() + "!");
                } else {

                }
            }
        };


        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mHome = findViewById(R.id.home);
        mSports = findViewById(R.id.sports);
        mHealth = findViewById(R.id.health);
        mScience = findViewById(R.id.science);
        mTechnology = findViewById(R.id.technology);
        mEntertainment = findViewById(R.id.entertainment);

        ViewPager viewPager = findViewById(R.id.fragment_container);
        tabLayout = findViewById(R.id.include);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), 6);
        viewPager.setAdapter(pagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0
                        || tab.getPosition() == 1
                        || tab.getPosition() == 2
                        || tab.getPosition() == 3
                        || tab.getPosition() == 4
                        || tab.getPosition() == 5);
                {
                    pagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.action_logout){
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        Log.d(TAG, "onSingleTapConfirmed: called");
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        Log.d(TAG, " onDoubleTap: called");
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        Log.d(TAG, "onDoubleTapEvent: called");
        return false;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        Log.d(TAG, "onDown:  called");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
        Log.d(TAG, "onShowPress: called");

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        Log.d(TAG, "onSingleTapUp: called");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        Log.d(TAG, "onScroll: called");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        Log.d(TAG, "onLongPress: called");
        View.DragShadowBuilder builder = new View.DragShadowBuilder(tabLayout);
        tabLayout.startDrag(null,
                builder,
                null,
                0);
        builder.getView().setOnDragListener(this);

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        Log.d(TAG, "onFling: called");
        return false;
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        switch(dragEvent.getAction()) {

            case DragEvent.ACTION_DRAG_STARTED:
                Log.d(TAG, "onDrag: drag started.");

                return true;

            case DragEvent.ACTION_DRAG_ENTERED:
                Log.d(TAG, "onDrag: drag entered.");
                return true;

            case DragEvent.ACTION_DRAG_LOCATION:
                Log.d(TAG, "onDrag: current point: ( " + dragEvent.getX() + " , " + dragEvent.getY() + " )"  );

                return true;

            case DragEvent.ACTION_DRAG_EXITED:
                Log.d(TAG, "onDrag: exited.");
                return true;

            case DragEvent.ACTION_DROP:

                Log.d(TAG, "onDrag: dropped.");

                return true;

            case DragEvent.ACTION_DRAG_ENDED:
                Log.d(TAG, "onDrag: ended.");


                return true;

            // An unknown action type was received.
            default:
                Log.e(TAG,"Unknown action type received by OnStartDragListener.");
                break;

        }

        return false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view == tabLayout){
            mGestureDetector.onTouchEvent(motionEvent);
            return true;
        }
        return true;
    }
}