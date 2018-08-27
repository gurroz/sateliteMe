package rmit.mad.project.model;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Logger;

import rmit.mad.project.R;
import rmit.mad.project.controller.TrackableDetailActivity;
import rmit.mad.project.controller.TrackingDetailActivity;

public class TrackingAdapter extends RecyclerView.Adapter<TrackingAdapter.ViewHolder> {

    private List<Tracking> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View customView;
        public ViewHolder(View v) {
            super(v);
            customView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TrackingAdapter(List<Tracking> myDataset) {
        mDataset = myDataset;
        Log.d("Asignment", "Trackins are: " + this.mDataset.size());
    }

    @Override
    public TrackingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_tracking, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final View customView = holder.customView;
        final Tracking tracking = mDataset.get(position);

        Log.d("ASIGMENT", "Trackin es" + tracking.toString());
        customView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickReal(customView, tracking);
            }
        });

        TextView nameView = customView.findViewById(R.id.title);
        nameView.setText(tracking.getTitle());

        SimpleDateFormat df = new SimpleDateFormat("HH:mm dd/MM/yy");

        TextView startView = customView.findViewById(R.id.start);
        startView.setText(df.format(tracking.getTargetStartTime()));

        TextView endView = customView.findViewById(R.id.finish);
        endView.setText(df.format(tracking.getTargetFinishTime()));

        TextView locationView = customView.findViewById(R.id.actual_location);
        locationView.setText(tracking.getActualLocation());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void onClickReal(final View view, final Tracking tracking) {
        Intent intent = new Intent(view.getContext(), TrackingDetailActivity.class);
        intent.putExtra("TRACKING_ID", tracking.getId());
        view.getContext().startActivity(intent);
    }
}
