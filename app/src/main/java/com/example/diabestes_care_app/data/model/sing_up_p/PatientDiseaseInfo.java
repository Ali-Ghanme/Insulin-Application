package com.example.diabestes_care_app.data.model.sing_up_p;

public class PatientDiseaseInfo {
    public String dateInjury;
    public String causes;
    public String otherDiseases;
    public boolean noOtherDiseases;

    public PatientDiseaseInfo() {}

    public PatientDiseaseInfo(String dateInjury, String causes, String otherDiseases, boolean noOtherDiseases) {
        this.dateInjury = dateInjury;
        this.causes = causes;
        this.otherDiseases = otherDiseases;
        this.noOtherDiseases = noOtherDiseases;
    }
}
