package alberto.medicconsultapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.widget.AdapterView.OnItemClickListener;







public class ListHours extends AppCompatActivity {

    String opcion = "";
    int i = 0;
    int tamaño;

    static final String[] Hours = new String[] { "Apple", "Avocado", "Banana",
            "Blueberry", "Coconut", "Durian", "Guava", "Kiwifruit",
            "Jackfruit", "Mango", "Olive", "Pear", "Sugar-apple" };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();//Recibimos los datos del activity anterior
        if(extras != null){
            tamaño = (int)extras.get("Tamaaño");
            while (i<tamaño)
                Hours[i] = (String)extras.get("Horas");
        }
        // no more this
        // setContentView(R.layout.list_fruit);

        setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_main,Hours));

        ListView listView = getListView();
        listView.setTextFilterEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                opcion = ((TextView) view).getText().toString();
                Toast.makeText(getApplicationContext(),
                        opcion, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
