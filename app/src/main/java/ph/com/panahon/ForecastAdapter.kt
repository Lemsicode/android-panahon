package ph.com.panahon

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView

class ForecastAdapter(private var forecasts: ArrayList<Forecast>, private var weather: Weather, private var degreeUnitCode: Int) : RecyclerView.Adapter<ForecastViewHolder>() {

    private var allHolders: ArrayList<ForecastViewHolder> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.forecast_template, parent, false)
        val fvh = ForecastViewHolder(view)
        allHolders.add(fvh)

        return fvh
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {

        holder.setDay(forecasts[position].day)
        holder.location = forecasts[position].location
        holder.setWeather(forecasts[position].weather)
        holder.setTemperature(forecasts[position].temperature, forecasts[position].unitDegree)
        holder.humidityPercentage = forecasts[position].humidity
        holder.precipitationPercentage = forecasts[position].precipitation

        holder.bg.setOnClickListener {
            weather.setLocation(holder.location)
            weather.setWeather(holder.weatherCode)

            if (degreeUnitCode == Weather.C)
                weather.setTemperature(holder.celsius, Weather.C)
            else
                weather.setTemperature(holder.fahrenheit, Weather.F)

            weather.setUnitDegree(degreeUnitCode)
            weather.setHumidityPercentage(holder.humidityPercentage)
            weather.setPrecipitationPercentage(holder.precipitationPercentage)

            if (position == 0)
                weather.setDay("Today")
            else
                weather.setDay(holder.today)
        }

        if (degreeUnitCode == Weather.F)
            holder.setUnitDegree(Weather.F)

        weather.tvTemperature.setOnClickListener {
            if (weather.degreeUnitCode == Weather.C) {
                weather.setUnitDegree(Weather.F)
            } else {
                weather.setUnitDegree(Weather.C)
            }

            for (item in allHolders) {
                item.setUnitDegree(weather.degreeUnitCode)
            }
        }
    }

    override fun getItemCount(): Int {
        return forecasts.size
    }
}