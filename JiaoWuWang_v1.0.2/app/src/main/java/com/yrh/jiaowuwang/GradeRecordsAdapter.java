package com.yrh.jiaowuwang;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yrh.jiaowuwang.model.GradeRecord;

import java.util.List;

/**
 * Created by Yrh on 2015/10/27.
 * 记录成绩的自定义 Adapter 类
 */
public class GradeRecordsAdapter extends RecyclerView.Adapter<GradeRecordsViewHolder> {

    private LayoutInflater mInflater;
    private Context mContext;
    private List<GradeRecord> mDatas;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public GradeRecordsAdapter(Context context, List<GradeRecord> datas) {
        this.mContext = context;
        this.mDatas = datas;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public GradeRecordsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        // Item 的View
        View view = mInflater.inflate(R.layout.recycleview_item_show_grade, viewGroup, false);
        return new GradeRecordsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GradeRecordsViewHolder gradeRecordsViewHolder, final int pos) {
        GradeRecord gr = mDatas.get(pos);
        gradeRecordsViewHolder.tvCourseName.setText(gr.getCourseName());
        gradeRecordsViewHolder.tvScore.setText(gr.getScore());
        gradeRecordsViewHolder.tvCourseCredit.setText(String.valueOf(gr.getCourseCredit()));
        gradeRecordsViewHolder.tvCoursePoint.setText(String.valueOf(gr.getCoursePoint()));

        if (mOnItemClickListener != null) {

            gradeRecordsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(gradeRecordsViewHolder.itemView, pos);
                }
            });

            gradeRecordsViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onItemLongClick(gradeRecordsViewHolder.itemView, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void addData(GradeRecord data, int pos) {
        mDatas.add(data);
        notifyItemInserted(pos);
    }

    public void addData(GradeRecord data) {
        mDatas.add(data);
        notifyItemInserted(mDatas.indexOf(data));
    }

    public void deleteData(int pos) {
        mDatas.remove(pos);
        notifyItemRemoved(pos);
    }

    public void changeData(List<GradeRecord> newData) {
        mDatas = newData;
        notifyDataSetChanged();
    }
}


class GradeRecordsViewHolder extends RecyclerView.ViewHolder {

    TextView tvCourseName;
    TextView tvScore;
    TextView tvCourseCredit;
    TextView tvCoursePoint;

    public GradeRecordsViewHolder(View itemView) {
        super(itemView);

        tvCourseName = (TextView) itemView.findViewById(R.id.id_item_tvCourseName);
        tvScore = (TextView) itemView.findViewById(R.id.id_item_tvScore);
        tvCourseCredit = (TextView) itemView.findViewById(R.id.id_item_tvCourseCredit);
        tvCoursePoint = (TextView) itemView.findViewById(R.id.id_item_tvCoursePoint);
    }
}
