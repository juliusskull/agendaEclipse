package aguasdelnorte.com.ar.agenda2.ui;

import aguasdelnorte.com.ar.agenda2.R;
import android.os.Bundle;
import android.view.MenuItem;
import net.sf.andpdf.pdfviewer.PdfViewerActivity;

public class MyPdfViewerActivity extends PdfViewerActivity {

	private float STARTZOOM;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    // TODO Auto-generated method stub
	    setToolbar();
	    STARTZOOM = 0.435f;
	  
	}

	public int getPreviousPageImageResource() { return R.drawable.left_arrow; }
    public int getNextPageImageResource() { return R.drawable.right_arrow; }
    public int getZoomInImageResource() { return R.drawable.zoom_in; }
    public int getZoomOutImageResource() { return R.drawable.zoom_out; }
    public int getPdfPasswordLayoutResource() { return R.layout.pdf_file_password; }
    public int getPdfPageNumberResource() { return R.layout.dialog_pagenumber; }
    public int getPdfPasswordEditField() { return R.id.etPassword; }
    public int getPdfPasswordOkButton() { return R.id.btOK; }
    public int getPdfPasswordExitButton() { return R.id.btExit; }
    public int getPdfPageNumberEditField() { return R.id.pagenum_edit; }
    
    private void setToolbar() {
        
    	if (getActionBar() != null)
            getActionBar().setDisplayHomeAsUpEnabled(true);
          getActionBar().setHomeButtonEnabled(true);         
       
         
      }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
        	finish();
            
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}
