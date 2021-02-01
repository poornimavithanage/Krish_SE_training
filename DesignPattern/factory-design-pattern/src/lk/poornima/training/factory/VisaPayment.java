package lk.poornima.training.factory;

public class VisaPayment extends Payment{
    @Override
    protected void makePayment() {
        universities.add(new CivilEngineering());
        universities.add(new Psychology());
        universities.add(new InformationTechnology());
        universities.add(new MedicalScience());
    }
}
