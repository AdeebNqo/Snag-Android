package co.snagapp.android;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.Collection;

/**
 * Created by zola on 2015/07/20.
 */
public class SpamNumbersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public SpamNumbersAdapter(Collection spamNumbers, SpamNumberDetailsItemOnClickListener spamNumberDetailsItemOnClickListener) {
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public interface SpamNumberDetailsItemOnClickListener{
        void onClick(SpamNumber spamNumber);
    }
}
