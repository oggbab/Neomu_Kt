package com.neomu.neomu.app.index.util;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class JoinValidation {
    public final String MSG_EMPTY_EMAIL = "이메일을 입력해주세요";
    public final String MSG_EMPTY_NICKNAME = "닉네임을 입력해주세요";
    public final String MSG_EMPTY_PW = "비밀번호를 입력해주세요";
    public final String MSG_EMPTY_PW_MATCH = "비밀번호를 입력해주세요";
    public final String MSG_INVALID_EMAIL ="유효하지 않은 이메일 형식입니다";
    public final String MSG_INVALID_NICKNAME = "아이디를 6자 이상 입력해주세요";
    public final String MSG_INVALID_PW = "비밀번호를 6자 이상 입력해주세요";
    public final String MSG_INVALID_PW_MATCH = "동일한 비밀번호를 입력해주세요";

    public ValidationListener validationListener;

    //유효성 검사를 위한 콜백 리스너
    public interface ValidationListener {
        void onInvalid(View view, String msg);
        void onSuccess();
    }


    //회원가입시
    public  void isValidation(EditText email, EditText nickName, EditText pw, EditText pw2, ValidationListener listener) {
        validationListener = listener;

        //텍스트 미입력시
        if(email != null && TextUtils.isEmpty(email.getText().toString())) {
            validationListener.onInvalid(email, MSG_EMPTY_EMAIL);
            return;
        } else if(nickName != null && TextUtils.isEmpty(nickName.getText().toString())) {
            validationListener.onInvalid(nickName, MSG_EMPTY_NICKNAME);
            return;
        } else if(pw != null && TextUtils.isEmpty(pw.getText().toString())) {
            validationListener.onInvalid(pw, MSG_EMPTY_PW);
            return;
        } else if(pw2 != null && TextUtils.isEmpty(pw2.getText().toString())) {
            validationListener.onInvalid(pw2, MSG_EMPTY_PW_MATCH);
            return;
        }

        //todo 연속숫자금지
        //이메일 유효성검사
        if (email != null && !android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            validationListener.onInvalid(email, MSG_INVALID_EMAIL);
            return;
        }else if (nickName != null && nickName.length()<6) {
            validationListener.onInvalid(nickName, MSG_INVALID_NICKNAME);
            return;
        }else if (pw != null && pw.length()<6) {
            validationListener.onInvalid(pw, MSG_INVALID_PW);
            return;
        }else if (pw != null && pw2 != null && !pw.getText().toString().equals(pw2.getText().toString())) {
            validationListener.onInvalid(pw2, MSG_INVALID_PW_MATCH);
            return;
        }

        //성공시 호출
        validationListener.onSuccess();

    }
}
