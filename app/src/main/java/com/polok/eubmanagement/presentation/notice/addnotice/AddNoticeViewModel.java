package com.polok.eubmanagement.presentation.notice.addnotice;

import static com.polok.eubmanagement.util.Extension.showErrorOnUi;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.firebase.FirebaseDataRef;
import com.polok.eubmanagement.presentation.notice.model.NoticeData;
import com.polok.eubmanagement.util.Extension;

public class AddNoticeViewModel extends BaseViewModel {
    private final MutableLiveData<Boolean> uploadNoticeResponse = new MutableLiveData<>();
    public LiveData<Boolean> getUploadNoticeResponse() {return uploadNoticeResponse;}

    public void validateNoticeInputAndUploadToFirebase(
            EditText noticeTitleEt, EditText noticeDetailsEt
    ) {
        if (noticeTitleEt.getText().toString().isEmpty()) {
            showErrorOnUi(noticeTitleEt, "Enter Notice Title");
            return;
        }
        if (noticeDetailsEt.getText().toString().isEmpty()) {
            showErrorOnUi(noticeDetailsEt, "Enter Notice Details");
            return;
        }
        uploadNoticeTOFirebase(
                noticeTitleEt.getText().toString(),
                noticeDetailsEt.getText().toString(),
                Extension.getCurrentDate()
        );
    }

    private void uploadNoticeTOFirebase(String title, String details, String date) {
        fireLoadingEvent(true);
        DatabaseReference pushNoticeRef = FirebaseDataRef.provideNoticeRef().push();
        pushNoticeRef.setValue(
                new NoticeData(title,details,date,pushNoticeRef.getKey().toString())
        ).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()) fireMessageEvent("Notice Added Successfully");
                else fireMessageEvent(task.getException().getLocalizedMessage());
                fireLoadingEvent(false);
                uploadNoticeResponse.postValue(true);
            }
        });
    }
}
