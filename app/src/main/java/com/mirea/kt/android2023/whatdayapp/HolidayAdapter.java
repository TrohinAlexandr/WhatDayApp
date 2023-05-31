package com.mirea.kt.android2023.whatdayapp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HolidayAdapter extends RecyclerView.Adapter<HolidayAdapter.ViewHolder> {

    private ArrayList<String> holidays;
    private OnHolidayClickListener onHolidayClickListener;
    private static final int RED = Color.parseColor("#fa7373");
    private static final int ORANGE = Color.parseColor("#ffa15e");
    private static final int YELLOW = Color.parseColor("#fce96d");
    private static final int GREEN = Color.parseColor("#9cf590");
    private static final int BLUE = Color.parseColor("#72c0fc");
    private static final int PURPLE = Color.parseColor("#eeaafa");

    private int currentIndex;

    public HolidayAdapter(ArrayList<String> holidays, OnHolidayClickListener onHolidayClickListener) {
        this.holidays = holidays;
        this.onHolidayClickListener = onHolidayClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_holiday, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String holiday = holidays.get(position);

        holder.holidayName.setText(holiday);
        if (position == 0 || position == holidays.size() - 1) {
            holder.itemLayout.setBackgroundColor(ORANGE);
        } else {
            switch (position % 6) {
                case 1:
                    holder.itemLayout.setBackgroundColor(YELLOW);
                    break;
                case 2:
                    holder.itemLayout.setBackgroundColor(GREEN);
                    break;
                case 3:
                    holder.itemLayout.setBackgroundColor(BLUE);
                    break;
                case 4:
                    holder.itemLayout.setBackgroundColor(PURPLE);
                    break;
                case 5:
                    holder.itemLayout.setBackgroundColor(RED);
                    break;
                default:
                    holder.itemLayout.setBackgroundColor(ORANGE);
            }
        }

        holder.itemView.setOnClickListener(x -> {
            onHolidayClickListener.onHolidayClick(holiday, holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return holidays.size();
    }

    public interface OnHolidayClickListener {
        void onHolidayClick(String holiday, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView holidayName;
        private RelativeLayout itemLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemLayout = itemView.findViewById(R.id.itemLayout);
            holidayName = itemView.findViewById(R.id.textViewHolidayItem);
        }
    }
}
