package com.hoavy.orapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hoavy.orapp.api.ApiUtils;
import com.hoavy.orapp.api.RetrofitAPI;
import com.hoavy.orapp.databinding.ActivityPostBinding;
import com.hoavy.orapp.models.dtos.response.PostResponse;
import com.hoavy.orapp.ui.home.HomeFragment;
import com.hoavy.orapp.utils.SharedHelper;

import org.w3c.dom.Text;

import java.time.Duration;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostActivity extends AppCompatActivity {
    private SharedHelper sharedHelper;
    private RetrofitAPI mRetrofitAPI;
    private PostResponse post;
    private ImageView imgPostDetails, imgOwnerPostAvatar;
    private TextView titlePostDetails, descriptionPostDetails, pricePostDetails, durationPostDetails,
            ownerPostName, ownerPostEmail, ownerPostPhone, postCategory;
    Button selectPostDetails;
    ImageButton buttonEditPost;
    CardView cardOwnerPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        mRetrofitAPI = ApiUtils.getAPIService();
        sharedHelper = SharedHelper.getInstance(this);
        Bundle bundle = getIntent().getBundleExtra("post");
        String id = bundle.getString("id");


        binding();

        if (post != null) {
            titlePostDetails.setText(post.getTitle());
            descriptionPostDetails.setText(post.getDescription());
            String postPrice = "$" + String.valueOf(post.getPrice());
            pricePostDetails.setText(postPrice);
            String postDuration = "";
            if (post.getDuration() > 1) {
                postDuration = String.valueOf(post.getDuration()) + " days";
            } else {
                postDuration = String.valueOf(post.getDuration()) + " day";
            }
            durationPostDetails.setText(postDuration);
            ownerPostEmail.setText(post.getAuthor().getEmail());
            String authorName = post.getAuthor().getFirstName()
                    + " " + post.getAuthor().getLastName();
            ownerPostName.setText(authorName);

            if (post.getAuthor().getPhone() != null) {
                ownerPostPhone.setText(post.getAuthor().getPhone());
            }

            if (post.getAuthor().getFeaturedAvatar() != null) {
                Glide.with(this)
                        .load(post.getAuthor().getFeaturedAvatar())
                        .into(imgOwnerPostAvatar);
            }

            if (TextUtils.equals(post.getAuthorId(), sharedHelper.getId())) {
                selectPostDetails.setVisibility(View.INVISIBLE);
                buttonEditPost.setVisibility(View.VISIBLE);
                cardOwnerPost.setVisibility(View.INVISIBLE);
            } else {
                buttonEditPost.setVisibility(View.INVISIBLE);
                cardOwnerPost.setVisibility(View.VISIBLE);
            }

            selectPostDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

//        String title = bundle.getString("title");
//        String description = bundle.getString("description");
//        String featuredImage = bundle.getString("featuredImage");
//        double price = bundle.getDouble("price");
//        double duration = bundle.getDouble("duration");
//        String postPrice = "$" + String.valueOf(price);
//        String postDuration = "";
//
//        if (duration > 1) {
//            postDuration = String.valueOf(duration) + " days";
//        } else {
//            postDuration = String.valueOf(duration) + " day";
//        }
//        String authorId = bundle.getString("authorId");
//        String authorName = bundle.getString("authorName");
//        String authorEmail = bundle.getString("authorEmail");
//        String authorPhone = bundle.getString("authorPhone");
//        String authorAvatar = bundle.getString("authorAvatar");
//
//        Glide.with(this)
//                .load(featuredImage)
//                .into(imgPostDetails);
//        titlePostDetails.setText(title);
//        descriptionPostDetails.setText(description);
//        pricePostDetails.setText(postPrice);
//        durationPostDetails.setText(postDuration);
//
//        if (TextUtils.equals(authorId, sharedHelper.getId())) {
//
//            buttonEditPost.setVisibility(View.VISIBLE);
//            cardOwnerPost.setVisibility(View.INVISIBLE);
//        } else {
//            buttonEditPost.setVisibility(View.INVISIBLE);
//            cardOwnerPost.setVisibility(View.VISIBLE);
//        }
//
//        selectPostDetails.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        ownerPostName.setText(authorName);
//        ownerPostEmail.setText(authorEmail);
//        ownerPostPhone.setText(authorPhone);
//
//        if (!authorAvatar.isEmpty()) {
//            Glide.with(this)
//                    .load(authorAvatar)
//                    .into(imgOwnerPostAvatar);
//        }
    }

    private void binding() {
        imgPostDetails = (ImageView) findViewById(R.id.imgPostDetails);
        titlePostDetails = (TextView) findViewById(R.id.titlePostDetail);
        descriptionPostDetails = (TextView) findViewById(R.id.descriptionPostDetails);
        durationPostDetails = (TextView) findViewById(R.id.durationPostDetails);
        pricePostDetails = (TextView) findViewById(R.id.pricePostDetail);
        selectPostDetails = (Button) findViewById(R.id.selectPostDetails);
        buttonEditPost = (ImageButton) findViewById(R.id.btnEditPost);
        cardOwnerPost = (CardView) findViewById(R.id.card_owner_post);
        postCategory = (TextView) findViewById(R.id.postCategory);

        ownerPostEmail = (TextView) findViewById(R.id.ownerPostEmail);
        ownerPostName = (TextView) findViewById(R.id.ownerPostName);
        ownerPostPhone = (TextView) findViewById(R.id.ownerPostPhone);
        imgOwnerPostAvatar = (ImageView) findViewById(R.id.imgOwnerPostAvatar);
    }
}