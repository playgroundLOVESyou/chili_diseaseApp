package com.example.chili_diseaseapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;



import com.example.chili_diseaseapp.ml.Ordaniza;


import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class predict_then_save extends AppCompatActivity {

    //wennie ordaniza coding

    TextView result,confidence,label;
    Button takepicture, map, weather;
    ImageView imagepic;

    int imageSize = 224;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict_then_save);
        result = findViewById(R.id.prediction) ;
        confidence = findViewById(R.id.confidences) ;
        takepicture = findViewById(R.id.takeapicture);
        weather = findViewById(R.id.toweather);
        imagepic = findViewById(R.id.image);
        label = findViewById(R.id.image_label);


weather.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(getApplicationContext(),myweather.class);
        startActivity(intent);
    }
});
        takepicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

if(checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
    Intent intent =  new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
    startActivityForResult(intent,1);

}else {
    requestPermissions(new String []{Manifest.permission.CAMERA}, 100);
}

            }
        });








    }






public void classifyImage(Bitmap picture){
    try {
        Ordaniza model = Ordaniza.newInstance(getApplicationContext());


        // Creates inputs for reference.
        TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize*imageSize *3);
        byteBuffer.order(ByteOrder.nativeOrder());

        int [] intValues = new int [imageSize * imageSize];
picture.getPixels(intValues, 0 ,picture.getWidth(),0,0,picture.getWidth(), picture.getHeight());
int pixel = 0;
for(int i = 0; i < imageSize; i++){
for(int j =0; j < imageSize; j++){
    int val = intValues[pixel++];
    byteBuffer.putFloat(((val >> 16) &0xFF)* (1.f / 255));
    byteBuffer.putFloat(((val >> 8) &0xFF)* (1.f / 255));
    byteBuffer.putFloat((val & 0xFF) * (1.f / 255.f));
    }
        }

        inputFeature0.loadBuffer(byteBuffer);

        // Runs model inference and gets result.
        Ordaniza.Outputs outputs = model.process(inputFeature0);
        TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();


        float[] confidences = outputFeature0.getFloatArray();
        int maxPos = 0;
       float maxConfidence = 0;
       for(int i = 0; i < confidences.length; i++){
           if(confidences[i] > maxConfidence) {
               maxConfidence = confidences[i];
               maxPos = i;
           }
       }
       String[] classes = {"healthy","leaf spot","whitefly","Fungus"};

       if (classes[maxPos].equals("healthy")) {
            label.setText("The plant is healthy. Nothing to worry!");

        } else if (classes[maxPos].equals("leaf spot")) {
            label.setText("Treating Leaf Spot:\n" +
                    "Leaf spot is a common disease that can affect many different plants. It is caused by a variety of fungi, bacteria, and viruses. The symptoms of leaf spot include brown, yellow, or black spots on the leaves, which may eventually cause the leaves to fall off.\n" +
                    "\n" +
                    "Here are some steps you can take to treat leaf spot:\n" +
                    "\n" +
                    "General Tips:\n" +
                    "\n" +
                    "Remove and destroy infected leaves and plant parts: This will help to prevent the spread of the disease. Be sure to bag the infected leaves and throw them away in the trash, or burn them if possible.\n" +
                    "Pick up and destroy fallen foliage: This will also help to prevent the spread of the disease.\n" +
                    "Avoid getting water on the leaves: This can help to prevent the development of the disease. Water the plant at the base, rather than overhead.\n" +
                    "Keep the plant away from other plants: This will help to prevent the spread of the disease to other plants.\n" +
                    "Monitor the plant daily: This will help you to catch the disease early and take steps to control it.\n" +
                    "Treatment Options:\n" +
                    "\n" +
                    "Fungicides: There are a variety of fungicides that can be effective in treating leaf spot. Be sure to choose a fungicide that is labeled for the specific type of plant you are treating. You can find fungicides at your local garden center.\n" +
                    "Neem oil: Neem oil is a natural fungicide that can be effective in treating leaf spot. It is available at most garden centers.\n" +
                    "Baking soda spray: A baking soda spray can be used to treat leaf spot. Mix 1 tablespoon of baking soda with 1 gallon of water and spray the affected leaves.\n" +
                    "Copper fungicides: Copper fungicides are another option for treating leaf spot. Be sure to choose a copper fungicide that is labeled for the specific type of plant you are treating.\n" +
                    "Biological control: There are a number of biological control agents that can be used to treat leaf spot. These agents are usually available from specialty garden centers or online.\n" +
                    "Here are some additional resources that you may find helpful:\n" +
                    "\n" +
                    "Leaf spot diseases of trees and shrubs: https://apps.extension.umn.edu/garden/diagnose/\n" +
                    "Bacterial and Fungal Leaf Spot: https://smartgardenguide.com/rust-spots-on-leaves/\n" +
                    "How to Treat Leaf Spot: https://howmanyplants.com/post/decoding-leaf-changes-in-your-houseplant\n" +
                    "It is important to note that the best way to treat leaf spot will vary depending on the type of plant you are treating and the severity of the disease. If you are unsure what to do, it is always best to consult with a professional.");
        } else if (classes[maxPos].equals("whitefly")) {
            label.setText("Whitefly Treatment:\n" +
                    "Whiteflies are tiny, sap-sucking insects that can damage a variety of plants. They are most active in warm weather and can quickly multiply in large numbers.\n" +
                    "\n" +
                    "Here are some safe and effective ways to treat whiteflies:\n" +
                    "\n" +
                    "General Management:\n" +
                    "\n" +
                    "Monitor your plants regularly: Inspect both the upper and lower surfaces of leaves for signs of whiteflies or their eggs.\n" +
                    "Remove infested leaves: This will help to reduce the population of whiteflies and prevent them from spreading.\n" +
                    "Encourage natural predators: Ladybugs, lacewings, and parasitic wasps are all natural predators of whiteflies. Attract these beneficial insects to your garden by planting flowers that they like, such as dill, fennel, and yarrow.\n" +
                    "Keep your garden clean: Remove fallen leaves and debris, as these can provide a place for whiteflies to overwinter.\n" +
                    "Water your plants regularly: Stressed plants are more susceptible to whitefly infestations.\n" +
                    "Use reflective mulch: Silver-colored mulch can help to repel whiteflies.\n" +
                    "Organic Treatment Options:\n" +
                    "\n" +
                    "Neem oil: Neem oil is a natural insecticide that is effective against whiteflies. It can be applied to the leaves of your plants as a spray or drench.\n" +
                    "Soap spray: Mix 1 tablespoon of dish soap with 1 gallon of water and spray the affected leaves.\n" +
                    "Hot pepper spray: Mix 1 teaspoon of hot pepper flakes with 1 gallon of water and spray the affected leaves.\n" +
                    "Diatomaceous earth: Diatomaceous earth is a powder that dehydrates insects. Sprinkle it on the leaves of your plants or around the base of the plant.\n" +
                    "Sticky traps: Yellow sticky traps can be used to trap adult whiteflies.\n" +
                    "Commercial Treatment Options:\n" +
                    "\n" +
                    "Insecticidal soap: Insecticidal soap is a readily available product that is effective against whiteflies. Follow the directions on the label for application.\n" +
                    "Horticultural oil: Horticultural oil can be used to smother whiteflies and their eggs. Follow the directions on the label for application.\n" +
                    "Systemic insecticides: Systemic insecticides are absorbed by the plant and kill whiteflies that feed on it. These should be used as a last resort, as they can also harm beneficial insects.\n" +
                    "Important Safety Precautions:\n" +
                    "\n" +
                    "Always read and follow the label directions on any pesticide you use.\n" +
                    "Wear protective clothing, such as gloves and a mask, when applying pesticides.\n" +
                    "Do not apply pesticides on windy days.\n" +
                    "Wash your hands thoroughly after applying pesticides.\n" +
                    "Remember that the best way to control whiteflies is through a combination of methods. By using a combination of the methods listed above, you can effectively control whiteflies and protect your plants.\n" +
                    "\n" +
                    "If you are unsure how to treat whiteflies, it is always best to consult with a professional.");
        } else if (classes[maxPos].equals("Fungus")) {
            label.setText("Leaf Fungus Treatment:\n" +
                    "There are many different types of leaf fungus, so the best treatment will vary depending on the specific type of fungus you are dealing with. However, there are some general steps you can take to treat leaf fungus:\n" +
                    "\n" +
                    "Identify the fungus: This is the most important step, as different fungi require different treatments. You can identify the fungus by looking at the symptoms it causes on your plant. You can also take a sample of the infected leaves to your local garden center or extension office for diagnosis.\n" +
                    "\n" +
                    "Remove and destroy infected leaves: Once you have identified the fungus, you need to remove and destroy any infected leaves. This will help to prevent the fungus from spreading to other parts of the plant.\n" +
                    "\n" +
                    "Improve air circulation: Fungi thrive in humid environments. To prevent leaf fungus, it is important to improve air circulation around your plants. You can do this by thinning out crowded plants, pruning dead or diseased leaves, and spacing plants further apart.\n" +
                    "\n" +
                    "Water your plants correctly: Overwatering can create the perfect environment for fungi to thrive. Water your plants deeply but infrequently, and avoid getting water on the leaves.\n" +
                    "\n" +
                    "Use organic treatments: There are a number of organic treatments that can be effective against leaf fungus. These include neem oil, copper fungicides, baking soda spray, and potassium bicarbonate. You can find these treatments at most garden centers.\n" +
                    "\n" +
                    "Use fungicides: If organic treatments are not effective, you may need to use a fungicide. There are a number of different fungicides available, so be sure to choose one that is labeled for the specific type of fungus you are dealing with.\n" +
                    "\n" +
                    "Here are some additional resources that you may find helpful:\n" +
                    "\n" +
                    "How to Identify and Control Common Plant Fungal Diseases: https://www.gardentech.com/\n" +
                    "Management strategies: https://gurugramluxuryfloors.com/?e=fungal-leaf-spots-on-indoor-plants-university-of-maryland-xx-M6YeL8Iq\n" +
                    "How to Treat Leaf Spot: https://gardenforindoor.com/polka-dot-plant-crispy-leaves/\n" +
                    "Here are some additional tips:\n" +
                    "\n" +
                    "It is important to act quickly when you first notice signs of leaf fungus. The longer you wait, the harder it will be to control the disease.\n" +
                    "Be sure to clean your gardening tools and pots after working with infected plants. This will help to prevent the spread of the disease.\n" +
                    "Do not compost infected leaves. This can help to spread the disease to other plants.\n" +
                    "If you are unsure how to treat leaf fungus, it is always best to consult with a professional.");
        }
       result.setText(classes[maxPos]);

        String s = "";
        for (int i = 0; i < classes.length; i++) {
            s += String.format("%s: %.1f%%\n", classes[i], confidences[i] * 100);
        }
       confidence.setText(s);


       // Releases model resources if no longer used.
        model.close();
    } catch (IOException e) {
       e.printStackTrace();
    }
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

            if (requestCode == 1 && resultCode == RESULT_OK) {
                Bitmap image = (Bitmap) data.getExtras().get("data");
                int dimension = Math.min(image.getWidth(), image.getHeight());
                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
                imagepic.setImageBitmap(image);

                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                classifyImage(image);
            }


        super.onActivityResult(requestCode, resultCode, data);


    }
}