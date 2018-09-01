package rmit.mad.project.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rmit.mad.project.R;
import rmit.mad.project.controller.TrackingDetailController;
import rmit.mad.project.model.Trackable;

public class TrackableAdapter extends RecyclerView.Adapter<TrackableAdapter.ViewHolder> {

    private List<Trackable> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View customView;
        public ViewHolder(View v) {
            super(v);
            customView = v;
        }
    }

    public TrackableAdapter() {
        mDataset = new ArrayList<>();
    }

    @Override
    public TrackableAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_trackables, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final View customView = holder.customView;
        final Trackable trackable = mDataset.get(position);

        customView.setOnClickListener(new TrackingDetailController(trackable.getId()));

        TextView nameView = customView.findViewById(R.id.name);
        nameView.setText(trackable.getName());

        TextView descriptionView = customView.findViewById(R.id.description);
        descriptionView.setText(trackable.getDescription());

        ImageView imageView = customView.findViewById(R.id.trackableImage);
        imageView.setImageDrawable(customView.getResources().getDrawable(R.drawable.foodtruck,null));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void updateData(List<Trackable> myDataset) {
        mDataset = myDataset;
        notifyDataSetChanged();
    }

}
