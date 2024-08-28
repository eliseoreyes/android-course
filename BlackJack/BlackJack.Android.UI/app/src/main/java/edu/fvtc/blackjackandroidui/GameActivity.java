package edu.fvtc.blackjackandroidui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import edu.fvtc.blackjackandroidui.models.Deck;
import edu.fvtc.blackjackandroidui.models.Player;

public class GameActivity extends AppCompatActivity {

    public static final String TAG = GameActivity.class.toString();
    static int TWENTY_ONE = 21;
    static int MAX_CARDS_PER_HAND = 12;

    Button btnDeal;
    Button btnHit;
    Button btnStand;
    ImageButton btnImage;
    Deck deck = new Deck();
    Player player = new Player();
    Player dealer = new Player();

    LinearLayout.LayoutParams closedParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
    LinearLayout.LayoutParams openParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    LinearLayout resultsLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        btnDeal = findViewById(R.id.btnDeal);
        btnHit = findViewById(R.id.btnHit);
        btnStand = findViewById(R.id.btnStand);
        resultsLayout = findViewById(R.id.resultsLayout);

       // init();
    }

    private void init(){
        player.resetValues();;
        dealer.resetValues();

        for (ImageView imageView : player.getImages()) {
            imageView.setImageResource(R.drawable.back_blue);
        }

        for (ImageView imageView : dealer.getImages()) {
            imageView.setImageResource(R.drawable.back_blue);
        }

        resultsLayout.setLayoutParams(closedParams);

    }
}