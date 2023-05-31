package com.mirea.kt.android2023.whatdayapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class HolidaysActivity extends AppCompatActivity implements HolidayAdapter.OnHolidayClickListener {

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private ArrayList<String> holidays;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holidays);

        recyclerView = findViewById(R.id.recyclerView);
        toolbar = findViewById(R.id.toolbar);
        HolidayManager holidayManager = new HolidayManager();

        Thread thread = new Thread(holidayManager);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            Log.e("app_tag", e.getMessage());
        }

        date = holidayManager.getDateName();

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Какой сегодня день? - " + date);
        }

        holidays = holidayManager.getHolidays();

        HolidayAdapter adapter = new HolidayAdapter(holidays, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        Log.d("app_tag", "finish creating HolidayActivity");

    }

    @Override
    public void onHolidayClick(String holiday, int position) {
        Log.i("app_tag", "Recycler view's item was clicked");

        String text = date + " - " + holiday;

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(shareIntent, "Поделиться праздником"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_change_date) {
            Log.i("app_tag", "Change date item was clicked");

            showDialog();

            return true;
        }

        if (id == R.id.action_current_date) {
            Log.i("app_tag", "Set current date item was clicked");

            setCurrentDate();

            return true;
        }

        if (id == R.id.action_share) {
            Log.i("app_tag", "Share item was clicked");

            StringBuilder builder = new StringBuilder();
            builder.append("Праздники " + date + ":\n");
            if (!holidays.isEmpty()) {
                for (String holiday : holidays) {
                    builder.append(holiday + "\n");
                }
            }

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, builder.toString());
            startActivity(Intent.createChooser(shareIntent, "Поделиться праздниками"));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(HolidaysActivity.this);
        View changeDateView = getLayoutInflater().inflate(R.layout.dialog_change_date, null);

        Spinner spinner = changeDateView.findViewById(R.id.spinner);
        EditText editTextDay = changeDateView.findViewById(R.id.editTextDialogDay);
        Button button = changeDateView.findViewById(R.id.buttonChangeDate);
        TextView dialogInfo = changeDateView.findViewById(R.id.textViewDialogInfo);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.months,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        dialogBuilder.setView(changeDateView);
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
        Log.i("app_tag", "Dialog was shown");

        button.setOnClickListener(x -> {
            Log.i("app_tag", "Dialog button was clicked");

            String stringDay = editTextDay.getText().toString();
            if (stringDay.isEmpty()) {
                dialogInfo.setText("Вы не ввели число!");
                return;
            }
            int day = Integer.parseInt(stringDay);

            String month;
            switch (spinner.getSelectedItem().toString()) {
                case "ФЕВРАЛЬ":
                    if (day < 1 || day > 29) {
                        dialogInfo.setText("В феврале 29 дней!");
                        return;
                    }
                    month = "fevral";
                    break;

                case "МАРТ":
                    if (day < 1 || day > 31) {
                        dialogInfo.setText("В марте 31 день!");
                        return;
                    }
                    month = "mart";
                    break;

                case "АПРЕЛЬ":
                    if (day < 1 || day > 30) {
                        dialogInfo.setText("В апреле 30 дней!");
                        return;
                    }
                    month = "aprel";
                    break;

                case "МАЙ":
                    if (day < 1 || day > 31) {
                        dialogInfo.setText("В мае 31 день!");
                        return;
                    }
                    month = "may";
                    break;

                case "ИЮНЬ":
                    if (day < 1 || day > 30) {
                        dialogInfo.setText("В июне 30 дней!");
                        return;
                    }
                    month = "iyun";
                    break;

                case "ИЮЛЬ":
                    if (day < 1 || day > 31) {
                        dialogInfo.setText("В июле 31 день!");
                        return;
                    }
                    month = "iyul";
                    break;

                case "АВГУСТ":
                    if (day < 1 || day > 31) {
                        dialogInfo.setText("В августе 31 день!");
                        return;
                    }
                    month = "avgust";
                    break;

                case "СЕНТЯБРЬ":
                    if (day < 1 || day > 30) {
                        dialogInfo.setText("В сентябре 30 дней!");
                        return;
                    }
                    month = "sentyabr";
                    break;

                case "ОКТЯБРЬ":
                    if (day < 1 || day > 31) {
                        dialogInfo.setText("В октябре 31 день!");
                        return;
                    }
                    month = "oktyabr";
                    break;

                case "НОЯБРЬ":
                    if (day < 1 || day > 30) {
                        dialogInfo.setText("В ноябре 30 дней!");
                        return;
                    }
                    month = "noyabr";
                    break;

                case "ДЕКАБРЬ":
                    if (day < 1 || day > 31) {
                        dialogInfo.setText("В декабре 31 день!");
                        return;
                    }
                    month = "dekabr";
                    break;

                default:
                    if (day < 1 || day > 31) {
                        dialogInfo.setText("В январе 31 день!");
                        return;
                    }
                    month = "yanvar";
            }

            dialog.cancel();

            HolidayManager manager = new HolidayManager("baza/" + month + "/" + day);

            Thread thread = new Thread(manager);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                Log.e("app_tag", e.getMessage());
            }

            date = manager.getDateName();
            holidays = manager.getHolidays();

            if (actionBar != null) {
                actionBar.setTitle("Какой сегодня день? - " + date);
            }

            recyclerView.setAdapter(new HolidayAdapter(holidays, this));
        });
    }

    private void setCurrentDate() {

        HolidayManager manager = new HolidayManager();

        Thread thread = new Thread(manager);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            Log.e("app_tag", e.getMessage());
        }

        date = manager.getDateName();
        holidays = manager.getHolidays();

        if (actionBar != null) {
            actionBar.setTitle("Какой сегодня день? - " + date);
        }

        recyclerView.setAdapter(new HolidayAdapter(holidays, this));
    }
}