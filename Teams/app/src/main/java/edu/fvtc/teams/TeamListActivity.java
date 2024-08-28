package edu.fvtc.teams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TeamListActivity extends AppCompatActivity {
    public static final String TAG = "TeamListActivity";
    public static final String TEAMSAPI = "https://fvtcdp.azurewebsites.net/api/team/";
    public static final String FILENAME = "teams.txt";
    ArrayList<Team> teams;
    RecyclerView teamList;
    TeamAdapter teamAdapter;

    // String sorter
    Comparator<Team> nameComparator = (c1, c2) -> c1.getName().compareTo(c2.getName());
    Comparator<Team> cityComparator = (c1, c2) -> c1.getCity().compareTo(c2.getCity());
    Comparator<Team> isFavoriteComparator = (c1, c2) -> (String.valueOf(c1.getIsfavorite()).compareTo(String.valueOf(c2.getIsfavorite())));

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            int id = teams.get(position).getId();
            Team team = teams.get(position);
            Log.d(TAG, "onClick: " + team.getName());

            Intent intent = new Intent(TeamListActivity.this, TeamEditActivity.class);
            intent.putExtra("teamid", team.getId());
            startActivity(intent);
        }
    };

    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) buttonView.getTag();
            int position = viewHolder.getAdapterPosition();
            int id = teams.get(position).getId();
            teams.get(position).setIsfavorite(isChecked);
            Log.d(TAG, "onClick: " + teams.get(position).getName());
            RestClient.execPutRequest(teams.get(position),
                    TeamListActivity.TEAMSAPI + id,
                    TeamListActivity.this,
                    new VolleyCallback() {
                        @Override
                        public void onSuccess(ArrayList<Team> result) {
                            Log.d(TAG, "onSuccess: Post" + teams.get(position).getId());
                            RebindScreen();
                        }
                    });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_list);

        teams = new ArrayList<Team>();

        // init buttons
        Navbar.initListButton(this);
        Navbar.initMapButton(this);
        Navbar.initSettingsButton(this);

        this.setTitle(getString(R.string.team_list));

        initDeleteButton();
        initAddTeamButton();

        //initDatabase();
        readFromAPI();


        // Get the battery life
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                double batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                double levelScale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
                int batteryPercent = (int)Math.floor(batteryLevel / levelScale * 100);

                TextView txtBatteryLevel = findViewById(R.id.txtBatteryLevel);
                txtBatteryLevel.setText(batteryPercent + "%");
            }
        };

        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(broadcastReceiver, filter);


        Log.d(TAG, "onCreate: End");

    }

    private void readFromAPI()
    {
        try{
            Log.d(TAG, "readFromAPI: Start");

            RestClient.execGetRequest(TEAMSAPI, this,
                    new VolleyCallback() {
                        @Override
                        public void onSuccess(ArrayList<Team> result) {
                            Log.d(TAG, "onSuccess: " + result.size());
                            teams = result;
                            for (Team t : teams) {
                                Log.d(TAG, "onSuccess: " + t.getName());
                            }
                            RebindScreen();
                            //teamAdapter.notifyDataSetChanged();
                        }
                    });
        }
        catch(Exception e)
        {

        }
    }

    private void initDatabase() {
        TeamDataSource ds = new TeamDataSource(this);
        ds.open();
        //teams = ds.get();
        if(teams.size() == 0)
        {
            ds.refreshData();
            //teams = ds.get();
        }
        ds.close();
        Log.d(TAG, "initDatabase: Teams: " + teams.size());
    }

    private void initDeleteButton() {
        Switch switchDelete = findViewById(R.id.switchDelete);

        switchDelete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d(TAG, "onCheckedChanged: " + b);
                teamAdapter.setDelete(b);
                teamAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initAddTeamButton() {
        Button btnAddTeam = findViewById(R.id.buttonAddTeam);
        btnAddTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TeamListActivity.this, TeamEditActivity.class);
                intent.putExtra("teamid", -1);
                startActivity(intent);
            }
        });
    }

    public static String[] createTeamArray(ArrayList<Team> teams) {
        String[] teamData = new String[teams.size()];
        for(Integer count = 0; count < teams.size(); count++)
        {
            Log.d(TAG, "createTeamArray: " + teams.get(count).toString());
            teamData[count] = teams.get(count).toString();
        }
        return teamData;
    }

    public static ArrayList<Team> readFromTextFile(AppCompatActivity activity) {
        Log.d(TAG, "readFromTextFile: Start");
        ArrayList<String> strData = FileIO.readFile(FILENAME, activity);
        Log.d(TAG, "readFromTextFile: After read");
        ArrayList<Team> teams = new ArrayList<Team>();

        for(String s : strData)
        {
            Log.d(TAG, "readFromTextFile: " + s);
            String[] data = s.split("\\|");
            teams.add(new Team(
                    Integer.parseInt(data[0]),
                    data[1],
                    data[2],
                    data[3],
                    Float.parseFloat(data[4]),
                    Boolean.parseBoolean(data[5]),
                    Integer.parseInt(data[6]),
                    Double.parseDouble(data[7]),
                    Double.parseDouble(data[8])
            ));
        }
        Log.d(TAG, "readFromTextFile: " + teams.size());
        return teams;
    }

    public void RebindScreen()
    {
        Log.d(TAG, "RebindScreen: Start: " + teams.size());
        sortTeams();
        teamList = findViewById(R.id.rvTeams);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        teamList.setLayoutManager(layoutManager);
        teamAdapter = new TeamAdapter(teams, this);
        teamAdapter.setOnItemClickListener(onClickListener);
        teamAdapter.setOnItemCheckedChangedListener(onCheckedChangeListener);
        teamList.setAdapter(teamAdapter);

        teamAdapter.notifyDataSetChanged();

        Log.d(TAG, "RebindScreen: End: "+ teams.size());
    }

    private void sortTeams()
    {
        String sortBy = getSharedPreferences("teamspreferences",
                Context.MODE_PRIVATE)
                .getString("sortfield", "name");
        String sortOrder = getSharedPreferences("teamspreferences",
                Context.MODE_PRIVATE)
                .getString("sortorder", "ASC");

        Log.d(TAG, "sortTeams: " + sortBy + ":" + sortOrder);

        if(sortOrder == "ASC"){
            if(sortBy == "name") teams.sort(nameComparator);
            if(sortBy == "city") teams.sort(cityComparator);
            if(sortBy == "isfavorite") teams.sort(isFavoriteComparator);
        }
        else {
            if(sortBy == "name") teams.sort(Collections.reverseOrder(nameComparator));
            if(sortBy == "city") teams.sort(Collections.reverseOrder(cityComparator));
            if(sortBy == "isfavorite") teams.sort(Collections.reverseOrder(isFavoriteComparator));
        }
    }
}