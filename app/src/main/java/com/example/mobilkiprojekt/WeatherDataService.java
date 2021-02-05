package com.example.mobilkiprojekt;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherDataService {

    public static final String QUERY_FOR_CITY_ID = "https://www.metaweather.com/api/location/search/?query=";
    public static final String QUERY_FOR_CITY_WEATHER_BY_ID = "https://www.metaweather.com/api/location/";

    Context context;
    String cityID;

    public WeatherDataService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(String cityID);
    }


    public void getCityID(String cityName, VolleyResponseListener volleyResponseListener) {
        String url = QUERY_FOR_CITY_ID + cityName;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                cityID = "";
                try {
                    JSONObject cityInfo = response.getJSONObject(0);
                    cityID = cityInfo.getString("woeid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(context, "CityID: " + cityID, Toast.LENGTH_SHORT).show();
                volleyResponseListener.onResponse(cityID);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "err", Toast.LENGTH_SHORT).show();
                volleyResponseListener.onError("err");
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);

        //return cityID;
    }

    public interface ForeCastByIDResponse {
        void onError(String message);

        void onResponse(List<WeatherReportModel> weatherReportModels);
    }

    public void getCityForecastByID(String cityID,ForeCastByIDResponse foreCastByIDResponse) {
        List<WeatherReportModel> weatherReportModels = new ArrayList<>();

        String url = QUERY_FOR_CITY_WEATHER_BY_ID + cityID;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray consolidated_weather_list = response.getJSONArray("consolidated_weather");



                    for (int i = 0; i < consolidated_weather_list.length(); i++) {

                        WeatherReportModel oneDaYWeather = new WeatherReportModel();
                    JSONObject firstDayFromAPI = (JSONObject) consolidated_weather_list.get(i);

                    oneDaYWeather.setId(firstDayFromAPI.getInt("id"));
                    oneDaYWeather.setWeather_state_name(firstDayFromAPI.getString("weather_state_name"));
                    oneDaYWeather.setWeather_state_abbr(firstDayFromAPI.getString("weather_state_abbr"));
                    oneDaYWeather.setWind_direction_compass(firstDayFromAPI.getString("wind_direction_compass"));
                    oneDaYWeather.setCreated(firstDayFromAPI.getString("created"));
                    oneDaYWeather.setApplicable_date(firstDayFromAPI.getString("applicable_date"));
                    oneDaYWeather.setMin_temp(firstDayFromAPI.getLong("min_temp"));
                    oneDaYWeather.setMax_temp(firstDayFromAPI.getLong("max_temp"));
                    oneDaYWeather.setThe_temp(firstDayFromAPI.getLong("the_temp"));
                    oneDaYWeather.setWind_speed(firstDayFromAPI.getLong("wind_speed"));
                    oneDaYWeather.setWind_direction(firstDayFromAPI.getLong("wind_direction"));
                    oneDaYWeather.setAir_pressure(firstDayFromAPI.getInt("air_pressure"));
                    oneDaYWeather.setHumidity(firstDayFromAPI.getInt("humidity"));
                    oneDaYWeather.setVisibility(firstDayFromAPI.getLong("visibility"));
                    oneDaYWeather.setPredictability(firstDayFromAPI.getInt("predictability"));
                    weatherReportModels.add(oneDaYWeather);
                }
                    foreCastByIDResponse.onResponse(weatherReportModels);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public interface GetCityForecastByNameCallback
    {
        void onError(String message);
        void onResponse(List<WeatherReportModel> weatherReportModels);
    }
    public void getCityForecastByName(String cityName,GetCityForecastByNameCallback getCityForecastByNameCallback){
        getCityID(cityName, new VolleyResponseListener() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(String cityID) {
                getCityForecastByID(cityID, new ForeCastByIDResponse() {
                    @Override
                    public void onError(String message) {

                    }

                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReportModels) {
                        getCityForecastByNameCallback.onResponse(weatherReportModels);
                    }
                });
            }
        });
    }
}
