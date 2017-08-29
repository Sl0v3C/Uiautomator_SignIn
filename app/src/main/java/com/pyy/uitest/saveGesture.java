package com.pyy.uitest;

/**
 * Created by pyy on 2017/8/29.
 */

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.pyy.uitest.LockPatternView.Cell;
import com.pyy.uitest.LockPatternView.DisplayMode;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class saveGesture extends Activity implements
        LockPatternView.OnPatternListener {
    List<Aint> array = null;
    private final int[][] ARRAY = {
            {248, 939},
            {538, 912},
            {804, 894},
            {280, 1166},
            {544, 1160},
            {805, 1173},
            {281, 1435},
            {535, 1426},
            {805, 1431},
    };
    private final String PATH =  Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/AutoSignIn/";
    private final String FILE  = PATH + "gesture.txt";
    private static final String logTag = "saveGesture";
    private LockPatternView lockPatternView;
    private Button lButton;
    private Button rButton;
    private List<Cell> savePattern;
    private boolean confirm = false;

    public static class Aint {
        int value;
        public Aint(int a) {
            this.value = a;
        }
        public int getValue() {return value;}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_gesture);
        lockPatternView = (LockPatternView) findViewById(R.id.lock_pattern);
        lockPatternView.setOnPatternListener(this);
        lButton = (Button) findViewById(R.id.left_btn);
        lButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirm = false;
                    update();
                }
            }
        );
        rButton = (Button) findViewById(R.id.right_btn);
        rButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveGesture();
                    saveGesture.this.finish();
                }
            }
        );
        update();
    }

    private void patternToArray(List<Cell> pattern) {
        if (pattern != null) {
            if (array == null)
                array = new ArrayList<Aint>();
            for (int i = 0; i < pattern.size(); i++) {
                int row = pattern.get(i).getRow();
                int column = pattern.get(i).getColumn();
                int index = row * 3 + column;
                array.add(new Aint(ARRAY[index][0]));
                array.add(new Aint(ARRAY[index][1]));
            }
        }
    }

    private String array2string(List<Aint> array){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < array.size(); i++){
            sb.append(array.get(i).getValue()).append(",");
        }
        sb.deleteCharAt(sb.length() -1);
        return sb.toString();
    }

    private void saveGesture() {
        File desDir = new File(PATH);
        try {
            if (!desDir.exists()) {
                boolean ret  = desDir.mkdir();
            }

            File file = new File(FILE);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fout = new FileOutputStream(file);
            fout.write(array2string(array).getBytes());
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void update() {
        if (!confirm) {
            lButton.setText(R.string.retap);
            rButton.setText(R.string.save);
            rButton.setEnabled(false);
            savePattern = null;
            if (array != null)
                array.clear();
            lockPatternView.clearPattern();
            lockPatternView.enableInput();
        } else {
            lButton.setText(R.string.retap);
            rButton.setText(R.string.save);
            rButton.setEnabled(true);
            lockPatternView.disableInput();
        }
    }

    @Override
    public void onPatternStart() {
        Log.d(logTag, "onPatternStart");
    }

    @Override
    public void onPatternCleared() {
        Log.d(logTag, "onPatternCleared");
    }

    @Override
    public void onPatternCellAdded(List<Cell> pattern) {
        Log.d(logTag, "onPatternCellAdded");
    }

    @Override
    public void onPatternDetected(List<Cell> pattern) {
        Log.d(logTag, "onPatternDetected");
        if (pattern.size() < LockPatternView.MIN_LOCK_PATTERN_SIZE) {
            Toast.makeText(this,
                    R.string.lockpattern_too_short,
                    Toast.LENGTH_LONG).show();
            lockPatternView.setDisplayMode(DisplayMode.Wrong);
            return;
        }

        if (savePattern == null) {
            savePattern = new ArrayList<Cell>(pattern);
            patternToArray(savePattern);
            confirm = true;
            update();
            return;
        }
    }
}
