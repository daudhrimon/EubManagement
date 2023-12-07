package com.polok.eubmanagement.presentation.notice.noticeadd;

import static com.polok.eubmanagement.util.Extension.showErrorOnUi;
import android.widget.EditText;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.base.model.OnNavigate;
import com.polok.eubmanagement.firebase.FirebaseDataRef;
import com.polok.eubmanagement.model.NoticeData;
import com.polok.eubmanagement.util.Extension;

public class NoticeAddViewModel extends BaseViewModel {

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
                if (task.isComplete()) {
                    fireMessageEvent("Notice Added Successfully");
                    fireNavigateEvent(new OnNavigate(1));
                } else fireMessageEvent(task.getException().getLocalizedMessage());
                fireLoadingEvent(false);
            }
        });
    }
}
