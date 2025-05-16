package com.moneylover.data.remote;


import com.moneylover.data.model.api.ResponseContentWrapper;
import com.moneylover.data.model.api.ResponseWrapper;
import com.moneylover.data.model.api.request.CreateBillRequest;
import com.moneylover.data.model.api.request.CreateBudgetRequest;
import com.moneylover.data.model.api.request.CreateCategoryRequest;
import com.moneylover.data.model.api.request.CreateEventRequest;
import com.moneylover.data.model.api.request.CreateReminderRequest;
import com.moneylover.data.model.api.request.CreateTagRequest;
import com.moneylover.data.model.api.request.CreateWalletRequest;
import com.moneylover.data.model.api.request.LoginRequest;
import com.moneylover.data.model.api.request.RegisterRequest;
import com.moneylover.data.model.api.request.RequestRegisterRequest;
import com.moneylover.data.model.api.request.RequestResetPasswordRequest;
import com.moneylover.data.model.api.request.RequestUpdateEmailRequest;
import com.moneylover.data.model.api.request.RequestUpdatePasswordRequest;
import com.moneylover.data.model.api.request.ResetPasswordRequest;
import com.moneylover.data.model.api.request.UpdateBillRequest;
import com.moneylover.data.model.api.request.UpdateBudgetRequest;
import com.moneylover.data.model.api.request.UpdateCategoryOrderingRequest;
import com.moneylover.data.model.api.request.UpdateCategoryRequest;
import com.moneylover.data.model.api.request.UpdateEmailRequest;
import com.moneylover.data.model.api.request.UpdateEventCompletedRequest;
import com.moneylover.data.model.api.request.UpdateEventRequest;
import com.moneylover.data.model.api.request.UpdatePasswordRequest;
import com.moneylover.data.model.api.request.UpdateTagRequest;
import com.moneylover.data.model.api.request.UpdateUserRequest;
import com.moneylover.data.model.api.request.UpdateWalletRequest;
import com.moneylover.data.model.api.response.BillDetailStatisticsResponse;
import com.moneylover.data.model.api.response.BillResponse;
import com.moneylover.data.model.api.response.BillStatisticsResponse;
import com.moneylover.data.model.api.response.BudgetResponse;
import com.moneylover.data.model.api.response.CategoryResponse;
import com.moneylover.data.model.api.response.CategoryStatisticsResponse;
import com.moneylover.data.model.api.response.CurrencyResponse;
import com.moneylover.data.model.api.response.EventResponse;
import com.moneylover.data.model.api.response.FileResponse;
import com.moneylover.data.model.api.response.LoginResponse;
import com.moneylover.data.model.api.response.RegisterResponse;
import com.moneylover.data.model.api.response.ReminderResponse;
import com.moneylover.data.model.api.response.RequestRegisterResponse;
import com.moneylover.data.model.api.response.TagResponse;
import com.moneylover.data.model.api.response.TokenResponse;
import com.moneylover.data.model.api.response.UserResponse;
import com.moneylover.data.model.api.response.WalletResponse;

