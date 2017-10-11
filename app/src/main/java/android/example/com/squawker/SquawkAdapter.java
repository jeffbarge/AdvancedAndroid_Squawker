/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package android.example.com.squawker;

import android.example.com.squawker.data.Squawk;
import android.example.com.squawker.provider.SquawkContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.threeten.bp.Instant;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts cursor data for squawk messages into visible list items in a RecyclerView
 */
public class SquawkAdapter extends RecyclerView.Adapter<SquawkAdapter.SquawkViewHolder> {


    private List<Squawk> mData = new ArrayList<>();
    private static DateTimeFormatter sFormatter = DateTimeFormatter.ofPattern("dd MMM");

    @Override
    public SquawkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_squawk_list, parent, false);

        SquawkViewHolder vh = new SquawkViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(SquawkViewHolder holder, int position) {
        final Squawk squawk = mData.get(position);

        String message = squawk.getMessage();
        String author = squawk.getAuthor();
        String authorKey = squawk.getAuthorKey();

        // Get the date for displaying
        final Instant timeStamp = squawk.getTimestamp();
        final Instant now = Instant.now();
        String date = "";

        // Change how the date is displayed depending on whether it was written in the last minute,
        // the hour, etc.
        if (ChronoUnit.DAYS.between(timeStamp, now) < 1) {
            if (ChronoUnit.HOURS.between(timeStamp, now) < 1) {
                date = String.valueOf(ChronoUnit.MINUTES.between(timeStamp, now)) + "m";
            } else {
                date = String.valueOf(ChronoUnit.HOURS.between(timeStamp, now)) + "h";
            }
        } else {
            date = sFormatter.format(timeStamp);
        }

        // Add a dot to the date string
        date = "\u2022 " + date;

        holder.messageTextView.setText(message);
        holder.authorTextView.setText(author);
        holder.dateTextView.setText(date);

        // Choose the correct, and in this case, locally stored asset for the instructor. If there
        // were more users, you'd probably download this as part of the message.
        switch (authorKey) {
            case SquawkContract.ASSER_KEY:
                holder.authorImageView.setImageResource(R.drawable.asser);
                break;
            case SquawkContract.CEZANNE_KEY:
                holder.authorImageView.setImageResource(R.drawable.cezanne);
                break;
            case SquawkContract.JLIN_KEY:
                holder.authorImageView.setImageResource(R.drawable.jlin);
                break;
            case SquawkContract.LYLA_KEY:
                holder.authorImageView.setImageResource(R.drawable.lyla);
                break;
            case SquawkContract.NIKITA_KEY:
                holder.authorImageView.setImageResource(R.drawable.nikita);
                break;
            default:
                holder.authorImageView.setImageResource(R.drawable.test);
        }
    }

    @Override
    public int getItemCount() {
        if (null == mData) return 0;
        return mData.size();
    }

    public void setSquawks(List<Squawk> squawks) {
        mData.clear();
        mData.addAll(squawks);
        notifyDataSetChanged();
    }

    public class SquawkViewHolder extends RecyclerView.ViewHolder {
        final TextView authorTextView;
        final TextView messageTextView;
        final TextView dateTextView;
        final ImageView authorImageView;

        public SquawkViewHolder(View layoutView) {
            super(layoutView);
            authorTextView = layoutView.findViewById(R.id.author_text_view);
            messageTextView = layoutView.findViewById(R.id.message_text_view);
            dateTextView = layoutView.findViewById(R.id.date_text_view);
            authorImageView = layoutView.findViewById(
                    R.id.author_image_view);
        }
    }
}
