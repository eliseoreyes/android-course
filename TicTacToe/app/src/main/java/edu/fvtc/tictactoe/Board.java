package edu.fvtc.tictactoe;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

public class Board {
    public static final int BOARDSIZE = 3;
    String[][] cellvalues = new String[BOARDSIZE][BOARDSIZE];
    Rect[][] cells = new Rect[BOARDSIZE][BOARDSIZE];
    String[][] computercellvalues = new String[BOARDSIZE][BOARDSIZE];
    int SIZE = 135;
    int OFFSET = 5;
    public static final String TAG = "Board";
    int viewWidth;
    int viewHeight;

    public Board()
    {

    }

    public Board(int width)
    {
        viewWidth = width / BOARDSIZE;
        viewHeight = viewWidth;
        SIZE = viewWidth - 3;

    }

    public Board(int width, int height)
    {
        viewWidth = width / BOARDSIZE;
        viewHeight = height / BOARDSIZE;
        SIZE = viewWidth - 3;
    }

    public String hitTest(Point pt, String turn)
    {
        String results = "-1";

        for (int row = 0; row < cells[0].length; row++) {
            for (int col = 0; col < cells[1].length; col++) {
                Log.d(TAG, "hitTest: ");
                cellvalues[row][col] = turn;
                results = turn + ":" + String.valueOf(row) + ":" + String.valueOf(col);
            }
        }
        return results;
    }

    public void Draw(Canvas canvas)
    {
        Log.d(TAG, "Draw: ");
        Paint paint = new Paint();
        paint.setColor(Color.LTGRAY);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        Paint paintGrid = new Paint();
        paintGrid.setColor(Color.BLACK);
        paintGrid.setStrokeWidth(20);
        paintGrid.setStyle(Paint.Style.FILL_AND_STROKE);

        for (int row = 0; row < cells[0].length; row++) {
            for (int col = 0; col < cells[1].length; col++) {
                cells[row][col] = new Rect();
                cells[row][col].left = col * SIZE + OFFSET;
                cells[row][col].top = row * SIZE + OFFSET;
                cells[row][col].right = col * SIZE + OFFSET + SIZE;
                cells[row][col].bottom = row * SIZE + OFFSET + SIZE;

                canvas.drawRect(cells[row][col], paint);
                canvas.drawLine(cells[row][col].right, cells[row][col].top, cells[row][col].right, cells[row][col].bottom, paintGrid);
                canvas.drawLine(cells[row][col].left, cells[row][col].bottom, cells[row][col].right, cells[row][col].bottom, paintGrid);

            }
        }



    }
}
