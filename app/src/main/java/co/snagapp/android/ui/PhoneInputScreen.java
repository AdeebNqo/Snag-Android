package co.snagapp.android.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import co.snagapp.android.R;

public class PhoneInputScreen extends Fragment {

    private OnFragmentInteractionListener mListener;

    private View one;
    private View two;
    private View three;
    private View four;
    private View five;
    private View six;
    private View seven;
    private View eight;
    private View nine;
    private View star;
    private View zero;
    private View hash;

    private EditText etNumberInput;
    private FloatingActionButton fabAddNumber;

    public static PhoneInputScreen newInstance() {
        PhoneInputScreen fragment = new PhoneInputScreen();
        return fragment;
    }

    public PhoneInputScreen() {
        // Required empty public constructor
    }

    public void loadItemsFromView(View view){
        one = view.findViewById(R.id.one);
        setButtonText(one, 1, "");

        two = view.findViewById(R.id.two);
        setButtonText(two, 2, "ABC");

        three = view.findViewById(R.id.three);
        setButtonText(three, 3, "DEF");

        four = view.findViewById(R.id.four);
        setButtonText(four, 4, "GHI");

        five = view.findViewById(R.id.five);
        setButtonText(five, 5, "JKL");

        six = view.findViewById(R.id.six);
        setButtonText(six, 6, "MNO");

        seven = view.findViewById(R.id.seven);
        setButtonText(seven, 7, "PQRS");

        eight = view.findViewById(R.id.eight);
        setButtonText(eight, 8, "TUV");

        nine = view.findViewById(R.id.nine);
        setButtonText(nine, 9, "WXYZ");

        star = view.findViewById(R.id.star);
        setButtonText(star, "", "*");
        star.setVisibility(View.INVISIBLE);

        zero = view.findViewById(R.id.zero);
        setButtonText(zero, 0, "+");

        hash = view.findViewById(R.id.hash);
        setButtonText(hash, "", "#");
        hash.setVisibility(View.INVISIBLE);


        etNumberInput = (EditText) getView().findViewById(R.id.editText);
        fabAddNumber = (FloatingActionButton) getView().findViewById(R.id.fab_add_number);
        fabAddNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed();
            }
        });
    }

    private void setButtonText(View button, int mainText, String subText){
        setButtonText(button, String.valueOf(mainText), subText);
    }
    private void setButtonText(View button, String mainText, String subText){
        TextView mainTextView = (TextView) button.findViewById(R.id.mainText);
        if (mainTextView != null){
            mainTextView.setText(mainText);
        }
        TextView subTextView = (TextView) button.findViewById(R.id.subText);
        if (subTextView != null){
            subTextView.setText(subText);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phone_input_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadItemsFromView(view);
    }

    public void onButtonPressed() {
        if (mListener != null) {
            String number = etNumberInput.getText().toString();
            //todo: validate num perhaps?
            mListener.onInputGiven(number);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        /*try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onInputGiven(String number);
    }

}
