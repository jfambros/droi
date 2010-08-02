package utils;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class MapaOverlayOptimizado extends ItemizedOverlay<OverlayItem>{
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;
	
	public MapaOverlayOptimizado(Drawable defaultMarker){
		  super(boundCenterBottom(defaultMarker));
	}
	
	public MapaOverlayOptimizado(Drawable defaultMarker, Context context) {
		  super(defaultMarker);
		  mContext = context;
	}

	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}
	
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		return mOverlays.get(i);
	}

	public int size() {
		// TODO Auto-generated method stub
		return mOverlays.size();
	}
	
	protected boolean onTap(int index) {
		  OverlayItem item = mOverlays.get(index);
		  Toast.makeText(mContext, item.getSnippet(), Toast.LENGTH_SHORT).show();
		  return true;
		}

}
