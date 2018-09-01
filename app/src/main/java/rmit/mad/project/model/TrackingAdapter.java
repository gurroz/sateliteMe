package rmit.mad.project.model;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.List;

import rmit.mad.project.R;
import rmit.mad.project.controller.TrackingEditActivity;

import static rmit.mad.project.enums.IntentModelEnum.TRACKABLE_ID;
import static rmit.mad.project.enums.IntentModelEnum.TRACKING_ID;

public class TrackingAdapter extends RecyclerView.Adapter<TrackingAdapter.ViewHolder> {

    private List<Tracking> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View customView;
        public ViewHolder(View v) {
            super(v);
            customView = v;
        }
    }

    public TrackingAdapter(List<Tracking> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public TrackingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_tracking, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final View customView = holder.customView;
        final Tracking tracking = mDataset.get(position);

        customView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickReal(customView, tracking);
            }
        });

        TextView nameView = customView.findViewById(R.id.title);
        nameView.setText(tracking.getTitle());

        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);

        TextView meetTimeView = customView.findViewById(R.id.meetTime);
        meetTimeView.setText(df.format(tracking.getMeetTime()));

        TextView meetLocationView = customView.findViewById(R.id.meetLocation);
        meetLocationView.setText(tracking.getMeetLocation());

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void onClickReal(final View view, final Tracking tracking) {
        Intent intent = new Intent(view.getContext(), TrackingEditActivity.class);
        intent.putExtra(TRACKING_ID.name(), tracking.getId());
        intent.putExtra(TRACKABLE_ID.name(), tracking.getIdTrackable());
        view.getContext().startActivity(intent);
    }
}
