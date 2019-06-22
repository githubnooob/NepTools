package h.mobile.neptools.HelperMobile.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

import h.mobile.neptools.HelperMobile.data.EmergencyNumberFeatures;
import h.mobile.neptools.R;

public class EmergencyNumberAdapter extends RecyclerView.Adapter<EmergencyNumberAdapter.MyViewHolder> {

    private ArrayList<EmergencyNumberFeatures> emergencyNumbers;
    private Context c;

    public EmergencyNumberAdapter(ArrayList <EmergencyNumberFeatures> features, Context c)
    {
        emergencyNumbers = features;
        this.c = c;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(c).inflate(R.layout.emergency_numbers,viewGroup,false);
        return new EmergencyNumberAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.emergencyPlaceName.setText(emergencyNumbers.get(i).getEmergencyPlaceName());
        myViewHolder.emergencyPlaceNumber.setText(emergencyNumbers.get(i).getEmergencyPlaceNumber());
        myViewHolder.iconForPlace.setImageResource(emergencyNumbers.get(i).getImageResource());

        myViewHolder.callingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number  =    emergencyNumbers.get(i).getEmergencyPlaceNumber();
                if(number.length() >3)
                {
                    if(!number.startsWith("01"))
                    {
                        number = "01"+number;
                    }
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(number)));
                    c.startActivity(intent);
                }
                else if(number.length()==3)
                {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(number)));
                    c.startActivity(intent);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return emergencyNumbers.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView emergencyPlaceName;
        private TextView emergencyPlaceNumber;
        private Button callingButton;
        private ImageView iconForPlace;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            emergencyPlaceName = itemView.findViewById(R.id.nameOfEmergencyPlace);
            emergencyPlaceNumber = itemView.findViewById(R.id.numberOfEmergencyPlace);
            callingButton = itemView.findViewById(R.id.callingButton);
            iconForPlace = itemView.findViewById(R.id.iconForImage);

        }
    }
}
