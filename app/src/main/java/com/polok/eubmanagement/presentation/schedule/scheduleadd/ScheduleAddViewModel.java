package com.polok.eubmanagement.presentation.schedule.scheduleadd;

import static com.polok.eubmanagement.util.Extension.showErrorOnUi;
import android.widget.EditText;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.firebase.FirebaseDataRef;
import com.polok.eubmanagement.model.ScheduleData;

public class ScheduleAddViewModel extends BaseViewModel {
    String day, dayZero;

    public void validateScheduleInputAndUploadToFirebase(
            EditText courseTitleEt, EditText courseCodeEt, EditText lecturerNameEt, EditText startEndTimeEt, EditText roomNoEt
    ) {
        if (day == null || day.isEmpty() || day.equals(dayZero)) {
            fireMessageEvent(dayZero);
            return;
        }
        if (courseTitleEt.getText().toString().isEmpty()) {
            showErrorOnUi(courseTitleEt, "Enter Course Title");
            return;
        }
        if (courseCodeEt.getText().toString().isEmpty()) {
            showErrorOnUi(courseCodeEt, "Enter Course Code");
            return;
        }
        if (lecturerNameEt.getText().toString().isEmpty()) {
            showErrorOnUi(lecturerNameEt, "Enter Lecturer Name");
            return;
        }
        if (startEndTimeEt.getText().toString().isEmpty()) {
            showErrorOnUi(startEndTimeEt, "Enter Start to End Time");
            return;
        }
        if (roomNoEt.getText().toString().isEmpty()) {
            showErrorOnUi(roomNoEt, "Enter Room Number");
            return;
        }
        uploadScheduleTOFirebase(
                day, courseTitleEt.getText().toString(),
                courseCodeEt.getText().toString(),
                lecturerNameEt.getText().toString(),
                startEndTimeEt.getText().toString(),
                roomNoEt.getText().toString()
        );
    }

    private void uploadScheduleTOFirebase(
            String day, String courseTitle, String courseCode, String lecturerName, String startEndTime, String roomNo
    ) {
        fireLoadingEvent(true);
        DatabaseReference pushNoticeRef = FirebaseDataRef.provideScheduleRef().push();
        pushNoticeRef.setValue(
                new ScheduleData(day,courseTitle,courseCode,lecturerName,startEndTime,roomNo)
        ).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()) {
                    fireMessageEvent("Class Schedule Added Successfully");
                    fireNavigateEvent(0,null);
                } else fireMessageEvent(task.getException().getLocalizedMessage());
                fireLoadingEvent(false);
            }
        });
    }
}
