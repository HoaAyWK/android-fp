package com.hoavy.orapp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hoavy.orapp.BuildConfig;
import com.hoavy.orapp.R;
import com.hoavy.orapp.api.ApiUtils;
import com.hoavy.orapp.api.RetrofitAPI;
import com.hoavy.orapp.models.Post;
import com.hoavy.orapp.models.dtos.response.Freelancer;
import com.hoavy.orapp.models.dtos.response.PostRequest;
import com.hoavy.orapp.models.dtos.response.PostResponse;
import com.hoavy.orapp.models.dtos.response.SelectResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FreelancerRequestAdapter extends RecyclerView.Adapter<FreelancerRequestAdapter.FreelancerRequestHolder> {

    private List<PostRequest> postRequests = new ArrayList<>();
    private Context context;
    private RetrofitAPI mRetrofitAPIAuth;

    public FreelancerRequestAdapter(Context context) {
        this.context = context;
        mRetrofitAPIAuth = ApiUtils.getAuthAPIService(context);
    }

    public void setPostRequests(List<PostRequest> postRequests) {
        this.postRequests = postRequests;
    }

    @NonNull
    @Override
    public FreelancerRequestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_freelancer_card, parent, false);

        return new FreelancerRequestHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FreelancerRequestHolder holder, int position) {
        PostRequest postRequest = postRequests.get(position);
        Freelancer freelancer = postRequest.getFreelancer();
        String name = freelancer.getFirstName() + " " + freelancer.getLastName();

        holder.freelancerName.setText(name);

        if (freelancer.getFeaturedAvatar() != null) {
            Glide.with(holder.itemView)
                    .load(freelancer.getFeaturedAvatar())
                    .into(holder.freelancerAvatar);
        }

        holder.freelancerPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle("Select Freelancer")
                        .setMessage("Are your sure to select '" + name + "' to do the job")
                        .setCancelable(true);

                builder.setPositiveButton("Select", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mRetrofitAPIAuth.processPost(postRequest.getPostId(),
                                freelancer.getId()).enqueue(new Callback<SelectResponse>() {
                            @Override
                            public void onResponse(Call<SelectResponse> call, Response<SelectResponse> response) {
                                if (response.body() != null) {
                                    if (response.code() == 200) {
                                        holder.selectButton.setText(R.string.selected);
                                        Toast.makeText(context, "Selected '" + name + "'", Toast.LENGTH_SHORT).show();
                                    }
                                    dialog.cancel();
                                }
                                if (response.code() != 200) {
                                    Toast.makeText(context, "Cannot select freelancer: " + name, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<SelectResponse> call, Throwable t) {
                                Toast.makeText(context, "Cannot select freelancer: " + name, Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        });
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return postRequests.size();
    }

    public class FreelancerRequestHolder extends RecyclerView.ViewHolder {

        private LinearLayout freelancerPanel;
        private ImageView freelancerAvatar;
        private TextView freelancerName;
        private Button selectButton;

        public FreelancerRequestHolder(@NonNull View itemView) {
            super(itemView);

            freelancerAvatar = itemView.findViewById(R.id.imgFreelancerAvatar);
            freelancerName = itemView.findViewById(R.id.freelancerName);
            freelancerPanel = itemView.findViewById(R.id.freelancerPanel);
            selectButton = itemView.findViewById(R.id.selectFreelancer);

        }
    }
}
