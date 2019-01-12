package com.example.doldrum.tortoiseandrabbit.me.personandteam;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.doldrum.tortoiseandrabbit.R;
import com.example.doldrum.tortoiseandrabbit.utils.ToastUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateSelectActivity extends AppCompatActivity {
    private EditText startTime,endTime;
  /*  private boolean isStartOnclick = true;
    private boolean isEndOnclick = false;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//因为不是所有的系统都可以设置颜色的，在4.4以下就不可以。。有的说4.1，所以在设置的时候要检查一下系统版本是否是4.1以上
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        setContentView(R.layout.activity_date_select);
        startTime = findViewById(R.id.startTime);
        endTime = findViewById(R.id.endTime);

        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //时间选择器
        final TimePickerView pvTime = new TimePickerBuilder(DateSelectActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
               // Toast.makeText(DateSelectActivity.this,  dateFormat.format(date), Toast.LENGTH_SHORT).show();
                startTime.setText(dateFormat.format(date));
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .build();
        //时间选择器
        final TimePickerView pvTime1 = new TimePickerBuilder(DateSelectActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                //Toast.makeText(DateSelectActivity.this, date.toString(), Toast.LENGTH_SHORT).show();
                endTime.setText(dateFormat.format(date));
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .build();
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDatePickerDialog(DateSelectActivity.this,0,startTime,Calendar.getInstance());
          /*      isStartOnclick = true;
                isEndOnclick = false;*/
                pvTime.show();


            }
        });
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pvTime1.show();
               /* isEndOnclick = true;
                isStartOnclick = false;*/
                //showDatePickerDialog(DateSelectActivity.this,0,endTime,Calendar.getInstance());
            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.tv_select).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String strStartTime = startTime.getText().toString();
                String strEndTime = endTime.getText().toString();
                if (strEndTime.compareToIgnoreCase(strStartTime) >= 0) {
                    intent.putExtra("startTime", strStartTime);
                    intent.putExtra("endTime", strEndTime);
                    setResult(2, intent);
                    finish(); //结束当前的activity的生命周期
                } else {
                    ToastUtils.centerShow(DateSelectActivity.this, "结束时间必须大于等于开始时间");
                }

            }
        });
        }
    }
        /*final Calendar calendar = Calendar.getInstance();

        final WheelView month = (WheelView) findViewById(R.id.month);
        final WheelView year = (WheelView) findViewById(R.id.year);
        final WheelView day = (WheelView) findViewById(R.id.day);



        OnWheelChangedListener listener = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updateDays(year, month, day);
                int yearItem = year.getCurrentItem();
                int monthItem = month.getCurrentItem() +1;
                int dayItem = day.getCurrentItem()+1;
                Log.d("dataSelect", "yearItem: "+yearItem);
                Log.d("dataSelect", "monthItem: "+monthItem);
                Log.d("dataSelect", "dayItem: "+dayItem);
                int yearnum = calendar.get(Calendar.YEAR);
                int monthnum = calendar.get(Calendar.MONTH);
                int daynum = calendar.get(Calendar.DAY_OF_MONTH);

                if (isStartOnclick){
                    startTime.setText((yearnum+yearItem -10)+"-"+monthItem+"-"+dayItem);
                }else {
                    endTime.setText((yearnum+yearItem -10)+"-"+monthItem+"-"+dayItem);
                }

            }
        };

        // month
        int curMonth = calendar.get(Calendar.MONTH);
        String months[] = new String[] {"1月", "2月", "3月", "4月", "5月",
                "6月", "7月", "8月", "9月", "10月", "11月", "12月"};
        month.setViewAdapter(new DateSelectActivity.DateArrayAdapter(this, months, curMonth));
        month.setCurrentItem(curMonth);
        month.addChangingListener(listener);

        // year
        int curYear = calendar.get(Calendar.YEAR);
        year.setViewAdapter(new DateSelectActivity.DateNumericAdapter(this, curYear-10, curYear + 10, 0));
        year.setCurrentItem(10);
        year.addChangingListener(listener);

        //day
        updateDays(year, month, day);
        day.addChangingListener(listener);
        day.setCurrentItem(calendar.get(Calendar.DAY_OF_MONTH) - 1);
    }

    *//**
     * Updates day wheel. Sets max days according to selected month and year
     *//*
    void updateDays(WheelView year, WheelView month, WheelView day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + year.getCurrentItem());
        calendar.set(Calendar.MONTH, month.getCurrentItem());

        int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        day.setViewAdapter(new DateSelectActivity.DayNumericAdapter(this, 1, maxDays, calendar.get(Calendar.DAY_OF_MONTH) - 1));
        int curDay = Math.min(maxDays, day.getCurrentItem() + 1);
        day.setCurrentItem(curDay - 1, true);
    }

    *//**
     * Adapter for numeric wheels. Highlights the current value.
     *//*
    private class DateNumericAdapter extends NumericWheelAdapter {
        // Index of current item
        int currentItem;
        // Index of item to be highlighted
        int currentValue;

        *//**
         * Constructor
         *//*
        public DateNumericAdapter(Context context, int minValue, int maxValue, int current) {
            super(context, minValue, maxValue);
            this.currentValue = current;
            setTextSize(16);
        }

        @Override
        protected void configureTextView(TextView view) {
            super.configureTextView(view);
         *//*   if (currentItem == currentValue) {
                view.setTextColor(0xFF0000F0);
            }*//*
            view.setTypeface(Typeface.SANS_SERIF);
        }

        @Override
        public View getItem(int index, View convertView, ViewGroup parent) {
            currentItem = index;
            if (index >= 0 && index < getItemsCount()) {
                if (convertView == null) {
                    convertView = getView(itemResourceId, parent);
                }
                TextView textView = getTextView(convertView, itemTextResourceId);
                if (textView != null) {
                    CharSequence text = getItemText(index);
                    if (text == null) {
                        text = "";
                    }
                    textView.setText(text+"年");

                    if (itemResourceId == TEXT_VIEW_ITEM_RESOURCE) {
                        configureTextView(textView);
                    }
                }
                return convertView;
            }
            return null;
        }
    }


    *//**
     * Adapter for numeric wheels. Highlights the current value.
     *//*
    private class DayNumericAdapter extends NumericWheelAdapter {
        // Index of current item
        int currentItem;
        // Index of item to be highlighted
        int currentValue;

        *//**
         * Constructor
         *//*
        public DayNumericAdapter(Context context, int minValue, int maxValue, int current) {
            super(context, minValue, maxValue);
            this.currentValue = current;
            setTextSize(16);
        }

        @Override
        protected void configureTextView(TextView view) {
            super.configureTextView(view);
           *//* if (currentItem == currentValue) {
                view.setTextColor(0xFF0000F0);
            }*//*
            view.setTypeface(Typeface.SANS_SERIF);
        }

        @Override
        public View getItem(int index, View convertView, ViewGroup parent) {
            currentItem = index;
            if (index >= 0 && index < getItemsCount()) {
                if (convertView == null) {
                    convertView = getView(itemResourceId, parent);
                }
                TextView textView = getTextView(convertView, itemTextResourceId);
                if (textView != null) {
                    CharSequence text = getItemText(index);
                    if (text == null) {
                        text = "";
                    }
                    textView.setText(text+"日");

                    if (itemResourceId == TEXT_VIEW_ITEM_RESOURCE) {
                        configureTextView(textView);
                    }
                }
                return convertView;
            }
            return null;
        }
    }





    *//**
     * Adapter for string based wheel. Highlights the current value.
     *//*
    private class DateArrayAdapter extends ArrayWheelAdapter<String> {
        // Index of current item
        int currentItem;
        // Index of item to be highlighted
        int currentValue;

        *//**
         * Constructor
         *//*
        public DateArrayAdapter(Context context, String[] items, int current) {
            super(context, items);
            this.currentValue = current;
            setTextSize(16);
        }

        @Override
        protected void configureTextView(TextView view) {
            super.configureTextView(view);
         *//*   if (currentItem == currentValue) {
                view.setTextColor(0xFF0000F0);
            }*//*
            view.setTypeface(Typeface.SANS_SERIF);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            currentItem = index;
            return super.getItem(index, cachedView, parent);
        }



    }

    *//**
     * 日期选择
     * @param activity
     * @param themeResId
     * @param tv
     * @param calendar
     *//*
    public static void showDatePickerDialog(Activity activity, int themeResId, final TextView tv, Calendar calendar) {
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(activity , themeResId,new DatePickerDialog.OnDateSetListener() {
            // 绑定监听器(How the parent is notified that the date is set.)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 此处得到选择的时间，可以进行你想要的操作
                tv.setText(year + "-" + (monthOfYear+1)+ "-" + dayOfMonth );

            }
        }
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                ,calendar.get(Calendar.MONTH)
                ,calendar.get(Calendar.DAY_OF_MONTH)).show();
    }*/

