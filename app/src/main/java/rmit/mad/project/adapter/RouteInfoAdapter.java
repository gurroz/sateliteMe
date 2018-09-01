package rmit.mad.project.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import rmit.mad.project.R;
import rmit.mad.project.controller.TrackingCreateController;
import rmit.mad.project.model.RouteInfo;


public class RouteInfoAdapter extends RecyclerView.Adapter<RouteInfoAdapter.ViewHolder> {

    private List<RouteInfo> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View customView;
        public ViewHolder(View v) {
            super(v);
            customView = v;
        }
    }

    public RouteInfoAdapter(List<RouteInfo> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public RouteInfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_route_info, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final RouteInfo routeInfo = mDataset.get(position);
        View customView = holder.customView;

        TextView locationView = customView.findViewById(R.id.actual_location);
        TextView startView = customView.findViewById(R.id.start);
        TextView finishView = customView.findViewById(R.id.finish);

        locationView.setText(routeInfo.getLocation());
        startView.setText(routeInfo.getStartDate());
        finishView.setText(routeInfo.getEndDate());

        if(routeInfo.getTimeStopped() > 0) {
            ImageView calendarView = customView.findViewById(R.id.calendar);
            calendarView.setVisibility(View.VISIBLE);

            customView.setOnClickListener(new TrackingCreateController(routeInfo));
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}
