package com.example.chandru.reportbeeapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Attend extends AppCompatActivity implements View.OnLongClickListener {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    ArrayList Contactlist =new ArrayList<>();
    ArrayList<Object> selection_list= new ArrayList<>();
    Button store,grap,Butt;
    Spinner spinner;
    int n, times =0;
    String date,kan;
    public int co=0;
    JSONObject jsonObject;
    JSONArray jsonArray;
    String[] Name,Roll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attend);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



//*******************************************************************************
        json_parsing();
//*******************************************************************************
        List<String> spinnerArray =  new ArrayList<String>();
        SimpleDateFormat df=new SimpleDateFormat("dd-MM-yyyy,EEEE");
        Date d=new Date();
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE, 0);
        spinnerArray.add(df.format(cal.getTime()));


        Butt =(Button)findViewById(R.id.button4);
        Butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intente = new Intent(Attend.this, Calendarview.class);
                startActivity(intente);
            }
        });
        //
        Intent i = getIntent();
        String easyPuzzle = i.getStringExtra("puzzle");

        TextView textView=(TextView)findViewById(R.id.textView5);
        textView.setText(easyPuzzle);
 kan=textView.getText().toString();
//

        for(int c=0;c<30;c++)
        {
            cal.add(Calendar.DATE, -1);
            spinnerArray.add(df.format(cal.getTime()));
        }


        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner) findViewById(R.id.spinner2);

        spinner.setAdapter(adapter1);
        date=spinner.getSelectedItem().toString();
        store =(Button)findViewById(R.id.button2);
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Saveto(view);
            }
        });

        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        int j=0;
        for(String NAME:Name)
        {
            Contactlistview contactlistview =new Contactlistview(NAME,Roll[j],0);
            Contactlist.add(contactlistview);
            j++;
        }
        adapter = new ContactAdapter(Contactlist, Attend.this);
        recyclerView.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                List<Contactlistview> stList = ((ContactAdapter) adapter).getList();
                date = spinner.getSelectedItem().toString();
                SharedPreferences s = getSharedPreferences(date, 0);
                String Nma = s.getString(date, "");
                String arr[] = Nma.split(",");
                List<String> fin = new ArrayList<String>();
                for (int g = 0; g < stList.size(); g++) {
                    Contactlistview singleStudent = stList.get(g);
                    fin.add(singleStudent.getName());
                }
                System.out.println("length= " + arr.length);
                for (int a = 0; a < fin.size(); a++) {
                    System.out.println(fin.get(a));
                }
                List<String> l = new ArrayList<String>();
                for (int k = 0; k < arr.length; k++) {
                    l.add(arr[k]);
                }
                stList.clear();
                for (int j = 0; j < 20; j++) {
                    if (l.contains(fin.get(j))) {
                        Contactlistview dp = new Contactlistview(fin.get(j), "" + (j + 1), 1);
                        stList.add(dp);
                    } else {
                        Contactlistview dp = new Contactlistview(fin.get(j), "" + (j + 1), 0);
                        stList.add(dp);
                    }
                }
                recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
                layoutManager = new LinearLayoutManager(Attend.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void Saveto(final View view)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Attend.this);
        builder.setTitle("Press Ok to store");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

                onButtonClick(view);
