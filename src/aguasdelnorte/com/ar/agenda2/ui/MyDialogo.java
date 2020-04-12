package aguasdelnorte.com.ar.agenda2.ui;


import aguasdelnorte.com.ar.agenda2.R;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class MyDialogo {
	public static android.app.AlertDialog.Builder create(final Context context, final LayoutInflater layoutInflater, final String title, final String content) {
	    View view = layoutInflater.inflate(R.layout.dialogo, null);
	    TextView txt1=(TextView)view.findViewById(R.id.textViewTitleDialog);
	    TextView txt2=(TextView)view.findViewById(R.id.textViewContentDialog);
	    txt1.setText(title);
	    txt2.setText(content);
	    return new AlertDialog.Builder(context).setView(view);
	}

}
