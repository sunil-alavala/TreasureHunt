package com.geoschnitzel.treasurehunt.shlist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.geoschnitzel.treasurehunt.R;
import com.geoschnitzel.treasurehunt.game.GameActivity;
import com.geoschnitzel.treasurehunt.rest.SHListItem;

import java.util.List;

public class SHListAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
    static class SHListViewHolderItem {
        TextView name;
        TextView author;
        TextView length;
        TextView description;
        TextView visited;
    }

    List<SHListItem> items;
    Context context;

    public SHListAdapter(List<SHListItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public SHListItem getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return items.get(i).getHuntid();
    }


    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        SHListViewHolderItem viewHolder;

        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_shlist, null, false);

            viewHolder = new SHListViewHolderItem();

            viewHolder.name = convertView.findViewById(R.id.shlist_item_name);
            viewHolder.author = convertView.findViewById(R.id.shlist_item_author);
            viewHolder.length = convertView.findViewById(R.id.shlist_item_length);
            viewHolder.description = convertView.findViewById(R.id.shlist_item_description);
            viewHolder.visited = convertView.findViewById(R.id.shlist_item_visited);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (SHListViewHolderItem) convertView.getTag();
        }

        SHListItem item = items.get(i);
        viewHolder.name.setText(item.getName());
        viewHolder.author.setText(item.getAuthor());
        viewHolder.length.setText(Float.toString(item.getLength()));
        viewHolder.description.setText(item.getDescription());

        if(item.getVisited()) {
            viewHolder.visited.setBackgroundColor(Color.parseColor("#4bf442"));
        }
        else {
            viewHolder.visited.setBackgroundColor(Color.parseColor("#ff0000"));
        }

        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SHListItem item = getItem(position);

        Intent game_intent = new Intent(context, GameActivity.class);
        Bundle b = new Bundle();
        b.putLong("huntID", id);
        game_intent.putExtras(b);
        context.startActivity(game_intent);
    }
}
