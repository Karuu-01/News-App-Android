package com.moringaschool.newsapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.moringaschool.newsapp.R;
import com.moringaschool.newsapp.adapters.NewsPagerAdapter;
import com.moringaschool.newsapp.models.ArticleNews;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetailActivity extends AppCompatActivity {

    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    private NewsPagerAdapter adapterViewPager;
    List<ArticleNews> mNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);

        mNews = Parcels.unwrap(getIntent().getParcelableExtra("news"));
        int startingPosition = getIntent().getIntExtra("position", 0);

        adapterViewPager = new NewsPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, mNews);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
    }
}