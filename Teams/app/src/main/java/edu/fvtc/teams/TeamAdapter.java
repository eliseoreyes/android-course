package edu.fvtc.teams;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TeamAdapter extends RecyclerView.Adapter {
    boolean isDeleting;
    private ArrayList<Team> TeamData;
    private View.OnClickListener onItemClickListener;
    private CompoundButton.OnCheckedChangeListener onItemCheckedChangedListener;
    public static final String TAG = "TeamAdapter";

    private Context parentContext;

    public void setDelete(boolean b) {
        isDeleting = b;
    }

    public class TeamViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName;
        private TextView tvCity;
        private Button btnDelete;
        private CheckBox chkFavorite;

        private ImageButton imageButtonPhoto;

        private View.OnClickListener onClickListener;
        private CompoundButton.OnCheckedChangeListener onCheckedChangeListener;
        public TeamViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvCity = itemView.findViewById(R.id.tvCity);
            chkFavorite = itemView.findViewById(R.id.chkFavorite);
            imageButtonPhoto = itemView.findViewById(R.id.imgPhoto);
            btnDelete = itemView.findViewById(R.id.btnDelete);

            itemView.setTag(this);
            itemView.setOnClickListener(onItemClickListener);
            chkFavorite.setTag(this);
            chkFavorite.setOnCheckedChangeListener(onCheckedChangeListener);
        }

        public TextView getName()
        {
            return tvName;
        }
        public TextView getCity()
        {
            return tvCity;
        }
        public Button getBtnDelete() {return btnDelete; }

        public CheckBox getChkFavorite()
        {
            return chkFavorite;
        }
        public ImageButton getImageButtonPhoto()
        {
            return imageButtonPhoto;
        }

    }

    public TeamAdapter(ArrayList<Team> data, Context context)
    {
        TeamData = data;
        Log.d(TAG, "TeamAdapter: " + data.size());
        parentContext = context;
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener)
    {
        Log.d(TAG, "setOnItemClickListener: ");
        onItemClickListener = itemClickListener;
    }

    public void setOnItemCheckedChangedListener(CompoundButton.OnCheckedChangeListener listener)
    {
        Log.d(TAG, "setOnItemCheckedChangedListener: ");
        onItemCheckedChangedListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new TeamViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + TeamData.get(position));

        Team team = TeamData.get(position);

        TeamViewHolder teamViewHolder = (TeamViewHolder) holder;
        teamViewHolder.getName().setText(team.getName());
        teamViewHolder.getCity().setText(team.getCity());
        //teamViewHolder.getImageButtonPhoto().setImageResource(team.getImgId());
        teamViewHolder.getChkFavorite().setChecked(team.getIsfavorite());


        Bitmap teamPhoto = team.getPhoto();

        if(teamPhoto != null)
        {
            teamViewHolder.getImageButtonPhoto().setImageBitmap(teamPhoto);
        }
        else {
            teamViewHolder.getImageButtonPhoto().setImageResource(R.drawable.photoicon);
        }

        if(isDeleting)
            teamViewHolder.getBtnDelete().setVisibility(View.VISIBLE);
        else
            teamViewHolder.getBtnDelete().setVisibility(View.INVISIBLE);


        teamViewHolder.getBtnDelete().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Delete");
                deleteItem(position);
            }
        });

        teamViewHolder.getChkFavorite().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, "onCheckedChanged: " + isChecked);
                onItemCheckedChangedListener.onCheckedChanged(buttonView, isChecked);
            }
        });

    }

    private void deleteItem(int position) {


        try {
            Log.d(TAG, "deleteItem: Start");
            Team team = TeamData.get(position);
            //TeamDataSource ds = new TeamDataSource(parentContext);
            //ds.open();
            Log.d(TAG, "deleteItem: " + team.getName());
            //boolean didDelete = ds.delete(team) > 0;

            RestClient.execDeleteRequest(team,
                    TeamListActivity.TEAMSAPI + team.getId(),
                    this.parentContext,
                    new VolleyCallback() {
                        @Override
                        public void onSuccess(ArrayList<Team> result) {
                            Log.d(TAG, "onSuccess: Delete" + team.getId());
                            TeamData.remove(position);
                            notifyDataSetChanged();
                        }
                    });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public int getItemCount() {
        return TeamData.size();
    }
}

