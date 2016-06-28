package ph.kita.devsquare.com.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ph.kita.devsquare.com.kita.R;

/**
 * Created by paul on 6/8/15.
 */
public class ConfirmationDialog extends DialogFragment {

    private static final String TAG = ConfirmationDialog.class.getSimpleName();
    private Dialog alertDialog;

    private static final String TITLE = "TITLE";

    private OnDialogConfirmationListener onDialogConfirmationListener;

    public interface OnDialogConfirmationListener{
        public void onOK();
//        public void onCancel();
    }

    public static ConfirmationDialog newInstance(String title) {
        ConfirmationDialog d = new ConfirmationDialog();
        //add item
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        d.setArguments(args);
        return d;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final String title = getArguments().getString(TITLE);
        alertDialog = new Dialog(getActivity());
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.dialog_confirmation);
        alertDialog.setTitle(title);
        ((TextView) alertDialog.findViewById(R.id.title)).setText(title);
        ((Button) alertDialog.findViewById(R.id.ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDialogConfirmationListener.onOK();
                alertDialog.dismiss();
            }
        });

        ((Button) alertDialog.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onDialogConfirmationListener.onCancel();
                alertDialog.dismiss();
            }
        });

        return alertDialog;
    }

    public void setOnListener(OnDialogConfirmationListener onDialogConfirmationListener) throws ClassCastException{

        try{
            this.onDialogConfirmationListener = onDialogConfirmationListener;
        }catch (ClassCastException e){
            throw new ClassCastException();
        }

    }

}
