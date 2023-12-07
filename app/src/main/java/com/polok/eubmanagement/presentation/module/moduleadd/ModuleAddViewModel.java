package com.polok.eubmanagement.presentation.module.moduleadd;

import static com.polok.eubmanagement.util.Extension.showErrorOnUi;
import android.widget.EditText;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.firebase.FirebaseDataRef;
import com.polok.eubmanagement.model.ModuleData;

public class ModuleAddViewModel extends BaseViewModel {

    public void validateModuleInputsAndUploadToFirebase(EditText moduleTitleEt, EditText moduleLinkEt, String createdAt) {
        if (moduleTitleEt.getText().toString().isEmpty()) {
            showErrorOnUi(moduleTitleEt, "Enter Course Module Title");
            return;
        }
        if (moduleLinkEt.getText().toString().isEmpty()) {
            showErrorOnUi(moduleLinkEt, "Enter Course Module Link");
            return;
        }
        uploadModuleTOFirebase(
                moduleTitleEt.getText().toString(), moduleLinkEt.getText().toString(), createdAt
        );
    }

    private void uploadModuleTOFirebase(String moduleTitle, String moduleLink, String createdAt) {
        fireLoadingEvent(true);
        DatabaseReference pushNoticeRef = FirebaseDataRef.provideModuleRef().push();
        pushNoticeRef.setValue(new ModuleData(moduleTitle, moduleLink, createdAt, pushNoticeRef.getKey())
        ).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()) {
                    fireMessageEvent("Course Module Added Successfully");
                    fireNavigateEvent(0, null);
                } else fireMessageEvent(task.getException().getLocalizedMessage());
                fireLoadingEvent(false);
            }
        });
    }
}
