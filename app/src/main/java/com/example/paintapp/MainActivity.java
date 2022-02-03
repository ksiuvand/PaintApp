package com.example.paintapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import petrov.kristiyan.colorpicker.ColorPicker;
import com.google.android.material.slider.RangeSlider;




public class MainActivity extends AppCompatActivity {

    private DrawView paint;
    private ImageButton color, pen, lastic;
    private RangeSlider slider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paint = (DrawView) findViewById(R.id.draw_view);
        slider = (RangeSlider) findViewById(R.id.rangebar);
        lastic = findViewById(R.id.btn_lastic);
        color = (ImageButton) findViewById(R.id.btn_color);
        pen = (ImageButton) findViewById(R.id.btn_pen);

        pen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paint.setColor(Color.BLACK);
            }
        });

        lastic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paint.setColor(Color.WHITE);
            }
        });

        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ColorPicker colorPicker = new ColorPicker(MainActivity.this);
                colorPicker.setOnFastChooseColorListener(new ColorPicker.OnFastChooseColorListener() {
                    @Override
                    public void setOnFastChooseColorListener(int position, int color) {
                        paint.setColor(color);
                    }
                    @Override
                    public void onCancel() {
                        colorPicker.dismissDialog();
                    }
                })
                        .setColumns(5)
                        .setDefaultColorButton(Color.parseColor("#000000"))
                        .show();
            }
        });


        slider.setValueFrom(0.0f);
        slider.setValueTo(100.0f);

        slider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                paint.setStrokeWidth((int) value);
            }
        });

        ViewTreeObserver vto = paint.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                paint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width = paint.getMeasuredWidth();
                int height = paint.getMeasuredHeight();
                paint.init(height, width);
            }
        });
    }
}