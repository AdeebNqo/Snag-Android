package co.snagapp.android;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collection;
import java.util.List;

import me.grantland.widget.AutofitTextView;

/**
 * Created by zola on 2015/07/20.
 */
public class SpamNumbersAdapter extends RecyclerView.Adapter<SpamNumbersAdapter.ViewHolder>{

    private List<Sms> smsList;
    private SpamNumberDetailsItemOnClickListener spamNumberDetailsItemOnClickListener;

    public SpamNumbersAdapter(List<Sms> smses, SpamNumberDetailsItemOnClickListener spamNumberDetailsItemOnClickListener) {
        this.smsList = smses;
        this.spamNumberDetailsItemOnClickListener = spamNumberDetailsItemOnClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sms_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Sms sms = smsList.get(position);

        holder.number.setText(sms.getId());
        holder.body.setText(sms.getMsg());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public interface SpamNumberDetailsItemOnClickListener{
        void onClick(Sms sms);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView number;
        public AutofitTextView body;
        public ViewHolder(View v) {
            super(v);
            number = (TextView) v.findViewById(R.id.textView4);
            body = (AutofitTextView) v.findViewById(R.id.textView5);
        }
    }
}
