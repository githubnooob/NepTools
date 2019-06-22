package h.mobile.neptools.HelperMobile.data;

import android.content.Context;


import java.util.ArrayList;

import h.mobile.neptools.R;

public class EmergencyNumbersGenerator {

    private  ArrayList<EmergencyNumberFeatures> features;
    private Context c;

    public EmergencyNumbersGenerator(Context c)
    {
        this.c = c;
    }

    public ArrayList<EmergencyNumberFeatures> generateEmergencyNumber()
    {

        features = new ArrayList<>();

        EmergencyNumberFeatures em = new EmergencyNumberFeatures(
                c.getResources().getString(R.string.policeControl),
                c.getResources().getString(R.string.policeControlNumber),R.drawable.list_policeman
        );
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.policeEmergency), c.getResources().getString(R.string.policeEmergencyNumber), R.drawable.list_policeman );
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.kathmanduPolice), c.getResources().getString(R.string.kathmanduPoliceNumber),R.drawable.list_policeman  );
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.lalitpurPolice), c.getResources().getString(R.string.lalitpurPoliceNumber),R.drawable.list_policeman  );
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.bhaktapurPolice), c.getResources().getString(R.string.bhaktapurPoliceNumber),R.drawable.list_policeman  );
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.paropkarAmbulance), c.getResources().getString(R.string.paropkarAmbulanceNumber), R.drawable.list_ambulance );
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.lalitpurRedCrossAmbulance), c.getResources().getString(R.string.lalitpurRedCrossAmbulanceNumber),R.drawable.list_ambulance  );
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.bishalBazaarRedCrossAmbulance), c.getResources().getString(R.string.bishalBazaarRedCrossAmbulanceNumber),R.drawable.list_ambulance  );
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.redCrossAmbulance), c.getResources().getString(R.string.redCrossAmbulanceNumber),R.drawable.list_ambulance  );
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.agarwalSewaCenter), c.getResources().getString(R.string.agarwalSewaCenterNumber),R.drawable.lis_customer );
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.aasaraRehabilitationCenter), c.getResources().getString(R.string.aasaraRehabilitationCenterNumber),R.drawable.list_rehabilitation );
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.neaplEyeBank), c.getResources().getString(R.string.neaplEyeBankNumber) , R.drawable.list_bank);
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.nepalEyeHospital), c.getResources().getString(R.string.nepalEyeHospitalNumber),R.drawable.list_eye );
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.tilGangaeyeHospital), c.getResources().getString(R.string.tilGangaeyeHospitalNumber),R.drawable.list_eye );
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.bitHospital), c.getResources().getString(R.string.birHospitalNumber),R.drawable.list_hospital );
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.nepalPoliceHospital), c.getResources().getString(R.string.nepalPoliceHospitalNumber), R.drawable.list_hospital );
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.tuTeachingHospital), c.getResources().getString(R.string.tuTeachingHospitalNumber), R.drawable.list_hospital );
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.maternityHospital), c.getResources().getString(R.string.maternityHospitalNumber), R.drawable.list_hospital );
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.tekuHospital), c.getResources().getString(R.string.tekuHospitalNumber),R.drawable.list_hospital );
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.patanHospital), c.getResources().getString(R.string.patanHospitalNumber),R.drawable.list_hospital );
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.bhaktapurHospital), c.getResources().getString(R.string.bhaktapurHospitalNumber),R.drawable.list_hospital );
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.mentalHospital), c.getResources().getString(R.string.mentalHospitalNumber),R.drawable.list_hospital );
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.kantiChildrenHospital), c.getResources().getString(R.string.kantiChildrenHospitalNumber),R.drawable.list_hospital );
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.kathmanduModelHospital), c.getResources().getString(R.string.kathmanduModelHospitalNumber),R.drawable.list_hospital );
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.bAndbHospital), c.getResources().getString(R.string.bAndbHospitalNumber),R.drawable.list_hospital );
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.mediaCareNationalHospital), c.getResources().getString(R.string.mediaCareNationalHospitalNumber),R.drawable.list_hospital );
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.mediaCareNationalHospitalAmbulance), c.getResources().getString(R.string.mediaCareNationalHospitalAmbulanceNumber),R.drawable.list_hospital );
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.nepalOrthoPedicHospital), c.getResources().getString(R.string.nepalOrthoPedicHospitalNumber) ,R.drawable.list_hospital);
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.teachingHospitalSinamangal), c.getResources().getString(R.string.teachingHospitalSinamangalNumber),R.drawable.list_hospital );
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.teachingHospitalJorpati), c.getResources().getString(R.string.teachingHospitalJorpatiNumber),R.drawable.list_hospital );
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.kantipurDentalHospital), c.getResources().getString(R.string.kantipurDentalHospitalNumber),R.drawable.list_hospital );
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.kantipurHospital), c.getResources().getString(R.string.kantipurHospitalNumber),R.drawable.list_hospital );
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.hospitalAndResearchCenter), c.getResources().getString(R.string.hospitalAndResearchCenterNumber),R.drawable.list_hospital );
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.norvicHospital), c.getResources().getString(R.string.norvicHospitalNumber),R.drawable.list_hospital );
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.gangalalNationalHeartCenter), c.getResources().getString(R.string.gangalalNationalHeartCenterNumber),R.drawable.list_hospital );
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.lifeCareHospital), c.getResources().getString(R.string.lifeCareHospitalNumber),R.drawable.list_hospital );
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.miteriHospital), c.getResources().getString(R.string.miteriHospitalNumber),R.drawable.list_hospital );
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.capitalHospital), c.getResources().getString(R.string.capitalHospitalNumber),R.drawable.list_hospital );
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.shreeSatyasahiCenter), c.getResources().getString(R.string.shreeSatyasahiCenterNumber),R.drawable.list_moon );
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.bhaktapurRedCross), c.getResources().getString(R.string.bhaktapurRedCrossNumber),R.drawable.list_redcross);
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.nationalKidneyCenter), c.getResources().getString(R.string.nationalKidneyCenterNumber),R.drawable.list_kidney );
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.fireBrigade), c.getResources().getString(R.string.fireBrigadeNumber),R.drawable.list_firetruck);
        features.add(em);
        em = new EmergencyNumberFeatures( c.getResources().getString(R.string.bloodBank), c.getResources().getString(R.string.bloodBankNumber),R.drawable.list_bloodbank );
        features.add(em);

        return features;
    }


}
