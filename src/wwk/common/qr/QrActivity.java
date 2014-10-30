package wwk.common.qr;

import wwk.common.widget.CustomDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.qr_codescan.MipcaActivityCapture;
import com.mining.app.zxing.decoding.CaptureActivityHandler;

public class QrActivity extends MipcaActivityCapture
{	
	protected void onResultHandler(String resultString, Bitmap bitmap)
	{
		if(TextUtils.isEmpty(resultString))
		{
			Toast.makeText(QrActivity.this,R.string.scanFail, Toast.LENGTH_SHORT).show();
			return;
		}
		
		//viewfinderView.stopAnimate();
		
		
		CustomDialog dlg=new CustomDialog(this);
		dlg.setTitle(getString(R.string.scanSuccess));
		dlg.setMessage(resultString);
		dlg.addButton(getString(android.R.string.copy));
		dlg.addButton(getString(android.R.string.cancel));
		
		if(resultString.startsWith("http"))
		{
			dlg.addButton(getString(R.string.openLink));
		}
		
		int ret=dlg.showWithBlock();
		
		viewfinderView.startAnimate();
		
		switch (ret)
		{
			case 0:
				{
					
//					 当前存在的Android版本:
//					
//					    API(08) – Froyo(2.2)
//					    API(10) – Gingerbread(2.3.x)
//					    API(11) – Honeycomb(3.0)
//					    API(12) – Honeycomb(3.1)
//					    API(13) – Honeycomb(3.2)
//					    API(15) – Ice Cream Sandwich(4.0)
//					    API(16) – Jelly Bean(4.1.x)
//					    API(17) – Jelly Bean(4.2.x)
//					    API(18) – Jelly Bean(4.3)
//					    API(19) – KitKat(4.4)
					
					int SDK_INT=Build.VERSION.SDK_INT;
					int HONEYCOMB=Build.VERSION_CODES.HONEYCOMB;
					
					if(SDK_INT<HONEYCOMB)
			        {
						android.text.ClipboardManager clipboardManager=(android.text.ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
						clipboardManager.setText(resultString);
			        }
					else
					{
						android.content.ClipboardManager clipboardManager=(android.content.ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
						ClipData clip=clipboardManager.getPrimaryClip();
						clip.addItem(new ClipData.Item(resultString));
						clipboardManager.setPrimaryClip(clip);
					}
				}
				break;
			case 1:
				{
					
				}
				break;
			case 2:
				{
					Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(resultString));
			        //it.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
			        startActivity(it);
				}
				break;
		}
		
		handler = new CaptureActivityHandler(this, decodeFormats,
					characterSet);
					
		
//		Intent resultIntent = new Intent();
//		Bundle bundle = new Bundle();
//		bundle.putString("result", resultString);
//		bundle.putParcelable("bitmap", bitmap);
//		resultIntent.putExtras(bundle);
//		setResult(RESULT_OK, resultIntent);
//		QrActivity.this.finish();
	}
}
