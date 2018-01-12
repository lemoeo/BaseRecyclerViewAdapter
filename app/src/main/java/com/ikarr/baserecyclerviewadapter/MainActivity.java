package com.ikarr.baserecyclerviewadapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.ikarr.baserecyclerviewadapter.adapter.DataBindingAdapter;
import com.ikarr.library.BaseRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rv = findViewById(R.id.recyclerView);

        //final MainAdapter adapter = new MainAdapter();
        DataBindingAdapter adapter = new DataBindingAdapter();
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(adapter);

        List<String> items = new ArrayList<>();
        items.add("Simple example");
        adapter.setData(items);

        adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        Toast.makeText(MainActivity.this, "simple", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        adapter.setOnItemLongClickListener(new BaseRecyclerViewAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, int position) {
                Toast.makeText(MainActivity.this, "simple"+position, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }
}