import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {
    @POST("api/login")
    @Headers({"IgnoreAuth: 1"})
    Observable<ResponseWrapper<LoginResponse>> login(@Body LoginRequest request);

    @POST("api/request-register")
    @Headers({"IgnoreAuth: 1"})
    Observable<ResponseWrapper<RequestRegisterResponse>> requestRegister(@Body RequestRegisterRequest request);

    @POST("api/register")
    @Headers({"IgnoreAuth: 1"})
    Observable<ResponseWrapper<RegisterResponse>> register(@Body RegisterRequest request);

    @POST("api/request-reset-password")
    @Headers({"IgnoreAuth: 1"})
    Observable<ResponseWrapper<TokenResponse>> requestForgotPassword(@Body RequestResetPasswordRequest request);

    @PUT("api/reset-password")
    @Headers({"IgnoreAuth: 1"})
    Observable<ResponseWrapper<String>> resetPassword(@Body ResetPasswordRequest request);

    //    -------------------------WALLET API----------------------------
    @POST("api/v1/wallet/client/create")
    Observable<ResponseWrapper<WalletResponse>> createWallet(@Body CreateWalletRequest request);

    @PUT("api/v1/wallet/client/update")
    Observable<ResponseWrapper<WalletResponse>> updateWallet(@Body UpdateWalletRequest request);

    @GET("api/v1/wallet/client/list")
    Observable<ResponseContentWrapper<WalletResponse>> getWalletList(@QueryMap Map<String, Object> queryMap);

    @GET("api/v1/wallet/client/get")
    Observable<ResponseWrapper<WalletResponse>> getWalletById(@Path("id") Long id);

    @DELETE("api/v1/wallet/client/delete/{id}")
    Observable<ResponseWrapper<WalletResponse>> deleteWallet(@Path("id") Long id);

    //    -------------------------USER API----------------------------
    @PUT("api/v1/user/client/update")
    Observable<ResponseWrapper<UserResponse>> updateUser(@Body UpdateUserRequest request);

    @PUT("api/v1/user/client/update-password")
    Observable<ResponseWrapper<UserResponse>> updatePassword(@Body UpdatePasswordRequest request);

    @PUT("api/v1/user/client/update-email")
    Observable<ResponseWrapper<UserResponse>> updateEmail(@Body UpdateEmailRequest request);

    @PUT("api/v1/user/client/request-update-password")
    Observable<ResponseWrapper<UserResponse>> requestUpdatePassword(@Body RequestUpdatePasswordRequest request);

    @PUT("api/v1/user/client/request-update-email")
    Observable<ResponseWrapper<UserResponse>> requestUpdateEmail(@Body RequestUpdateEmailRequest request);

    @GET("api/v1/user/client/get")
    Observable<ResponseWrapper<UserResponse>> getUserInfo();

    //    --------------------------TAG API---------------------------
    @GET("api/v1/tag/client/list")
    Observable<ResponseContentWrapper<TagResponse>> getTagList(@QueryMap Map<String, Object> queryMap);

    @GET("api/v1/tag/client/get")
    Observable<ResponseWrapper<TagResponse>> getTagById(@Path("id") Long id);

    @POST("api/v1/tag/client/create")
    Observable<ResponseWrapper<TagResponse>> createTag(@Body CreateTagRequest request);

    @PUT("api/v1/tag/client/update")
    Observable<ResponseWrapper<TagResponse>> updateTag(@Body UpdateTagRequest request);

    @DELETE("api/v1/tag/client/delete/{id}")
    Observable<ResponseWrapper<TagResponse>> deleteTag(@Path("id") Long id);

    //    -----------------------------REMINDER API-------------------------------
    @GET("api/v1/reminder/client/list")
    Observable<ResponseContentWrapper<ReminderResponse>> getReminderList(@QueryMap Map<String, Object> queryMap);

    @GET("api/v1/reminder/client/get")
    Observable<ResponseWrapper<ReminderResponse>> getReminderById(@Path("id") Long id);

    @POST("api/v1/reminder/client/create")
    Observable<ResponseWrapper<ReminderResponse>> createReminder(@Body CreateReminderRequest request);

    @DELETE("api/v1/reminder/client/delete")
    Observable<ResponseWrapper<ReminderResponse>> deleteReminder(@Path("id") Long id);

    //  ---------------------------------EVENT API----------------------------------
    @GET("api/v1/event/client/list")
    Observable<ResponseContentWrapper<EventResponse>> getEventList(@QueryMap Map<String, Object> queryMap);

    @GET("api/v1/event/client/get")
    Observable<ResponseWrapper<EventResponse>> getEventById(@Path("id") Long id);

    @POST("api/v1/event/client/create")
    Observable<ResponseWrapper<EventResponse>> createEvent(@Body CreateEventRequest request);

    @PUT("api/v1/event/client/update")
    Observable<ResponseWrapper<EventResponse>> updateEvent(@Body UpdateEventRequest request);

    @PUT("api/v1/event/client/update-completed")
    Observable<ResponseWrapper<EventResponse>> updateEventCompleted(@Body UpdateEventCompletedRequest request);

    @DELETE("api/v1/event/client/delete")
    Observable<ResponseWrapper<EventResponse>> deleteEvent(@Path("id") Long id);

    //  ---------------------------CURRENCY API-------------------------------
    @GET("api/v1/currency/client/list")
    Observable<ResponseContentWrapper<CurrencyResponse>> getCurrencyByCode(@Query("code") String code);

    @GET("api/v1/currency/client/list")
    Observable<ResponseContentWrapper<CurrencyResponse>> getCurrencyList();

//    -----------------------------CATEGORY API---------------------------------
    @GET("api/v1/category/client/list")
    Observable<ResponseContentWrapper<CategoryResponse>> getCategoryList(@QueryMap Map<String, Object> queryMap);

    @GET("api/v1/category/client/get")
    Observable<ResponseWrapper<CategoryResponse>> getCategoryById(@Path("id") Long id);

    @POST("api/v1/category/client/create")
    Observable<ResponseWrapper<CategoryResponse>> createCategory(@Body CreateCategoryRequest request);

    @PUT("api/v1/category/client/update")
    Observable<ResponseWrapper<CategoryResponse>> updateCategory(@Body UpdateCategoryRequest request);

    @PUT("api/v1/category/client/update-ordering")
    Observable<ResponseWrapper<CategoryResponse>> updateCategoryOrdering(@Body UpdateCategoryOrderingRequest request);

    @DELETE("api/v1/category/client/delete")
    Observable<ResponseWrapper<CategoryResponse>> deleteCategory(@Path("id") Long id);

//    ------------------------------BUDGET API---------------------------------
    @GET("api/v1/budget/client/list")
    Observable<ResponseContentWrapper<BudgetResponse>> getBudgetList(@QueryMap Map<String, Object> queryMap);

    @GET("api/v1/budget/client/get")
    Observable<ResponseWrapper<BudgetResponse>> getBudgetById(@Path("id") Long id);

    @POST("api/v1/budget/client/create")
    Observable<ResponseWrapper<BudgetResponse>> createBudget(@Body CreateBudgetRequest request);

    @PUT("api/v1/budget/client/update")
    Observable<ResponseWrapper<BudgetResponse>> updateBudget(@Body UpdateBudgetRequest request);

    @DELETE("api/v1/budget/client/delete")
    Observable<ResponseWrapper<BudgetResponse>> deleteBudget(@Path("id") Long id);

//    ----------------------------BILL API-----------------------------
    @GET("api/v1/bill/client/list")
    Observable<ResponseContentWrapper<BillResponse>> getBillList(@QueryMap Map<String, Object> queryMap);

    @GET("api/v1/bill/client/get")
    Observable<ResponseWrapper<BillResponse>> getBillById(@Path("id") Long id);

    @GET("api/v1/bill/client/statistics")
    Observable<ResponseWrapper<BillStatisticsResponse>> getBillStatistics(@QueryMap Map<String, Object> queryMap);

    @POST("api/v1/bill/client/create")
    Observable<ResponseWrapper<BillResponse>> createBill(@Body CreateBillRequest request);

    @PUT("api/v1/bill/client/update")
    Observable<ResponseWrapper<BillResponse>> updateBill(@Body UpdateBillRequest request);

    @DELETE("api/v1/bill/client/delete")
    Observable<ResponseWrapper<BillResponse>> deleteBill(@Path("id") Long id);


//    ---------------------------FILE API----------------------------------
    @POST("api/v1/file/client/upload")
    Observable<ResponseWrapper<FileResponse>> uploadFile(@QueryMap Map<String, Object> queryMap);

    @GET("api/v1/file/client/list")
    Observable<ResponseContentWrapper<FileResponse>> getFileList(@QueryMap Map<String, Object> queryMap);

//    ----------------------------STATISTICS API-----------------------------
    @GET("api/v1/statistics/bill-detail")
    Observable<ResponseWrapper<BillDetailStatisticsResponse>> getDetailBillStatistics(@QueryMap Map<String, Object> queryMap);

    @GET("api/v1/statistics/category-detail")
    Observable<ResponseContentWrapper<CategoryStatisticsResponse>> getCategoryStatistics(@QueryMap Map<String, Object> queryMap);

//    --------------------------LOGOUT API----------------------------------
    @DELETE("api/logout")
    Observable<ResponseWrapper<Void>> logout();
}
