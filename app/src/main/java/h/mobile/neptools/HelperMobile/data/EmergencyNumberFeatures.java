package h.mobile.neptools.HelperMobile.data;

public class EmergencyNumberFeatures {

    private String emergencyPlaceName;
    private String emergencyPlaceNumber;
    private int imageResource;

    public EmergencyNumberFeatures(String emergencyPlaceName, String emergencyPlaceNumber, int imageResource) {
        this.emergencyPlaceName = emergencyPlaceName;
        this.emergencyPlaceNumber = emergencyPlaceNumber;
        this.imageResource = imageResource;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }


    public String getEmergencyPlaceName() {
        return emergencyPlaceName;
    }

    public void setEmergencyPlaceName(String emergencyPlaceName) {
        this.emergencyPlaceName = emergencyPlaceName;
    }

    public String getEmergencyPlaceNumber() {
        return emergencyPlaceNumber;
    }

    public void setEmergencyPlaceNumber(String emergencyPlaceNumber) {
        this.emergencyPlaceNumber = emergencyPlaceNumber;
    }
}