//                Intent intent = new Intent(Attend.this, MainActivity.class);
//                startActivity(intent);

            }
        });

        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setCancelable(false);
        builder.show();
    }

    public  void json_parsing()
    {
        AssetManager am=getAssets();
        InputStream is = null;

        try {
            is = am.open("students.json");
        } catch (IOException e) {
            e.printStackTrace();
        }


        BufferedReader br=new BufferedReader(new InputStreamReader(is));
        String st="";
        StringBuilder sb=new StringBuilder();
        try {
            while((st=br.readLine())!=null)
            {
                sb.append(st);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            jsonObject = new JSONObject(sb.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonArray = jsonObject.getJSONArray("student_details");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        n=jsonArray.length();
        Name=new String[n];
        Roll=new String[n];

        int i;
        while (times < jsonArray.length()) {

            JSONObject JO = null;
            try {
                JO = jsonArray.getJSONObject(times);
                if(JO.getString("id")!=null)
                {
                    Roll[times]=JO.getString("id");
                    Name[times] = JO.getString("name");
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }
            times++;
        }
    }
//    public void Mov(View view) {
//        grap = (Button) findViewById(R.id.button4);
//        grap.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        Intent in = new Intent(Attend.this, Main2Activity.class);
//                        startActivity(in);
//                    }
//                }
//        );}

    public  void onButtonClick(View view)
    {
        String absent="";

        Intent i1 = getIntent();
        String easyPuzzle = i1.getStringExtra("puzzle");

        TextView textView=(TextView)findViewById(R.id.textView5);
        textView.setText(easyPuzzle);
        kan=textView.getText().toString();
        List<Contactlistview> list=((ContactAdapter)adapter).getList();

        int i;
        for(i=0;i<20;i++)
        {
            Contactlistview contactlistview =list.get(i);
            if(contactlistview.getposition()==1)
            {
                absent=absent+""+ contactlistview.getName().toString()+",";
            }
            //   Toast.makeText(Attend.this, absent, Toast.LENGTH_SHORT).show();

        }

        SharedPreferences sp=getSharedPreferences(kan,0);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(kan, absent);
        editor.commit();

    }

    public  void load(){

        Spinner sItems = (Spinner) findViewById(R.id.spinner2);
        String d=sItems.getSelectedItem().toString();

        SharedPreferences sp1=getSharedPreferences(d, 0);
        String wordString = sp1.getString(d, "");
        String[] itemWords=wordString.split(",");
        List<String> items=new ArrayList<String>();
        for(int i1=0;i1< itemWords.length;i1++)
        {
            items.add(itemWords[i1]);
            Toast.makeText(Attend.this,itemWords[i1],Toast.LENGTH_SHORT).show();
            co=co+1;

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_mode, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.logout) {
            Intent intent=new Intent(Attend.this,MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.makku1) {
            load();
            Intent intent1 = new Intent(Attend.this,Main2Activity.class);
            Bundle extras=new Bundle();
            String abs_count=""+co;
            String pre_count=""+(20-co);
            co=0;
            extras.putString("pre",pre_count);
            extras.putString("abs", abs_count);
            intent1.putExtras(extras);
            startActivity(intent1);
            //Toast.makeText(getBaseContext(), co+"->"+pre_count+"--"+abs_count, Toast.LENGTH_LONG).show();
        }
        return true;
    }

//    public void Mov(View view){
//         grap=(Button)findViewById(R.id.button4);
//        grap.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        Intent intent1 = new Intent(Attend.this,Main2Activity.class);
//                        Bundle extras=new Bundle();
//                        extras.putString("pre","16");
//                        extras.putString("abs","4");
//                        intent1.putExtras(extras);
//                        startActivity(intent1);
//                       // Toast.makeText(getBaseContext(),"  ",Toast.LENGTH_LONG).show();
//                    }
//                }
//        );
//
//    }



    @Override
    public boolean onLongClick(View view) {
        return false;
    }

    public void prepareSelection(View v, int adapterPosition) {
    }
}


class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    ArrayList<Contactlistview> adapter_list=new ArrayList<>();
    Attend mainActivity;
    Context ctx;
    public ContactAdapter(ArrayList<Contactlistview> adapter_list,Context ctx)
    {
        this.adapter_list =adapter_list;
        this.ctx=ctx;
        mainActivity=(Attend)ctx;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_layout,parent,false);
        ContactViewHolder contactViewHolder=new ContactViewHolder(view,mainActivity);

        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, final int position) {

        holder.Name.setText(adapter_list.get(position).getName());
        holder.No.setText(adapter_list.get(position).getCont());

        holder.spin.setSelection(adapter_list.get(position).getposition());
        holder.spin.setTag(adapter_list.get(position));
        holder.spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adapterView.setBackgroundColor(Color.parseColor("#2ecc8d"));
                if (adapterView.getSelectedItem().toString().equals("Present")) {
                    adapterView.setBackgroundColor(Color.parseColor("#2ecc8d"));
//                    Spinner spinner = (Spinner) adapterView;
//                    Contactlistview contactlistview = (Contactlistview) spinner.getTag();
//                    contactlistview.setPosition(0);
                    adapter_list.get(position).setPosition(0);
                } else {
                    adapterView.setBackgroundColor(Color.parseColor("#ee4f4f"));
//                    Spinner spinner = (Spinner) adapterView;
//                    Contactlistview contactlistview = (Contactlistview) spinner.getTag();
//                    contactlistview.setPosition(1);
                    adapter_list.get(position).setPosition(1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return adapter_list.size();
    }

    public List<Contactlistview> getList()
    {
        return adapter_list;
    }

    public void updateAdapter(ArrayList<Contactlistview> list)
    {
        for(Contactlistview contactlistview :list)
        {
            adapter_list.remove(contactlistview);
        }
        notifyDataSetChanged();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public  Spinner spin;
        ImageView img;
        TextView Name,No;
        Attend mainActivity;
        CardView cardView;


        public ContactViewHolder(View itemView,Attend mainActivity) {
            super(itemView);

            Name=(TextView)itemView.findViewById(R.id.name);
            No=(TextView)itemView.findViewById(R.id.no);
            spin=(Spinner)itemView.findViewById(R.id.spinner);

            this.mainActivity=mainActivity;

            cardView=(CardView)itemView.findViewById(R.id.cardview);
            cardView.setOnLongClickListener((Attend) mainActivity);

        }

        @Override
        public void onClick(View v) {

            mainActivity.prepareSelection(v,getAdapterPosition() );




        }
    }
}

