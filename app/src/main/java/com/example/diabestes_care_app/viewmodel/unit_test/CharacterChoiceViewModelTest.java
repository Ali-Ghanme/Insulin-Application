//package com.example.diabestes_care_app.viewmodel.unit_test;
//
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.*;
//
//import android.app.Application;
//import android.content.SharedPreferences;
//
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
//import androidx.lifecycle.Observer;
//
//import com.google.firebase.firestore.util.Assert;
//
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//public class CharacterChoiceViewModelTest {
//
//    @Rule
//    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();
//
//    @Mock
//    Application mockApplication;
//
//    @Mock
//    SharedPreferences mockPrefsP;
//
//    @Mock
//    SharedPreferences mockPrefsD;
//
//    @Mock
//    SharedPreferences.Editor mockEditor;
//
//    @Mock
//    Observer<String> observer;
//
//    CharacterChoiceViewModel viewModel;
//
//    @Before
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//        // إعداد ViewModel
//        viewModel = new CharacterChoiceViewModel(mockApplication);
//        viewModel.getNavigationTarget().observeForever(observer);
//    }
//
//    @Test
//    public void checkSavedUser_patientRemembered_navigatesToPatient() {
//        // تحضير مخرجات SharedPreferences المقلدة
//        when(mockApplication.getSharedPreferences("checkbox_P", Application.MODE_PRIVATE)).thenReturn(mockPrefsP);
//        when(mockPrefsP.getString("remember_P", "")).thenReturn("true");
//
//        viewModel.checkSavedUser();
//
//        verify(observer).onChanged("patient");
//    }
//
//    @Test
//    public void checkSavedUser_doctorRemembered_navigatesToDoctor() {
//        when(mockApplication.getSharedPreferences("checkbox_P", Application.MODE_PRIVATE)).thenReturn(mockPrefsP);
//        when(mockPrefsP.getString("remember_P", "")).thenReturn("");
//
//        when(mockApplication.getSharedPreferences("checkbox_D", Application.MODE_PRIVATE)).thenReturn(mockPrefsD);
//        when(mockPrefsD.getString("remember_D", "")).thenReturn("true");
//
//        viewModel.checkSavedUser();
//
//        verify(observer).onChanged("doctor");
//    }
//
//    @Test
//    public void checkSavedUser_noneRemembered_noNavigation() {
//        when(mockApplication.getSharedPreferences("checkbox_P", Application.MODE_PRIVATE)).thenReturn(mockPrefsP);
//        when(mockPrefsP.getString("remember_P", "")).thenReturn("");
//
//        when(mockApplication.getSharedPreferences("checkbox_D", Application.MODE_PRIVATE)).thenReturn(mockPrefsD);
//        when(mockPrefsD.getString("remember_D", "")).thenReturn("");
//
//        viewModel.checkSavedUser();
//
//        verify(observer, never()).onChanged(any());
//    }
//}
