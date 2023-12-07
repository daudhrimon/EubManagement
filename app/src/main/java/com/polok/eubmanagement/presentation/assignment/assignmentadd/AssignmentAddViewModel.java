package com.polok.eubmanagement.presentation.assignment.assignmentadd;

import static com.polok.eubmanagement.util.Extension.showErrorOnUi;
import android.widget.EditText;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.firebase.FirebaseDataRef;
import com.polok.eubmanagement.presentation.assignment.modal.AssignmentData;
import com.polok.eubmanagement.util.Extension;

public class AssignmentAddViewModel extends BaseViewModel {

    public void validateAssignmentInputAndUploadToFirebase(
            EditText assignmentTitleEt, EditText assignmentDetailsEt
    ) {
        if (assignmentTitleEt.getText().toString().isEmpty()) {
            showErrorOnUi(assignmentTitleEt, "Enter Assignment Title");
            return;
        }
        if (assignmentDetailsEt.getText().toString().isEmpty()) {
            showErrorOnUi(assignmentDetailsEt, "Enter Assignment Details");
            return;
        }
        uploadAssignmentTOFirebase(
                assignmentTitleEt.getText().toString(),
                assignmentDetailsEt.getText().toString(),
                Extension.getCurrentDate()
        );
    }

    private void uploadAssignmentTOFirebase(String title, String details, String date) {
        fireLoadingEvent(true);
        DatabaseReference pushAssignmentRef = FirebaseDataRef.provideAssignmentRef().push();
        pushAssignmentRef.setValue(
                new AssignmentData(title,details,date,pushAssignmentRef.getKey().toString())
        ).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()) {
                    fireMessageEvent("Assignment Added Successfully");
                    fireNavigateEvent(0,null);
                } else fireMessageEvent(task.getException().getLocalizedMessage());
                fireLoadingEvent(false);
            }
        });
    }
}
