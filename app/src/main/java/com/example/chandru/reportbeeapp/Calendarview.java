package com.example.chandru.reportbeeapp;


    import android.content.Intent;
    import android.graphics.Color;
    import android.graphics.drawable.Drawable;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.Button;
    import android.widget.CalendarView;
    import android.widget.Toast;

    import java.util.Date;

public class Calendarview extends AppCompatActivity {

    CalendarView simpleCalendarView;
    Button b5;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_main);
        simpleCalendarView = (CalendarView) findViewById(R.id.simpleCalendarView);
        // get the reference of CalendarView
//simpleCalendarView.setBackgroundColor(Color.parseColor());
        simpleCalendarView.setFocusedMonthDateColor(Color.RED); // set the red color for the dates of  focused month
        simpleCalendarView.setUnfocusedMonthDateColor(Color.BLUE); // set the yellow color for the dates of an unfocused month
        simpleCalendarView.setSelectedWeekBackgroundColor(Color.RED); // red color for the selected week's background
        simpleCalendarView.setWeekSeparatorLineColor(Color.GREEN); // green color for the week separator line
        // perform setOnDateChangeListener event on CalendarViewstring
//             long presdate= simpleCalendarView.getMaxDate();
//            long mindate=presdate-30;
//

        simpleCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // display the selected date by using a toast

                          // Intent intente1 = new Intent(Calendarview.this, Attend.class);
////                Bundle extras=new Bundle();
//                 int a = dayOfMonth;
//                int b=month;
//                int c=year;
//
//               String day=""+  a+""+b+""+c;
//                String
//                extras.putString("da",  day);
//                extras.putString("mo", abs_count);
//                extras.putString("year", abs_count);

                String easyPuzzle  = ""+dayOfMonth+ "/"
                       +(month+1)+"/"+year;


                Intent i21 =  new Intent(Calendarview.this, Attend.class);

                i21.putExtra("puzzle", easyPuzzle);
                startActivity(i21);

                Toast.makeText(getApplicationContext(), dayOfMonth + "/" + (month+1) + "/" + year, Toast.LENGTH_LONG).show();
            }

        });
            b5 =(Button)findViewById(R.id.button5);
            b5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intente1 = new Intent(Calendarview.this, Attend.class);
                    startActivity(intente1);
                }
            });
        }


    }


