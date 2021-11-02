package com.example.createtaskfragment.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.createtaskfragment.MainActivity;
import com.example.createtaskfragment.databinding.ActivityPhoneAuthBinding;
import com.example.createtaskfragment.ui.boarding.BoardActivity;
import com.example.createtaskfragment.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneAuthActivity extends AppCompatActivity {
    private static final String TAG = "PhoneAuthActivity";
    private ActivityPhoneAuthBinding binding;
    boolean isShow = false;
    // [НАЧАТЬ declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        checkShow();
        onClickBTNnumber();

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        // Initialize phone auth callbacks
        // [START phone_auth_callbacks]
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            //по завершении проверки
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // Этот обратный вызов будет вызываться в двух ситуациях:
                // 1 - Мгновенная проверка. В некоторых случаях номер телефона может быть мгновенно
                //     проверено без необходимости отправлять или вводить проверочный код.
                // 2 - Авто-поиск. На некоторых устройствах сервисы Google Play могут автоматически
                //     обнаружить входящее проверочное SMS и выполнить проверку без
                //     действие пользователя.
                Log.d(TAG, "onVerificationCompleted:" + credential);

                signInWithPhoneAuthCredential(credential);
            }

            @Override
            //при неудачной проверке
            public void onVerificationFailed(FirebaseException e) {
                // Этот обратный вызов вызывается при выполнении недопустимого запроса на проверку,
                // например, если формат номера телефона недопустим.
                Log.w(TAG, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Неверный запрос
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // Превышена квота SMS для проекта
                }

                // Показать сообщение и обновить пользовательский интерфейс
            }

            @Override
            //при отправке кода
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // Код подтверждения SMS был отправлен на указанный номер телефона, мы
                // теперь нужно попросить пользователя ввести код, а затем создать учетные данные
                // путем объединения кода с идентификатором подтверждения.
                Log.d(TAG, "onCodeSent:" + verificationId);

                // Сохраните идентификатор подтверждения и токен повторной отправки,
                // чтобы мы могли использовать их позже
                mVerificationId = verificationId;
                mResendToken = token;
            }
        };
        // [END phone_auth_callbacks]
    }
    private void checkShow() {
        SharedPreferences sharedPreferences = PhoneAuthActivity.this.getSharedPreferences(Constants.PHONE_FILE, Context.MODE_PRIVATE);
        isShow = sharedPreferences.getBoolean(Constants.IS_SHOW, false);
        if (isShow){
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    private void onClickBTNnumber() {
        binding.btnActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = binding.etCode.getText().toString();
                if (phoneNumber.isEmpty()) {
                    Toast.makeText(PhoneAuthActivity.this, "вы не ввели code телефона", Toast.LENGTH_SHORT).show();
                    binding.etCode.setError("error");
                    return;
                }
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, phoneNumber);
                signInWithPhoneAuthCredential(credential);
            }
        });
        binding.btnActivityPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = binding.etNumber.getText().toString();
                if (phoneNumber.isEmpty()) {
                    Toast.makeText(PhoneAuthActivity.this, "вы не ввели number телефона", Toast.LENGTH_SHORT).show();
                    binding.etNumber.setError("error");
                    return;
                }
                startPhoneNumberVerification(binding.etNumber.getText().toString());
            }
        });
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Убедитесь, что пользователь вошел в систему (не ноль),
        // и обновите пользовательский интерфейс соответствующим образом.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    // [END on_start_check_user]

    //начать проверку номера телефона
    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Номер телефона для подтверждения
                        .setTimeout(60L, TimeUnit.SECONDS) // Тайм-аут и единица измерения
                        .setActivity(this)                 // Активность (для привязки обратного вызова)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks // Обратные вызовы при изменении состояния проверки
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        // [END start_phone_auth]
    }

    //подтвердить номер телефона с кодом
    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code] проверить с помощью кода
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
    }

    // [START resend_verification]Отправить подтверждение
    //Отправить заново код подтверждения
    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Номер телефона для подтверждения
                        .setTimeout(60L, TimeUnit.SECONDS) // Тайм-аут и единица измерения
                        .setActivity(this)                 // Активность (для привязки обратного вызова)
                        .setCallbacks(mCallbacks)          // Обратные вызовы при изменении состояния проверки
                        .setForceResendingToken(token)     // Принудительная повторная отправка токена из обратных вызовов
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    // [END resend_verification]Отправить подтверждение

    // [START sign_in_with_phone]войти с телефона
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Успешный вход, обновите пользовательский интерфейс, указав информацию о вошедшем пользователе.
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            Intent intent = new Intent(PhoneAuthActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            // Обновить интерфейс
                        } else {
                            // Не удалось войти в систему, отобразите сообщение и обновите пользовательский интерфейс
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // Введенный проверочный код недействителен
                            }
                        }
                    }
                });
    }
    // [END войти с телефона]

    private void updateUI(FirebaseUser user) {

    }
}