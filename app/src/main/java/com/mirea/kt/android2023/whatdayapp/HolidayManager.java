package com.mirea.kt.android2023.whatdayapp;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class HolidayManager implements Runnable {

    private static final String URL = "https://kakoysegodnyaprazdnik.ru/";
    private ArrayList<String> holidays;
    private String dateName;
    private String dayUrl;

    public HolidayManager() {
        holidays = new ArrayList<>();
        dayUrl = "";
    }

    public HolidayManager(String dayUrl) {
        holidays = new ArrayList<>();
        this.dayUrl = dayUrl;
    }

    @Override
    public void run() {
        try {
            Document document = Jsoup.connect(URL + dayUrl).get();

            Elements elements = document.select("span[itemprop]");

            for (Element element : elements) {
                Log.d("app_tag", "Getting " + element.text());
                holidays.add(element.text());
            }

            if (!dayUrl.isEmpty() && !holidays.isEmpty()) {
                holidays.remove(holidays.size() - 1);
                holidays.remove(holidays.size() - 1);
                holidays.remove(holidays.size() - 1);
            }

            if (!dayUrl.isEmpty()) {
                Element dateElement = document.select("h1[itemprop=\"name\"]").first();

                dateName = dateElement.text().replace("Праздники – ", "");
            } else {
                Element dateElement = document.select("h2.mainpage").first();

                String[] strings = dateElement.text().split(" ");
                dateName = strings[0] + " " + strings[1];
            }

            Log.d("app_tag", "dateName " + dateName);
        } catch (IOException e) {
            Log.e("app_tag", e.getMessage());
        }
    }

    public ArrayList<String> getHolidays() {
        return holidays;
    }

    public String getDateName() {
        return dateName;
    }
}
