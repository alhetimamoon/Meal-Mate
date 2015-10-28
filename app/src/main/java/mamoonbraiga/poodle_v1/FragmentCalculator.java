package mamoonbraiga.poodle_v1;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import mamoonbraiga.poodle_v3.R;

/**
 * Created by MamoonBraiga on 2015-10-25.
 */
public class FragmentCalculator extends Fragment implements View.OnClickListener{

        private Button bmrButton;
        private TextView bmrValue;
        private EditText weight, height, age;
        private int bmr = 0;

        public Fragment newInstance(Context context) {
            FragmentCalculator f = new FragmentCalculator();

            return f;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
            ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_calculator, null);
            intit(root);
            return root;
        }
    private void intit(ViewGroup root) {
        bmrButton = (Button) root.findViewById(R.id.bmrButton);
        weight = (EditText) root.findViewById(R.id.eWeight);
        height = (EditText) root.findViewById(R.id.eHeight);
        age = (EditText) root.findViewById(R.id.eAge);
        bmrValue = (TextView) root.findViewById(R.id.bmrValue);
        bmrButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bmrButton:
                bmr = (int) (66 + (13.7 * Integer.parseInt(weight.getText().toString())) + (5 * Integer.parseInt(height.getText().toString())) - (6.8 * Integer.parseInt(age.getText().toString())));
        }
        bmrValue.setText(Integer.toString(bmr));
    }
}
