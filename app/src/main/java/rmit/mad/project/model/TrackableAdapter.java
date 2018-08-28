package rmit.mad.project.model;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import rmit.mad.project.R;
import rmit.mad.project.controller.TrackableDetailActivity;

public class TrackableAdapter extends RecyclerView.Adapter<TrackableAdapter.ViewHolder> {

    private List<Trackable> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View customView;
        public ViewHolder(View v) {
            super(v);
            customView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TrackableAdapter(List<Trackable> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public TrackableAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_trackables, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final View customView = holder.customView;
        final Trackable trackable = mDataset.get(position);

        customView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickReal(customView, trackable);
            }
        });

        TextView nameView = customView.findViewById(R.id.name);
        nameView.setText(trackable.getName());

        TextView descriptionView = customView.findViewById(R.id.description);
        descriptionView.setText(trackable.getDescription());

        ImageView imageView = customView.findViewById(R.id.trackableImage);
        imageView.setImageDrawable(customView.getResources().getDrawable(R.drawable.foodtruck,null));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void updateData(List<Trackable> myDataset) {
        mDataset = myDataset;
        notifyDataSetChanged();
    }

    private void onClickReal(final View view, final Trackable trackable) {
        Intent intent = new Intent(view.getContext(), TrackableDetailActivity.class);
        intent.putExtra("TRACKABLE_ID", trackable.getId());
        view.getContext().startActivity(intent);
    }


}
