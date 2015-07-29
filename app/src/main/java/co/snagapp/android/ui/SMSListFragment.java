package co.snagapp.android.ui;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.snagapp.android.R;
import co.snagapp.android.Sms;
import co.snagapp.android.SmsReaderImpl;
import co.snagapp.android.SpamNumbersAdapter;
import co.snagapp.android.worker.SmsReader;

public class SMSListFragment extends Fragment {

    private SmsReader smsReader;
    private List<Sms> smses;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SpamNumbersAdapter.SpamNumberDetailsItemOnClickListener mListener;

    private TextView emptyStatusText;

    public SMSListFragment() {
        // Required empty public constructor
        smsReader = new SmsReaderImpl();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_smslist, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (SpamNumbersAdapter.SpamNumberDetailsItemOnClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        emptyStatusText = (TextView) getView().findViewById(R.id.textView6);

        smses =  smsReader.getAllSms(getActivity());

        mRecyclerView = (RecyclerView) getView().findViewById(R.id.sms_list);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new SpamNumbersAdapter(smses, mListener);
        mRecyclerView.setAdapter(mAdapter);

        if (smses.isEmpty()){
            emptyStatusText.setVisibility(View.VISIBLE);
        }else{
            emptyStatusText.setVisibility(View.INVISIBLE);
        }
    }
}
