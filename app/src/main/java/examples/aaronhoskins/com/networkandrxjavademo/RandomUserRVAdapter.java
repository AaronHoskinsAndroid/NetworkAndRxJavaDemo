package examples.aaronhoskins.com.networkandrxjavademo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import examples.aaronhoskins.com.networkandrxjavademo.model.randomuser.RandomMeResponse;
import examples.aaronhoskins.com.networkandrxjavademo.model.randomuser.Result;

public class RandomUserRVAdapter extends RecyclerView.Adapter<RandomUserRVAdapter.ViewHolder> {
    ArrayList<Result> resultList;

    public RandomUserRVAdapter(ArrayList<Result> resultList) {
        this.resultList = resultList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.random_user_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Result result = resultList.get(position);
        holder.tvEmail.setText(result.getEmail());
        holder.tvLocation.setText(String.format("%s, %s %s",
                result.getLocation().getCity(),
                result.getLocation().getState(),
                result.getLocation().getPostcode()));
        Glide
                .with(holder.itemView)
                .load(result.getPicture().getThumbnail())
                .into(holder.imgThumbnailImage);
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public void addToList(RandomMeResponse response) {
        for(Result result : response.getResults()) {
            resultList.add(result);
            notifyDataSetChanged();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvEmail;
        TextView tvLocation;
        ImageView imgThumbnailImage;
        public ViewHolder(View itemView) {
            super(itemView);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            imgThumbnailImage = itemView.findViewById(R.id.imgRandomUserThumbnail);
        }
    }
}
