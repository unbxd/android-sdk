package com.unbxdexample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by training on 10/03/16.
 */
public class FilterActivity extends AppCompatActivity {
    private SetFilterList setFilterList;
    private ArrayList<String> filterGroupList = new ArrayList<String>();
    private ArrayList<Object> filterChildList = new ArrayList<Object>();
    private HashMap<String, String> filterMulti = new HashMap<String, String>();
    ArrayList<String> isCheckedStatus = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        View customView = getLayoutInflater().inflate(R.layout.tool_bar_view, toolbar, false);
        toolbar.addView(customView);
        toolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.back_arrow, this.getTheme()));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setGroupData();
        setChildGroupData();
        ExpandableListView filterList = (ExpandableListView) findViewById(R.id.list_view_filter);
        setFilterList = new SetFilterList(FilterActivity.this, filterGroupList, filterChildList);
        filterList.setAdapter(setFilterList);
        Button filterApply = (Button) findViewById(R.id.btn_apply);
        filterApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(FilterActivity.this, MainActivity.class);
                n.putExtra("map", filterMulti);
                startActivity(n);
            }
        });
    }

    //    ---------------------------------------------------------  Set Static Group Filter ---------------------------------------------------------------------------
    public void setGroupData() {
        filterGroupList.add("color");
        filterGroupList.add("gender");
    }

    //    ---------------------------------------------------------  Set Static Child Filter ---------------------------------------------------------------------------
    public void setChildGroupData() {
        ArrayList<String> child = new ArrayList<String>();
        child.add("Navy");
        child.add("Blue");
        child.add("Heather Grey");
        child.add("Grey");
        filterChildList.add(child);

        child = new ArrayList<String>();
        child.add("Men");
        child.add("Women");
        filterChildList.add(child);
    }

    //    ---------------------------------------------------------  Set Expandable FilterList --------------------------------------------------------------------------
    public class SetFilterList extends BaseExpandableListAdapter {
        Context context;
        ArrayList<String> originalArrayList, tempChild, filterTempChild;
        ArrayList<Object> originalChildArrayList;
        public LayoutInflater minflater = (LayoutInflater) FilterActivity.this.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        public SetFilterList(Context context, ArrayList<String> arrayList, ArrayList<Object> originalChildArrayList) {
            this.originalArrayList = arrayList;
            this.originalChildArrayList = originalChildArrayList;
            this.context = context;
        }

        @Override
        public int getGroupCount() {
            return originalArrayList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return ((ArrayList<String>) originalChildArrayList.get(groupPosition)).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return null;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return null;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = minflater.inflate(R.layout.filter_list_row, null);
            }
            TextView txt_filter = (TextView) convertView.findViewById(R.id.text_list_view_filter);
            txt_filter.setText(originalArrayList.get(groupPosition));
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            tempChild = (ArrayList<String>) originalChildArrayList.get(groupPosition);
            TextView text = null;

            if (convertView == null) {
                convertView = minflater.inflate(R.layout.child_row, null);
            }
            text = (TextView) convertView.findViewById(R.id.child_text);
            CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox_child);
            text.setText(tempChild.get(childPosition));
            checkBox.setOnCheckedChangeListener(new CheckchangeListener(groupPosition, childPosition));
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(FilterActivity.this, tempChild.get(childPosition),
                            Toast.LENGTH_SHORT).show();
                }
            });
            return convertView;
        }

        class CheckchangeListener implements CompoundButton.OnCheckedChangeListener {
            private int groupPos, childPos;

            public CheckchangeListener(int groupPos, int childPos) {
                // TODO Auto-generated constructor stub
                this.groupPos = groupPos;
                this.childPos = childPos;

            }

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    String field_name = originalArrayList.get(groupPos);
                    filterTempChild = (ArrayList<String>) originalChildArrayList.get(groupPos);
                    String field_value = filterTempChild.get(childPos);
                    filterMulti.put(field_name, field_value);

                } else {
                    String field_name_removal = originalArrayList.get(groupPos);
                    filterMulti.remove(field_name_removal);
                }

            }
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }
}
