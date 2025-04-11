package com.moneylover.data.remote;


import com.moneylover.data.model.api.ResponseWrapper;
import com.moneylover.data.model.api.request.ForgotPasswordOtpVerificationRequest;
import com.moneylover.data.model.api.request.LoginRequest;
import com.moneylover.data.model.api.request.RegisterRequest;
import com.moneylover.data.model.api.request.RequestForgotPasswordRequest;
import com.moneylover.data.model.api.request.RequestRegisterRequest;
import com.moneylover.data.model.api.response.ForgotPasswordOtpVerificationResponse;
import com.moneylover.data.model.api.response.LoginResponse;
import com.moneylover.data.model.api.response.RegisterResponse;
import com.moneylover.data.model.api.response.RequestForgotPasswordResponse;
import com.moneylover.data.model.api.response.RequestRegisterResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

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
    Observable<ResponseWrapper<RequestForgotPasswordResponse>> requestForgotPassword(@Body RequestForgotPasswordRequest request);

    @PUT("api/reset-password")
    @Headers({"IgnoreAuth: 1"})
    Observable<ResponseWrapper<ForgotPasswordOtpVerificationResponse>> resetPassword(@Body ForgotPasswordOtpVerificationRequest request);
}
