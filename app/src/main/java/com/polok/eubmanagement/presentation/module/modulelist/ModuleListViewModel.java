package com.polok.eubmanagement.presentation.module.modulelist;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.firebase.FirebaseDataRef;
import com.polok.eubmanagement.model.ModuleData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModuleListViewModel extends BaseViewModel {
    private final MutableLiveData<List<ModuleData>> moduleLiveData = new MutableLiveData<>();
    public LiveData<List<ModuleData>> getModuleLiveData() {return moduleLiveData;}

    public void fetchModuleListFromFirebase() {
        fireLoadingEvent(true);
        FirebaseDataRef.provideModuleRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    List<ModuleData> moduleList = new ArrayList<>();
                    for (DataSnapshot moduleSnapshot : snapshot.getChildren()) {
                        if (moduleSnapshot.exists()) moduleList.add(moduleSnapshot.getValue(ModuleData.class));
                    }
                    try {
                        Collections.reverse(moduleList);
                    } catch (Exception ignored) {} finally {
                        moduleLiveData.postValue(moduleList);
                    }
                    fireLoadingEvent(false);
                } else fireLoadingEvent(false);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                fireMessageEvent(error.getMessage());
                fireLoadingEvent(false);
            }
        });
    }
}
