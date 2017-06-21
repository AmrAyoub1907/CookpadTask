package com.amrayoub.cookpadtask;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Amr Ayoub on 6/21/2017.
 */

public class WeatherAdapter  extends RecyclerView.Adapter<WeatherAdapter.MyViewHolder> {

    private List<weatherInfo> weatherList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date, high_temp, low_temp,weatherdesc;
        public ImageView weatherImage;

        public MyViewHolder(View view) {
            super(view);
            date = (TextView) view.findViewById(R.id.date);
            weatherdesc = (TextView) view.findViewById(R.id.weather_description);
            high_temp = (TextView) view.findViewById(R.id.high_temperature);
            low_temp = (TextView) view.findViewById(R.id.low_temperature);
            weatherImage = (ImageView) view.findViewById(R.id.weather_icon);
        }
    }
    public WeatherAdapter(List<weatherInfo> weatherList) {
        this.weatherList = weatherList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_list_item, parent, false);
        context = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        weatherInfo weather = weatherList.get(position);
        holder.date.setText(DateFormat.getDateTimeInstance().format(new Date()));
        holder.weatherdesc.setText(weather.getWeather_clouds());
        holder.high_temp.setText(weather.getTemp_max());
        holder.low_temp.setText(weather.getTemp_min());
        Picasso.with(context).load("http://openweathermap.org/img/w/"+weather.getWeather_ico()+".png")
                .into(holder.weatherImage);
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }
}
