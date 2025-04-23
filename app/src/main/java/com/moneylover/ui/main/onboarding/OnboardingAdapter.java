package com.moneylover.ui.main.onboarding;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moneylover.data.model.Onboarding;
import com.moneylover.databinding.ItemOnboardingBinding;

import java.util.List;

public class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder> {

    private final List<Onboarding> onboardingList;

    public OnboardingAdapter(List<Onboarding> onboardingList) {
        this.onboardingList = onboardingList;
    }

    @NonNull
    @Override
    public OnboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemOnboardingBinding binding = ItemOnboardingBinding.inflate(inflater, parent, false);
        return new OnboardingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingViewHolder holder, int position) {
        holder.bind(onboardingList.get(position));
    }

    @Override
    public int getItemCount() {
        return onboardingList.size();
    }

    static class OnboardingViewHolder extends RecyclerView.ViewHolder {
        private final ItemOnboardingBinding binding;

        public OnboardingViewHolder(ItemOnboardingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Onboarding item) {
            binding.imageOnboarding.setImageResource(item.getImage());
            binding.tvTitle.setText(item.getTitle());
            binding.txtDesc.setText(item.getDescription());
        }
    }
}
