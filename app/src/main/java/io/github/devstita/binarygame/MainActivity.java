package io.github.devstita.binarygame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;

import io.github.devstita.binarygame.databinding.ActivityMainBinding;

// TODO: Implement animations (nextNum to currentNum / beat when clicked / wrong input)
public class MainActivity extends AppCompatActivity {
    private final static int MAX_BITS = 6;

    private ActivityMainBinding binding;
    private int num, idx;
    private int[] numBits;

    Handler uiHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        num = 0;
        idx = 0;
        numBits = intToBinary(0);

        uiHandler = new Handler();

        binding.zeroButton.setOnClickListener(view -> {
            update(0);
        });

        binding.oneButton.setOnClickListener(view -> {
            update(1);
        });
    }

    static int boolToInt(boolean in) {
        return in ? 1 : 0;
    }

    static int[] intToBinary(int n) {
        int[] bits = new int[MAX_BITS];
        for (int i = MAX_BITS - 1; i >= 0; i--) {
            bits[i] = boolToInt((n & (1 << i)) != 0);
        }

        int filledIdx = 0;
        for (int i = MAX_BITS - 1; i >= 0; i--) {
            if (bits[i] == 1) {
                filledIdx = i;
                break;
            }
        }

        int[] ret = new int[filledIdx + 1];
        for (int i = 0; i <= filledIdx; i++) {
            ret[i] = bits[filledIdx - i];
        }

        return ret;
    }

    protected void update(int input) {
        /*
         * input: 0/1 -> button input, -1 -> force correct, -2 -> force incorrect
         */

        if (input == numBits[idx] || input == -1) { // Correct
            binding.showingTextView.setText(String.valueOf(input));
            if (numBits.length == ++idx) {
                numBits = intToBinary(++num);
                idx = 0;

                binding.currentTextView.setText(String.valueOf(num));
                binding.nextTextView.setText(String.valueOf(num + 1));

                Log.d("Debug", Arrays.toString(numBits));
            }
        } else { // Incorrect
            Toast.makeText(getApplicationContext(), "No no !!", Toast.LENGTH_SHORT).show();
        }
    }
}
