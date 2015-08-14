package co.snagapp.android.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Collections;
import java.util.List;
import co.snagapp.android.R;
import co.snagapp.android.model.Sms;

/**
 * Created by zola on 2015/07/20.
 */
public class SpamNumbersAdapter extends RecyclerView.Adapter<SpamNumbersAdapter.ViewHolder>{

    private List<Sms> smsList;
    private View.OnClickListener spamNumberDetailsItemOnClickListener;

    public SpamNumbersAdapter(List<Sms> smses, View.OnClickListener spamNumberDetailsItemOnClickListener) {
        this.smsList = smses;
        this.spamNumberDetailsItemOnClickListener = spamNumberDetailsItemOnClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sms_list_item, parent, false);
        v.setOnClickListener(spamNumberDetailsItemOnClickListener);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Sms sms = smsList.get(position);

        holder.number.setText(sms.getId());
        holder.body.setText(sms.getMsg());
        if (TextUtils.isEmpty(sms.getMsg())){
            holder.body.setVisibility(View.GONE);
        }else{
            holder.body.setVisibility(View.VISIBLE);
        }
    }

    public void swapItems(int from, int to){
        Collections.swap(smsList, from, to);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return smsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView number;
        public TextView body;
        public ViewHolder(View v) {
            super(v);
            number = (TextView) v.findViewById(R.id.textView4);
            body = (TextView) v.findViewById(R.id.textView5);
        }
    }

}
