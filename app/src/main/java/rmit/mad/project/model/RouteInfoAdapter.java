package rmit.mad.project.model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

import rmit.mad.project.R;

public class RouteInfoAdapter extends RecyclerView.Adapter<RouteInfoAdapter.ViewHolder> {

    private List<TrackingService.TrackingInfo> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View customView;
        public ViewHolder(View v) {
            super(v);
            customView = v;
        }
    }

    public RouteInfoAdapter(List<TrackingService.TrackingInfo> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public RouteInfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_route_info, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        View customView = holder.customView;
        TrackingService.TrackingInfo routeInfo = mDataset.get(position);

        TextView locationView = customView.findViewById(R.id.actual_location);
        TextView startView = customView.findViewById(R.id.start);
        TextView finishView = customView.findViewById(R.id.finish);

        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);

        locationView.setText(routeInfo.latitude + ", " + routeInfo.longitude);
        startView.setText(dateFormat.format(routeInfo.date));

        Calendar endDate= Calendar.getInstance();
        endDate.setTime(routeInfo.date);
        endDate.set(Calendar.MINUTE, endDate.get(Calendar.MINUTE) + routeInfo.stopTime);

        finishView.setText(dateFormat.format(endDate.getTime()));

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
