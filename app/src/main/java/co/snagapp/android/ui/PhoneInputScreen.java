package co.snagapp.android.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import co.snagapp.android.R;

public class PhoneInputScreen extends Fragment implements View.OnClickListener{

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
    private TextInputLayout numberInputHint;
    private ImageButton backspace;

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
        one.setOnClickListener(this);

        two = view.findViewById(R.id.two);
        setButtonText(two, 2, "ABC");
        two.setOnClickListener(this);

        three = view.findViewById(R.id.three);
        setButtonText(three, 3, "DEF");
        three.setOnClickListener(this);

        four = view.findViewById(R.id.four);
        setButtonText(four, 4, "GHI");
        four.setOnClickListener(this);

        five = view.findViewById(R.id.five);
        setButtonText(five, 5, "JKL");
        five.setOnClickListener(this);

        six = view.findViewById(R.id.six);
        setButtonText(six, 6, "MNO");
        six.setOnClickListener(this);

        seven = view.findViewById(R.id.seven);
        setButtonText(seven, 7, "PQRS");
        seven.setOnClickListener(this);

        eight = view.findViewById(R.id.eight);
        setButtonText(eight, 8, "TUV");
        eight.setOnClickListener(this);

        nine = view.findViewById(R.id.nine);
        setButtonText(nine, 9, "WXYZ");
        nine.setOnClickListener(this);

        star = view.findViewById(R.id.star);
        setButtonText(star, "", "*");
        star.setVisibility(View.INVISIBLE);

        zero = view.findViewById(R.id.zero);
        setButtonText(zero, 0, "+");
        zero.setOnClickListener(this);

        hash = view.findViewById(R.id.hash);
        setButtonText(hash, "", "#");
        hash.setVisibility(View.INVISIBLE);

        backspace = (ImageButton) getView().findViewById(R.id.backspace_button);
        numberInputHint = (TextInputLayout) getView().findViewById(R.id.number_input_hint);

        etNumberInput = (EditText) getView().findViewById(R.id.editText);
        etNumberInput.setInputType(InputType.TYPE_NULL);
        etNumberInput.setOnClickListener(null);

        backspace.setOnClickListener(this);

        numberInputHint.setHint(getString(R.string.number));

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
    private boolean isPhoneNumberValid(String number){
        return !TextUtils.isEmpty(number);
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
            if (isPhoneNumberValid(number)){
                mListener.onInputGiven(number);
            }else{
                numberInputHint.setError(getString(R.string.invalid_number));
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onInputGiven(String number);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id){
            case R.id.fab_add_number:{
                onButtonPressed();
                break;
            }
            case R.id.backspace_button:{
                int length = etNumberInput.getText().length();
                if (length > 0) {
                    etNumberInput.getText().delete(length - 1, length);
                }
                break;
            }
            default:{
                TextView tvNumber = (TextView) v.findViewById(R.id.mainText);
                String enteredNumber = tvNumber.getText().toString();
                etNumberInput.append(enteredNumber);
            }
        }
    }
}
