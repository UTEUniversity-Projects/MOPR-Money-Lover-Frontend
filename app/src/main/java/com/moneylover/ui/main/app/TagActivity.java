package com.moneylover.ui.main.app;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.moneylover.R;
import com.moneylover.data.model.api.ResponseWrapper;
import com.moneylover.data.model.api.request.CreateTagRequest;
import com.moneylover.data.model.api.request.UpdateTagRequest;
import com.moneylover.data.model.api.response.TagResponse;
import com.moneylover.databinding.ActivityTagBinding;
import com.moneylover.databinding.DialogAddTagBinding;
import com.moneylover.databinding.DialogEditTagBinding;
import com.moneylover.di.component.ActivityComponent;
import com.moneylover.ui.base.activity.BaseActivity;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.ui.main.MainCallback;

import java.util.ArrayList;
import java.util.List;

public class TagActivity extends BaseActivity<ActivityTagBinding, TagViewModel> {
    private TagAdapter adapter;
    private ArrayList<TagResponse> preSelectedTags = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_tag;
    }

    @Override
    public int getBindingVariable() {
        return com.moneylover.BR.vm;
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

        ArrayList<TagResponse> incomingTags = (ArrayList<TagResponse>) getIntent().getSerializableExtra("selected_tags");
        if (incomingTags != null) {
            preSelectedTags = incomingTags;
        }

        setupTagList();
        setupAddTag();

        viewBinding.tvSave.setOnClickListener(v -> {
            if (adapter != null) {
                ArrayList<TagResponse> selectedTags = new ArrayList<>(adapter.getSelectedTags());
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selected_tags", selectedTags);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    private void setupTagList() {
        viewModel.doGetTagList(new MainCallback<List<TagResponse>>() {
            @Override
            public void doError(Throwable error) {}

            @Override
            public void doSuccess() {}

            @Override
            public void doSuccess(List<TagResponse> tagResponses) {
                adapter = new TagAdapter(
                        TagActivity.this,
                        tagResponses,
                        new OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                showEditDialog(tagResponses.get(position));
                            }

                            @Override
                            public void onItemDelete(int position) {
                                TagResponse tag = tagResponses.get(position);
                                viewModel.doDeleteTag(new MainCallback<ResponseWrapper<?>>() {
                                    @Override public void doError(Throwable error) {}
                                    @Override public void doSuccess() {}
                                    @Override
                                    public void doSuccess(ResponseWrapper<?> response) {
                                        viewModel.showSuccessMessage("Xóa thành công");
                                        setupTagList();
                                    }

                                    @Override public void doFail() {}
                                }, tag.getId());
                            }
                        },
                        hasSelection -> viewBinding.tvSave.setVisibility(hasSelection ? View.VISIBLE : View.GONE),
                        preSelectedTags
                );

                viewBinding.rcvTagList.setLayoutManager(
                        new LinearLayoutManager(TagActivity.this, LinearLayoutManager.VERTICAL, false));
                viewBinding.rcvTagList.setAdapter(adapter);
            }

            @Override public void doFail() {}
        });
    }

    private void showEditDialog(TagResponse tagResponse) {
        AlertDialog.Builder builder = new AlertDialog.Builder(TagActivity.this);
        DialogEditTagBinding dialogBinding = DialogEditTagBinding.inflate(getLayoutInflater());
        builder.setView(dialogBinding.getRoot());

        AlertDialog dialog = builder.create();
        dialogBinding.edtTagName.setText(tagResponse.getName());

        dialogBinding.btnEdit.setOnClickListener(v -> {
            String tagName = dialogBinding.edtTagName.getText().toString().trim();
            if (!tagName.isEmpty()) {
                UpdateTagRequest request = UpdateTagRequest.builder()
                        .id(tagResponse.getId())
                        .name(tagName)
                        .build();

                viewModel.doEditTag(new MainCallback<ResponseWrapper<?>>() {
                    @Override public void doError(Throwable error) {}
                    @Override public void doSuccess() {}
                    @Override
                    public void doSuccess(ResponseWrapper<?> response) {
                        viewModel.showSuccessMessage("Cập nhật thành công");
                        setupTagList();
                        dialog.dismiss();
                    }

                    @Override public void doFail() {}
                }, request);
            } else {
                dialogBinding.edtTagName.setError("Vui lòng nhập tên tag");
            }
        });

        dialogBinding.btnCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void setupAddTag() {
        viewBinding.btnAddTag.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            DialogAddTagBinding dialogBinding = DialogAddTagBinding.inflate(getLayoutInflater());
            builder.setView(dialogBinding.getRoot());

            AlertDialog dialog = builder.create();

            dialogBinding.btnAdd.setOnClickListener(_v -> {
                String tagName = dialogBinding.edtTagName.getText().toString().trim();
                if (!tagName.isEmpty()) {
                    CreateTagRequest request = CreateTagRequest.builder().name(tagName).build();
                    viewModel.doAddTag(new MainCallback<ResponseWrapper<?>>() {
                        @Override public void doError(Throwable error) {}
                        @Override public void doSuccess() {}
                        @Override
                        public void doSuccess(ResponseWrapper<?> responseWrapper) {
                            viewModel.showSuccessMessage("Thêm thẻ thành công");
                            setupTagList();
                            dialog.dismiss();
                        }

                        @Override public void doFail() {}
                    }, request);
                } else {
                    dialogBinding.edtTagName.setError("Vui lòng nhập tên tag");
                }
            });

            dialogBinding.btnCancel.setOnClickListener(_v -> dialog.dismiss());
            dialog.show();
        });
    }
}
