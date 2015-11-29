package com.yrh.jiaowuwang;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

/**
 * Created by Yrh on 2015/10/30.
 */
public class DropdownSpinner extends Button implements View.OnClickListener,
        AdapterView.OnItemClickListener, OnDismissListener, AdapterView.OnItemSelectedListener {

    private PopupWindow pw;
    private ListView lv;
    private PopupListItemAdapter adapter;
    private List<PopupListItem> list;
    private int selectedPosition;
    private OnClickListener onClickListener = null;
    private OnItemClickListener onItemClickListener;
    private OnItemSelectedListener onItemSelectedListener;
    private int popupHeight, popupWidth;
    // private Drawable myBackgroundDrawable;
    private Drawable listDrawable;
    private float textSize = 20;
    private int textColor = Color.BLACK, backgroundColor = Color.WHITE;
    private String TAG = getClass().getName();
    // private Context context;
    private int visibleItemNo = 1;
    private int leftPadding = 8, rightPadding = 8, topPadding = 4,
            bottomPadding = 4;

    private int minWidth;

    public DropdownSpinner(Context context) {
        super(context);
        init();
    }

    public DropdownSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DropdownSpinner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        listDrawable = getRoundDrawable();
        // myBackgroundDrawable = getRoundDrawable();
        setPadding(8, 8, 8, 8);
        setGravity(Gravity.CENTER);
        setTextSize(textSize);
        setTextColor(textColor);
        setText("");
        super.setOnClickListener(this);
        // setBackgroundDrawable(myBackgroundDrawable);
        popupHeight = 100;
        popupWidth = 150;
    }

    /**
     * Set the item text font color in drop down.
     *
     * @param color
     *            - color code in integer
     */
    public void setItemTextColor(int color) {
        this.textColor = color;
    }

    /**
     * Set the item background color in drop down.
     *
     * @param color
     *            - color code in integer
     */
    public void setItemBackgroundColor(int color) {
        this.backgroundColor = color;
    }

    private Drawable getRoundDrawable() {
        ShapeDrawable drawable = new ShapeDrawable();
        drawable.getPaint().setColor(Color.WHITE);
        // drawable.setShape(new RoundRectShape(r, null, null));
        return drawable;
    }

    private void setAdapter(PopupListItemAdapter adapter) {
        if (adapter != null) {
            this.adapter = adapter;
            if (lv == null)
                lv = new ListView(getContext());
            // lv.setCacheColorHint(0);
            lv.setBackgroundDrawable(listDrawable);
            lv.setAdapter(adapter);
            // lv.setDividerHeight(1);
            lv.setOnItemClickListener(this);
            lv.setOnItemSelectedListener(this);
            lv.setSelector(android.R.color.transparent);
            lv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            selectedPosition = 0;
        } else {
            selectedPosition = -1;
            this.adapter = null;
        }
        // refreshView();
    }

    /**
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setItemPadding(int left, int top, int right, int bottom) {
        this.leftPadding = left;
        this.rightPadding = right;
        this.topPadding = top;
        this.bottomPadding = bottom;
        setListViewHeightBasedOnChildren(lv);

    }

    /**
     * Set the item text size
     *
     * @param size
     *            - size of text
     */
    public void setItemTextSize(int size) {
        this.textSize = size;
        setListViewHeightBasedOnChildren(lv);
    }

    @Override
    public void onClick(View v) {
        if (pw == null || !pw.isShowing()) {
            pw = new PopupWindow(v);
            pw.setContentView(lv);
            pw.setWidth(popupWidth);
            pw.setHeight(popupHeight);
            pw.setBackgroundDrawable(new BitmapDrawable());
            pw.setOutsideTouchable(false);
            pw.setFocusable(true);
            pw.setClippingEnabled(true);
            pw.showAsDropDown(v, v.getLeft(), 0);
            pw.setOnDismissListener(this);
        }
        if (onClickListener != null)
            onClickListener.onClick(v);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        if (pw != null)
            pw.dismiss();
        selectedPosition = arg2;
        // refreshView();
        if (onItemClickListener != null)
            onItemClickListener.onItemClick(arg0, arg1, arg2, arg3);
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                               long arg3) {
        if (onItemSelectedListener != null)
            onItemSelectedListener.onItemSelected(arg0, arg1, arg2, arg3);
    }

    private class PopupListItem {
        private final String text;
        private final int resId;

        public PopupListItem(String text) {
            this.text = text;
            this.resId = -1;
        }

        public PopupListItem(String text, int resId) {
            this.text = text;
            this.resId = resId;
        }

        public String getText() {
            return text;
        }

        public int getResId() {
            return resId;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    private class PopupListItemAdapter extends ArrayAdapter<PopupListItem> {

        public PopupListItemAdapter(Context context, List<PopupListItem> objects) {
            super(context, android.R.layout.activity_list_item, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = newView();
                viewHolder = new ViewHolder();
                viewHolder.tvText = (TextView) convertView
                        .findViewById(android.R.id.text1);
                viewHolder.ivIcon = (ImageView) convertView
                        .findViewById(android.R.id.icon);

                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();

            }
            viewHolder.tvText.setText(getItem(position).getText());
            int resId = getItem(position).getResId();
            if (resId != -1) {
                viewHolder.ivIcon.setVisibility(View.VISIBLE);
                viewHolder.ivIcon.setImageResource(resId);
            } else {
                viewHolder.ivIcon.setVisibility(View.GONE);
            }

            convertView.setTag(viewHolder);

            return convertView;
        }

        private class ViewHolder {
            TextView tvText;
            ImageView ivIcon;
        }

        private View newView() {
            LinearLayout ll_parent = new LinearLayout(getContext());
            ll_parent.setGravity(Gravity.CENTER);
            ll_parent.setMinimumWidth(minWidth);
            ll_parent.setOrientation(LinearLayout.HORIZONTAL);

            ImageView ivIcon = new ImageView(getContext());
            ivIcon.setId(android.R.id.icon);
            ll_parent.addView(ivIcon);

            TextView tvText = new TextView(getContext());
            tvText.setTextColor(textColor);
            tvText.setTextSize(textSize);
            tvText.setMaxLines(1);
            tvText.setEllipsize(TruncateAt.MARQUEE);
            tvText.setBackgroundColor(backgroundColor);
//            tvText.setBackgroundResource(R.drawable.spinner_item_boder);
            tvText.setMarqueeRepeatLimit(-1);
            tvText.setId(android.R.id.text1);
            tvText.setGravity(Gravity.CENTER);

            ll_parent.addView(tvText);
            ll_parent.setPadding(leftPadding, topPadding, rightPadding,
                    bottomPadding);
//            ll_parent.setBackgroundColor(backgroundColor);

            ///////////////////////////////////////
            ll_parent.setGravity(Gravity.CENTER);

            return ll_parent;
        }
    }

    /**
     * Set the height of DropDown spinner equal to number of visible rows
     *
     * @param no
     *            - number (Integer) of visible item row
     */
    public void setVisibleItemNo(int no) {
        this.visibleItemNo = no;
        setListViewHeightBasedOnChildren(lv);
    }

    /**
     * Add the single string item in spinner
     *
     * @param item
     *            - String add in spinner
     */
    public void addItem(String item) {
        if (item == null)
            throw new NullPointerException("Item is null.");

        if (list == null) {
            list = new ArrayList<DropdownSpinner.PopupListItem>();
            list.clear();
            adapter = new PopupListItemAdapter(getContext(), list);
            setAdapter(adapter);
        }
        list.add(new PopupListItem(item));
        adapter.notifyDataSetChanged();

    }

    /**
     * Add the single string item and image in spinner
     *
     * @param item
     *            - String add in spinner
     * @param resId
     *            - image (ResId) add in spinner
     */
    public void addItem(String item, int resId) {
        // if (item == null)
        // throw new NullPointerException("Item is null.");
        if (list == null) {
            list = new ArrayList<DropdownSpinner.PopupListItem>();
            list.clear();
            adapter = new PopupListItemAdapter(getContext(), list);
            setAdapter(adapter);
        }
        if (item != null && item.length() > 0 && resId != -1) {
            list.add(new PopupListItem(item, resId));
        } else if (item != null && item.length() > 0
                && (resId == -1 || resId == 0)) {
            list.add(new PopupListItem(item, -1));
        } else if ((item == null || item.length() == 0) && resId != -1) {
            list.add(new PopupListItem("", resId));
        } else {
            throw new InvalidParameterException("Invalid Parameter");
        }
        // list.add(new PopupListItem(item));
        adapter.notifyDataSetChanged();

    }

    /**
     * Set the string array in spinner
     *
     * @param arr
     *            - array of string
     */
    public void setItems(String[] arr) {
        if (arr == null)
            throw new NullPointerException("Items Array is null.");
        if (list == null)
            list = new ArrayList<DropdownSpinner.PopupListItem>();
        list.clear();
        for (String text : arr) {
            list.add(new PopupListItem(text));
        }
        adapter = new PopupListItemAdapter(getContext(), list);
        setAdapter(adapter);
        // refreshView();
    }

    /**
     *
     * Set the string array & image array in list. size of string array and image array
     * required equal.
     *
     * @param arr
     *            - array of string which is set in spinner
     * @param ico
     *            - array of image which is set in spinner
     * @throws SizeNotMatchException
     *             - throw exception if string array & image array size not
     *             match or equal
     */
    public void setItems(String[] arr, int[] ico) throws SizeNotMatchException {
        if (ico == null) {
            setItems(arr);
        } else {

            if (arr.length == ico.length) {

                if (list == null)
                    list = new ArrayList<DropdownSpinner.PopupListItem>();
                list.clear();
                for (int i = 0; i < arr.length; i++) {
                    if (i < ico.length) {
                        list.add(new PopupListItem(arr[i], ico[i]));
                    } else {
                        list.add(new PopupListItem(arr[i]));
                    }
                }
                adapter = new PopupListItemAdapter(getContext(), list);
                setAdapter(adapter);
            } else {
                throw new SizeNotMatchException("Both array size not match");
            }
        }

        // refreshView();
    }

    @Override
    public void setTextSize(float size) {
        super.setTextSize(size);
        textSize = size;
    }

    @Override
    public void onDismiss() {
        pw = null;
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        if (onItemSelectedListener != null)
            onItemSelectedListener.onNothingSelected(arg0);
    }

    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemSelectedListener(
            OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    /**
     * Return the position of currently selected item within the adapter's data
     * set.
     *
     * @return int position - (starting at 0).
     */
    public int getSelectedPosition() {
        return selectedPosition;
    }

    /**
     * Set the currently selected item.
     *
     * @param selectedPosition
     *            index - (starting at 0) of the data item to be selected.
     */
    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
        // refreshView();
    }

    private void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0, totalWidth = 0;
        // int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(),
        // MeasureSpec.AT_MOST);
        // Log.e(TAG, "desired width:" + desiredWidth);
        for (int i = 0; i < visibleItemNo; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            Log.e(TAG, "item width:" + listItem.getMeasuredWidth());
            if (listItem.getMeasuredWidth() > totalWidth) {
                Log.e(TAG, "max item width:" + listItem.getMeasuredWidth());
                totalWidth = listItem.getMeasuredWidth();
            }
        }
        // Log.e(TAG, "final height:" + totalHeight);
        Log.e(TAG, "final width:" + totalWidth);
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (visibleItemNo - 1));
        // Log.e(TAG, "height:" + params.height);
        popupHeight = params.height;
        popupWidth = totalWidth;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    // @Override
    // protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    // // TODO Auto-generated method stub
    // //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    // //setMeasuredDimension(45, 45);
    // }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        setText("");
    }

    public class SizeNotMatchException extends Exception {
        private static final long serialVersionUID = 1L;

        // Parameterless Constructor
        public SizeNotMatchException() {
        }

        // Constructor that accepts a message
        public SizeNotMatchException(String message) {
            super(message);
        }
    }

    @Override
    public int getMinWidth() {
        return minWidth;
    }

    @Override
    public void setMinWidth(int minWidth) {
        this.minWidth = minWidth;
    }
}
