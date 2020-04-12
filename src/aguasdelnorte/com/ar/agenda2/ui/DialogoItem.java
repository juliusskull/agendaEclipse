package aguasdelnorte.com.ar.agenda2.ui;

import aguasdelnorte.com.ar.agenda2.R;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class DialogoItem {

		public static android.app.AlertDialog.Builder create(final Context context, final LayoutInflater layoutInflater, final ObjectView o ) {
		    View view = layoutInflater.inflate(R.layout.dialogo_ppal, null);
		    TextView txt1=(TextView)view.findViewById(R.id.txtDialogotitulo);
		    TextView txt2=(TextView)view.findViewById(R.id.txtDialogoEtiqueta);
		    TextView txt3=(TextView)view.findViewById(R.id.txtDialogoFecha);
		    TextView txt4=(TextView)view.findViewById(R.id.txtDialogoDescripcion);
		    txt1.setText(o.getTitulo());
		    txt2.setText(o.getEtiqueta());
		    txt3.setText(o.getFecha());
		    txt4.setText(o.getDescripcion());
		    return new AlertDialog.Builder(context).setView(view);
		}
}
