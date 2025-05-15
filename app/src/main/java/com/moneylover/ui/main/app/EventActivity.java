package com.moneylover.ui.main.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.data.model.api.response.EventResponse;
import com.moneylover.databinding.ActivityEventBinding;
import com.moneylover.di.component.ActivityComponent;
import com.moneylover.ui.base.activity.BaseActivity;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.ui.main.MainCallback;

import java.util.List;

import timber.log.Timber;

public class EventActivity extends BaseActivity<ActivityEventBinding, EventViewModel> {

    private EventResponse selectedEvent;
    private EventAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_event;
    }

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding.setA(this);
        viewBinding.setVm(viewModel);

//        setupTabLayout();
        setupEventList();
    }

//    public void setupTabLayout() {
//        List<String> tabTitles = Arrays.asList("ĐANG ÁP DỤNG", "ĐÃ KẾT THÚC");
//        List<String> typeKeys = Arrays.asList("EXPENDITURE", "INCOME");
//        List<Class<? extends Fragment>> fragmentClasses = Arrays.asList(
//                EventListFragment.class,
//                EventListFragment.class
//        );
//
//        ViewPager2Adapter adapter = new ViewPager2Adapter(this, fragmentClasses);
//
//        for (int i = 0; i < typeKeys.size(); i++) {
//            Bundle args = new Bundle();
//            args.putString("type", typeKeys.get(i));
//            args.putSerializable("selected_event", selectedEvent);
//            adapter.setFragmentArguments(i, args);
//        }
//
//        viewBinding.viewPager.setAdapter(adapter);
//        viewBinding.viewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
//        viewBinding.viewPager.setOffscreenPageLimit(fragmentClasses.size());
//
//        new TabLayoutMediator(viewBinding.tabLayout, viewBinding.viewPager, (tab, position) -> {
//            View customView = LayoutInflater.from(this).inflate(R.layout.item_tab_group_type, viewBinding.tabLayout, false);
//            ((TextView) customView.findViewById(R.id.tabText)).setText(tabTitles.get(position));
//            tab.setCustomView(customView);
//        }).attach();
//
//
//        viewBinding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//        });
//
//        viewBinding.viewPager.setCurrentItem(0, false);
//    }

    public void setupEventList() {
        viewModel.doGetEventList(new MainCallback<List<EventResponse>>() {
            @Override
            public void doError(Throwable error) {
                Timber.tag("EventActivity").e(error, "Error fetching event list");
            }

            @Override
            public void doSuccess() {

            }

            @Override
            public void doSuccess(List<EventResponse> eventResponses) {
                adapter = new EventAdapter(EventActivity.this, eventResponses, new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent resultIntent = new Intent();
                        selectedEvent = eventResponses.get(position);
                        resultIntent.putExtra("selected_event", selectedEvent);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }

                    @Override
                    public void onItemDelete(int position) {

                    }
                });
                viewBinding.rcvEventList.setLayoutManager(new LinearLayoutManager(EventActivity.this, LinearLayoutManager.VERTICAL, false));
                viewBinding.rcvEventList.setAdapter(adapter);
            }

            @Override
            public void doFail() {

            }
        });
    }
}